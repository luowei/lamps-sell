package com.vvvv.lamps.service;

import java.util.List;

import com.vvvv.common.service.IBaseService;
import com.vvvv.lamps.model.Category;

/**
 * @className:ICategoryService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4
 */
public interface ICategoryService extends IBaseService<Category>{

	/**
	 * 查找父类别
	 * @author wei.luo
	 * @createTime 2012-5-1
	 * @return
	 */
	public List<Category> findBigCategory();
	
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
	
	/**
	 * 设置子类别产品数量
	 * @author wei.luo
	 * @createTime 2012-5-1
	 * @param subCategoryList
	 * @param hql
	 * @return
	 */
	public List<Category> getPCategory(List<Category> subCategoryList,String hql) ;
	
	/**
	 * 设置父类别产品数量
	 * @author wei.luo
	 * @createTime 2012-5-1
	 * @param subHql
	 * @param productHql
	 * @return
	 */
	public List<Category> getSubCategory(String subHql,String productHql);
	
	/**
	 * 根据sql查询数量
	 * @author wei.luo
	 * @createTime 2012-5-1
	 * @param sql
	 * @return
	 */
	public Integer findTotalCountBySql(String sql);
}
