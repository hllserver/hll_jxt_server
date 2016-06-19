package ssm.entity.privilege;

import ssm.entity.BaseEntity;
/**
 * Ȩ�� ��Ϣ
 * @author liaoyun
 * 2016-3-6
 */
public class PrivilegeO extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1738895119924370165L;
	private long id;
	private String priCode;  //权限 编号
	private String priDesc;  //权限 说明
	private int priType;     //权限类型 0--方法权限;1---链接权限
	private String PriUrl;   //链接 url
	public PrivilegeO() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPriCode() {
		return priCode;
	}
	public void setPriCode(String priCode) {
		this.priCode = priCode;
	}
	public String getPriDesc() {
		return priDesc;
	}
	public void setPriDesc(String priDesc) {
		this.priDesc = priDesc;
	}
	public int getPriType() {
		return priType;
	}
	public void setPriType(int priType) {
		this.priType = priType;
	}
	public String getPriUrl() {
		return PriUrl;
	}
	public void setPriUrl(String priUrl) {
		PriUrl = priUrl;
	}
	
}
