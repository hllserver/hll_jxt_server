package ssm.controller.privilege;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import privilege.Authority;
import privilege.Item;
import ssm.entity.common.RecordsO;
import ssm.entity.common.ResultO;
import ssm.entity.privilege.PrivilegeO;
import ssm.entity.user.UserO;
import ssm.service.privilege.PrivilegeService;
import ssm.util.AnnotationScanUtil;
import ssm.util.Email;
/**
 * 权限控件
 * @author liaoyun
 * 2016-3-6
 */
@Controller
@Transactional
@RequestMapping(value="/privilege")
public class PrivilegeAction {
	
	@Autowired
	private PrivilegeService privilegeService;
	/**
	 * 查询权限
	 * liaoyun 
	 * 2016-3-6
	 */
	@ResponseBody
	@RequestMapping(value="/getPrivilegeList/{currentPage}/{pageSize}",method=RequestMethod.POST,produces="application/json",
	consumes="application/json")
	public ResultO<PrivilegeO> getPrivilegeList(@RequestBody PrivilegeO privilegeO,@PathVariable int currentPage,@PathVariable int pageSize) throws Exception{
		ResultO<PrivilegeO> list = privilegeService.getPrivilegeList(privilegeO,currentPage,pageSize);
		return list;
	}
	/**
	 * 保存所有的数据
	 * @param recordsO
	 * liaoyun
	 * 2016-3-15
	 */
	@ResponseBody
	@RequestMapping(value="/saveAll",method=RequestMethod.POST,produces="application/json",consumes="application/json")
	public Map<String,Object> saveAll(@RequestBody RecordsO<PrivilegeO> recordsO,HttpSession  session){
		//返回到前台 的信息
		Map<String,Object> resultMap = new HashMap<>();
		UserO user = (UserO) session.getAttribute("userInfo");
		String account = user.getAccount();
		//新增的数据
		List<PrivilegeO> insertedRecords = recordsO.getInsertedRecords();
		//修改的数据
		List<PrivilegeO> updatedRecords = recordsO.getUpdatedRecords();
		//删除的数据
		List<PrivilegeO> deletedRecords = recordsO.getDeletedRecords();
		List<PrivilegeO> insertedList = new ArrayList<>();
		List<PrivilegeO> updatedList = new ArrayList<>();
		//-------------------------对删除的数据-----------------------------
		if(deletedRecords!=null && deletedRecords.size()>0){
			privilegeService.bachDelete(deletedRecords);
		}
		//-------------------------对新增的数据-----------------------------
		if(insertedRecords != null && insertedRecords.size()>0){
			for (PrivilegeO p : insertedRecords) {
				p.setPriCode(getValue(p.getPriCode()));
				p.setPriDesc(getValue(p.getPriDesc()));
				p.setPriUrl(getValue(p.getPriUrl()));
				p.setCreatedBy(account);
				p.setLastUpdatedBy(account);
				p.setPriType(1);
				insertedList.add(p);
			}
		}
		if(insertedList.size()>0){
			List<String> insertResult = privilegeService.batchInsert(insertedList);
			if(insertResult!=null && insertResult.size()>0){
				resultMap.put("result", insertResult);
				return resultMap;
			}
		}
		//-------------------------对修改的数据-----------------------------
		if(updatedRecords != null && updatedRecords.size()>0){
			for (PrivilegeO p : updatedRecords) {
				p.setPriCode(getValue(p.getPriCode()));
				p.setPriDesc(getValue(p.getPriDesc()));
				p.setPriUrl(getValue(p.getPriUrl()));
				p.setCreatedBy(account);
				p.setLastUpdatedBy(account);
				updatedList.add(p);
			}
		}
		if(updatedList.size()>0){
			List<String> updateResult =privilegeService.batchUpdate(updatedList);
			if(updateResult!=null && updateResult.size()>0){
				resultMap.put("result", updateResult);
				return resultMap;
			}
		}
		return resultMap;
	}
	@Authority(code="senEmail",desc="Send Email")
	@ResponseBody
	@RequestMapping(value="/sendEmail",method=RequestMethod.POST,produces="application/json",consumes="application/json")
	public void sendEmail() throws MessagingException{
		Email.sendMessage("smtp.sina.com", "jiaxiaohll@sina.com", "jiaxiaotong", "945505778@qq.com", "欢迎注册", "dfdfdfdf", "text/html;charset=gb2312");
	}
	/**
	 * 扫描controller 中的所有方法权限
	 * liaoyun
	 * 2016-4-19
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/scan",method=RequestMethod.POST,produces="application/json",consumes="application/json")	
	public Map<String,Object> scanPrivilege(HttpSession session){
		UserO user = (UserO) session.getAttribute("userInfo");
		String account = user.getAccount();
		Map<String,Object> map = new HashMap<>();
		List<Item> items = new ArrayList<>();
		List<Authority> list = AnnotationScanUtil.getPrivilegeNotion("ssm.controller.privilege");
		for (Authority au : list) {
			Item item = new Item();
			item.setCode(au.code().trim());
			item.setDesc(au.desc().trim());
			item.setCreatedBy(account);
			item.setLastUpdatedBy(account);
			items.add(item);
		}
		//判断重复,方法权限不能重复
		int size = items.size();
		for(int i=0; i<size-1; i++){
			for(int j=i+1; j<size; j++){
				String codei = items.get(i).getCode();
				String codej = items.get(j).getCode();
				if(codei.equals(codej)){
					map.put("error", codei+"重复了");
					return map;
				}
			}
		}
		//删除原来的方法权限
		privilegeService.deleteOldMethodPrivilege();
		//插入新扫描的方法权限 
		privilegeService.insertNewMethodPrivilege(items);
		map.put("privilege",items);
		return map;
	}
	private String getValue(String s){
		if(s==null || s.equals("")){
			return "";
		}
		return s.trim();
	}
}










