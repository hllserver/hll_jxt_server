package ssm.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ssm.entity.common.RecordsO;
import ssm.entity.common.ResultO;
import ssm.entity.privilege.PrivilegeO;
import ssm.entity.user.UserO;
import ssm.service.user.UserService;
import ssm.util.CommonUtil;
import ssm.util.Email;
import ssm.util.MD5Util;
/**
 * 登陆
 * @author liaoyun
 * 2016-3-3
 */
@Controller
@Transactional
@RequestMapping("/user")
public class LoginAction {
	@Autowired
	private UserService userService;
	/**
	 * 账号登陆
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	//@Authority(code="liaoyun",desc="jdjfldjf")
	@ResponseBody
	@RequestMapping(value="/login/{username}/{password}/{type}",method=RequestMethod.POST,produces="application/json",
			consumes="application/json")
	public Map<String,String> login(HttpServletRequest request, @PathVariable String username, @PathVariable String password, HttpSession  session,@PathVariable String type){
		Map<String,String> map = new HashMap<>();
		UserO userInfo = CommonUtil.getUserInfo(session);
		if(userInfo != null){
			map.put("loginType", "已经登陆了");
			return map;
		}
		String clientIp = request.getRemoteAddr();
		int clientPort = request.getRemotePort();
		System.out.println("username "+username +"   "+"password "+password);
		if(username !=null){
			username = username.trim();
		}
		if(password != null){
			password = password.trim();
			password = MD5Util.MD5Encode(password);
		}
		UserO user = new UserO();
		if(type!=null && type.equals("email")){//邮箱登陆
			user = userService.loginByEmail(username,password);
		}else if(type != null && type.equals("phone")){//电话登陆
			user = userService.loginByPhone(username,password);
		}else if(type != null && type.equals("account")){//账号登陆
			user = userService.findUser(username,password);
		}
		if(user!=null && user.getType()<5){//管理人员  //登陆验证成功
			session.setAttribute("userInfo", user);
			map.put("account", user.getAccount());
			System.out.println("user.getAccount()  "+ user.getAccount());
			map.put("type", ""+user.getType());
			map.put("name", user.getName());
			map.put("url","index.html");
			map.put("nickName", user.getNickName());
			map.put("email", user.getEmail());
			map.put("tel", user.getTel());
			map.put("lastLoadTime", CommonUtil.getServerTime());
			map.put("lastLoadIp", clientIp);
			map.put("lastLoadPort", ""+clientPort);
			prepareWebsocket(map, user);
			return map;
		}else if(user!=null && user.getType()==5){//普通用户  //登陆验证成功
			session.setAttribute("userInfo", user);
			map.put("account", user.getAccount());
			System.out.println("user.getAccount()  "+ user.getAccount());
			map.put("type", ""+user.getType());
			map.put("nickName", user.getNickName());
			map.put("email", user.getEmail());
			map.put("tel", user.getTel());
			map.put("lastLoadTime", CommonUtil.getServerTime());
			map.put("lastLoadIp", clientIp);
			map.put("lastLoadPort", ""+clientPort);
			prepareWebsocket(map, user);
			return map;
		}else{
			//登陆验证失败
			map.put("type", "0");
			return map;
		}
	}
	
	/**
	 * 为websocket登陆做准备 liaoyun 2016-8-19
	 * @param map
	 * @param user
	 */
	private void prepareWebsocket(Map<String,String> map, UserO user){
		Integer socketKey = CommonUtil.rundom8();
		CommonUtil.webSocketMap.put(user.getAccount().trim(), socketKey);     //保存websocket登陆认证
		CommonUtil.webSocketUserInfoMap.put(user.getAccount().trim(), user);  //保存websocket用户信息
		map.put("sessionKey", socketKey+"");                                  //将 webSocketKey 发送到客户端
	}
	
	/**
	 * 分页查询所有的用户 liaoyun 2016-3-26
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/findRoleList/{currentPage}/{pageSize}",method=RequestMethod.POST,produces="application/json",
	consumes="application/json")
	public ResultO<UserO> findRoleList(@RequestBody UserO userO,@PathVariable int currentPage,@PathVariable int pageSize) throws Exception{
		ResultO<UserO> list = userService.findAllUser(userO,currentPage,pageSize);
		return list;
	}
	/**
	 * 批量保存 增、删、改 的数据 
	 * liaoyun 2016-3-26
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/saveAll",method=RequestMethod.POST,produces="application/json",consumes="application/json")
	public Map<String,Object> batchSave(@RequestBody RecordsO<UserO> records,HttpSession session) throws Exception{
		Map<String,Object> resultMap = new HashMap<>();
		UserO user = (UserO) session.getAttribute("userInfo");
		String account = user.getAccount();
//		//新增的数据
//		List<UserO> insertedRecords = records.getInsertedRecords();
		//更新的数据
		List<UserO> updatedRecords = records.getUpdatedRecords();
//		//删除的数据
//		List<UserO> deletedRecords = records.getDeletedRecords();
//		//对删除的数据
//		if(deletedRecords != null && deletedRecords.size() > 0){
//			userService.batchDeleteUser(deletedRecords);
//		}
		//对新增的数据
//		if(insertedRecords != null && insertedRecords.size() > 0){
//			for (UserO o : insertedRecords) {
//				o.setAccount(getValue(o.getAccount()));
//				o.setTel(getValue(o.getTel()));
//				o.setEmail(getValue(o.getEmail()));
//				o.setQq(getValue(o.getQq()));
//				o.setWeChat(getValue(o.getWeChat()));
//				o.setNickName(getValue(o.getNickName()));
//				o.setCreatedBy(account);
//				o.setLastUpdatedBy(account);
//				o.setPassword("123456");
//			}
//			List<String> insertResult = userService.batchInsertUser(insertedRecords);
//			if(insertResult!=null && insertResult.size()>0){
//				resultMap.put("result", insertResult);
//				return resultMap;
//			}
//		}
		//对修改的数据
		if(updatedRecords != null && updatedRecords.size() > 0){
			for (UserO o : updatedRecords) {
				o.setAccount(getValue(o.getAccount()));
				o.setTel(getValue(o.getTel()));
				o.setEmail(getValue(o.getEmail()));
				o.setQq(getValue(o.getQq()));
				o.setWeChat(getValue(o.getWeChat()));
				o.setNickName(getValue(o.getNickName()));
				o.setLastUpdatedBy(account);
			}
			List<String> updateResult = userService.batchUpdateUser(updatedRecords);
			if(updateResult != null && updateResult.size()>0){
				resultMap.put("result", updateResult);
				return resultMap;
			}
			
		}
		return resultMap;
	}
	/**
	 * 查询用户权限页面 和  基本信息
	 * liaoyun
	 * 2016-3-27
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/myInfo",method=RequestMethod.GET,produces="application/json",consumes="application/json")
	public Map<String,Object> myInfo(HttpSession  session) throws Exception{
		Map<String,Object> map = new HashMap<>();
		UserO user = (UserO) session.getAttribute("userInfo");
		map.put("nickName", user.getNickName());     //昵称
		if(user.getType()==1){
			map.put("type", "系统管理员");
		}else if(user.getType()==2){
			map.put("type", "系统普通管理员");
		}else if(user.getType()==3){
			map.put("type", "驾校管理员");
		}else if(user.getType()==3){
			map.put("type", "驾校普通管理员");
		}
		//查询该用户角色的页面权限
		List<PrivilegeO> myPage = userService.findMypage(user.getType());
		map.put("page", myPage);
		return map;
	}
	/**
	 * 退出 系统  liaoyun 2016-3-27
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpSession  session)throws Exception{
//		UserO user = CommonUtil.getUserInfo(session);
//		String account = user.getAccount().trim();
		//销毁 websocket 信息
//		CommonUtil.webSocketMap.remove(account);
//		CommonUtil.webSocketUserInfoMap.remove(account);
		session.invalidate();
		return "redirect:/welcome.html";
	}
	/**
	 * 注册账号
	 * liaoyun
	 * 2016-4-5
	 */
	@ResponseBody
	@RequestMapping(value="/registAccount/{account}/{psw}/{code}/{registMethod}",method=RequestMethod.POST,produces="application/json",consumes="application/json")
	public Map<String,Object> registerByEmail(@PathVariable String account,@PathVariable String psw, @PathVariable String code,@PathVariable String registMethod, HttpSession session){
		Map<String,Object> resultMap = new HashMap<>();
		//注册码验证
		String verifycode = (String) session.getAttribute("verifycode");
		if(verifycode==null || code==null || !code.trim().equals(verifycode)){
			resultMap.put("result", "验证码为空");
			return resultMap;
		}
		//验证账号
		String sessionAccount = (String) session.getAttribute("account");
		if(account==null || sessionAccount==null || !account.trim().equals(sessionAccount)){
			resultMap.put("result", "账号为空!");
			return resultMap;
		}
		//验证 registMethod
		String sessionMethod = (String) session.getAttribute("registMethod");
		if(registMethod==null || sessionMethod==null || !registMethod.trim().equals(sessionMethod)){
			resultMap.put("result", "注册方式异常!");
			return resultMap;
		}
		//验证账号是否重复
		int repeat = userService.findIsAccountRepeat(account.trim(),registMethod.trim());
		if(repeat>0){
			resultMap.put("result", "账号已经注册过了!");
			return resultMap;
		}
		if(psw==null || psw.equals("")){
			resultMap.put("result", "密码不能为空!");
			return resultMap;
		}
		//密码验证  6-15位 [a-zA-Z0-9]
		 Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{6,15}$");
		 Matcher matcher = pattern.matcher(psw.trim());
		 boolean bool = matcher.matches();
		 if(bool==false){
			 resultMap.put("result", "密码由 字母 和数字 组成, 6至15位!");
			 return resultMap;
		 }
		 //保存账号
		 String password = MD5Util.MD5Encode(psw.trim()); //md5加密
		 userService.saveRegistAccount(account.trim(),password,sessionMethod);
		 resultMap.put("result", "1");
		 return resultMap;
	}
	/**
	 * 发送验证码
	 * liaoyun
	 * 2016-4-5
	 */
	@ResponseBody
	@RequestMapping(value="/verifyCode/{account}/{registMethod}",method=RequestMethod.POST,produces="application/json",consumes="application/json")
	public Map<String,String> sendVerifyCode(@PathVariable String account,@PathVariable String registMethod,HttpSession session){
		Map<String,String> map = new HashMap<>();
		
		if(account!=null && !account.equals("")){
			account = account.trim();
		}else{
			map.put("result", "账号不能为空!");
			return map;
		}
		
		if(registMethod!=null && !registMethod.equals("")){
			registMethod = registMethod.trim();
		}else{
			map.put("result", "注册方式异常!");
			return map;
		}
		
		String code = "";
		if(session.getAttribute("code")!=null && !session.getAttribute("code").equals("")){
			code=(String) session.getAttribute("verifycode");
		}else{
			//生成6位随机数
			int x=(int)(Math.random()*1000000);
			code = code + x;
			//保存验证码，用于注册时对比
			session.setAttribute("verifycode", code);
		}
		session.setAttribute("account", account);
		session.setAttribute("registMethod", registMethod);
		String messageText = "注册验证码为： "+code+",请勿泄漏";
		if(registMethod!=null && registMethod.equals("email")){
			//以邮箱注册时，发送验证码到邮箱
			try {
				Email.sendMessage("smtp.sina.com", "jiaxiaohll@sina.com", "jiaxiaotong", account, "欢迎注册", messageText, "text/html;charset=gb2312");
			} catch (MessagingException e) {
				map.put("result", "邮件发送失败!");
				return map;
			}
		}
		map.put("result", "1");
		return map;
	}
	
	private String getValue(String s){
		if(s==null){
			return null;
		}else{
			return s.trim();
		}
	}
}









