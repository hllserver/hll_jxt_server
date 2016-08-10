package ssm.orm.android.recommond;

import java.util.List;

import ssm.entity.android.recommend.CouchSelectBy;
import ssm.entity.android.recommend.RecommendCouchO;

public interface RecommondCouchDao {


public	List<RecommendCouchO> getCouchList(CouchSelectBy couchSelectBy);

}
