package ssm.controller.android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ssm.entity.android.orderLean.OrderLeanO;
import ssm.entity.android.orderLean.ScheduleO;
import ssm.entity.driverSchool.SchoolPlaceO;
import ssm.entity.user.UserO;
import ssm.service.android.QueueService;
import ssm.util.CommonUtil;
/**
 * 预约排队相关
 * @author liaoyun 2016-8-11
 */
@Controller
@Transactional
@RequestMapping("/queue")
public class QueueAction {
	@Autowired
	private QueueService queueService;
	private static Logger logger = Logger.getLogger(QueueAction.class);
	/**
	 * 获取我的预约信息
	 * liaoyun 2016-8-11
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getOrderLeanInfo")
	public OrderLeanO getOrderLeanInfo(HttpSession session){
		OrderLeanO ol = new OrderLeanO();
		ol.setLoginState(CommonUtil.isLogin(session));                                    //是否登陆账号
		ol.setServerTime(CommonUtil.getServerTime());                                     //获取服务器时间
		if(ol.getLoginState() == 0){                                                      //如果还没有登陆直接返回
			logger.warn("user not login,can not get order info");
			return ol;
		}
		UserO user = CommonUtil.getUserInfo(session);
		List<SchoolPlaceO> pList = queueService.findSchoolPlace(user.getAccount().trim());//查询的场地
		List<ScheduleO> oList = queueService.findMyOrderLean(user.getAccount().trim());   //查询三天的计划
		ol.setSchoolPlace(pList);
		ol.setSchedule(oList);
		return ol;
	}
	/**
	 * 保存用户的计划信息
	 * liaoyun 2016-8-12
	 * @param orderLeanO
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveOrderLeanInfo")
	public Map<String,String> saveOrderLeanInfo(@RequestBody OrderLeanO orderLeanO, HttpSession session){
		Map<String,String> map = new HashMap<>();
		try{
			map.put("state", "1");
		}catch(Exception e){
			logger.error(e);
			map.put("state", "0");
		}
		return map;
	}
}
