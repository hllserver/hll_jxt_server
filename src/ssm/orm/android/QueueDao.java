package ssm.orm.android;

import java.util.List;

import ssm.entity.android.Queue;
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

	/**
	 * 保存三天的预约计划 liaoyun 2016-8-13
	 * @param account 
	 * @param schedule
	 */
	public void saveSchedule(String account, List<ScheduleO> schedule);

	/**
	 * 加入排队 并 返回 队列的最新情况 liaoyun 2016-8-14
	 * @param account
	 * @param placeId
	 * @return
	 */
	public List<Queue> insertAndGetLastQueueState(String account, String placeId);

	/**
	 * 查询场地最新排队情况 liaoyun 2016-8-14
	 * @param placeId
	 * @return
	 */
	public List<Queue> findLastQueueStateByPlace(String placeId);

	/**
	 * 查询用户是否报名了该驾校，是否可以查看该场地的排队信息 liaoyun 2016-8-15
	 * @param account
	 * @param placeId
	 * @return
	 */
	public List<SchoolPlaceO> hasPlaceIdByAccountAndPlaceId(String account, String placeId);

	/**
	 * 放弃当前的排队 liaoyun 2016-8-15
	 * @param account
	 * @param placeId 
	 * @return
	 */
	public List<Queue> giveUpQueue(String account, String placeId);

}
