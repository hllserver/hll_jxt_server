package ssm.service.privilege;

import java.util.List;
import java.util.Map;

import privilege.Item;
import ssm.entity.common.ResultO;
import ssm.entity.privilege.PrivilegeO;

public interface PrivilegeService {

	/**
	 * ��ȡ Ȩ����Ϣ
	 * liaoyun 2016-3-6
	 */
	public ResultO<PrivilegeO> getPrivilegeList(PrivilegeO privilegeO,int currentPage,int pageSize);

	/**
	 * 批量 插入 数据
	 * @param insertedList
	 * liaoyun
	 * 2016-3-16
	 */
	public List<String> batchInsert(List<PrivilegeO> insertedList);
	/**
	 * 批量 修改  数据
	 * @param updatedList
	 * liaoyun
	 * 2016-3-20
	 */
	public List<String> batchUpdate(List<PrivilegeO> updatedList);

	/**
	 * 批量 删除  数据
	 * @param updatedList
	 * liaoyun
	 * 2016-3-20
	 */
	public void bachDelete(List<PrivilegeO> deletedList);

	/**
	 * 删除原来的方法权限
	 * liaoyun
	 * 2014-4-19
	 */
	public void deleteOldMethodPrivilege();

	/**
	 * 插入新扫描的方法权限  liaoyun 2016-4-19
	 * @param items
	 */
	public void insertNewMethodPrivilege(List<Item> items);
	
}
