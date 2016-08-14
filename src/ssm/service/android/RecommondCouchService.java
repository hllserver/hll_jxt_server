package ssm.service.android;

import java.util.List;

import ssm.entity.android.recommend.CouchSelectBy;
import ssm.entity.android.recommend.RecommendCouchO;

public interface RecommondCouchService {

	/**
	 * 获取教练列表
	 * @param couchSelectBy
	 * @return
	 */
	public List<RecommendCouchO> getCouchList(CouchSelectBy couchSelectBy);

	
}
