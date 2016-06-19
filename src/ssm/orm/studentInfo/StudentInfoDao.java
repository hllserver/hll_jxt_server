package ssm.orm.studentInfo;

import java.util.List;

import ssm.entity.driverSchool.SchoolO;
import ssm.entity.stu.StudentO;

public interface StudentInfoDao {
 
	/**
	 * 获取学员信息列表条数
	 * @param studentO
	 * @return
	 */
	public int getStudentInfoListTotal(StudentO studentO,String account);
	/**
	 * 获取学员信息列表
	 * @param studentO
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<StudentO> getStudentInfoList(StudentO studentO,int start,int pageSize,String account);
	
	/**
	 *根据账号判断重复
	 * @param accountList
	 * @return
	 */
	public List<String> findRepeatedByAccount(List<String> accountList);
	/**
	 * 添加学员信息
	 * @param insertedList
	 */
	public void batchInsert(List<StudentO> insertedList);
	/**
	 * 批量修改学员信息
	 * @param updatedList
	 */
	public void batchUpdate(List<StudentO> updatedList);
	/**
	 * 批量删除学员信息
	 * @param deletedList
	 */
	public void batchDelete(List<StudentO> deletedList);
	
	/**
	 * 修改时根据学员账号查重
	 * @author heyi
	 * 2016/4/4
	 * @param updatedList
	 * @return
	 */
	public List<String> findUpdateRepeatedByAccount(List<StudentO> updatedList);
}
