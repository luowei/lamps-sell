package com.vvvv.lamps.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vvvv.common.service.imp.BaseService;
import com.vvvv.lamps.dao.ContactDAO;
import com.vvvv.lamps.dao.MessageDAO;
import com.vvvv.lamps.model.Message;
import com.vvvv.lamps.service.IMessageService;

/**
 * @className:MessageService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4
 */
@Service
public class MessageService extends BaseService<Message> implements IMessageService{

	@Resource(name="messageDAO")
	protected MessageDAO messageDAO;
	
	public int changeOrder(int newOrder, int oldOrder) {
		int max = this.messageDAO.findCountByHql("from Message", "max(orders)");
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
			sql = "update Message set orders=orders-1 where orders <= " + newOrder + " and orders > " + oldOrder;
		} else if (newOrder < oldOrder) {
			sql = "update Message set orders=orders+1 where orders >= " + newOrder + " and orders < " + oldOrder;
		} else {
			return newOrder;
		}
		this.messageDAO.executeSql(sql);
		return newOrder;
	}

}
