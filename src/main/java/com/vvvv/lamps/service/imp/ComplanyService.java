package com.vvvv.lamps.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vvvv.common.service.imp.BaseService;
import com.vvvv.lamps.dao.ComplanyDAO;
import com.vvvv.lamps.model.Complany;
import com.vvvv.lamps.service.IComplanyService;

/**
 * @className:ComplanyService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4
 */
@Service
public class ComplanyService extends BaseService<Complany> implements IComplanyService{
	@Resource(name="complanyDAO")
	protected ComplanyDAO complanyDAO;
	
	/**
	 * 对排序编号的处理 *
	 * 
	 * @author wei.luo
	 * @createTime 2012-4-7
	 * @param newOrder
	 * @param oldOrder
	 * @return
	 */
	public int changeOrder(int newOrder, int oldOrder){
		int max = this.complanyDAO.findCountByHql("from Complany", "max(orders)");
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
			hql = "update Complany set orders=orders-1 where orders <= " + newOrder + " and orders > " + oldOrder;
		} else if (newOrder < oldOrder) {
			hql = "update Complany set orders=orders+1 where orders >= " + newOrder + " and orders < " + oldOrder;
		} else {
			return newOrder;
		}
		this.complanyDAO.executeSql(hql);
		return newOrder;
	}
}
