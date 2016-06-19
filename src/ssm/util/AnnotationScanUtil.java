package ssm.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import privilege.Authority;

/**
 * 扫描 指定包下的 java 文件
 * @author liaoyun
 * 2014-4-18
 */
public class AnnotationScanUtil {
	/**
	 * 获得 指定包下的 所有权限注解 
	 * liaoyun 2016-4-18
	 * @param packgeName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Authority> getPrivilegeNotion(String packgeName){
		List<Authority> list = new ArrayList<>();
		//得到 包下的 所有 class 文件 
		List<Class> clas = getClassFromPackage(packgeName);
		for (Class cla : clas) {
			Method[] fields = cla.getDeclaredMethods();
			for (Method field : fields) {
				Method hand = field;
				if(hand.isAnnotationPresent(Authority.class)){
					Authority auth = hand.getAnnotation(Authority.class);
					list.add(auth);
				}
			}
		}
		return list;
	}
	
	/**
	 * 扫描 一个包
	 * liaoyun
	 * 2016-4-18
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	private static List<Class> getClassFromPackage(String packgeName){
		List<Class> clas = new ArrayList<>();
		//是否循环搜索子包
		boolean recursive=true;
		//包名对应的路径
		String packgeUrl = packgeName.replace('.', '/');
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packgeUrl);
			if(dirs.hasMoreElements()){
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if("file".equals(protocol)){//file 类型的扫描
					String filePath = URLDecoder.decode(url.getFile(), "utf-8");
					findClassUnderPackage(packgeName,filePath,recursive,clas);
				}else if("jar".equals(protocol)){//jar 类型的扫描
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return clas;
	}
	
	private static void findClassUnderPackage(String packageName, String filePath,final boolean recursive,@SuppressWarnings("rawtypes") List<Class> clas){
		File dir = new File(filePath);
		//若不是文件夹 或 不存在
		if(!dir.exists() || !dir.isDirectory()){
			return;
		}
		//在给定的目录下 找到所有的文件(包括 文件 和 文件夹) ，并进行过滤，只接收 .class 文件
		File[] dirFiles = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				boolean acceptDir = recursive && file.isDirectory(); //接收 目录
				boolean acceptClass = file.getName().endsWith(".class"); //接收 class 文件
				return acceptDir || acceptClass;
			}
		});
		for (File file : dirFiles) {
			if(file.isDirectory()){//如果是文件夹，递归
				findClassUnderPackage(packageName+"."+file.getName(),file.getAbsolutePath(),recursive,clas);
			}else{//如果是文件
				String className = file.getName().substring(0,file.getName().length()-6);
				try {
					clas.add(Thread.currentThread().getContextClassLoader().loadClass(packageName+"."+className));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
