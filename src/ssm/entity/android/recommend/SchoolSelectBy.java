package ssm.entity.android.recommend;
/**
 * 学校筛选
 * @author liaoyun
 * 2016-6-11
 */
public class SchoolSelectBy {
	private String tranAreaSp;//练车区域
	private String tranTypeSp;//练车类型
	private double tranDistance;//距离(km)
	private int startPage;     //第几列
	private int pageSize;      //每页的记录条数
	public SchoolSelectBy() {
	}
	public String getTranAreaSp() {
		return tranAreaSp;
	}
	public void setTranAreaSp(String tranAreaSp) {
		this.tranAreaSp = tranAreaSp;
	}
	public String getTranTypeSp() {
		return tranTypeSp;
	}
	public void setTranTypeSp(String tranTypeSp) {
		this.tranTypeSp = tranTypeSp;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public double getTranDistance() {
		return tranDistance;
	}
	public void setTranDistance(double tranDistance) {
		this.tranDistance = tranDistance;
	}
}
