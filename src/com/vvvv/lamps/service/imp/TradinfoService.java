package com.vvvv.lamps.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vvvv.common.service.imp.BaseService;
import com.vvvv.lamps.dao.ProductDAO;
import com.vvvv.lamps.dao.TradinfoDAO;
import com.vvvv.lamps.model.Tradinfo;
import com.vvvv.lamps.service.ITradinfoService;

/**
 * @className:TradinfoService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4
 */
@Service
public class TradinfoService extends BaseService<Tradinfo> implements ITradinfoService{

	@Resource(name="tradinfoDAO")
	protected TradinfoDAO tradinfoDAO;
	
	public int changeOrder(int newOrder, int oldOrder) {
		int max = this.tradinfoDAO.findCountByHql("from Tradinfo", "max(orders)");
		if (max == 0) {
			max = 1;
		}
		// 防止newOrder越界
		if (newOrder < 1) {
			newOrder = 1;
		}
		if (newOrder > max) {
			newOrder = max;
		}
		String hql = "";
		// 对排序进行处理
		if (newOrder > oldOrder) {
			hql = "update Tradinfo set orders=orders-1 where orders <= " + newOrder + " and orders > " + oldOrder;
		} else if (newOrder < oldOrder) {
			hql = "update Tradinfo set orders=orders+1 where orders >= " + newOrder + " and orders < " + oldOrder;
		} else {
			return newOrder;
		}
		this.tradinfoDAO.executeSql(hql);
		return newOrder;
	}
	
}
