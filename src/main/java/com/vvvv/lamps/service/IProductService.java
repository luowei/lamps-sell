package com.vvvv.lamps.service;

import java.util.List;

import com.vvvv.common.service.IBaseService;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.lamps.model.Product;

/**
 * @className:IProductService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4
 */
public interface IProductService extends IBaseService<Product>{
	/**
	 * 改变排序
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param newOrder
	 * @param oldOrder
	 * @return
	 */
	public int changeOrder(int newOrder, int oldOrder);
	
	/**
	 * 查询所有
	 * @author wei.luo
	 * @createTime 2012-4-8
	 * @param baseService
	 * @param page
	 * @param pfList
	 * @param hql
	 * @return Page对象
	 */
	public Page<Product> findAll(Page<Product> page, 
			List<PropertyFilter> pfList,String hql);
	
	/**
	 * 根据complanyId查找productList
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param page 页面
	 * @param complanyId 公司id
	 * @return 产品列表
	 */
	public Page<Product> findByComplanyId(Page<Product> page,String complanyId);
}
