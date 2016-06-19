package ssm.service.driverSchoolService;
import java.util.List;
import java.util.Map;

import ssm.entity.common.ResultO;
import ssm.entity.driverSchool.SchoolPlaceO;

public interface DriverSchoolPlaceService {
	

	/**
	 * 获取驾校场地信息
	 * @author heyi
	 * 2016/4/2
	 * @param schoolPlaceO
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	ResultO<SchoolPlaceO> getDriverSchoolPlaceList(SchoolPlaceO schoolPlaceO, int currentPage, int pageSize,String leader);

	/**
	 * 添加场地信息
	 * @author heyi
	 * 2016/4/2
	 * @param insertedList
	 * @return
	 */
	List<String> batchInsert(List<SchoolPlaceO> insertedList);

	/**
	 * 批量修改场地信息
	 * @author heyi
	 * 2016/4/2
	 * @param updatedList
	 * @return
	 */
	List<String> batchUpdate(List<SchoolPlaceO> updatedList);

	/**
	 * 批量删除场地信息
	 * @author heyi
	 * 2016/4/2
	 * @param deletedList
	 * @return
	 */
	void batchDelete(List<SchoolPlaceO> deletedList);

	/**
	 * 按驾校名称获取驾校场地信息列表
	 * @author heyi
	 * 2016/4/3
	 */
	List<SchoolPlaceO> getSchoolPlace(String leader);

	/**
	 * 保存新增的场地
	 * liaoyun
	 * 2016-4-4
	 * @param schoolPlaceO
	 */
	public void saveNewPlace(SchoolPlaceO schoolPlaceO);

	/**
	 * 通过  id 与 驾校账号 查询 场地信息
	 * liaoyun  2016-4-4
	 * @param id
	 * @param account
	 * @return
	 */
	public SchoolPlaceO findPlaceDataById(String id, String account);

	/**
	 * 保存修改的场地信息
	 * liaoyun 2016-4-4
	 * @param place
	 */
	public void saveUpdatedPlace(SchoolPlaceO place);

	/**
	 * 删除数据库中 的记录
	 * liaoyun 2016-4-29
	 * @param id
	 * @param account
	 */
	void deleteSchoolPlace(int id, String account);


}
