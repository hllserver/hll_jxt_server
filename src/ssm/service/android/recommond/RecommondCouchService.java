package ssm.service.android.recommond;

import java.util.List;

import ssm.entity.android.recommend.CouchSelectBy;
import ssm.entity.android.recommend.RecommendCouchO;

public interface RecommondCouchService {

	public List<RecommendCouchO> getCouchList(CouchSelectBy couchSelectBy);

	
}
