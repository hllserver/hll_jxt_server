package ssm.entity.android.recommend;

public class RecommendCouchO {

	public String coachSelfImg;
	public int  coachPrice;
	public String  teachType;
	public String  orderLevel;
	
	
	public RecommendCouchO() {
		super();
	}


	public RecommendCouchO(String coachSelfImg,int coachPrice,String teachType
			,String orderLevel){
		this.coachSelfImg=coachSelfImg;
		this.coachPrice=coachPrice;
		this.teachType=teachType;
		this.orderLevel=orderLevel;
	}


	public String getCoachSelfImg() {
		return coachSelfImg;
	}


	public void setCoachSelfImg(String coachSelfImg) {
		this.coachSelfImg = coachSelfImg;
	}


	public int getCoachPrice() {
		return coachPrice;
	}


	public void setCoachPrice(int coachPrice) {
		this.coachPrice = coachPrice;
	}


	public String getTeachType() {
		return teachType;
	}


	public void setTeachType(String teachType) {
		this.teachType = teachType;
	}




	public String getOrderLevel() {
		return orderLevel;
	}


	public void setOrderLevel(String orderLevel) {
		this.orderLevel = orderLevel;
	}	
}
