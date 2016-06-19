package privilege;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 权限 拦截 
 * @author liaoyun
 * 
 */
public class AuthorityAnnotationIntercepter  extends HandlerInterceptorAdapter {
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		//获取访问的  Controller中的 Method （方法）
		HandlerMethod handle =  (HandlerMethod) arg2;
		//得到 Method 上的 Authority注解
		Authority authority = handle.getMethodAnnotation(Authority.class);
		// 当无 authority 注解时
		if(authority==null){
			return true;
		}
		String code = authority.code().trim();
		HttpSession session = arg0.getSession();
		String[] auth = (String[]) session.getAttribute("authority");
		int authLength = 0;
		if(auth!=null){
			authLength=auth.length;
		}
		for(int i=0; i<authLength; i++){
			if(code.equals(auth[i].trim())){//通过权限 校验
				return true;
			}
		}
		//当以 普通 page 方式 反回时
		if(authority.resultType().equals(ResultType.page)){
			//跳转到登陆页面
			arg1.sendRedirect(arg0.getContextPath()+"/welcome.html");
		}else{
			//以 json方式
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("text/html,charset=utf-8");
			OutputStream out = arg1.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out,"utf-8"));
			pw.println("{对不起,您没有访问该资源的权限!}");
			pw.flush();
			pw.close();
		}
		return false;
	}
}
