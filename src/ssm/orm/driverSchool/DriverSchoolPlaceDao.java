package ssm.orm.driverSchool;

import java.util.List;

import ssm.entity.driverSchool.SchoolPlaceO;

public interface DriverSchoolPlaceDao {

	/**
	 * 获取驾校场地个数
	 * @author heyi
	 * 2016/4/2
	 * @param schoolPlaceO
	 * @return
	 */
	int getDriverSchoolPlaceListTotal(SchoolPlaceO schoolPlaceO,String leader);
	/**
	 * 获取驾校信息列表
	 * @author heyi
	 * 2016/4/2
	 * @param schoolPlaceO
	 * @param start
	 * @param pageSize
	 * @return
	 */

	List<SchoolPlaceO> getDriverSchoolPlaceList(SchoolPlaceO schoolPlaceO, int start, int pageSize,String leader);
	
	/**
	 * 根据场地名称查重
	 * @author heyi
	 * 2016/4/2
	 * @param accountList
	 * @return
	 */
	List<String> findRepeatedByPlaceName(List<String> placeList);
	/**
	 * 插入驾校场地信息
	 * @author heyi
	 * 2016/4/2
	 * @param insertedList
	 */
	void batchInsert(List<SchoolPlaceO> insertedList);
	
	/**
	 * 修改时根据场地名称查重
	 * @author heyi
	 * 2016/4/2
	 * @param accountList
	 * @return
	 */
	List<String> findUpdateRepeatedByPlaceName(List<SchoolPlaceO> updatedList);
	
	/**
	 * 删除驾校场地信息
	 * @author heyi
	 * 2016/4/2
	 * @param deletedList
	 */
	void batchDelete(List<SchoolPlaceO> deletedList);
	/**
	 * 修改驾校场地信息
	 * @author heyi
	 * 2016/4/2
	 * @param updatedList
	 */
	void batchUpdate(List<SchoolPlaceO> updatedList);
	
	/**
	 * 按驾校名称获取驾校场地信息列表
	 * @author heyi
	 * 2016/4/3
	 */
	List<SchoolPlaceO> getSchoolPlaceList(String leader);
	/**
	 * 保存新增的场地
	 * liaoyun
	 * 2016-4-4
	 * @param schoolPlaceO
	 */
	void saveNewPlace(SchoolPlaceO schoolPlaceO);
	/**
	 * 通过  id 与 驾校账号 查询 场地信息
	 * liaoyun  2016-4-4
	 * @param id
	 * @param account
	 * @return
	 */
	SchoolPlaceO findPlaceDataById(String id, String account);
	/**
	 * 保存修改的场地信息
	 * liaoyun 2016-4-4
	 * @param place
	 */
	void saveUpdatedPlace(SchoolPlaceO place);
	/**
	 * 删除数据库中 的记录
	 * liaoyun 2016-4-29
	 * @param id
	 * @param account
	 */
	void deleteSchoolPlace(int id, String account);

}
