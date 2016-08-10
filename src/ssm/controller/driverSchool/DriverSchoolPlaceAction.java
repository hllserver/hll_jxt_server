package ssm.controller.driverSchool;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ssm.entity.common.RecordsO;
import ssm.entity.common.ResultO;
import ssm.entity.driverSchool.SchoolPlaceO;
import ssm.entity.user.UserO;
import ssm.service.driverSchoolService.DriverSchoolPlaceService;
import ssm.util.CommonUtil;

@Controller
@Transactional
@RequestMapping(value="/driverSchoolPlace")
public class DriverSchoolPlaceAction {
	
	private final String SUCCESS="1";
	private final String FAILD="0";

	@Autowired
	private DriverSchoolPlaceService driverSchoolPlaceService;
	/**
	 * 获取驾校场地信息列表
	 * @author heyi
	 * 2016/4/2
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getDriverSchoolPlaceList/{currentPage}/{pageSize}",method=RequestMethod.POST,produces="application/json",
			consumes="application/json")
	public ResultO<SchoolPlaceO> getDriverSchoolPlaceList(@RequestBody SchoolPlaceO schoolPlaceO,@PathVariable int currentPage,
			@PathVariable int pageSize,HttpSession session)throws Exception{
		String account = CommonUtil.getSchoolAccount(session);
		ResultO<SchoolPlaceO> list = driverSchoolPlaceService.getDriverSchoolPlaceList(schoolPlaceO,currentPage,pageSize,account);
		return list;
	}
	
	
	/**
	 * 按驾校名称获取驾校场地信息列表
	 * @author heyi
	 * 2016/4/3
	 */
	@ResponseBody
	@RequestMapping(value="/getSchoolPlace",method=RequestMethod.GET,produces="application/json",consumes="application/json")
	public Map<String,Object> getSchoolPlace(HttpSession session){
		Map<String,Object> map=new HashMap<String,Object>();
		String account = CommonUtil.getSchoolAccount(session);
		List<SchoolPlaceO> places =driverSchoolPlaceService.getSchoolPlace( account);
		map.put("places",places);
		map.put("item", account);
		return map;
		
	}
	
	
	/**
	 * 保存所有驾校场地信息
	 * @author heyi
	 * 2016/4/2
	 * @param recordsO
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveAll",method=RequestMethod.POST,produces="application/json",consumes="application/json")
	public Map<String,Object> saveAll(@RequestBody RecordsO<SchoolPlaceO> recordsO,HttpSession session){
		
		Map<String,Object> resultMap =new HashMap<>();
		UserO user =(UserO)session.getAttribute("userInfo");
		String account =user.getAccount();
		String leader=user.getLeader();
		List<SchoolPlaceO> insertedRecords =recordsO.getInsertedRecords();
		List<SchoolPlaceO> uptatedRecords =recordsO.getUpdatedRecords();
		List<SchoolPlaceO> deletedRecords=recordsO.getDeletedRecords();
		List<SchoolPlaceO> insertedList= new ArrayList<>();
		List<SchoolPlaceO> updatedList=new ArrayList<>();
		List<SchoolPlaceO> deletedList=new ArrayList<>();
		//批量新增数据
		if(insertedRecords!=null&&insertedRecords.size()>0){
			for(SchoolPlaceO s:insertedRecords){
				s.setCreatedBy(account);
				s.setCreatedDate(new Date());
				s.setLastUpdatedBy(account);
				s.setLastUpdatedDate(new Date());
				s.setSchoolAccount(leader);
				s.trim();
				insertedList.add(s);
			}
			
			if(insertedList.size()>0){
				List<String> insertResult =driverSchoolPlaceService.batchInsert(insertedList);
				if(insertResult!=null && insertResult.size()>0){
					resultMap.put("result", insertResult);
				    return resultMap;
				}
			}
		}
		
		/**
		 * 批量修改驾校场地信息
		 * @author heyi
		 * 2016/4/2
		 */
		if(uptatedRecords!=null&&uptatedRecords.size()>0){
			for(SchoolPlaceO s:uptatedRecords){
				s.setLastUpdatedBy(account);
				s.setLastUpdatedDate(new Date());
				s.trim();
				updatedList.add(s);
			}
			if(updatedList.size()>0){
				List<String> updateResult =driverSchoolPlaceService.batchUpdate(updatedList);
				if(updateResult!=null&&updateResult.size()>0){
					resultMap.put("result", updateResult);
					return resultMap;
				}
			}
		
		}
		//批量删除驾校场地信息
		if(deletedRecords!=null&&deletedRecords.size()>0){
			for(SchoolPlaceO s:deletedRecords){
				s.setLastUpdatedBy(account);
				s.setLastUpdatedDate(new Date());
				s.setSchoolAccount(leader);
				s.trim();
				deletedList.add(s);
			}
			if(deletedList.size()>0){
			 driverSchoolPlaceService.batchDelete(deletedList);
			}
		}
		return resultMap;
	}	
	/**
	 * 添加新场地 /修改场地
	 * liaoyun
	 * 2016-4-4
	 */
	@ResponseBody
	@RequestMapping(value="/addNewPlace")
	public void addNewPlace(MultipartHttpServletRequest request,HttpServletResponse response,
			HttpSession session) throws Exception{
		//是否操作成功
		boolean iscucess = true;
		Writer output = CommonUtil.getResponseWriter(response);
		
		String id = request.getParameter("id");
		String placeName = request.getParameter("placeName");
		String area = request.getParameter("area");
		String carNo = request.getParameter("carNo");
		String remark = request.getParameter("remark");
		String position = request.getParameter("position");
		
		//得到驾校编号
		UserO user =(UserO)session.getAttribute("userInfo");
		String account = CommonUtil.getSchoolAccount(session);
		//插入数据库的数据
		SchoolPlaceO schoolPlaceO = new SchoolPlaceO();
		schoolPlaceO.setPlaceName(placeName);
		schoolPlaceO.setArea(Double.parseDouble(area));
		schoolPlaceO.setCarNo(Integer.parseInt(carNo));
		schoolPlaceO.setRemark(remark);
		schoolPlaceO.setPosition(position);
		schoolPlaceO.setSchoolAccount(account);
		schoolPlaceO.setCreatedBy(user.getAccount());
		schoolPlaceO.setLastUpdatedBy(user.getAccount());
		
		if(id==null || id.equals("")){//-----当 id 为空,为新增的数据----------------------
			try{
				Map<String,MultipartFile> fileMap = request.getFileMap();              //获取 文件 map
				Iterator<String> fileNameIterator = fileMap.keySet().iterator();       //创建 迭代器
				int i=1;
				while(fileNameIterator.hasNext()){
					String uploadFileName = fileNameIterator.next();                   // input 中的 name 属性 的 名称
					MultipartFile file = fileMap.get(uploadFileName);
					if(file.getSize()>0){
						String newName = CommonUtil.savePic(request, session, file, i);//保存图片并返回图片名称
						if(uploadFileName.equals("pic1")){
							schoolPlaceO.setPic1(newName);
						}else if(uploadFileName.equals("pic2")){
							schoolPlaceO.setPic2(newName);
						}else if(uploadFileName.equals("pic3")){
							schoolPlaceO.setPic3(newName);
						}else if(uploadFileName.equals("pic4")){
							schoolPlaceO.setPic4(newName);
						}else if(uploadFileName.equals("pic5")){
							schoolPlaceO.setPic5(newName);
						}
					}
					i++;
				}
				//将数据保存到数据库
				schoolPlaceO.trim();
				driverSchoolPlaceService.saveNewPlace(schoolPlaceO);
			}catch(Exception e){
				e.printStackTrace();
				iscucess=false;
			}
		}else{//-------为修改的数据-------------------------------
			schoolPlaceO.setId(Long.valueOf(id));
			SchoolPlaceO place = driverSchoolPlaceService.findPlaceDataById(id, account);//通过 id 查询历史数据
			schoolPlaceO.setPic1(place.getPic1());
			schoolPlaceO.setPic2(place.getPic2());
			schoolPlaceO.setPic3(place.getPic3());
			schoolPlaceO.setPic4(place.getPic4());
			schoolPlaceO.setPic5(place.getPic5());
			try{
				Map<String,MultipartFile> fileMap = request.getFileMap();  //获取 文件 map
				Iterator<String> fileNameIterator = fileMap.keySet().iterator();//创建 迭代器
				int i=1;
				while(fileNameIterator.hasNext()){
					String uploadFileName = fileNameIterator.next();       //input 中的 name 属性 的 名称
					MultipartFile file = fileMap.get(uploadFileName);
					if(file.getSize()>0){                                  //如果不为空,删除原来的图片  ， 存入新的图片
						String newName = CommonUtil.savePic(request, session, file, i);
						if(uploadFileName.equals("pic1")){
							CommonUtil.deletePic(request, place.getPic1());//删除旧的文件 
							schoolPlaceO.setPic1(newName);
						}else if(uploadFileName.equals("pic2")){
							CommonUtil.deletePic(request, place.getPic2());//删除旧的文件 
							schoolPlaceO.setPic2(newName);
						}else if(uploadFileName.equals("pic3")){
							CommonUtil.deletePic(request, place.getPic3());//删除旧的文件 
							schoolPlaceO.setPic3(newName);
						}else if(uploadFileName.equals("pic4")){
							CommonUtil.deletePic(request, place.getPic4());
							schoolPlaceO.setPic4(newName);
						}else if(uploadFileName.equals("pic5")){
							CommonUtil.deletePic(request, place.getPic5());
							schoolPlaceO.setPic5(newName);
						}
					}
					i++;
				}
				schoolPlaceO.trim();
				driverSchoolPlaceService.saveUpdatedPlace(schoolPlaceO);   //保存入数据库
			}catch(Exception e){
				iscucess=false;
			}
		}
		if(iscucess==true){
			output.write("<script language='javascript'>parent.callback(1)</script>");
		}else{
			output.write("<script language='javascript'>parent.callback(0)</script>");
		}
	}
	/**
	 * 删除场地
	 * liaoyun 2016-4-29
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteSchoolPlace/{id}",method=RequestMethod.GET,produces="application/json",
	consumes="application/json")
	public Map<String,String> deleteSchoolPlace(@PathVariable int id,HttpSession session,HttpServletRequest request){
		Map<String,String> map= new HashMap<>();
		
		String account = CommonUtil.getSchoolAccount(session);
		try{
			//通过 id 查询历史数据
			SchoolPlaceO place = driverSchoolPlaceService.findPlaceDataById(String.valueOf(id), account);
			//删除原有的图片pic1
			CommonUtil.deletePic(request, place.getPic1());
			//删除原有的图片pic2
			CommonUtil.deletePic(request, place.getPic2());
			//删除原有的图片pic3
			CommonUtil.deletePic(request, place.getPic3());
			//删除原有的图片pic4
			CommonUtil.deletePic(request, place.getPic4());
			//删除原有的图片pic5
			CommonUtil.deletePic(request, place.getPic5());
			//删除数据库中 的记录
			driverSchoolPlaceService.deleteSchoolPlace(id,account);
		}catch(Exception e){
			e.printStackTrace();
			map.put("state", FAILD);
			return map;
		}
		map.put("state", SUCCESS);
		return map;
	}
}
