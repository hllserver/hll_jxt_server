package ssm.orm.privilege;

import java.util.List;

import ssm.entity.privilege.PrivilegeO;
import ssm.entity.privilege.RoleO;
import ssm.orm.SqlMapper;

public interface RoleDao extends SqlMapper{

	/**
	 * 查询角色 的数量
	 * liaoyun
	 * 2016-3-20
	 * @param role
	 * @return
	 */
	public int findTotalRole(RoleO role);

	/**
	 * 分页查询 角色 及 权限
	 * liaoyun
	 * 2016-3-20
	 * @param role
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<RoleO> findRoleList(RoleO role, int start, int pageSize);

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

	public void insertRole(List<RoleO> insertedRecords);

	public void updateRole(List<RoleO> updatedRecords);

	public void deleteRole(List<RoleO> deletedRecords);

	/**
	 * 插入数据时，查询重复的 role code
	 * liaoyun 2016-4-3
	 * @param insertedRecords
	 * @return
	 */
	public List<String> findInsertRepeat(List<RoleO> insertedRecords);

	/**
	 * 查询修改的数据是否有重复  role code
	 * liaoyun 2016-4-3
	 * @param updatedRecords
	 * @return
	 */
	public List<RoleO> findUpdateRepeat(List<RoleO> updatedRecords);

}
