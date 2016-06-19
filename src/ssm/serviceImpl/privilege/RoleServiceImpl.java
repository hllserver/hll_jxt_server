package ssm.serviceImpl.privilege;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssm.entity.common.PageO;
import ssm.entity.common.ResultO;
import ssm.entity.privilege.PrivilegeO;
import ssm.entity.privilege.RoleO;
import ssm.orm.privilege.RoleDao;
import ssm.service.privilege.RoleService;
import ssm.util.PageUtil;
@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleDao roleDao;

	/**
	 * 分页查询 角色 及 权限, liaoyun, 2016-3-20
	 */
	@Override
	public ResultO<RoleO> findRoleList(RoleO role, int currentPage, int pageSize) {
		//查询角色 的数量
		int total = roleDao.findTotalRole(role);
		PageO pageO = PageUtil.getPage(currentPage, pageSize, total);
		int start = (pageO.getCurrentPage()-1)*pageO.getPageSize();
		List<RoleO> list = roleDao.findRoleList(role,start,pageSize);
		ResultO<RoleO> result = new ResultO<>();
		result.setResultList(list);
		result.setPage(pageO);
		return result;
	}

	/**
	 * 查询所有的权限 liaoyun 2016-3-22
	 */
	@Override
	public List<PrivilegeO> findAllPrivilege() {
		List<PrivilegeO> list = roleDao.findAllPrivilege();
		return list;
	}

	/**
	 * 拥有的权限 liaoyun 2016-3-22
	 * @param roleCode
	 * @return
	 */
	@Override
	public List<String> findMyOwnPrivilege(String roleCode) {
		List<String> list = roleDao.findMyOwnPrivilege(roleCode);
		return list;
	}

	/**
	 * 添加 角色
	 * liaoyun
	 * 2015-3-25
	 * @param insertedRecords
	 */
	@Override
	public List<String> insertRole(List<RoleO> insertedRecords) {
		List<String> list=new ArrayList<>();
		//校验 传入的数据 是否重复
		for(int i=0; i<insertedRecords.size()-1; i++){
			for(int j=i+1; j<insertedRecords.size(); j++){
				String si=insertedRecords.get(i).getRoleCode();
				String sj=insertedRecords.get(j).getRoleCode();
				if(si==null || sj==null || si.trim().equals(sj.trim())){
					list.add(sj);
					return list;
				}
			}
		}
		//校验传入的数据 是否和数据库中 的数据重复
		list = roleDao.findInsertRepeat(insertedRecords);
		if(list!=null && list.size()>0){//如果rolecode重复
			return list;
		}
		roleDao.insertRole(insertedRecords);
		return null;
	}

	/**
	 * 修改 角色
	 * liaoyun
	 * 2015-3-25
	 * @param insertedRecords
	 */
	@Override
	public List<String> updateRole(List<RoleO> updatedRecords) {
		List<String> rep = new ArrayList<>();
		//校验传入的数据 是否重复
		for(int i=0; i<updatedRecords.size(); i++){
			for(int j=i+1; j<updatedRecords.size(); j++){
				String si = updatedRecords.get(i).getRoleCode();
				String sj = updatedRecords.get(j).getRoleCode();
				if(si==null || sj==null || si.trim().contentEquals(sj.trim())){
					rep.add(sj);
				}
			}
		}
		//校验 传入的数据 与 数据库中的数据 是否重复
		List<RoleO> list = roleDao.findUpdateRepeat(updatedRecords);
		for (RoleO roleO : updatedRecords) {
			String code1 = roleO.getRoleCode();
			long id1 = roleO.getId();
			for (RoleO r : list) {
				String code2 = r.getRoleCode();
				long id2 = r.getId();
				if(code1.equals(code2) && id1!=id2){
					rep.add(code1);
				}
			}
		}
		if(rep!=null && rep.size()>0){//如果重复 role code
			return rep;
		}
		roleDao.updateRole(updatedRecords);
		return null;
	}

	/**
	 *  角删除色
	 * liaoyun
	 * 2015-3-25
	 * @param insertedRecords
	 */
	@Override
	public void deleteRole(List<RoleO> deletedRecords) {
		roleDao.deleteRole(deletedRecords);
	}

}
