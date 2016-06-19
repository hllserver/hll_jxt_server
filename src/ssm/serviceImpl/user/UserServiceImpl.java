package ssm.serviceImpl.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssm.entity.common.PageO;
import ssm.entity.common.ResultO;
import ssm.entity.privilege.PrivilegeO;
import ssm.entity.user.UserO;
import ssm.orm.user.UserDao;
import ssm.service.user.UserService;
import ssm.util.PageUtil;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	/**
	 * 根据 用户名 和 密码 查询用户信息
	 * @author liaoyun
	 * 2016-3-18
	 */
	@Override
	public UserO findUser(String username, String password) {
		List<UserO> user = userDao.findUser(username,password);
		if(user!=null && user.size()>0){
			return user.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 分页查询所有的用户 liaoyun 2016-3-26
	 * @param userO
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public ResultO<UserO> findAllUser(UserO userO, int currentPage, int pageSize) {
		//查询用户 的数量
		int total = userDao.findTotalUser(userO);
		PageO pageO = PageUtil.getPage(currentPage, pageSize, total);
		int start = (pageO.getCurrentPage()-1)*pageO.getPageSize();
		List<UserO> list = userDao.findAllUser(userO,start,pageSize);
		ResultO<UserO> result = new ResultO<UserO>();
		result.setResultList(list);
		result.setPage(pageO);
		return result;
	}
	/**
	 * 批量添加 user 
	 * liaoyun
	 * 2016-3-26
	 * @param insertedRecords
	 */
	@Override
	public List<String> batchInsertUser(List<UserO> insertedRecords) {
		List<String> list = userDao.findInsertUserRepeat(insertedRecords);
		if(list!=null && list.size()>0){//如果添加 的 数据 重复
			return list;
		}
		userDao.batchInsertUser(insertedRecords);
		return null;
	}
	/**
	 * 批量修改 user 
	 * liaoyun
	 * 2016-3-26
	 * @param insertedRecords
	 */
	@Override
	public List<String> batchUpdateUser(List<UserO> updatedRecords) {
//		List<String> list = userDao.findUpdateUserRepeat(updatedRecords);
//		if(list != null && list.size()>0){
//			return list;
//		}
		userDao.batchUpdateUser(updatedRecords);
		return null;
	}
	/**
	 * 批量删除 user 
	 * liaoyun
	 * 2016-3-26
	 * @param insertedRecords
	 */
	@Override
	public void batchDeleteUser(List<UserO> deletedRecords) {
		userDao.batchDeleteUser(deletedRecords);
	}
	/**
	 * 查询该用户角色的页面权限
	 * liaoyun 2016-3-27
	 * @param type
	 * @return
	 */
	@Override
	public List<PrivilegeO> findMypage(int type) {
		//查询  该角色的 权限 code
		List<String> code = userDao.findPriCodeByType(type);
//		if(code ==null || code.size()==0){
//			
//		}
		//以逗号为分隔 将 权限 拆分为 字符串数组
		if(code != null && code.size()>0){
			String codeArray[]=code.get(0).split(",");
			//转为list
			List<String> list = new ArrayList<>();
			for(int i=0; i<codeArray.length; i++){
				list.add(codeArray[i]);
			}
			//查询 该角色拥有的  页面
			List<PrivilegeO> priPageList = userDao.findPriPageList(list);
			return priPageList;
		}
		return null;
	}
	/**
	 * 验证账号是否重复
	 * liaoyun 2016-4-6
	 * @param account
	 * @return
	 */
	@Override
	public int findIsAccountRepeat(String account,String registMethod) {
		if(registMethod.equals("email")){
			int a = userDao.findIsEmailAccountRepeat(account);
			return a;
		}else if(registMethod.equals("phone")){
			int a = userDao.findIsPhoneAccountRepeat(account);
			return a;
		}
		return 2;
	}
	/**
	 * 保存新注册的账号
	 * liaoyun 2016-4-7
	 * @param account
	 * @param password
	 * @param sessionMethod
	 */
	@Override
	public void saveRegistAccount(String account, String password, String sessionMethod) {
		Date da = new Date();
		long data = da.getTime();
		if(sessionMethod.equals("email")){//用邮箱注册时
			userDao.saveRegistAccountByMail(account,password);
			//查询刚刚注册账号的 id
			int id = userDao.findMailRegistId(account,password);
			//保存 account leader createdBy lastUpdatedBy
			String acc = ""+data+id;
			userDao.finishMailRegist(id,acc);
		}else if(sessionMethod.equals("phone")){//用电话注册时
			userDao.saveRegistAccountByPhone(account,password);
			int id = userDao.findPhoneRegistId(account,password);
			//保存 account leader createdBy lastUpdatedBy
			String acc = ""+data+id;
			userDao.finishPhoneRegist(id,acc);
		}
		
	}
	/**
	 * 用邮箱登陆
	 * liaoyun 2016-4-9
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public UserO loginByEmail(String username, String password) {
		UserO user = userDao.loginByEmail(username, password);
		return user;
	}
	/**
	 * 用电话号码登陆
	 * liaoyun 2016-4-9
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public UserO loginByPhone(String username, String password) {
		UserO user = userDao.loginByPhone(username, password);
		return user;
	}

}
