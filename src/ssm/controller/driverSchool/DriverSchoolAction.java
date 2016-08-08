package ssm.controller.driverSchool;

import java.util.ArrayList;
import java.util.HashMap;
/*
 *显示学校信息操作类
 *@heyi
 *2016/3/12
*/
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ssm.entity.common.RecordsO;
import ssm.entity.common.ResultO;
import ssm.entity.driverSchool.SchoolO;
import ssm.entity.user.UserO;
import ssm.service.driverSchoolService.DriverSchoolService;

@Controller
@Transactional
@RequestMapping(value = "/driverSchool")
public class DriverSchoolAction {

	@Autowired
	private DriverSchoolService driverSchoolService;

	/**
	 * 获取驾校列表的方法
	 * @author heyi
	 * 2016-3-13
	 */
	@ResponseBody
	@RequestMapping(value="/getDriverSchoolList/{currentPage}/{pageSize}",method=RequestMethod.POST,produces="application/json",
			consumes="application/json")
	public ResultO<SchoolO> getDriverSchoolList(@RequestBody SchoolO schoolO,@PathVariable int currentPage,
			@PathVariable int pageSize) throws Exception

	{
	   SchoolO s=new SchoolO();
	   s.setAccount(getValue(schoolO.getAccount()));
	   s.setSchoolName(getValue(schoolO.getSchoolName()));
	   s.setPosition(getValue(schoolO.getPosition()));
	   s.setScale(schoolO.getScale());
		ResultO<SchoolO> list = driverSchoolService.getDriverSchoolList(s,currentPage,pageSize);
		return list;
	}
	
	
	/**
	 * 保存所有的数据
	 * @author heyi
	 * 2016/3/22
	 */
	@ResponseBody
	@RequestMapping(value="/saveAll",method=RequestMethod.POST,produces="application/json",consumes="application/json")
	public Map<String,Object> saveAll(@RequestBody RecordsO<SchoolO> recordsO,HttpSession session){
		
		Map<String,Object> resultMap =new HashMap<>();
		UserO user =(UserO)session.getAttribute("userInfo");
		String account =user.getAccount();
		List<SchoolO> insertedRecords =recordsO.getInsertedRecords();
		List<SchoolO> uptatedRecords =recordsO.getUpdatedRecords();
		List<SchoolO> deletedRecords=recordsO.getDeletedRecords();
		List<SchoolO> insertedList= new ArrayList<>();
		List<SchoolO> updatedList=new ArrayList<>();
		List<SchoolO> deletedList=new ArrayList<>();
		//新增数据
		if(insertedRecords!=null&&insertedRecords.size()>0){
			for(SchoolO s:insertedRecords){
				s.setAccount(getValue(s.getAccount()));
				s.setCreatedBy(account);
				s.setLastUpdatedBy(account);
				s.setEmail(getValue(s.getEmail()));
				s.setPolicy(getValue(s.getPolicy()));
				s.setIntruduce(getValue(s.getIntruduce()));
				s.setPosition(getValue(s.getPosition()));
				s.setRemark(getValue(s.getRemark()));
				s.setScale((s.getScale()));
				s.setSchoolName(getValue(s.getSchoolName()));
				s.setTel(getValue(s.getTel()));
				insertedList.add(s);
			}
		}
		if(insertedList.size()>0){
			List<String> insertResult =driverSchoolService.batchInsert(insertedList);
			if(insertResult!=null && insertResult.size()>0){
			resultMap.put("result", insertResult);
		     return resultMap;
		}
		}
	//修改数据
	if(uptatedRecords!=null &&uptatedRecords.size()>0){
		for (SchoolO s : uptatedRecords) {
			s.setId(s.getId());
			s.setLastUpdatedBy(account);
			s.setEmail(getValue(s.getEmail()));
			s.setIntruduce(getValue(s.getIntruduce()));
			s.setPosition(getValue(s.getPosition()));
			s.setRemark(getValue(s.getRemark()));
			s.setPolicy(getValue(s.getPolicy()));
			s.setScale((s.getScale()));
			s.setSchoolName(getValue(s.getSchoolName()));
			s.setTel(getValue(s.getTel()));
			updatedList.add(s);
		}
	}
	
	if(updatedList.size()>0){
		List<String> updateResult= driverSchoolService.batchUpdate(updatedList);
		if(updateResult!=null && updateResult.size()>0){
			resultMap.put("result", updateResult);
		  return resultMap;
	}
	}
	//删除数据
	if(deletedRecords!=null&&deletedRecords.size()>0){
	for(SchoolO s:deletedRecords){
		s.setLastUpdatedBy(account);
		s.setEmail(getValue(s.getEmail()));
		s.setIntruduce(getValue(s.getIntruduce()));
		s.setPosition(getValue(s.getPosition()));
		s.setPolicy(getValue(s.getPolicy()));
		s.setRemark(getValue(s.getRemark()));
		s.setScale((s.getScale()));
		s.setSchoolName(getValue(s.getSchoolName()));
		s.setTel(getValue(s.getTel()));
		deletedList.add(s);
	}	
	}
	
	if(deletedList.size()>0){
		driverSchoolService.batchDelete(deletedList);
	}
	return resultMap;
	}
	
	//需要修改，适合任何类型
	private String getValue(String s){
		if(s==null||s.equals("")){
			return "";
		}
		return s.trim();
	}
}
