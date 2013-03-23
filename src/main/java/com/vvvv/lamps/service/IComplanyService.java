package com.vvvv.lamps.service;

import com.vvvv.common.service.IBaseService;
import com.vvvv.lamps.model.Complany;

/**
 * @className:IComplanyService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4
 */
public interface IComplanyService extends IBaseService<Complany>{
	/**
	 * 对排序编号的处理 *
	 * 
	 * @author wei.luo
	 * @createTime 2012-4-7
	 * @param newOrder
	 * @param oldOrder
	 * @return
	 */
	public int changeOrder(int newOrder, int oldOrder);
}
