package ssm.service.user;

import java.util.List;

import ssm.entity.common.ResultO;
import ssm.entity.privilege.PrivilegeO;
import ssm.entity.user.UserO;
/**
 * 
 * @author liaoyun
 * 2016-3-18
 */
public interface UserService {

	/**
	 * 根据 用户名 和 密码 查询用户信息
	 * @author liaoyun
	 * 2016-3-18
	 */
	public UserO findUser(String username, String password);

	/**
	 * 分页查询所有的用户 liaoyun 2016-3-26
	 * @param userO
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public ResultO<UserO> findAllUser(UserO userO, int currentPage, int pageSize);

	/**
	 * 批量添加 user 
	 * liaoyun
	 * 2016-3-26
	 * @param insertedRecords
	 */
	public List<String> batchInsertUser(List<UserO> insertedRecords);

	/**
	 * 批量修改 user 
	 * liaoyun
	 * 2016-3-26
	 * @param updatedRecords
	 */
	public List<String> batchUpdateUser(List<UserO> updatedRecords);

	/**
	 * 批量删除 user 
	 * liaoyun
	 * 2016-3-26
	 * @param deletedRecords
	 */
	public void batchDeleteUser(List<UserO> deletedRecords);

	/**
	 * 查询该用户角色的页面权限
	 * liaoyun 2016-3-27
	 * @param type
	 * @return
	 */
	public List<PrivilegeO> findMypage(int type);

	/**
	 * 验证账号是否重复
	 * liaoyun 2016-4-6
	 * @param account
	 * @return
	 */
	public int findIsAccountRepeat(String account,String registMethod);

	/**
	 * 保存新注册的账号
	 * liaoyun 2016-4-7
	 * @param account
	 * @param password
	 * @param sessionMethod
	 */
	public void saveRegistAccount(String account, String password, String sessionMethod);

	/**
	 * 用邮箱登陆
	 * liaoyun 2016-4-9
	 * @param username
	 * @param password
	 * @return
	 */
	public UserO loginByEmail(String username, String password);

	/**
	 * 用电话号码登陆
	 * liaoyun 2016-4-9
	 * @param username
	 * @param password
	 * @return
	 */
	public UserO loginByPhone(String username, String password);
	
}
