package com.vvvv.lamps.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vvvv.common.service.imp.BaseService;
import com.vvvv.lamps.dao.ContactDAO;
import com.vvvv.lamps.model.Contact;
import com.vvvv.lamps.service.IContactService;

/**
 * @className:ContactService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4
 */
@Service
public class ContactService extends BaseService<Contact> implements IContactService{

	@Resource(name="contactDAO")
	protected ContactDAO contactDAO;
	
	public int changeOrder(int newOrder, int oldOrder) {
		int max = this.contactDAO.findCountByHql("from Contact", "max(orders)");
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
		String sql = "";
		// 对排序进行处理
		if (newOrder > oldOrder) {
			sql = "update Contact set orders=orders-1 where orders <= " + newOrder + " and orders > " + oldOrder;
		} else if (newOrder < oldOrder) {
			sql = "update Contact set orders=orders+1 where orders >= " + newOrder + " and orders < " + oldOrder;
		} else {
			return newOrder;
		}
		this.contactDAO.executeSql(sql);
		return newOrder;
	}
}
