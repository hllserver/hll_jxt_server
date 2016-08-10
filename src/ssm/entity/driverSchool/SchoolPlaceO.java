package ssm.entity.driverSchool;

import ssm.entity.BaseEntity;
/**
 * 驾校场地
 * @author liaoyun
 * 2016-3-6
 */
public class SchoolPlaceO extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5692525244765090845L;
	private long id;
	private String schoolAccount;
	private String placeName;
	private double area;     //面积 m^2
	private int carNo;     //规模  车辆数
	private String pic1;
	private String pic2;
	private String pic3;
	private String pic4;
	private String pic5;
	private String remark;
	private String position;
	public SchoolPlaceO() {
		super();
	}
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setSchoolAccount(String schoolAccount) {
		this.schoolAccount = schoolAccount;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
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
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}

	public int getCarNo() {
		return carNo;
	}
	public void setCarNo(int carNo) {
		this.carNo = carNo;
	}
	public String getPic1() {
		return pic1;
	}
	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}
	public String getPic2() {
		return pic2;
	}
	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}
	public String getPic3() {
		return pic3;
	}
	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}
	public String getPic4() {
		return pic4;
	}
	public void setPic4(String pic4) {
		this.pic4 = pic4;
	}
	public String getPic5() {
		return pic5;
	}
	public void setPic5(String pic5) {
		this.pic5 = pic5;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void trim(){
		setSchoolAccount(getValue(getSchoolAccount()));
		setPlaceName(getValue(getPlaceName()));
		setPic1(getValue(getPic1()));
		setPic2(getValue(getPic2()));
		setPic3(getValue(getPic3()));
		setPic4(getValue(getPic4()));
		setPic5(getValue(getPic5()));
		setRemark(getValue(getRemark()));
		setPosition(getValue(getPosition()));
	}
	//需要修改，适合任何类型
	private String getValue(String s){
		if(s!=null){
			return s.trim();
		}
		return null;
	}
}
