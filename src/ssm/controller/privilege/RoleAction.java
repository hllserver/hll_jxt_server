package ssm.controller.privilege;

import java.util.HashMap;
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
import ssm.entity.privilege.PrivilegeO;
import ssm.entity.privilege.RoleO;
import ssm.entity.user.UserO;
import ssm.service.privilege.RoleService;
/**
 * 角色权限管理
 * @author liaoyun
 * 2016-3-20
 */
@Transactional//事务
@Controller
@RequestMapping("/role")
public class RoleAction {
	@Autowired
	private RoleService roleService;
	/**
	 * 分页查询 角色 及 权限 liaoyun 2016-3-20
	 * @param role
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/findRoleList/{currentPage}/{pageSize}",method=RequestMethod.POST,produces="application/json",
	consumes="application/json")
	public ResultO<RoleO> findRoleList(@RequestBody RoleO role,@PathVariable int currentPage,@PathVariable int pageSize) throws Exception{
		ResultO<RoleO> result = roleService.findRoleList(role,currentPage,pageSize);
		return result;
	}
	/**
	 * 查询出所有的权限 和 已经拥有的权限
	 * liaoyun
	 * 2016-3-22
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findPrivilege/{roleCode}",method=RequestMethod.POST,produces="application/json",
	consumes="application/json")
	public Map<String,Object> findAllRole(@PathVariable String roleCode) throws Exception{
		Map<String,Object> map = new HashMap<>();
		//所有的权限
		List<PrivilegeO> allPrivilege = roleService.findAllPrivilege();
		//拥有的权限 其实就是一个字符串，用逗号隔开的
		List<String> ownPrivilege = roleService.findMyOwnPrivilege(roleCode);
		if(allPrivilege != null && allPrivilege.size()>0){
			map.put("all", allPrivilege);
		}
		if(ownPrivilege != null && ownPrivilege.size()>0){
			map.put("own", ownPrivilege.get(0));
		}
		return map;
	}
	@ResponseBody
	@RequestMapping(value="/saveAll",method=RequestMethod.POST,produces="application/json",
	consumes="application/json")
	public Map<String,Object> saveAll(@RequestBody RecordsO<RoleO> recordsO,HttpSession  session) throws Exception{
		Map<String,Object> resultMap = new HashMap<>();
		UserO user = (UserO) session.getAttribute("userInfo");
		String account = user.getAccount();
		//新增的数据
		List<RoleO> insertedRecords = recordsO.getInsertedRecords();
		//更新的数据
		List<RoleO> updatedRecords = recordsO.getUpdatedRecords();
		//删除的数据
		List<RoleO> deletedRecords = recordsO.getDeletedRecords();
		//对删除的数据
		if(deletedRecords != null && deletedRecords.size()>0){
			roleService.deleteRole(deletedRecords);
		}
		//对新增的数据
		if(insertedRecords != null && insertedRecords.size()>0){
			for (RoleO r : insertedRecords) {
				r.setRoleCode(getValue(r.getRoleCode()));
				r.setRoleName(getValue(r.getRoleName()));
				r.setPrivilegeCode(getValue(r.getPrivilegeCode()));
				r.setCreatedBy(account);
				r.setLastUpdatedBy(account);
			}
			List<String> insertResult = roleService.insertRole(insertedRecords);
			if(insertResult!=null && insertResult.size()>0){
				resultMap.put("result", insertResult);
			}
		}
		//对修改的数据
		if(updatedRecords != null && updatedRecords.size()>0){
			for (RoleO r : updatedRecords) {
				r.setRoleCode(getValue(r.getRoleCode()));
				r.setRoleName(getValue(r.getRoleName()));
				r.setPrivilegeCode(getValue(r.getPrivilegeCode()));
				r.setCreatedBy(account);
				r.setLastUpdatedBy(account);
			}
			List<String> updateResult = roleService.updateRole(updatedRecords);
			if(updateResult != null && updateResult.size()>0){
				resultMap.put("result", updateResult);
			}
		}
		return resultMap;
		
	}
	private String getValue(String s){
		if(s!=null){
			return s.trim();
		}
		return null;
	}
}






