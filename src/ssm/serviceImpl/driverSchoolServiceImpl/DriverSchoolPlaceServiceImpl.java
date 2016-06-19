package ssm.serviceImpl.driverSchoolServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssm.entity.common.PageO;
import ssm.entity.common.ResultO;
import ssm.entity.driverSchool.SchoolPlaceO;
import ssm.entity.stu.StudentO;
import ssm.orm.driverSchool.DriverSchoolPlaceDao;
import ssm.service.driverSchoolService.DriverSchoolPlaceService;
import ssm.util.PageUtil;
@Service
public class DriverSchoolPlaceServiceImpl implements DriverSchoolPlaceService {

	@Autowired
	private DriverSchoolPlaceDao driverSchoolPlaceDao;
	/**
	 * 查询驾校场地信息列表
	 * @author heyi
	 * 2016/4/2
	 */
	@Override
	public ResultO<SchoolPlaceO> getDriverSchoolPlaceList(SchoolPlaceO schoolPlaceO, int currentPage, int pageSize,String leader) {
		
		
			int totalNum=driverSchoolPlaceDao.getDriverSchoolPlaceListTotal(schoolPlaceO,leader);
			PageO pageO= PageUtil.getPage(currentPage, pageSize, totalNum);
			int start = (pageO.getCurrentPage()-1)*pageO.getPageSize();
			List<SchoolPlaceO> list =driverSchoolPlaceDao.getDriverSchoolPlaceList(schoolPlaceO,start,pageSize,leader);
			ResultO<SchoolPlaceO> result = new ResultO<>();
			result.setResultList(list);
			result.setPage(pageO);
			return result;
	}
	
	/**
	 * 插入驾校场地信息
	 * @author heyi
	 * 2016/4/2
	 */
	@Override
	public List<String> batchInsert(List<SchoolPlaceO> insertedList) {
		Map<String,String> map =new HashMap<>();
		//根据账号判断重复
		List<String> placeList= new ArrayList<>();
		for(SchoolPlaceO o:insertedList){
			placeList.add(o.getPlaceName());
		}
		List<String> repeatedPlaceNameList=driverSchoolPlaceDao.findRepeatedByPlaceName(placeList);
		if(repeatedPlaceNameList!=null&&repeatedPlaceNameList.size()>0){
			return repeatedPlaceNameList;
		}
		driverSchoolPlaceDao.batchInsert(insertedList);
		return null;
		
	}

	/**
	 * 批量修改驾校场地信息
	 * @author heyi
	 * 2016/4/2
	 */
	@Override
	public List<String> batchUpdate(List<SchoolPlaceO> updatedList) {
			List<String> repeatedAccountList=driverSchoolPlaceDao.findUpdateRepeatedByPlaceName(updatedList);
			if(repeatedAccountList!=null&&repeatedAccountList.size()>0){
				return repeatedAccountList;
			}
			driverSchoolPlaceDao.batchUpdate(updatedList);
			return null;
	}

	/**
	 * 批量删除驾校场地信息
	 * @author heyi
	 * 2016/4/2
	 */
	@Override
	public void batchDelete(List<SchoolPlaceO> deletedList) {
		driverSchoolPlaceDao.batchDelete(deletedList);
	}

	/**
	 * 按驾校名称获取驾校场地信息列表
	 * @author heyi
	 * 2016/4/3
	 */
	@Override
	public List<SchoolPlaceO> getSchoolPlace(String leader) {
		List<SchoolPlaceO> list =driverSchoolPlaceDao.getSchoolPlaceList(leader);
		return list;
	}

	/**
	 * 保存新增的场地
	 * liaoyun
	 * 2016-4-4
	 * @param schoolPlaceO
	 */
	@Override
	public void saveNewPlace(SchoolPlaceO schoolPlaceO) {
		driverSchoolPlaceDao.saveNewPlace(schoolPlaceO);
	}

	/**
	 * 通过  id 与 驾校账号 查询 场地信息
	 * liaoyun  2016-4-4
	 * @param id
	 * @param account
	 * @return
	 */
	@Override
	public SchoolPlaceO findPlaceDataById(String id, String account) {
		SchoolPlaceO place = driverSchoolPlaceDao.findPlaceDataById(id,account);
		return place;
	}

	/**
	 * 保存修改的场地信息
	 * liaoyun 2016-4-4
	 * @param place
	 */
	@Override
	public void saveUpdatedPlace(SchoolPlaceO place) {
		driverSchoolPlaceDao.saveUpdatedPlace(place);
	}

	/**
	 * 删除数据库中 的记录
	 * liaoyun 2016-4-29
	 * @param id
	 * @param account
	 */
	@Override
	public void deleteSchoolPlace(int id, String account) {
		driverSchoolPlaceDao.deleteSchoolPlace(id,account);
	}
}


