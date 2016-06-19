package ssm.service.privilege;

import java.util.List;

import ssm.entity.common.ResultO;
import ssm.entity.privilege.PrivilegeO;
import ssm.entity.privilege.RoleO;

public interface RoleService {

	/**
	 * 分页查询 角色 及 权限, liaoyun, 2016-3-20
	 * @param role
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	ResultO<RoleO> findRoleList(RoleO role, int currentPage, int pageSize);

	/**
	 * 查询所有的权限 liaoyun 2016-3-22
	 */
	public List<PrivilegeO> findAllPrivilege();

	/**
	 * 拥有的权限 liaoyun 2016-3-22
	 * @param roleCode
	 * @return
	 */
	public List<String> findMyOwnPrivilege(String roleCode);

	/**
	 * 添加 角色
	 * liaoyun
	 * 2015-3-25
	 * @param insertedRecords
	 */
	public List<String> insertRole(List<RoleO> insertedRecords);

	/**
	 * 修改 角色
	 * liaoyun
	 * 2015-3-25
	 * @param insertedRecords
	 */
	public List<String> updateRole(List<RoleO> updatedRecords);

	/**
	 *  角删除色
	 * liaoyun
	 * 2015-3-25
	 * @param insertedRecords
	 */
	void deleteRole(List<RoleO> deletedRecords);

}
