package com.vvvv.lamps.service;

import com.vvvv.common.service.IBaseService;
import com.vvvv.lamps.model.Contact;

/**
 * @className:IcontactService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4
 */
public interface IContactService extends IBaseService<Contact>{
	public int changeOrder(int newOrder, int oldOrder);
}
