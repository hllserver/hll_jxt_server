package ssm.controller.android;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ssm.entity.android.recommend.RecommendSchoolInfoO;
import ssm.entity.android.recommend.SchoolSelectBy;
import ssm.service.android.recommond.RecommondService;

@Controller
@Transactional
@RequestMapping(value="/recommond")
public class RecommondAction {
	
	@Autowired
	private RecommondService recommondService;
	/**
	 * 分页查询  liaoyun 2016-6-2
	 * @param schoolSelect
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/getSchoolList",method=RequestMethod.POST,produces="application/json",
			consumes="application/json")
	public List<RecommendSchoolInfoO> getSchoolList(@RequestBody SchoolSelectBy schoolSelect) throws Exception{
		System.out.println("fgfhgghgjhjh");
		if(schoolSelect==null){
			return null;
		}
		List<RecommendSchoolInfoO> list = recommondService.getSchoolList(schoolSelect);
		return list;
	}
	/**
	 * 特别推荐栏的数据
	 * liaoyun 2016-6-4
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/getRecommondAdInfo/{index}")
	public List<RecommendSchoolInfoO> getRecommondAdInfo(@PathVariable int index) throws Exception{
		List<RecommendSchoolInfoO> list = recommondService.getRecommondAdInfo(index);
		return list;
	}
}
