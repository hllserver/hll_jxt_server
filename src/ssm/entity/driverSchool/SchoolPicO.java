package ssm.entity.driverSchool;

import ssm.entity.BaseEntity;

/**
 * 驾校相关图片
 * @author liaoyun
 * 2016-4-12
 */
public class SchoolPicO extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7813946631939377979L;
	private int id;
	private String schoolAccount;
	private int picType;          //照片类别：1--驾校资质荣誉照片;2--驾校风采照片;3--明星教练照片;
	private String pic;           //照片名字
	private String picContent;    //照片介绍
	private String extra1;
	private String extra2;
	public SchoolPicO() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSchoolAccount() {
		return schoolAccount;
	}
	public void setSchoolAccount(String schoolAccount) {
		this.schoolAccount = schoolAccount;
	}
	public int getPicType() {
		return picType;
	}
	public void setPicType(int picType) {
		this.picType = picType;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getPicContent() {
		return picContent;
	}
	public void setPicContent(String picContent) {
		this.picContent = picContent;
	}
	public String getExtra1() {
		return extra1;
	}
	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}
	public String getExtra2() {
		return extra2;
	}
	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}
	public void trim(){
		setSchoolAccount(getValue(getSchoolAccount()));
		setPic(getValue(getPic()));
		setPicContent(getValue(getPicContent()));
		setExtra1(getValue(getExtra1()));
		setExtra2(getValue(getExtra2()));
	}
	//需要修改，适合任何类型
	private String getValue(String s){
		if(s!=null){
			return s.trim();
		}
		return null;
	}
}
