package ssm.serviceImpl.privilege;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import privilege.Item;
import ssm.entity.common.PageO;
import ssm.entity.common.ResultO;
import ssm.entity.privilege.PrivilegeO;
import ssm.orm.privilege.PrivilegeDao;
import ssm.service.privilege.PrivilegeService;
import ssm.util.PageUtil;
@Service
public class PrivilegeServiceImpl implements PrivilegeService{
	
	@Autowired
	private PrivilegeDao privilegeDao;

	/**
	 * 查询 权限 信息
	 * liaoyun 2016-3-6
	 */
	@Override
	public ResultO<PrivilegeO> getPrivilegeList(PrivilegeO privilegeO,int currentPage,int pageSize) {
		int total = privilegeDao.getPrivilegeListTotal(privilegeO);
		PageO pageO = PageUtil.getPage(currentPage, pageSize, total);
		//起始编号
		int start = (pageO.getCurrentPage()-1)*pageO.getPageSize();
		List<PrivilegeO> list = privilegeDao.getPrivilegeList(privilegeO,start,pageSize);
		ResultO<PrivilegeO> result = new ResultO<>();
		result.setResultList(list);
		result.setPage(pageO);
		return result;
	}

	/**
	 * 批量插入数据
	 * @param insertedList
	 * liaoyun
	 * 2016-3-16
	 */
	@Override
	public List<String> batchInsert(List<PrivilegeO> insertedList) {
		List<String> repeatedCodeList=new ArrayList<>();
		//判断传入的数据是否有重复
		for(int i=0; i<insertedList.size(); i++){
			String si = insertedList.get(i).getPriCode();
			for(int j=i+1; j<insertedList.size(); j++){
				String sj = insertedList.get(j).getPriCode();
				if(si==null || sj==null || si.trim().equals(sj.trim())){
					repeatedCodeList.add(sj);
					return repeatedCodeList;
				}
			}
		}
		//判断传入的数据 与 数据库中的数据 是否有重复
		List<String> codeList = new ArrayList<>();
		for (PrivilegeO o : insertedList) {
			codeList.add(o.getPriCode());
		}
		repeatedCodeList = privilegeDao.findRepeatedByCode(codeList);
		if(repeatedCodeList != null && repeatedCodeList.size()>0){//如果有重复，返回 批量操作失败
			return repeatedCodeList;
		}
		//保存
		privilegeDao.batchInsert(insertedList);
		return null;
	}

	/**
	 * 批量 修改  数据
	 * @param updatedList
	 * liaoyun
	 * 2016-3-20
	 */
	@Override
	public List<String> batchUpdate(List<PrivilegeO> updatedList) {
		List<String> list = new ArrayList<>();
		//校验传入的数据 是否重复
		for(int i=0; i<updatedList.size(); i++){
			String si = updatedList.get(i).getPriCode();
			for(int j=i+1; j<updatedList.size(); j++){
				String sj = updatedList.get(j).getPriCode();
				if(si==null || sj==null || si.trim().equals(sj.trim())){
					list.add(sj);
					return list;
				}
			}
		}
		//校验传入的数据 与 数据 库中 的数据 是否重复
		List<PrivilegeO> listPri = privilegeDao.findUpdateRepeatedList(updatedList);
		for (PrivilegeO vo : updatedList) {
			String codei = vo.getPriCode();
			long idi = vo.getId();
			for (PrivilegeO prio : listPri) {
				String codej = prio.getPriCode();
				long idj = prio.getId();
				if(codei.trim().equals(codej.trim()) && idi!=idj){
					list.add(codej);
				}
			}
		}
		if(list!=null && list.size()>0){
			return list;
		}
		//保存
		privilegeDao.batchUpdate(updatedList);
		return null;
	}

	/**
	 * 批量 删除  数据
	 * @param updatedList
	 * liaoyun
	 * 2016-3-20
	 */
	@Override
	public void bachDelete(List<PrivilegeO> deletedList) {
		privilegeDao.bachDelete(deletedList);
	}

	/**
	 * 删除原来的方法权限
	 * liaoyun
	 * 2014-4-19
	 */
	@Override
	public void deleteOldMethodPrivilege() {
		privilegeDao.deleteOldMethodPrivilege();
	}

	/**
	 * 插入新扫描的方法权限  liaoyun 2016-4-19
	 * @param items
	 */
	@Override
	public void insertNewMethodPrivilege(List<Item> items) {
		privilegeDao.insertNewMethodPrivilege(items);
	}

}
