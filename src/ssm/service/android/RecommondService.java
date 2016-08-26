package ssm.service.android;

import java.util.List;

import ssm.entity.android.recommend.RecommendSchoolInfoO;
import ssm.entity.android.recommend.SchoolDetailInfo;
import ssm.entity.android.recommend.SchoolSelectBy;


/**
 * android recommond page
 * @author liaoyun
 * 2016-6-2
 */
public interface RecommondService {
	/**
	 * 分页查询  liaoyun 2016-6-2
	 * @return
	 */
	public List<RecommendSchoolInfoO> getSchoolList(SchoolSelectBy schoolSelect);

	/**
	 * 特别推荐栏的数据
	 * liaoyun 2016-6-4
	 * @param index
	 * @return
	 */
	public List<RecommendSchoolInfoO> getRecommondAdInfo(int index);

	/**
	 * 通过 placeId 获取驾校详细信息 LiaoYun 2016-8-26
	 * @param placeId
	 * @return
	 */
	public SchoolDetailInfo getSchoolDetailByPlaceId(String placeId);
}
