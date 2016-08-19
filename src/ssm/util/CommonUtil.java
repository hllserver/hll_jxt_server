package ssm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ssm.entity.user.UserO;
/**
 * 上传下载相关方法
 * @author liaoyun
 * 2016-8-10
 */
public class CommonUtil {
	
	private static Logger logger = Logger.getLogger(CommonUtil.class);
	
	/**
	 * liaoyun 2016-8-19
	 * 保存 account ---- websocket key
	 */
	public static Map<String,Integer> webSocketMap = new HashMap<>();
	public static Map<String,UserO>   webSocketUserInfoMap = new HashMap<>();
	/**
	 * 生成8位随机数 liaoyun 2016-8-19
	 * @return
	 */
	public static int rundom8(){           
		int x=(int)(Math.random()*100000000);
		return x;
	}
	/**
	 * 获取保存图片的路径
	 * @author liaoyun 2016-8-10
	 * @param request
	 * @return
	 */
	public static String getPicUrl(HttpServletRequest request){
		String dir = request.getSession().getServletContext().getRealPath("/");
		if(!dir.endsWith(File.separator)){
			dir = dir+File.separator;
		}
		dir = dir + "file/pic";
		File docu = new File(dir);
		if(!docu.exists()){
			docu.mkdirs();
		}
		dir = request.getSession().getServletContext().getRealPath("/file/pic");
		return dir;
	}
	/**
	 * 设置 response 输出的各种参数,并返回writer
	 * @param response
	 * @return 
	 */
	public static PrintWriter getResponseWriter(HttpServletResponse response){
		response.setContentType("text/html;charset='utf-8'");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("content-type", "text/html;charset=utf-8");
		try {
			return response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据个人登陆信息，获取驾校账号
	 * liaoyun 2016-8-10
	 * @param session
	 * @return
	 */
	public static String getSchoolAccount(HttpSession session){
		UserO user = getUserInfo(session);
		String schoolAccount="";
		if(user.getType()==4){               //如果是驾校的职员
			schoolAccount = user.getLeader();
		}else if(user.getType()==3){         //如果是驾校的管理员
			schoolAccount = user.getAccount();
		}
		return schoolAccount.trim();	
	}
	/**
	 * 获取登陆的用户的信息
	 * liaoyun 2016-8-10
	 * @param session
	 * @return
	 */
	public static UserO getUserInfo(HttpSession session){
		UserO user = (UserO) session.getAttribute("userInfo");
		return user;
	} 
	
	/**
	 * 保存文件
	 * @param MultipartHttpServletRequest request
	 * @param HttpSession session
	 * @param file      
	 * @param serialNum 文件的序号，当同时上传多个文件时，添加一个序号，防止文件同名，默认为0
	 * @return new file name
	 */
	public static String savePic(MultipartHttpServletRequest request,HttpSession session,MultipartFile file,int serialNum){
		UserO user = (UserO) session.getAttribute("userInfo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String origFileName = file.getOriginalFilename();                                //文件原始名称
		int pos = origFileName.lastIndexOf(".");
		if(pos == -1){
			logger.warn("the file "+origFileName+" has no suffix");
			return null;
		}
		String suffix = origFileName.substring(pos, origFileName.length());              //文件后缀
		String newName = user.getAccount() + sdf.format(new Date()) + serialNum + suffix;//文件取别名，防止同名
		File targetFile = new File(CommonUtil.getPicUrl(request), newName);              //新文件全路径
		try {
			targetFile.createNewFile();
			InputStream isRef = file.getInputStream();                                   //得到文件的流
			FileOutputStream fosRef = new FileOutputStream(targetFile);                  //文件输出流
			IOUtils.copy(isRef, fosRef); 
		} catch (IOException e) {
			logger.error(e);
			return null;
		}
		return newName;
	}
	
	/**
	 * 删除图片，根据图片名称
	 * liaoyun 2016-8-10
	 * @param picName
	 * @return
	 */
	public static boolean deletePic(HttpServletRequest request,String picName){
		try{
			String picUrl = getPicUrl(request);
			if(picName != null && !picName.equals("")){
				picName = picName.trim();
			}else{
				logger.info("picture name is empty!");
				return true;
			}
			File deleFile = new File(picUrl,picName);
			if (deleFile.isFile()) {
				deleFile.delete();
			}
			return true;
		}catch(Exception e){
			logger.error(e);
			return false; 
		}
	}
	/**
	 * 获取当前服务器时间
	 * liaoyun 2016-8-11
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getServerTime(){
		Date now  = new Date();
		int year  = now.getYear() + 1900;
		int month = now.getMonth() + 1;
		int day   = now.getDate();
		int hour  = now.getHours();
		int min   = now.getMinutes();
		int sec   = now.getSeconds();
		String time = year+","+month+","+day+","+hour+","+min+","+sec;
		return time;
	}
	/**
	 * 获取用户登陆信息
	 * liaoyun 2016-8-11
	 * @param session
	 * @return
	 */
	public UserO getSessionInfo(HttpSession session){
		UserO user = (UserO) session.getAttribute("userInfo");
		return user;
	}
	
	/**
	 * 判断用户是否登陆了 0---没有登陆；1---已经登陆
	 * liaoyun 2016-8-11
	 * @param session
	 * @return
	 */
	public static int isLogin(HttpSession session){
		UserO user = CommonUtil.getUserInfo(session);
		if(user == null){
			return 0;
		}else{
			return 1;
		}
	}
}
