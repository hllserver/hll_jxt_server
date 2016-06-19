package ssm.orm.user;

import java.util.List;

import ssm.entity.privilege.PrivilegeO;
import ssm.entity.user.UserO;
import ssm.orm.SqlMapper;

public interface UserDao extends SqlMapper{

	/**
	 * 根据 用户名 和 密码 查询用户信息
	 * @author liaoyun
	 * 2016-3-18
	 */
	public List<UserO> findUser(String username, String password);

	/**
	 * 查询用户 的数量 liaoyun 2016-3-26
	 * @param userO
	 * @return
	 */
	public int findTotalUser(UserO userO);

	/**
	 * 分页查询所有的用户 liaoyun 2016-3-26
	 * @param userO
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<UserO> findAllUser(UserO userO, int start, int pageSize);

	/**
	 * 批量添加 user 
	 * liaoyun
	 * 2016-3-26
	 * @param insertedRecords
	 */
	public void batchInsertUser(List<UserO> insertedRecords);

	/**
	 * 批量修改 user 
	 * liaoyun
	 * 2016-3-26
	 * @param insertedRecords
	 */
	public void batchUpdateUser(List<UserO> updatedRecords);

	/**
	 * 批量删除 user 
	 * liaoyun
	 * 2016-3-26
	 * @param insertedRecords
	 */
	public void batchDeleteUser(List<UserO> deletedRecords);

	/**
	 * 查询  该角色的 权限 code
	 * liaoyun 2016-3-27
	 * @param type
	 * @return
	 */
	public List<String> findPriCodeByType(int type);

	/**
	 * 查询 该角色拥有的  页面 liaoyun 2016-3-27
	 * @param list
	 * @return
	 */
	public List<PrivilegeO> findPriPageList(List<String> list);

	/**
	 * 查询新添加的用户是否重复
	 * liaoyun 2016-4-3
	 * @param insertedRecords
	 * @return
	 */
	public List<String> findInsertUserRepeat(List<UserO> insertedRecords);

	/**
	 * 查询 修改用户 是否重复
	 * liaoyun 2016-4-3
	 * @param updatedRecords
	 * @return
	 */
	public List<String> findUpdateUserRepeat(List<UserO> updatedRecords);

	/**
	 * 验证邮箱账号是否重复
	 * liaoyun 2016-4-6
	 * @param account
	 * @return
	 */
	public int findIsEmailAccountRepeat(String account);

	/**
	 * 验证电话号是否重复
	 * liaoyun 2016-4-6
	 * @param account
	 * @return
	 */
	public int findIsPhoneAccountRepeat(String account);

	/**
	 * 用 邮箱注册 账号 liaoyun 2016-4-7
	 * @param account
	 * @param password
	 * @param data
	 */
	public void saveRegistAccountByMail(String account, String password);

	/**
	 * 用 电话注册 账号 liaoyun 2016-4-7
	 * @param account
	 * @param password
	 * @param data
	 */
	public void saveRegistAccountByPhone(String account, String password);

	/**
	 * 查询刚刚 用邮箱 注册账号的 id
	 * liaoyun 2016-4-9
	 * @param account
	 * @param password
	 * @return
	 */
	public int findMailRegistId(String account, String password);

	/**
	 * 查询刚刚 用电话 注册账号的 id
	 * liaoyun 2016-4-9
	 * @param account
	 * @param password
	 * @return
	 */
	public int findPhoneRegistId(String account, String password);

	/**
	 * 完成 email 注册 
	 * liaoyun 2016-4-9
	 * @param id
	 * @param acc
	 */
	public void finishMailRegist(long id, String acc);

	/**
	 * 完成 电话 注册 
	 * liaoyun 2016-4-9
	 * @param id
	 * @param acc
	 */
	public void finishPhoneRegist(long id, String acc);

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
