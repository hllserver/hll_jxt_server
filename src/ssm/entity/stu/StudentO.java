package ssm.entity.stu;

import ssm.entity.BaseEntity;
/**
 * 学生类实体
 * @author liaoyun
 * 2016-3-6
 */
public class StudentO extends BaseEntity{

	private static final long serialVersionUID = -788677175777268169L;
	private long id;
	private String name;
	private int privilegeFlag; //权限标记：0没权限，1有权限
	private String nickname;
	private String account;
	private String tel;
	private String email;
	private int carType;
	private String qq;
	private String wechat;
	private int gender;        //0男，1女
	private String password;  
	private String schoolId;
	private int age;
	private String address;   //身份证地址
	private String idCard;    //身份证号码
	private int present;      //在场
	private int wantToGo;     //是否想要去     0不想去，1想去
	private int trainingStatus;//̬训练状态
	private int examStatus;   //考试状态̬
	private String gps;       //定位
	private int healthExamination; //体检情况 0未体检，1已体检
	private String schoolAccount;//学生所属驾校
	public StudentO() {
		super();
	}
	
	public String getSchoolAccount() {
		return schoolAccount;
	}

	public void setSchoolAccount(String schoolAccount) {
		this.schoolAccount = schoolAccount;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	public int getCarType() {
		return carType;
	}

	public void setCarType(int carType) {
		this.carType = carType;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getPrivilegeFlag() {
		return privilegeFlag;
	}
	public void setPrivilegeFlag(int privilegeFlag) {
		this.privilegeFlag = privilegeFlag;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public int getPresent() {
		return present;
	}
	public void setPresent(int present) {
		this.present = present;
	}
	public int getWantToGo() {
		return wantToGo;
	}
	public void setWantToGo(int wantToGo) {
		this.wantToGo = wantToGo;
	}
	public int getTrainingStatus() {
		return trainingStatus;
	}
	public void setTrainingStatus(int trainingStatus) {
		this.trainingStatus = trainingStatus;
	}
	public int getExamStatus() {
		return examStatus;
	}
	public void setExamStatus(int examStatus) {
		this.examStatus = examStatus;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	public int getHealthExamination() {
		return healthExamination;
	}
	public void setHealthExamination(int healthExamination) {
		this.healthExamination = healthExamination;
	}
}
