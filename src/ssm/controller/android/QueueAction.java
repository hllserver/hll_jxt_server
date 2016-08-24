package ssm.controller.android;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.WebSocketSession;

import ssm.entity.android.Queue;
import ssm.entity.android.orderLean.OrderLeanO;
import ssm.entity.android.orderLean.ScheduleO;
import ssm.entity.common.SocketMsg;
import ssm.entity.driverSchool.SchoolPlaceO;
import ssm.entity.user.UserO;
import ssm.service.android.QueueService;
import ssm.util.CommonUtil;
import ssm.util.WebSocketEndPoint;
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
	public OrderLeanO getOrderLeanInfo(HttpSession session) throws Exception{
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
	public OrderLeanO saveOrderLeanInfo(@RequestBody OrderLeanO orderLeanO, HttpSession session) throws Exception{
		try{
			UserO user = CommonUtil.getUserInfo(session);
			List<ScheduleO> list = orderLeanO.getSchedule();
			for (ScheduleO vo : list) {
				vo.setUserAccount(user.getAccount());
				vo.setCreatedBy(user.getAccount());
				vo.setLastUpdatedBy(user.getAccount());
				vo.trim();
			}
			queueService.saveSchedule(user.getAccount(),list); //保存数据
			return getOrderLeanInfo(session);                  //查询最新的数据
		}catch(Exception e){
			logger.error(e);
			return null;
		}
	}
	
	/**
	 * 加入队列，返回排队的最新情况 liaoyun 2016-8-14
	 * @param placeId
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/insertQueue/{placeId}")
	public List<Queue> enQueue(@PathVariable String placeId,HttpSession session) throws Exception{
		//如果没有报名该驾校，则不能查看相关的信息
		UserO user = CommonUtil.getUserInfo(session);
		if(user==null || !hasPlaceId(user.getAccount(), placeId)){
			return null;
		}
		//加入排队 并 返回 队列的最新情况
		List<Queue> list = queueService.insertAndGetLastQueueState(user.getAccount(),placeId);
		//发送通知所有排队的人
		sendQueueStateChangedMsg(list, user);
		return list;
	}
	/**
	 * 排队信息发生变化，通知所有人， 
	 * @param list
	 * @param user
	 */
	private void sendQueueStateChangedMsg(List<Queue> list,UserO user){
		WebSocketSession wss = WebSocketEndPoint.sessionMap.get(user.getAccount());
		if(wss != null){
			List<String> users = new ArrayList<>();
			for (Queue queue : list) {
				users.add(queue.getUserAccount());
			}
			String str = WebSocketEndPoint.objectToJson(list);
			String severTime = WebSocketEndPoint.getServiceTime();
			SocketMsg stmg = new SocketMsg(user.getAccount(), user.getName(), user.getNickName(), null, 
					SocketMsg.SCENE_QUEUE, null, users, str, severTime);
			WebSocketEndPoint.sendToUsers(stmg);
		}
	}
	/**
	 * 查询用户可选择的场地 liaoyun 2016-8-14
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/findTrainingPlace")
	public List<SchoolPlaceO> findTrainingPlace(HttpSession session) throws Exception{
		UserO user = CommonUtil.getUserInfo(session);
		List<SchoolPlaceO> list = queueService.findSchoolPlace(user.getAccount().trim());//查询的场地
		return list;
	}
	
	/**
	 * 查询场地最新排队情况 liaoyun 2016-8-14
	 * @param placeId
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findLastQueueState/{placeId}")
	public List<Queue> findLatestQueueState(@PathVariable String placeId,HttpSession session){
		List<Queue> list = findLastQueueStateByPlace(placeId, session);
		return list;
	}
	
	/**
	 * 查询场地最新排队情况 liaoyun 2016-8-14
	 * @param placeId
	 * @param session
	 * @return
	 */
	private List<Queue> findLastQueueStateByPlace(String placeId,HttpSession session){
		//如果没有报名该驾校，则不能查看相关的信息
		UserO user = CommonUtil.getUserInfo(session);
		if(user==null || !hasPlaceId(user.getAccount(), placeId)){
			return null;
		}
		List<Queue> list = queueService.findLastQueueStateByPlace(placeId);
		return list;
	}
	
	/**
	 * 查询用户是否报名了该驾校，是否可以查看该场地的排队信息 liaoyun 2016-8-15
	 * @param account
	 * @param placeId
	 * @return
	 */
	private boolean hasPlaceId(String account ,String placeId){
		List<SchoolPlaceO> place = queueService.hasPlaceIdByAccountAndPlaceId(account,placeId);
		if(place != null && place.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 放弃当前的排队 liaoyun 2016-8-15
	 * @param placeId
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/giveUpMyQueue/{placeId}")
	public List<Queue> giveUpQueue(@PathVariable String placeId, HttpSession session){
		//如果没有报名该驾校，则不能查看相关的信息
		UserO user = CommonUtil.getUserInfo(session);
		if(user==null){
			return null;
		}
		List<Queue> list = queueService.giveUpQueue(user.getAccount(),placeId);
		return list;
	}
	/**
	 * 训练完毕，排到最后面，LiaoYun 2016-8-24
	 * @param placeId
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/goToEndQueue/{placeId}")
	public List<Queue> goToEnd(@PathVariable String placeId, HttpSession session){
		//如果没有报名该驾校，则不能查看相关的信息
		UserO user = CommonUtil.getUserInfo(session);
		if(user==null || !hasPlaceId(user.getAccount(), placeId)){
			return null;
		}
		List<Queue> list = queueService.goToEnd(user.getAccount(),placeId);
		return list;
	}
	/**
	 * 设置为正在训练状态 liaoyun 2016-8-16
	 * @param placeId
	 * @param session
	 * @return
	 */
	private List<Queue> setTraining(@PathVariable String placeId, HttpSession session){
		return null;
	}
	
	//private 
}
