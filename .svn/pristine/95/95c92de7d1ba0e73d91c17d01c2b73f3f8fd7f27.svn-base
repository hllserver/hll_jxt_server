package ssm.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * catch control
 * @author liaoyun
 * 2016-3-5
 */
@WebFilter("/CacheHeaderFilter")
public class CacheHeaderFilter implements Filter {
	 private String[][] replyHeaders = {{}};  

    /**
     * Default constructor. 
     */
    public CacheHeaderFilter() {
        
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse)response;  
	      for (int n = 0; n < replyHeaders.length; n++)  
	      {  
	         String name = replyHeaders[n][0];  
	         String value = replyHeaders[n][1];  
	         httpResponse.setHeader(name, value);  
	      }  
	   
	      long relExpiresInMillis = System.currentTimeMillis() + (1000 * 259200);  
	      httpResponse.setHeader("Expires", getGMTTimeString(relExpiresInMillis));  
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		Enumeration names = config.getInitParameterNames();  
	      ArrayList tmp = new ArrayList();  
	      while (names.hasMoreElements())  
	      {  
	         String name = (String)names.nextElement();  
	         String value = config.getInitParameter(name);  
	         String[] pair = {name, value};  
	         tmp.add(pair);  
	      }  
	      replyHeaders = new String[tmp.size()][2];  
	      tmp.toArray(replyHeaders);  
	}
	
	public static String getGMTTimeString(long milliSeconds)  {  
	      SimpleDateFormat sdf = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'");  
	      return sdf.format(new Date(milliSeconds));  
	}

}
