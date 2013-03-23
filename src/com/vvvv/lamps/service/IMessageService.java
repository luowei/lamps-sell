package com.vvvv.lamps.service;

import com.vvvv.common.service.IBaseService;
import com.vvvv.lamps.model.Message;

/**
 * @className:IMessageService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4上午10:28:54 
 */
public interface IMessageService extends IBaseService<Message>{
	public int changeOrder(int newOrder, int oldOrder);
}
