package ssm.entity.driverSchool;

import ssm.entity.BaseEntity;
/**
 * ∆¿º€ –≈œ¢
 * @author liaoyun
 * 2016-3-6
 */
public class SchoolAppraiseO extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4523823354783021121L;
	private long id;
	private long schoolId;
	private String nickName;
	private String account;
	private String content;
	public SchoolAppraiseO() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
