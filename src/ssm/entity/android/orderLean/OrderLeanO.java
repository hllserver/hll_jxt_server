package ssm.entity.android.orderLean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ssm.entity.driverSchool.SchoolPlaceO;

/**
 * 查询用户预约计划，及驾校信息，及登陆状态
 * @author liaoyun
 * 2016-8-11
 */
public class OrderLeanO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6085593748360644125L;
	private int loginState;                                     //是否登陆  0---没有登陆，1---已经登陆
	private String serverTime;                                  //服务器时间
	private List<SchoolPlaceO> schoolPlace = new ArrayList<>(); //报名驾校的场地
	private List<ScheduleO> schedule = new ArrayList<>();       //我的计划
	public OrderLeanO() {
		super();
	}
	public List<SchoolPlaceO> getSchoolPlace() {
		return schoolPlace;
	}
	public void setSchoolPlace(List<SchoolPlaceO> schoolPlace) {
		this.schoolPlace = schoolPlace;
	}
	public List<ScheduleO> getSchedule() {
		return schedule;
	}
	public void setSchedule(List<ScheduleO> schedule) {
		this.schedule = schedule;
	}
	public String getServerTime() {
		return serverTime;
	}
	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}
	public int getLoginState() {
		return loginState;
	}
	public void setLoginState(int loginState) {
		this.loginState = loginState;
	}
}
