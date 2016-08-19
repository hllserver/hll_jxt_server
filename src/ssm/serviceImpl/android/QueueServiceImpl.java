package ssm.serviceImpl.android;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssm.entity.android.Queue;
import ssm.entity.android.orderLean.ScheduleO;
import ssm.entity.driverSchool.SchoolPlaceO;
import ssm.orm.android.QueueDao;
import ssm.service.android.QueueService;
/**
 * @author liaoyun 2016-8-12
 */
@Service
public class QueueServiceImpl implements QueueService{
	
	@Autowired
	private QueueDao queueDao;
	@Override
	public List<SchoolPlaceO> findSchoolPlace(String account) {
		List<SchoolPlaceO> list = queueDao.findSchoolPlace(account);
		return list;
	}

	@Override
	public List<ScheduleO> findMyOrderLean(String account) {
		List<ScheduleO> list = queueDao.findMyOrderLean(account);
		return list;
	}

	@Override
	public void saveSchedule(String account,List<ScheduleO> schedule) {
		queueDao.saveSchedule(account,schedule);
	}

	@Override
	public List<Queue> insertAndGetLastQueueState(String account, String placeId) {
		return queueDao.insertAndGetLastQueueState(account, placeId);
	}

	@Override
	public List<Queue> findLastQueueStateByPlace(String placeId) {
		return queueDao.findLastQueueStateByPlace(placeId);
	}

	@Override
	public List<SchoolPlaceO> hasPlaceIdByAccountAndPlaceId(String account, String placeId) {
		List<SchoolPlaceO> a = queueDao.hasPlaceIdByAccountAndPlaceId(account,placeId);
		return a;
	}

	@Override
	public List<Queue> giveUpQueue(String account,String placeId) {
		List<Queue> list = queueDao.giveUpQueue(account,placeId);
		return list;
	}
}
