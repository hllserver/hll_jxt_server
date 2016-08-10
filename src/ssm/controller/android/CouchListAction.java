package ssm.controller.android;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ssm.entity.android.recommend.CouchSelectBy;
import ssm.entity.android.recommend.RecommendCouchO;
import ssm.service.android.recommond.RecommondCouchService;

@Controller
@Transactional
@RequestMapping(value="/couchList")
public class CouchListAction {

	@Autowired
	private RecommondCouchService  couchService;

	@ResponseBody
	@RequestMapping(value="/getCouchList",method=RequestMethod.POST,produces="application/json",
            consumes="application/json")
	public List<RecommendCouchO> getCouchList(@RequestBody CouchSelectBy couchSelectBy){
		if(couchSelectBy==null){
			System.out.println("jjjjkk-----------------------------------");
			return null;
		}
		List<RecommendCouchO> list=couchService.getCouchList(couchSelectBy);
		System.out.println(".............................................");
		return list;
	}
	
}
