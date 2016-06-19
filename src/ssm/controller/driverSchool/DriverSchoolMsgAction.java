package ssm.controller.driverSchool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
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
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDriverSchoolMsg", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public SchoolO getDriverSchoolMsg(HttpSession session) {
		UserO user = (UserO) session.getAttribute("userInfo");
		String account = "";
		if (user.getType() == 4) {// 如果是驾校的职员
			account = user.getLeader();
		} else if (user.getType() == 3) {// 如果是驾校的管理员
			account = user.getAccount();
		}
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
		String account = "";
		if (user.getType() == 4) {// 如果是驾校的职员
			account = user.getLeader();
		} else if (user.getType() == 3) {// 如果是驾校的管理员
			account = user.getAccount();
		}
		if (school != null) {
			school.setSchoolName(getValue(school.getSchoolName()));
			school.setTel(getValue(school.getTel()));
			school.setEmail(getValue(school.getEmail()));
			school.setWechat(getValue(school.getWechat()));
			school.setQq(getValue(school.getQq()));
			school.setPosition(getValue(school.getPosition()));
			school.setRegisteredTime(school.getRegisteredTime());
			school.setScale(school.getScale());
			school.setRemark(getValue(school.getRemark()));
			school.setIntruduce(getValue(school.getIntruduce()));
			school.setCreatedBy(user.getAccount());
			school.setLastUpdatedBy(user.getAccount());
			
		}
		//driverSchoolService.saveSchoolMsg(school, account);
		//查找该账号是否有驾校
		int isHas = driverSchoolService.isHasDriverSchool(account);
		try{
			//如果有，则修改
			if(isHas>0){
				driverSchoolService.saveSchoolMsg(school,account);
			}
			//如果没有，则添加
			else{
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
		UserO user = (UserO) session.getAttribute("userInfo");
		String account = "";
		if (user.getType() == 4) {// 如果是驾校的职员
			account = user.getLeader();
		} else if (user.getType() == 3) {// 如果是驾校的管理员
			account = user.getAccount();
		}
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
		String account = "";
		if (user.getType() == 4) {// 如果是驾校的职员
			account = user.getLeader();
		} else if (user.getType() == 3) {// 如果是驾校的管理员
			account = user.getAccount();
		}
		List<SchoolInstructorO> insertedRecords = recordsO.getInsertedRecords();
		List<SchoolInstructorO> uptatedRecords = recordsO.getUpdatedRecords();
		List<SchoolInstructorO> deletedRecords = recordsO.getDeletedRecords();
		List<SchoolInstructorO> insertedO =new ArrayList<>();
		List<SchoolInstructorO> updatedO =new ArrayList<>();
		List<SchoolInstructorO> deletedO=new ArrayList<>();
		
		// 对新增的数据
		if (insertedRecords != null && insertedRecords.size() > 0) {
			for (SchoolInstructorO s : insertedRecords) {
				s.setId(s.getId());
				s.setJobNumber(getValue(s.getJobNumber()));
				s.setName(getValue(s.getName()));
				s.setAge(s.getAge());
				s.setGender(s.getGender());
				s.setRemark(getValue(s.getRemark()));
				s.setTel(getValue(s.getTel()));
				s.setEmail(getValue(s.getEmail()));
				s.setCreatedBy(user.getAccount());
				s.setLastUpdatedBy(user.getAccount());
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
				s.setId(s.getId());
				s.setJobNumber(getValue(s.getJobNumber()));
				s.setName(getValue(s.getName()));
				s.setAge(s.getAge());
				s.setGender(s.getGender());
				s.setRemark(getValue(s.getRemark()));
				s.setTel(getValue(s.getTel()));
				s.setEmail(getValue(s.getEmail()));
				s.setCreatedBy(user.getAccount());
				s.setLastUpdatedBy(user.getAccount());
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
				s.setId(s.getId());
				s.setJobNumber(getValue(s.getJobNumber()));
				s.setName(getValue(s.getName()));
				s.setAge(s.getAge());
				s.setGender(s.getGender());
				s.setRemark(getValue(s.getRemark()));
				s.setTel(getValue(s.getTel()));
				s.setEmail(getValue(s.getEmail()));
				s.setCreatedBy(user.getAccount());
				s.setLastUpdatedBy(user.getAccount());
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
	public Map<String, Object> getAdSchoolName() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SchoolAdO> ads = driverSchoolService.getAdSchoolName();
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
		boolean iscucess = true;
		response.setContentType("text/html;charset='utf-8'");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Cache-Control", "no-cache");
		Writer output = response.getWriter();
		response.setHeader("content-type", "text/html;charset=utf-8");
		
		SchoolAdO schoolAd = new SchoolAdO();
		//获取驾校账号
		UserO user = (UserO) session.getAttribute("userInfo");
		String schoolAccount="";
		if(user.getType()==4){//如果是驾校的职员
			schoolAccount = user.getLeader();
		}else if(user.getType()==3){//如果是驾校的管理员
			schoolAccount = user.getAccount();
		}
		try {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			schoolAd.setTitle(title);
			schoolAd.setContent(content);
			schoolAd.setSchoolAccount(schoolAccount);
			// 获取 文件 map
			Map<String, MultipartFile> fileMap = request.getFileMap();
			// 得到 map 的 key值
			Set<String> fileSet = fileMap.keySet();
			// 创建 迭代器
			Iterator<String> fileNameIterator = fileSet.iterator();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			int i = 1;
			
			// 保存文件的根路径
			String dir = request.getSession().getServletContext().getRealPath("/");
			if(!dir.endsWith(File.separator)){
				dir = dir+File.separator;
			}
			dir = dir + "file/pic";
			File docu = new File(dir);
			if(!docu.exists()){
				docu.mkdirs();
			}
			dir = request.getSession().getServletContext().getRealPath("/file/pic");
			
			while (fileNameIterator.hasNext()) {
				// input 中的 name 属性 的 名称
				String uploadFileName = fileNameIterator.next();
				MultipartFile file = fileMap.get(uploadFileName);
				// 上传的文件 的名字如：kk.jpg
				String origFileName = file.getOriginalFilename();
				// 得到文件的 流
				InputStream isRef = file.getInputStream();
				// 获取图片后缀
				int pos = origFileName.lastIndexOf(".");
				String suffix;
				if (pos != -1) {
					suffix = origFileName.substring(pos, origFileName.length());
					Date date = new Date();
					// 文件取别名，防止同名
					String newName = user.getAccount() + sdf.format(date) + i + suffix;
					i++;
					// 新文件全路径
					File targetFile = new File(dir, newName);
					targetFile.createNewFile();
					// 文件输出流
					FileOutputStream fosRef = new FileOutputStream(targetFile);
					
					// 保存文件到文件夹
					IOUtils.copy(isRef, fosRef);
					if (uploadFileName.equals("pic1")) {
						schoolAd.setPic1(newName);
					}
				}
			}
			// 广告数据存入数据库
			schoolAd.setCreatedBy(user.getAccount());
			schoolAd.setLastUpdatedBy(user.getAccount());
			driverSchoolService.saveAdInfo(schoolAd, schoolAccount);
		} catch (IOException e) {
			e.printStackTrace();
			iscucess=false;
		}
		if(iscucess==true){
			output.write("<script language='javascript'>parent.callback(1)</script>");
		}else{
			output.write("<script language='javascript'>parent.callback(0)</script>");
		}
	}

	// 根据驾校账号修改广告 （图片、标题、内容）
	@ResponseBody
	@RequestMapping(value = "/updateAd")
	public void updateAd(MultipartHttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
		boolean iscucess = true;
		response.setContentType("text/html;charset='utf-8'");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Cache-Control", "no-cache");
		Writer output = response.getWriter();
		response.setHeader("content-type", "text/html;charset=utf-8");
		
		//获取驾校账号
		UserO user = (UserO) session.getAttribute("userInfo");
		String schoolAccount="";
		if(user.getType()==4){//如果是驾校的职员
			schoolAccount = user.getLeader();
		}else if(user.getType()==3){//如果是驾校的管理员
			schoolAccount = user.getAccount();
		}
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		// 保存文件的根路径
		String dir = request.getSession().getServletContext().getRealPath("/");
		if(!dir.endsWith(File.separator)){
				dir = dir+File.separator;
		}
		dir = dir + "file/pic";
		File docu = new File(dir);
		if(!docu.exists()){
			docu.mkdirs();
		}
		dir = request.getSession().getServletContext().getRealPath("/file/pic");
		
		//查询要修改的历史数据
		SchoolAdO schoolAd = driverSchoolService.getAdByIdAndAccount(id, schoolAccount);
		try {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			schoolAd.setId(id);
			schoolAd.setTitle(title);
			schoolAd.setContent(content);
			// 获取 文件 map
			Map<String, MultipartFile> fileMap = request.getFileMap();
			// 得到 map 的 key值
			Set<String> fileSet = fileMap.keySet();
			// 创建 迭代器
			Iterator<String> fileNameIterator = fileSet.iterator();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			int i = 1;
			while (fileNameIterator.hasNext()) {
				// input 中的 name 属性 的 名称
				String uploadFileName = fileNameIterator.next();
				MultipartFile file = fileMap.get(uploadFileName);
				if(file.getSize()>0){//如果不为空,删除原来的图片  ， 存入新的图片
					// 删除原有图片
					SchoolAdO ad = driverSchoolService.getAdByIdAndAccount(id, schoolAccount);
					String adr = ad.getPic1();
					File deleFile = new File(dir,adr);
					if (deleFile.isFile()) {
						deleFile.delete();
					}
					
					// 上传的文件 的名字如：kk.jpg
					String origFileName = file.getOriginalFilename();
					// 得到文件的 流
					InputStream isRef = file.getInputStream();
					// 获取图片后缀
					int pos = origFileName.lastIndexOf(".");
					if (pos != -1) {
						String suffix = origFileName.substring(pos, origFileName.length());
						Date date = new Date();
						// 文件取别名，防止同名
						String newName = user.getAccount() + sdf.format(date) + i + suffix;
						i++;
						// 新文件全路径
						File targetFile = new File(dir, newName);
						// 文件输出流
						FileOutputStream fosRef = new FileOutputStream(targetFile);
						// 保存文件到文件夹
						IOUtils.copy(isRef, fosRef);
						if (uploadFileName.equals("pic1")) {
							schoolAd.setPic1(newName);
						}
					}
				}
			
			}
			// 广告数据存入数据库
			schoolAd.setLastUpdatedBy(user.getAccount());
			driverSchoolService.saveUpdateAdInfo(schoolAd, schoolAccount);
		} catch (IOException e) {
			e.printStackTrace();
			iscucess=false;
		}
		if(iscucess==true){
			output.write("<script language='javascript'>parent.callback(1)</script>");
		}else{
			output.write("<script language='javascript'>parent.callback(0)</script>");
		}
	}

	/**
	 * 删除广告记录
	 * 
	 * @author heyi 2016/4/11
	 * @param s
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAd/{id}/{schoolAccount1}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Map<String,String> deleteAd(HttpServletRequest request, @PathVariable int id,
			@PathVariable String schoolAccount1,HttpSession session) {
		Map<String,String> map = new HashMap<>();
		//获取驾校账号
		UserO user = (UserO) session.getAttribute("userInfo");
		String schoolAccount="";
		if(user.getType()==4){//如果是驾校的职员
			schoolAccount = user.getLeader();
		}else if(user.getType()==3){//如果是驾校的管理员
			schoolAccount = user.getAccount();
		}
				
		SchoolAdO ad = driverSchoolService.getAdByIdAndAccount(id, schoolAccount);
		int result = driverSchoolService.deleteAd(id, schoolAccount);
		if(result>0){
			// 保存文件的根路径
			String dir = request.getSession().getServletContext().getRealPath("/");
			if(!dir.endsWith(File.separator)){
					dir = dir+"/";
			}
			dir = dir + "file/pic";
			File docu = new File(dir);
			if(!docu.exists()){
				docu.mkdirs();
			}
			dir = request.getSession().getServletContext().getRealPath("/file/pic");
			
			
			String adr = null;
			if(ad!=null&&!ad.equals("")){
				 adr = ad.getPic1();
			}
			
			File deleFile = new File(dir,adr);
			if (deleFile.isFile()) {
				deleFile.delete();
			}
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
		response.setContentType("text/html;charset='utf-8'");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Cache-Control", "no-cache");
		Writer output = response.getWriter();
		response.setHeader("content-type", "text/html;charset=utf-8");
		
		UserO user =(UserO)session.getAttribute("userInfo");
		String account="";
		if(user.getType()==4){//如果是驾校的职员
			account = user.getLeader();
		}else if(user.getType()==3){//如果是驾校的管理员
			account = user.getAccount();
		}
		
		// 保存文件的根路径
		String dir = request.getSession().getServletContext().getRealPath("/");
		if(!dir.endsWith(File.separator)){
				dir = dir+File.separator;
		}
		dir = dir + "file/pic";
		File docu = new File(dir);
		if(!docu.exists()){
			docu.mkdirs();
		}
		dir = request.getSession().getServletContext().getRealPath("/file/pic");
		
		SchoolPicO picO = new SchoolPicO();
		try{
			picO.setSchoolAccount(account);
			//姓名
			String name = request.getParameter("name");
			//图片类型
			String type = request.getParameter("type");
			//图片说明
			String explain = request.getParameter("explain");
			if(name!=null){//教练姓名
				picO.setExtra1(name.trim());
			}
			if(type!=null && (type.equals("honor") || type.equals("view") || type.equals("instru"))){
				//照片类别：1--驾校资质荣誉照片;2--驾校风采照片;3--明星教练照片;
				if(type.equals("honor")){
					picO.setPicType(1);
				}else if(type.equals("view")){
					picO.setPicType(2);
				}else{
					picO.setPicType(3);
				}
			}
			if(explain!=null){//照片介绍
				picO.setPicContent(explain);
			}
			//获取 文件 map
			Map<String,MultipartFile> fileMap = request.getFileMap();
			//得到 map 的 key值
			Set<String> fileSet = fileMap.keySet();
			//创建 迭代器
			Iterator<String> fileNameIterator = fileSet.iterator();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			while(fileNameIterator.hasNext()){
				// input 中的 name 属性 的 名称
				String uploadFileName = fileNameIterator.next();
				MultipartFile file = fileMap.get(uploadFileName);
				//上传的文件 的名字如：kk.jpg
				String origFileName = file.getOriginalFilename();
				//得到文件的 流
				InputStream isRef = file.getInputStream();
				//获取图片后缀
				int pos = origFileName.lastIndexOf(".");
				String suffix = origFileName.substring(pos, origFileName.length());
				Date date = new Date();
				//文件取别名，防止同名
				String newName = account+sdf.format(date)+suffix;
				//新文件全路径
				File targetFile = new File(dir,newName);
				//文件输出流
				FileOutputStream fosRef = new FileOutputStream(targetFile);
				//保存文件到文件夹
				IOUtils.copy(isRef, fosRef);
				//照片名字
				picO.setPic(newName);
				picO.setCreatedBy(user.getAccount());
				picO.setLastUpdatedBy(user.getAccount());
				driverSchoolService.saveSchoolPic(picO);
			}
		}catch(Exception e){
			e.printStackTrace();
			output.write("<script language='javascript'>parent.callback(0)</script>");
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
	//需要修改，适合任何类型
	private String getValue(String s){
		if(s!=null){
			return s.trim();
		}
		return null;
	}
}
