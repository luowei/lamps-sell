package com.vvvv.lamps.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vvvv.common.service.IBaseService;
import com.vvvv.common.service.imp.BaseService;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.tool.query.QueryUtil;
import com.vvvv.lamps.dao.ProductDAO;
import com.vvvv.lamps.model.Category;
import com.vvvv.lamps.model.Product;
import com.vvvv.lamps.service.IProductService;

/**
 * @className:ProductService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4
 */
@Service
public class ProductService extends BaseService<Product> implements IProductService{
	@Resource(name="productDAO")
	protected ProductDAO productDAO;
	
	public int changeOrder(int newOrder, int oldOrder) {
		int max = this.productDAO.findCountByHql("from Product", "max(orders)");
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
			hql = "update Product set orders=orders-1 where orders <= " + newOrder + " and orders > " + oldOrder;
		} else if (newOrder < oldOrder) {
			hql = "update Product set orders=orders+1 where orders >= " + newOrder + " and orders < " + oldOrder;
		} else {
			return newOrder;
		}
		this.productDAO.executeSql(hql);
		return newOrder;
	}
	
	
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
			List<PropertyFilter> pfList,String hql) {
		//初始化hql
		StringBuffer thql=new StringBuffer(hql);
		//设置查询条件
		String condition= QueryUtil.toSqlString(pfList, true);
		int totalCount= 0;
		//查找所有用户的总条数
		thql.append(condition);
		totalCount=this.productDAO.findCountByHql("select count(*) "+thql.toString());
		page.setTotalCount(totalCount);
		
		//是否存在排序
		if(page.isOrderBySetted()){
			thql.append(page.getOrder());
		}
		List<Product> list= this.productDAO.findList(thql.toString(), page.getPageNo(), page.getPageSize());
		//查出结果集
		page.setResult(list);
		return page;
	}
	
	/**
	 * 根据complanyId查找productList
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param page 页面
	 * @param complanyId 公司id
	 * @return 产品列表
	 */
	public Page<Product> findByComplanyId(Page<Product> page,String complanyId){
		//String hql="select p from Product p ,Complany c where c = some (p.complanySet)) pc  ";
		String hql="select p from Product p ,Complany c " +
				" where c.id='"+complanyId+"' and  c in elements(p.complanySet) ";
		List<Product> productList=this.productDAO.findByComplanyId(page,hql);
		page.setResult(productList);
		return page;
	}
	
}
