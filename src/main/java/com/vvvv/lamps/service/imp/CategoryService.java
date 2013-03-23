package com.vvvv.lamps.service.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vvvv.common.service.imp.BaseService;
import com.vvvv.lamps.dao.CategoryDAO;
import com.vvvv.lamps.dao.ProductDAO;
import com.vvvv.lamps.model.Category;
import com.vvvv.lamps.model.Product;
import com.vvvv.lamps.service.ICategoryService;

/**
 * @className:CategoryService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4
 */
@Service
public class CategoryService extends BaseService<Category> implements ICategoryService{
	@Resource(name="categoryDAO")
	protected CategoryDAO categoryDAO;
	@Resource(name="productDAO")
	protected ProductDAO productDAO;
	
	/**
	 * 查找父类别
	 */
	public List<Category> findBigCategory() {
		//Map map=new HashMap();
		//map.put("parentId", 1);
		//List<Category> categoryList=this.categoryDAO.findListOfMap("from Category where parent_id=:parentId", map);
		List<Category> categoryList=this.categoryDAO.findList("from Category where parent_id='1'");
		return categoryList;
	}

	/**
	 * 对排序编号的处理 *
	 * 
	 * @author wei.luo
	 * @createTime 2012-4-7
	 * @param newOrder
	 * @param oldOrder
	 * @return
	 */
	public int changeOrder(int newOrder, int oldOrder) {
		int max = this.categoryDAO.findCountByHql("from Category", "max(orders)");
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
			hql = "update Category set orders=orders-1 where orders <= " + newOrder + " and orders > " + oldOrder;
		} else if (newOrder < oldOrder) {
			hql = "update Category set orders=orders+1 where orders >= " + newOrder + " and orders < " + oldOrder;
		} else {
			return newOrder;
		}
		this.categoryDAO.executeSql(hql);
		return newOrder;
	}

	/**
	 * 设置子类别产品数量
	 * @author wei.luo
	 * @createTime 2012-5-1
	 * @param subCategoryList
	 * @param hql
	 * @return
	 */
	public List<Category> getPCategory(List<Category> subCategoryList,String hql) {
		List<Category> pCategoryList;
		//父类别
		pCategoryList=this.findList(hql);
		int count=0;
		for(Category cat:pCategoryList){
			for(Category cate:subCategoryList){
				if(cate.getParentId()==cat.getId()){
					count+=cate.getProductCount();
				}
			}
			cat.setProductCount(count);
			this.update(cat);
			count=0;
		}
		return pCategoryList;
	}

	/**
	 * 设置父类别产品数量
	 * @author wei.luo
	 * @createTime 2012-5-1
	 * @param subHql
	 * @param productHql
	 * @return
	 */
	public List<Category> getSubCategory(String subHql,String productHql) {
		List<Category> subCategoryList;
		//子类别
		subCategoryList=this.findList(subHql);
		for(Category cat:subCategoryList){
			List<Product> tProductList=this.productDAO
				.findList(productHql+"'"+cat.getId()+"' ");
			cat.setProductCount(tProductList.size());
			this.update(cat);
		}
		return subCategoryList;
	}
	
	/**
	 * 根据sql查询数量
	 * @author wei.luo
	 * @createTime 2012-5-1
	 * @param sql
	 * @return
	 */
	public Integer findTotalCountBySql(String sql){
		int totalCount=0;
		ResultSet rs=this.categoryDAO.findResultBySql("select count(*) totalCount from "+sql);
		try {
			if(rs.next()){
				totalCount=rs.getInt("totalCount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalCount;
	}
}
