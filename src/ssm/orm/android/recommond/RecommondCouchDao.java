package ssm.orm.android.recommond;

import java.util.List;

import ssm.entity.android.recommend.CouchSelectBy;
import ssm.entity.android.recommend.RecommendCouchO;

public interface RecommondCouchDao {

/**
 * 获取listview的教练列表
 * @param couchSelectBy
 * @return
 */
public	List<RecommendCouchO> getCouchList(CouchSelectBy couchSelectBy);

}
