package com.vvvv.lamps.service;

import com.vvvv.common.service.IBaseService;
import com.vvvv.lamps.model.Ads;

/**
 * @className:IAdsService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4
 */
public interface IAdsService extends IBaseService<Ads>{
	
	public int changeOrder(int newOrder, int oldOrder);
}
