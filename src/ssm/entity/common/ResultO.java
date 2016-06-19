package ssm.entity.common;

import java.io.Serializable;
import java.util.List;
/**
 * 用于分分页查询 
 * @author liaoyun
 * 2016-3-13
 * @param <T>
 */
public class ResultO <T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3454601056468882703L;
	private List<T> resultList;
	private PageO page;
	public ResultO() {
		super();
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public PageO getPage() {
		return page;
	}
	public void setPage(PageO page) {
		this.page = page;
	}
	
}
