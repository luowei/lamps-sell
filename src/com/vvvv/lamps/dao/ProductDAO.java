package com.vvvv.lamps.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.vvvv.common.dao.BaseDAO;
import com.vvvv.common.tool.page.Page;
import com.vvvv.lamps.model.Product;

/**
 * @className:ProductDAO.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4下午12:48:12 
 */
@Repository
public class ProductDAO extends BaseDAO<Product>{

//	public Integer findCountByHql(String hql){
//		final String shql=hql;
//		 HibernateCallback cb = new HibernateCallback() {
//			 public Object doInHibernate(Session session)
//			 throws HibernateException {
//				return Integer.parseInt(session.createQuery(shql).list().get(0).toString());
//	
//			 } // end function
//		 };// end callback
//		 return   (Integer) this.handler.getHibernateTemplate().execute(cb);
//		
//	}
	
	/**
	 * 根据hql查找记录数
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param hql
	 * @return
	 */
	public Integer findCountByHql(String hql){
		return this.handler.findCountByHql(hql);
	}
	
	/**
	 * 根据公司Id查找产品列表
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param page
	 * @param hql
	 * @return
	 */
	public List<Product> findByComplanyId(Page<Product> page,final String hql){
		final int maxResuts = page.getPageSize();
		final int firstResult = (page.getPageNo() - 1) * maxResuts;
		List executeFind = getHandler().getHibernateTemplate().executeFind(
			new HibernateCallback<Product>() {
			public Product doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setParameter(0, firstResult);
				query.setParameter(1, maxResuts);
				return (Product)query.list();
			}
		});
		return (List<Product>)executeFind;
	}
}
