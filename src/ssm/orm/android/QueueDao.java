package ssm.orm.android;

import java.util.List;

import ssm.entity.android.orderLean.ScheduleO;
import ssm.entity.driverSchool.SchoolPlaceO;

public interface QueueDao {

	/**
	 * 查询三天的计划 liaoyun 2016-8-12
	 * @param account
	 * @return
	 */
	public List<ScheduleO> findMyOrderLean(String account);

	/**
	 * 查询的场地 liaoyun 2016-8-12
	 * @param account
	 * @return
	 */
	public List<SchoolPlaceO> findSchoolPlace(String account);

}