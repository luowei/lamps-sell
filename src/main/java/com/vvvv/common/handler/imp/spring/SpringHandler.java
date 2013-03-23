package com.vvvv.common.handler.imp.spring;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.vvvv.common.handler.IHandler;


/**
 * @className:BaseDAO.java
 * @classDescription:spring持久层
 */
@Repository("springHandler")
public class SpringHandler<T> extends HibernateDaoSupport implements IHandler {
	// private static final Log log = LogFactory.getLog(SpringHandler.class);
	boolean flag = false;
	/**
	 * 为父类HibernateDaoSupport注入sessionFactory的值
	 * 
	 * @param sessionFactory
	 */
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * 保存对象
	 * 
	 * @param obj
	 *            要保存的对象
	 * @return 布尔值
	 */
	public boolean saveObj(Object obj) {
		try {
			this.getHibernateTemplate().save(obj);
			flag = true;
		} catch (Exception e) {
			// log.error(obj.getClass().getName()+"在spring-hibearnate刪除出现异常");
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除对象
	 * 
	 * @param objQ
	 *            要删除的对象
	 * @return 布尔值
	 */
	public boolean deleteObj(Object obj) {
		try {
			this.getHibernateTemplate().delete(obj);
			flag = true;
		} catch (Exception e) {
			// log.error(obj.getClass().getName()+"在spring-hibearnate刪除出现异常");
			flag = false;
		}
		return flag;
	}

	/**
	 * 修改对象
	 * 
	 * @param obj
	 *            要修改的对象
	 * @return 布尔值
	 */
	public boolean alterObj(Object obj) {
		try {
			//Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			//session.refresh(obj);
			this.getHibernateTemplate().saveOrUpdate(obj);
			flag = true;
		} catch (Exception e) {
			// log.info(obj.getClass().getName()+"在spring-hibearnate修改出现异常");
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 合并缓存中的对象
	 * @author wei.luo
	 * @createTime 2012-4-26
	 */
	public boolean mergeObj(Object obj) {
		try {
			//Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			//session.refresh(obj);
			this.getHibernateTemplate().merge(obj);
			flag = true;
		} catch (Exception e) {
			// log.info(obj.getClass().getName()+"在spring-hibearnate修改出现异常");
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 更新数据库
	 * @author wei.luo
	 * @createTime 2012-4-26
	 * @param obj
	 * @return
	 */
	public boolean updateObj(Object obj){
		try {
			//Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			//session.refresh(obj);
			this.getHibernateTemplate().update(obj);
			//this.getHibernateTemplate().flush();
			flag = true;
		} catch (Exception e) {
			// log.info(obj.getClass().getName()+"在spring-hibearnate修改出现异常");
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 根据id查询对象
	 * 
	 * @param className
	 *            类名
	 * @param id
	 *            类id
	 * @return 对象
	 */
	public Object findObjById(Class className, Serializable id) {
		if (null == id || "".equals(id)) {
			return null;

		} else {
			return this.getHibernateTemplate().get(className, id);
		}
	}
	/**
	 * 根据id查询延时对象
	 * 
	 * @param className
	 *            类名
	 * @param id
	 *            类id
	 * @return 对象
	 */
	public Object findObjByIdLoad(Class className, Serializable id) {
		if (null == id || "".equals(id)) {
			return null;
		} else {
			return this.getHibernateTemplate().load(className, id);
		}
	}

	/**
	 * 查询对象
	 * 
	 * @param hql
	 *            查找语句
	 * @return 对象
	 */
	public Object findObj(final String hql) {
		if (null == hql || "".equals(hql)) {
			return null;
		}
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(0);
				query.setMaxResults(1);
				Object obj = query.uniqueResult();
				return obj;
			}
		});
	}

	/**
	 * 查询对象(防止依赖注入)
	 * 
	 * @param hql
	 *            查找语句
	 * @param map
	 *            参数
	 * @return 对象
	 */
	public Object findObj(final String hql,
			final Map<Serializable, Serializable> map) {
		if (null == hql || "".equals(hql)) {
			return null;
		}
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				for (Serializable key : map.keySet()) {
					query.setParameter((String) key, map.get(key));
				}
				query.setFirstResult(0);
				query.setMaxResults(1);
				Object obj = query.uniqueResult();
				return obj;
			}
		});
	}

	/**
	 * 查询集合
	 * 
	 * @param sql
	 *            查询语句
	 * @return 集合
	 */
	public List findListOfObj(final String hql) {
		if (null == hql || "".equals(hql)) {
			return null;
		}
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}

	/**
	 * 主要用于防止sql依赖注入
	 * 
	 * @param hql
	 *            hql语句（例如" from UserInfo where id=:id and
	 *            password=:password")query.setParameter("id",userForm.getId())
	 * @param map
	 * @return List集合
	 */
	public List findListOfObj(final String hql,
			final Map<Serializable, Serializable> map) {
		if (null == hql || "".equals(hql)) {
			return null;
		}
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				for (Serializable key : map.keySet()) {
					query.setParameter((String) key, map.get(key));
				}
				List list = query.list();
				if (list.size() == 0) {
					return null;
				} else {
					return list;
				}
			}
		});
	}

	/**
	 * 获取聚合函数的值
	 * 
	 * @param hql
	 *            
	 * @param function 聚合函数 count(*)   sum(*)      
	 * @return 返回个数
	 */
	public int findCountBySql(String hql,String function) {
		final String sql = "select "+function+" " + hql;
		HibernateCallback cb = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				List list=session.createQuery(sql).list();
				if(list.get(0)!=null){
					return Integer.parseInt(list.get(0).toString());
				}else{
					return 0;
				}

			} // end function
		};// end callback
		return (Integer) this.getHibernateTemplate().execute(cb);

	}
	
	/**
	 * 查找总数
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param hql
	 * @return
	 */
	public int findCountByHql( String hql) {
		final String shql=hql;
		 HibernateCallback cb = new HibernateCallback() {
			 public Object doInHibernate(Session session)
			 throws HibernateException {
				return Integer.parseInt(session.createQuery(shql).list().get(0).toString());
	
			 } // end function
		 };// end callback
		 return   (Integer) this.getHibernateTemplate().execute(cb);
	}
	
	/**
	 * 获取记录的总条数
	 * 
	 * @param sql
	 *            要保存的对象
	 * @return 返回个数
	 */
	public int findCountBySql( String hql) {
		final String sql="select count(*) "+hql;
		 HibernateCallback cb = new HibernateCallback() {
		 public Object doInHibernate(Session session)
		 throws HibernateException {
			return Integer.parseInt(session.createQuery(sql).list().get(0).toString());

		 } // end function
		 };// end callback
		 return   (Integer) this.getHibernateTemplate().execute(cb);
		// 总数
//		int allCount = 0;
//		hql = "select count(*) " + hql;
//		Session session = getHibernateTemplate().getSessionFactory()
//				.openSession();
//		// 定义 Transaction 事务
//		Transaction transaction = null;
//		List list = null;
//		try {
//			// 获取 Transaction 事务对象
//			transaction = session.beginTransaction();
//			// 查询多个持久对象
//			Query query = session.createQuery(hql);
//			List cc = query.list();
//			allCount = (Integer) cc.get(0);
//			// 提交事务，完成数据库操作
//			transaction.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			if (transaction != null) {
//				// 当发生异常时回滚事务，取消数据库操作
//				transaction.rollback();
//			}
//		} finally {
//			// 销毁 session 对象，释放资源
//			session.close();
//		}
//		return allCount;
		/** -------------------111111111111------------------ */
		// 通过 HibernateUtil 工具获取 Session 对象
		// Session session = getHibernateTemplate().getSessionFactory()
		// .openSession();
		// // 定义 Transaction 事务
		// Transaction transaction = null;
		// List list = null;
		// try {
		// // 获取 Transaction 事务对象
		// transaction = session.beginTransaction();
		// // 查询多个持久对象
		// Query query = session.createQuery(hql);
		// // 设置为滚动结果集
		// ScrollableResults scrollableResults = query
		// .scroll(ScrollMode.SCROLL_SENSITIVE);
		// // 定位到最后一条记录
		// scrollableResults.last();
		// // 获取记录总数
		// allCount = scrollableResults.getRowNumber() + 1;
		// // 提交事务，完成数据库操作
		// transaction.commit();
		// } catch (Exception e) {
		// e.printStackTrace();
		// if (transaction != null) {
		// // 当发生异常时回滚事务，取消数据库操作
		// transaction.rollback();
		// }
		// } finally {
		// // 销毁 session 对象，释放资源
		// session.close();
		// }
		/** ------------------2------------------- */
		// String sql = "select count(*) as allCount " + hql;
		// ResultSet rs = findResultBySql(sql);
		// try {
		// rs.next();
		// allCount = rs.getInt("allCount");
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } finally {
		// // 销毁 rs 对象，释放资源
		// try {
		// rs.close();
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//return allCount;
	}

	/**
	 * 根據查詢語句以及输入页数，以及显示条数查询对象（分页）
	 * 
	 * @param hql
	 *            hql语句
	 * @param page
	 *            显示的页数
	 * @param count
	 *            显示的条数
	 * @return 集合对象
	 */
	public List findListOfObj(final String hql, final int page, final int count) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				if (null == hql || "".equals(hql)) {
					return null;
				}
				Query query = session.createQuery(hql);
				int index = 0;
				int tempPageCount = 0;
				// 如果page为负数,将page设置为1
				if (page < 1) {
					tempPageCount = 1;
				} else {
					tempPageCount = page;
				}
				// 如果为第一页,索引为0
				if (tempPageCount == 1) {
					// 但前页数索引
					index = count;
				} else {
					index = tempPageCount * count;
				}
				// 设置取出的第一条索引
				query.setFirstResult(index - count);
				// 设置取出得最大的索引
				query.setMaxResults(count);
				return query.list();
			}
		});
	}
	
	// -------------------sql语句-----------------------//
	/**
	 * 根据sql查询语句返回ResultSet对象
	 * 
	 * @param sql
	 *            sql查询语句，
	 * @return ResultSet
	 */
	public ResultSet findResultBySql(String sql) {
		// 记录集
		ResultSet result = null;
		// 获取会话
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();

		// 查询多个持久对象
		try {
			result = session.connection().createStatement().executeQuery(sql);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 注意关闭链接
		return result;
	}

	/**
	 * sql語句操作數據庫
	 * 
	 * @param sql
	 * @return boolean
	 */
	public boolean executeSql(final String sql) {
		boolean result = true;
		try {
			HibernateCallback cb = new HibernateCallback() {
				public Object doInHibernate(Session session) {
					return session.createSQLQuery(sql).executeUpdate();
				} // end function
			};// end callback

			this.getHibernateTemplate().execute(cb);
		} catch (Exception e) {
			result = false;
		}

		return result;
		// boolean result = false;
		// // 获取会话
		// Session session = getHibernateTemplate().getSessionFactory()
		// .openSession();
		// // 定义 Transaction 事务
		// Transaction transaction = null;
		// List list = null;
		// try {
		// // 获取 Transaction 事务对象
		// transaction = session.beginTransaction();
		// // 查询多个持久对象
		// result = session.connection().createStatement().execute(sql);
		// result = true;
		// // 提交事务，完成数据库操作
		// transaction.commit();
		// } catch (Exception e) {
		// e.printStackTrace();
		// if (transaction != null) {
		// // 当发生异常时回滚事务，取消数据库操作
		// transaction.rollback();
		// }
		// }
		//
		// return result;
	}

	/**
	 * sql語句查询集合
	 * 
	 * @param sql
	 * @return boolean
	 */
	public List findListBySql(final String sql,Class className){
		HibernateCallback cb = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				return session.createSQLQuery(sql).list();
			} // end function
		};// end callback

		List list=(List) this.getHibernateTemplate().execute(cb);
		Iterator it=list.iterator();
		while ( it.hasNext()){
			 Object[] objs = (Object[]) it.next();
		    Class obj = null;
			try {
				obj = Class.forName(className.toString());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Field[]fields=obj.getFields();
		    for(Field field:fields){
		    	//objs[0];
		    }
			
		} 
		return null;
	}
}
