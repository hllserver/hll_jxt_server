package ssm.entity.privilege;

import ssm.entity.BaseEntity;
/**
 * 角色管理
 * @author liaoyun
 * 2016-3-20
 */
public class RoleO extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8984447024369959791L;
	private long id;
	private String roleCode;      //角色编号
	private String roleName;      //角色名称
	private String privilegeCode; //角色拥有的页面权限
	private String methodPrivilegeCode; //角色拥有的 方法权限
	public RoleO() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getPrivilegeCode() {
		return privilegeCode;
	}
	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}
	public String getMethodPrivilegeCode() {
		return methodPrivilegeCode;
	}
	public void setMethodPrivilegeCode(String methodPrivilegeCode) {
		this.methodPrivilegeCode = methodPrivilegeCode;
	}
	
}
