package ssm.serviceImpl.studentInfoServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssm.entity.common.PageO;
import ssm.entity.common.ResultO;
import ssm.entity.driverSchool.SchoolO;
import ssm.entity.stu.StudentO;
import ssm.orm.studentInfo.StudentInfoDao;
import ssm.service.studentInfoService.StudentInfoService;
import ssm.util.PageUtil;
@Service
public class StudentInfoServiceImpl implements StudentInfoService {

	@Autowired
	StudentInfoDao studentInfoDao;
	/**
	 * 获取驾校信息列表
	 * @author heyi
	 * 2016/3/27
	 */
	@Override
	public ResultO<StudentO> getStudentInfoList(StudentO studentO, int currentPage, int pageSize,String account) {

		int totalNum=studentInfoDao.getStudentInfoListTotal(studentO,account);
		PageO pageO=PageUtil.getPage(currentPage, pageSize, totalNum);
		int start=(pageO.getCurrentPage()-1)*pageO.getPageSize();
		List<StudentO> list=studentInfoDao.getStudentInfoList(studentO, start, pageSize,account);
		ResultO<StudentO> result=new ResultO<StudentO>();
		result.setPage(pageO);
		result.setResultList(list);
		return result;
	}
	
	//批量插入数据
	@Override
	public List<String> batchInsert(List<StudentO> insertedList) {

		Map<String,String> map =new HashMap<>();
		//根据账号判断重复
		List<String> accountList= new ArrayList<>();
		for(StudentO o:insertedList){
			accountList.add(o.getAccount());
		}
		List<String> repeatedAccountList=studentInfoDao.findRepeatedByAccount(accountList);
		if(repeatedAccountList!=null&&repeatedAccountList.size()>0){
			return repeatedAccountList;
		}
		studentInfoDao.batchInsert(insertedList);
		return null;
		
	}

	/**
	 * 批量修改学生数据
	 * @author heyi
	 * 2016/4/4
	 */
	@Override
	public List<String> batchUpdate(List<StudentO> updatedList) {
			List<String> repeatedAccountList=studentInfoDao.findUpdateRepeatedByAccount(updatedList);
			if(repeatedAccountList!=null&&repeatedAccountList.size()>0){
				return repeatedAccountList;
			}
			studentInfoDao.batchUpdate(updatedList);
			return null;
	}

	/**
	 * 批量删除学生数据
	 * @author heyi
	 * 2016/4/22
	 */
	@Override
	public void batchDelete(List<StudentO> deletedList) {
		studentInfoDao.batchDelete(deletedList);
	}

}
