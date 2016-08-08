package ssm.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErwemaServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1831413328567310705L;
	private Log log = LogFactory.getLog(ErwemaServlet.class);
	protected void service(HttpServletRequest requset, HttpServletResponse response)  
            throws ServletException, IOException { 
		String text = "http://www.hll.com";
        try {
			QRCodeUtil.encode(text,"d:/LOGO.jpg",response.getOutputStream(),true);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }  
}
