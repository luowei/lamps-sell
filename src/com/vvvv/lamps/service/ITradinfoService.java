package com.vvvv.lamps.service;

import com.vvvv.common.service.IBaseService;
import com.vvvv.lamps.model.Tradinfo;

/**
 * @className:ITradinfoService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4
 */
public interface ITradinfoService extends IBaseService<Tradinfo>{
	public int changeOrder(int newOrder, int oldOrder);
}
