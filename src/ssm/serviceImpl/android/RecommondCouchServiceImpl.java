package ssm.serviceImpl.android;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm.entity.android.recommend.CouchSelectBy;
import ssm.entity.android.recommend.RecommendCouchO;
import ssm.orm.android.recommond.RecommondCouchDao;
import ssm.service.android.recommond.RecommondCouchService;

@Service
public class RecommondCouchServiceImpl implements RecommondCouchService{

	@Autowired
	private RecommondCouchDao recommondCouchDao;
	@Override
	public List<RecommendCouchO> getCouchList(CouchSelectBy couchSelectBy) {

		int startIndex=couchSelectBy.getStartPage();
		int loadSize=couchSelectBy.getPageSize();
		startIndex=(startIndex-1)*loadSize;
		couchSelectBy.setStartPage(startIndex);
		List<RecommendCouchO> list=recommondCouchDao.getCouchList(couchSelectBy);
		//System.out.println("dao执行了");
		return list;
	}

}
