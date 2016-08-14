package ssm.service.android;

import java.util.List;
import ssm.entity.android.orderLean.ScheduleO;
import ssm.entity.driverSchool.SchoolPlaceO;

/**
 * 排队相关
 * @author liaoyun 2016-8-12
 */
public interface QueueService {

	/**
	 * 查询的场地 liaoyun 2016-8-12
	 * @param account
	 * @return
	 */
	public List<SchoolPlaceO> findSchoolPlace(String account);

	/**
	 * 查询三天的计划 liaoyun 2016-8-12
	 * @param account
	 * @return
	 */
	public List<ScheduleO> findMyOrderLean(String account);
	
}
