package ssm.serviceImpl.driverSchoolServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssm.entity.common.PageO;
import ssm.entity.common.ResultO;
import ssm.entity.driverSchool.SchoolAdO;
import ssm.entity.driverSchool.SchoolInstructorO;
import ssm.entity.driverSchool.SchoolO;
import ssm.entity.driverSchool.SchoolPicO;
import ssm.orm.driverSchool.DriverSchoolDao;
import ssm.service.driverSchoolService.DriverSchoolService;
import ssm.util.PageUtil;
@Service
public class DriverSchoolServiceImpl implements DriverSchoolService {

	@Autowired
	private DriverSchoolDao driverSchoolDao;
	
	/**
	 * 查询驾校信息
	 * heyi 
	 * 2016/3/22
	 */
	@Override
	public ResultO<SchoolO> getDriverSchoolList(SchoolO schoolO,int currentPage,int pageSize){
		int totalNum=driverSchoolDao.getDriverSchoolListTotal(schoolO);
		PageO pageO= PageUtil.getPage(currentPage, pageSize, totalNum);
		int start = (pageO.getCurrentPage()-1)*pageO.getPageSize();
		List<SchoolO> list =driverSchoolDao.getDriverSchoolList(schoolO,start,pageSize);
		ResultO<SchoolO> result = new ResultO<>();
		result.setResultList(list);
		result.setPage(pageO);
		return result;
		
	}
	
	/**
	 * 批量插入数据
	 * heyi
	 * 2016/3/22
	 */
	
	@Override
	public List<String>  batchInsert(List<SchoolO> insertedList) {
		List<String> accountList= new ArrayList<>();
		for(SchoolO o:insertedList){
			accountList.add(o.getAccount());
		}
		List<String> repeatedAccountList=driverSchoolDao.findRepeatedByAccount(accountList);
		if(repeatedAccountList!=null&&repeatedAccountList.size()>0){
			return repeatedAccountList;
		}
		driverSchoolDao.batchInsert(insertedList);
		return null;
	}

	
	/**
	 * 批量修改数据
	 * heyi
	 * 2016/3/22
	 */
	@Override
	public List<String> batchUpdate(List<SchoolO> updatedList) {
       Map<String,String> map=new HashMap<>();
		List<String> repeatedAccountList=driverSchoolDao.findUpdateRepeatedByAccount(updatedList);
		if(repeatedAccountList!=null&&repeatedAccountList.size()>0){
			return repeatedAccountList;
		}
		driverSchoolDao.batchUpdate(updatedList);
		return null;
	}

	/**
	 * 批量删除数据
	 * heyi
	 * 2016/3/22
	 */
	@Override
	public void batchDelete(List<SchoolO> deletedList) {

	driverSchoolDao.batchDelete(deletedList);
	}

	/**
	 * 获取自己驾校信息 liaoyun 2016-3-27
	 * @param account
	 * @return
	 */
	@Override
	public SchoolO getDriverSchoolMsg(String account) {
		List<SchoolO> list = driverSchoolDao.findDriverSchoolMsg(account);
		if(list != null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 保存修改的驾校信息数据 liaoyun 2016-3-27
	 * @param school
	 * @param account
	 */
	@Override
	public void saveSchoolMsg(SchoolO school, String account) {
		driverSchoolDao.saveSchoolMsg(school,account);
	}

	/**
	 * 查询 驾校 所属的 教练 liaoyun 2016-3-27
	 */
	@Override
	public ResultO<SchoolInstructorO> getDriverSchoolList(SchoolInstructorO schoolInstructorO, int currentPage,
			int pageSize, String account) {
		int totalNum=driverSchoolDao.getDriverInstructorListTotal(schoolInstructorO,account);
		PageO pageO= PageUtil.getPage(currentPage, pageSize, totalNum);
		int start = (pageO.getCurrentPage()-1)*pageO.getPageSize();
		List<SchoolInstructorO> list =driverSchoolDao.getDriverInstructorList(schoolInstructorO,start,pageSize,account);
		ResultO<SchoolInstructorO> result = new ResultO<>();
		result.setResultList(list);
		result.setPage(pageO);
		return result;
	}

	/**
	 * 新增  驾校的教练 liaoyun 2016-3-27
	 * @param insertedRecords
	 */
	@Override
	public List<String> insertSchoolInstructor(List<SchoolInstructorO> insertedRecords,String account) {
		List<String> repeat = new ArrayList<>();
		//传入数据重复校验
		int size = insertedRecords.size();
		for(int i=0; i<size-1; i++){
			String jobNoi = insertedRecords.get(i).getJobNumber();
			for(int j=i+1; j<size; j++){
				String jobNoj = insertedRecords.get(j).getJobNumber();
				if(jobNoi==null || jobNoj==null || jobNoi.trim().equals(jobNoj.trim())){
					repeat.add(jobNoj);
					return repeat;
				}
			}
		}
		//传入的数据 与 数据库 中的数据不重复
		List<String> jobNumberList= new ArrayList<>();
		for(SchoolInstructorO o:insertedRecords){
			jobNumberList.add(o.getJobNumber().trim());
		}
		List<String> repeatedJobNumberList=driverSchoolDao.findRepeatedByJobNumber(jobNumberList,account);
		if(repeatedJobNumberList!=null&&repeatedJobNumberList.size()>0){
			return repeatedJobNumberList;
		}
		
		driverSchoolDao.insertSchoolInstructor(insertedRecords,account);
		return null;
	}

	/**
	 * 修改  驾校的教练 liaoyun 2016-3-27
	 * @param uptatedRecords
	 * @return 
	 */
	@Override
	public List<String> updateSchoolInstructor(List<SchoolInstructorO> uptatedRecords,String account) {
		List<String> repeatedJobNumberList = new ArrayList<>();
		//传入的数据不能重复
		int size = uptatedRecords.size();
		for(int i=0; i<size-1; i++){
			String jobNumi = uptatedRecords.get(i).getJobNumber();
			for(int j=i+1; j<size; j++){
				String jobNumj = uptatedRecords.get(j).getJobNumber();
				if(jobNumi==null || jobNumj==null || jobNumi.trim().equals(jobNumj.trim())){
					repeatedJobNumberList.add(jobNumj);
					return repeatedJobNumberList;
				}
			}
		}
		//传入的数据  与 数据库 中的数据不能重复
		List<SchoolInstructorO> jobNumberList=driverSchoolDao.findUpdatedRepeatedByJobNumber(uptatedRecords,account);
		for (SchoolInstructorO so : jobNumberList) {
			long idi = so.getId();
			String jobNoi = so.getJobNumber()==null ? "" : so.getJobNumber().trim();
			for(int i=0; i<size; i++){
				long idj = uptatedRecords.get(i).getId();
				String jobNoj = uptatedRecords.get(i).getJobNumber().trim();
				if(jobNoi.equals(jobNoj) && idi != idj){
					repeatedJobNumberList.add(jobNoj);
				}
			}
		}
		if(repeatedJobNumberList != null && repeatedJobNumberList.size()>0){
			return repeatedJobNumberList;
		}
		
		driverSchoolDao.updateSchoolInstructor(uptatedRecords,account);
		return null;
	}

	/**
	 * 剔除  驾校的教练 liaoyun 2016-3-27
	 * @param deletedRecords
	 */
	@Override
	public void deleteSchoolInstructor(List<SchoolInstructorO> deletedRecords,String account) {
		driverSchoolDao.deleteSchoolInstructor(deletedRecords,account);
	}

	/**
	 * 广告数据存入数据库
	 * liaoyun 2016-4-2
	 * @param schoolAd
	 * @param account
	 */
	@Override
	public void saveAdInfo(SchoolAdO schoolAd, String schoolAcount) {
		driverSchoolDao.saveAdInfo(schoolAd, schoolAcount);
	}

	/**
	 * 广告数据修改存入数据库
	 * heyi 2016-4-10
	 * @param schoolAd
	 * @param account
	 */
	@Override
	public void saveUpdateAdInfo(SchoolAdO schoolAd, String schoolAcount) {
		driverSchoolDao.saveUpdateAdInfo(schoolAd,schoolAcount);
	}

	/**
	 * 获取广告列表
	 * heyi 2016-4-10
	 * @param schoolAd
	 * @param account
	 */
	@Override
	public List<SchoolAdO> getAdSchoolName(String account) {
		List<SchoolAdO> list =driverSchoolDao.getAdSchoolName(account);
		return list;
	}

	/*
	 * 删除广告
	 * @author heyi
	 * 2016/4/11
	 */
	@Override
	public int deleteAd(int id,String schoolAccount1) {
		int result=driverSchoolDao.deleteAd(id,schoolAccount1);
		return result;
	}

	/**
	 * 根据id和驾校账号查找广告
	 * @author heyi
	 * 2016/4/16
	 */
	@Override
	public SchoolAdO getAdByIdAndAccount(int id, String schoolAccount1) {
		SchoolAdO ad=driverSchoolDao.getAdByIdAndAccount(id,schoolAccount1);
		return ad;
	}

	/**
	 * 该账号是否有驾校
	 * liaoyun 2016-4-11
	 * @param account
	 * @return
	 */
	@Override
	public int isHasDriverSchool(String account) {
		return driverSchoolDao.isHasDriverSchool(account);
	}

	/**
	 * 添加 驾校信息
	 * liaoyun 2016-4-11
	 * @param school
	 * @param account
	 */
	@Override
	public void insertSchoolMsg(SchoolO school, String account) {
		driverSchoolDao.insertSchoolMsg(school,account);
		
	}

	/**
	 * 保存驾校相关照片
	 * liaoyun 2016-4-12
	 * @param picO
	 * @param account
	 */
	@Override
	public void saveSchoolPic(SchoolPicO picO) {
		driverSchoolDao.saveSchoolPic(picO);
	}

	/**
	 * 根据 school account 获取 驾校详细信息
	 * liaoyun 2016-4-16
	 * @return
	 */
	@Override
	public SchoolO getRecommendSchoolInfo(String schoolAccount) {
		SchoolO so = driverSchoolDao.getRecommendSchoolInfo(schoolAccount);
		return so;
	}

	/**
	 * 根据 学校账号 查询 驾校照片
	 * liaoyun 2016-4-16
	 * @param schoolAccount
	 * @param type
	 * @return
	 */
	@Override
	public List<SchoolPicO> findHonorPic(String schoolAccount,String type) {
		List<SchoolPicO> o = driverSchoolDao.findHonorPic(schoolAccount,type);
		return o;
	}
}
