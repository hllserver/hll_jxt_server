package ssm.controller.driverSchool;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ssm.entity.common.RecordsO;
import ssm.entity.common.ResultO;
import ssm.entity.driverSchool.SchoolAdO;
import ssm.entity.driverSchool.SchoolInstructorO;
import ssm.entity.driverSchool.SchoolO;
import ssm.entity.driverSchool.SchoolPicO;
import ssm.entity.user.UserO;
import ssm.service.driverSchoolService.DriverSchoolService;
import ssm.util.CommonUtil;

@Controller
@Transactional
@RequestMapping(value = "/driverSchoolMsg")
public class DriverSchoolMsgAction {
	
	private final String SUCCESS="1";  //操作成功
	private final String FAILD="0";    //操作失败
	@Autowired
	DriverSchoolService driverSchoolService;

	/**
	 * 获取自己驾校信息 liaoyun 2016/3/27
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDriverSchoolMsg", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public SchoolO getDriverSchoolMsg(HttpSession session) {
		String account = CommonUtil.getSchoolAccount(session);
		if (account != null && !account.equals("")) {
			SchoolO schO = driverSchoolService.getDriverSchoolMsg(account);
			if(schO!=null){
				return schO;
			}
		} 
		return null;
	}

	/**
	 * 保存修改的驾校信息数据
	 * @author liaoyun 2016/3/27
	 */
	@ResponseBody
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Map<String, String> save(@RequestBody SchoolO school, HttpSession session) {
		Map<String, String> resultMap = new HashMap<>();
		UserO user = (UserO) session.getAttribute("userInfo");
		String account = CommonUtil.getSchoolAccount(session);
		if (school != null) {
			school.setCreatedBy(user.getAccount());
			school.setLastUpdatedBy(user.getAccount());
			school.trim();
		}
		int isHas = driverSchoolService.isHasDriverSchool(account);//查找该账号是否有驾校
		try{
			if(isHas>0){//如果有，则修改
				driverSchoolService.saveSchoolMsg(school,account);
			}
			else{//如果没有，则添加
				driverSchoolService.insertSchoolMsg(school,account);
			}
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("da", FAILD);
			return resultMap;
		}
		resultMap.put("da", SUCCESS);
		return resultMap;
	}

	/**
	 * 查询 驾校 所属的 教练
	 * @param schoolInstructorO
	 * @param currentPage
	 * @param pageSize
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDriverInstructor/{currentPage}/{pageSize}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResultO<SchoolInstructorO> findDriverInstructor(@RequestBody SchoolInstructorO schoolInstructorO,
			@PathVariable int currentPage, @PathVariable int pageSize, HttpSession session) {
		String account = CommonUtil.getSchoolAccount(session);
		ResultO<SchoolInstructorO> list = driverSchoolService.getDriverSchoolList(schoolInstructorO, currentPage,
				pageSize, account);
		return list;
	}

	/**
	 * 增、删、改 驾校的教练 liaoyun 2016-3-27
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveInstructor", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Map<String, Object> saveInstructor(@RequestBody RecordsO<SchoolInstructorO> recordsO, HttpSession session) {
		Map<String, Object> resultMap = new HashMap<>();
		UserO user = (UserO) session.getAttribute("userInfo");
		String account = CommonUtil.getSchoolAccount(session);
		List<SchoolInstructorO> insertedRecords = recordsO.getInsertedRecords();
		List<SchoolInstructorO> uptatedRecords = recordsO.getUpdatedRecords();
		List<SchoolInstructorO> deletedRecords = recordsO.getDeletedRecords();
		List<SchoolInstructorO> insertedO =new ArrayList<>();
		List<SchoolInstructorO> updatedO =new ArrayList<>();
		List<SchoolInstructorO> deletedO=new ArrayList<>();
		
		// 对新增的数据
		if (insertedRecords != null && insertedRecords.size() > 0) {
			for (SchoolInstructorO s : insertedRecords) {
				s.setCreatedBy(user.getAccount());
				s.setLastUpdatedBy(user.getAccount());
				s.trim();
				insertedO.add(s);
			}
			if(insertedO.size()>0){
				List<String> insertlist=driverSchoolService.insertSchoolInstructor(insertedO, account);
				if(insertlist!=null && insertlist.size()>0){
					resultMap.put("result", insertlist);
					return resultMap;
				}
			}
		}
		// 对修改的数据
		if (uptatedRecords != null && uptatedRecords.size() > 0) {
			for (SchoolInstructorO s : uptatedRecords) {
				s.setCreatedBy(user.getAccount());
				s.setLastUpdatedBy(user.getAccount());
				s.trim();
				updatedO.add(s);
			}
			if(updatedO!=null&&updatedO.size()>0){
				List<String> updateList=driverSchoolService.updateSchoolInstructor(updatedO, account);
			    if(updateList!=null&&updateList.size()>0){
				    resultMap.put("result",updateList );
				    return resultMap;
			    }
			}
		}
		// 对剔除的数据
		if (deletedRecords != null && deletedRecords.size() > 0) {
			for (SchoolInstructorO s : deletedRecords) {
				s.setCreatedBy(user.getAccount());
				s.setLastUpdatedBy(user.getAccount());
				s.trim();
				deletedO.add(s);
			}
			driverSchoolService.deleteSchoolInstructor(deletedO, account);
		}
		return resultMap;
	}

	/**
	 * 获取广告列表
	 * @author heyi 2016/4/10
	 */
	@ResponseBody
	@RequestMapping(value = "/getAdSchoolName", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	public Map<String, Object> getAdSchoolName(HttpSession session) {
		String account = CommonUtil.getSchoolAccount(session);
		Map<String, Object> map = new HashMap<String, Object>();
		List<SchoolAdO> ads = driverSchoolService.getAdSchoolName(account);
		map.put("ads", ads);
		return map;
	}

	/**
	 * 根据id和驾校账号查找广告
	 * @author heyi 2016/4/16
	 */
	@ResponseBody
	@RequestMapping(value = "/getAdByIdAndAccount", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	public SchoolAdO getAdByIdAndAccount(MultipartHttpServletRequest request,@PathVariable int id, @PathVariable String schoolAccount1) {
		SchoolAdO ad = driverSchoolService.getAdByIdAndAccount(id, schoolAccount1);
		return ad;
	}

	/**
	 * 增加广告信息
	 * @author heyi 2016/4/10
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "/addAd")
	public void addAd(MultipartHttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
		Writer output = CommonUtil.getResponseWriter(response);
		String schoolAccount = CommonUtil.getSchoolAccount(session);//获取驾校账号
		UserO user = CommonUtil.getUserInfo(session);
		
		SchoolAdO schoolAd = new SchoolAdO();
		schoolAd.setTitle(request.getParameter("title"));
		schoolAd.setContent(request.getParameter("content"));
		schoolAd.setSchoolAccount(schoolAccount);
		
		Map<String, MultipartFile> fileMap = request.getFileMap();  // 获取 文件 map
		Set<String> fileSet = fileMap.keySet();                     // 得到 map 的 key值
		Iterator<String> fileNameIterator = fileSet.iterator();     // 创建 迭代器
		int i = 1;
		while (fileNameIterator.hasNext()) {
			String uploadFileName = fileNameIterator.next();       //input 中的 name 属性 的 名称
			MultipartFile file = fileMap.get(uploadFileName);
			String newName = CommonUtil.savePic(request, session, file, i);
			i++;
			if (uploadFileName.equals("pic1") && newName!=null) {
				schoolAd.setPic1(newName);
				schoolAd.setCreatedBy(user.getAccount());          // 广告数据存入数据库
				schoolAd.setLastUpdatedBy(user.getAccount());
			}
			i++;
		}
		driverSchoolService.saveAdInfo(schoolAd, schoolAccount);
		output.write("<script language='javascript'>parent.callback(1)</script>");
	}

	// 根据驾校账号修改广告 （图片、标题、内容）
	@ResponseBody
	@RequestMapping(value = "/updateAd")
	public void updateAd(MultipartHttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
		Writer output = CommonUtil.getResponseWriter(response);
		UserO user = (UserO) session.getAttribute("userInfo");
		String schoolAccount = CommonUtil.getSchoolAccount(session);                    //获取驾校账号
		int id = Integer.parseInt(request.getParameter("id"));
		
		SchoolAdO schoolAd = driverSchoolService.getAdByIdAndAccount(id, schoolAccount);//查询要修改的历史数据
		schoolAd.setId(id);
		schoolAd.setTitle(request.getParameter("title"));
		schoolAd.setContent(request.getParameter("content"));
		Map<String, MultipartFile> fileMap = request.getFileMap();             // 获取 文件 map
		Set<String> fileSet = fileMap.keySet();                                // 得到 map 的 key值
		Iterator<String> fileNameIterator = fileSet.iterator();                // 创建 迭代器
		int i = 1;
		while (fileNameIterator.hasNext()) {
			String uploadFileName = fileNameIterator.next();                   // input 中的 name 属性 的 名称
			MultipartFile file = fileMap.get(uploadFileName);
			if(file.getSize()>0){                                              //如果不为空,删除原来的图片  ， 存入新的图片
				CommonUtil.deletePic(request, schoolAd.getPic1());             // 删除原有图片
				String newName = CommonUtil.savePic(request, session, file, i);// 保存图片，返回保存的文件名
				if (uploadFileName.equals("pic1")) {
					schoolAd.setPic1(newName);
				}
			}
			i++;
		}
		schoolAd.setLastUpdatedBy(user.getAccount());
		driverSchoolService.saveUpdateAdInfo(schoolAd, schoolAccount);// 广告数据存入数据库
		output.write("<script language='javascript'>parent.callback(1)</script>");
	}

	/**
	 * 删除广告记录
	 * @author heyi 2016/4/11
	 * @param s
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAd/{id}/{schoolAccount1}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Map<String,String> deleteAd(HttpServletRequest request, @PathVariable int id,
			@PathVariable String schoolAccount1,HttpSession session) {
		
		Map<String,String> map = new HashMap<>();
		String schoolAccount = CommonUtil.getSchoolAccount(session);//获取驾校账号
		SchoolAdO ad = driverSchoolService.getAdByIdAndAccount(id, schoolAccount);
		int result = driverSchoolService.deleteAd(id, schoolAccount);
		if(result>0){
			CommonUtil.deletePic(request, ad.getPic1());
		}
		map.put("state", SUCCESS);
		return map;
	}
	/**
	 * 保存 驾校 相关图片
	 * liaoyun  2016-4-12
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="/saveSchoolPic")
	public void saveSchoolPic(MultipartHttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		Writer output = CommonUtil.getResponseWriter(response);
		UserO user =(UserO)session.getAttribute("userInfo");
		String account = CommonUtil.getSchoolAccount(session);
		SchoolPicO picO = new SchoolPicO();
		try{
			picO.setSchoolAccount(account);
			picO.setExtra1(request.getParameter("name"));                     //姓名
			picO.setPicContent(request.getParameter("explain"));              //图片说明
			String type = request.getParameter("type");                       //图片类型
			if(type!=null && (type.equals("honor") || type.equals("view") || type.equals("instru"))){
				if(type.equals("honor")){                                     //照片类别：1--驾校资质荣誉照片;2--驾校风采照片;3--明星教练照片;
					picO.setPicType(1);
				}else if(type.equals("view")){
					picO.setPicType(2);
				}else{
					picO.setPicType(3);
				}
			}
			Map<String,MultipartFile> fileMap = request.getFileMap();          //获取 文件 map
			Set<String> fileSet = fileMap.keySet();                            //得到 map 的 key值
			Iterator<String> fileNameIterator = fileSet.iterator();            //创建 迭代器
			int i=0;
			while(fileNameIterator.hasNext()){
				String uploadFileName = fileNameIterator.next();               // input 中的 name 属性 的 名称
				MultipartFile file = fileMap.get(uploadFileName);
				String newName = CommonUtil.savePic(request, session, file, i);//保存文件到文件夹,返回照片新名字
				picO.setPic(newName);
				picO.setCreatedBy(user.getAccount());
				picO.setLastUpdatedBy(user.getAccount());
				i++;
			}
			picO.trim();
			driverSchoolService.saveSchoolPic(picO);
		}catch(Exception e){
			e.printStackTrace();
			output.write("<script language='javascript'>parent.callback(0)</script>");
			return;
		}
		output.write("<script language='javascript'>parent.callback(1)</script>");
	}
	/**
	 * 跳转到 驾校详细信息 页面
	 * liaoyun 2016-4-16
	 * @return
	 */
	@RequestMapping(value="/recommend",method=RequestMethod.POST)
	public String adDetail(@RequestParam("schoolAccount") String schoolAccount,Model model){ 
		model.addAttribute("account", schoolAccount);
		return "/front/adDetail.jsp";
	}
	/**
	 * 根据 school account 获取 驾校详细信息
	 * liaoyun 2016-4-16
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getRecommendSchoolInfo/{schoolAccount}")
	public SchoolO recommend(@PathVariable String schoolAccount){
		SchoolO sco = driverSchoolService.getRecommendSchoolInfo(schoolAccount);
		return sco;
	}
	/**
	 * 根据 school account 获取 驾校相关的图片
	 * liaoyun 2016-4-16
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getRecommendSchoolPic/{schoolAccount}")
	public Map<String,Object> recommendPic(@PathVariable String schoolAccount){
		//1--驾校资质荣誉照片
		List<SchoolPicO> honor = driverSchoolService.findHonorPic(schoolAccount,"1");
		//2--驾校风采照片
		List<SchoolPicO> spirit = driverSchoolService.findHonorPic(schoolAccount,"2");
		//3--明星教练照片;
		List<SchoolPicO> instructor = driverSchoolService.findHonorPic(schoolAccount,"3");
		Map<String,Object> map = new HashMap<>();
		map.put("honor", honor);
		map.put("spirit", spirit);
		map.put("instructor", instructor);
		return map;
	}
}
