package ssm.entity.driverSchool;

import ssm.entity.BaseEntity;
/**
 * ���� ��Ϣ
 * @author liaoyun
 * 2016-3-6
 */
public class SchoolInstructorO extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8588685096718910877L;
	private long id;
	private String schoolAccount;
	private String userAccount;     //教练的账号
	private String jobNumber;//
	private String name;
	private int age;
	private String tel;
	private int gender;      //0---女  1---男
	private String email;
	private int online;      //是否在线 0---不在线，1---在线
	private String remark;
	public SchoolInstructorO() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSchoolAccount() {
		return schoolAccount;
	}
	public void setSchoolAccount(String schoolAccount) {
		this.schoolAccount = schoolAccount;
	}
	
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getOnline() {
		return online;
	}
	public void setOnline(int online) {
		this.online = online;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
