package ssm.serviceImpl.android;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
