package test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

public class TestDemo {
	private  Log log = LogFactory.getLog(TestDemo.class);
	//Logger log = Logger.getLogger(TestDemo.class);
	private void showLog(){
		log.debug("debug");
		log.error("error");
		log.warn("warn");
	}
	public static void main(String[] args) {
		TestDemo td = new TestDemo();
		td.showLog();
	}
}
