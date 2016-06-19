package ssm.serviceImpl.android;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm.entity.android.recommend.RecommendSchoolInfoO;
import ssm.entity.android.recommend.SchoolSelectBy;
import ssm.orm.android.recommond.RecommondDao;
import ssm.service.android.recommond.RecommondService;

/**
 * android recommond page
 * @author liaoyun
 * 2016-6-2
 */
@Service
public class RecommondServiceImpl implements RecommondService{
	
	@Autowired
	private RecommondDao recommondDao;
	
	/**
	 * 分页查询  liaoyun 2016-6-2
	 * @return
	 */
	@Override
	public List<RecommendSchoolInfoO> getSchoolList(SchoolSelectBy schoolSelect) {
		int startIndex = schoolSelect.getStartPage();
		int loadSize = schoolSelect.getPageSize();
		startIndex = (startIndex-1)*loadSize;
		schoolSelect.setStartPage(startIndex);
		List<RecommendSchoolInfoO> list = recommondDao.getSchoolList(schoolSelect);
		return list;
	}
	
	/**
	 * 特别推荐栏的数据
	 * liaoyun 2016-6-4
	 * @param index
	 * @return
	 */
	@Override
	public List<RecommendSchoolInfoO> getRecommondAdInfo(int index) {
		List<RecommendSchoolInfoO> list = recommondDao.getRecommondAdInfo(index);
		return list;
	}

}
