package com.vvvv.common.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.vvvv.common.handler.IHandler;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.tool.query.QueryUtil;
import com.vvvv.lamps.model.Product;


/**
 * @className:BaseDAO.java
 * @classDescription:公共DAO，实现对数据库的增删改查
 */
@Repository
public abstract class BaseDAO<T> {
	@Resource(name="springHandler")
	protected IHandler handler;
	   
	private Class<T> clazz;
	 public BaseDAO() {
		 clazz =(Class<T>) ((ParameterizedType) getClass()
	                                .getGenericSuperclass()).getActualTypeArguments()[0];
	    }
	/**
	 * 保存对象
	 * 
	 * @param obj
	 *            要保存的对象
	 * @return 布尔值
	 */
	public boolean save(T obj) {
		
		return handler.saveObj(obj);

	}

	/**
	 * 删除对象
	 * 
	 * @param obj
	 *            要删除的对象
	 * @return 布尔值
	 */
	public boolean delete(T obj) {

		return handler.deleteObj(obj);

	}
	/**
	 * 根据Id删除对象
	 * 
	 * @param obj
	 *            要删除的对象
	 * @return 布尔值
	 */
	public boolean deleteById(Serializable id) {

		return handler.deleteObj(handler.findObjById(clazz, id));

	}
	/**
	 * 修改对象
	 * 
	 * @param obj
	 *            要修改的对象
	 * @return 布尔值
	 */
	public boolean alter(T obj) {
		return handler.alterObj(obj);
	}

	/**
	 * 合并对象
	 * @author wei.luo
	 * @createTime 2012-4-26
	 * @param obj
	 * @return
	 */
	public boolean merge(T obj) {
		return handler.mergeObj(obj);
	}
	
	public boolean update(T obj){
		return handler.updateObj(obj);
	}
	
	/**
	 * 刷新缓存
	 * @author wei.luo
	 * @createTime 2012-4-23
	 */
	public void flush(){
		try {
			this.handler.getHibernateTemplate().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询对象
	 * 
	 * @param hql
	 *            查找语句
	 * @return 对象
	 */
	public T find(final String hql) {
		return (T) handler.findObj(hql);		
	}
	/**
	 * 查询对象(防止依赖注入)
	 * 
	 * @param hql
	 *            查找语句
	 * @param map 参数           
	 * @return 对象
	 */
	public T findOfMap(final String hql,Map<Serializable,Serializable> map) {
		return (T) handler.findObj(hql,map);		
	}
	/**
	 * 主要用于防止sql依赖注入
	 * 
	 * @param hql
	 *            hql语句
	 * @param map
	 * @return List集合
	 */
	public List<T> findListOfMap(final String hql,
			final Map<Serializable, Serializable> map) {
		return handler.findListOfObj(hql, map);
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
	public T findById(Serializable id) {

		return (T) handler.findObjById(clazz, id);

	}
	/**
	 * 根据id查询缓存对象
	 * 
	 * @param className
	 *            类名
	 * @param id
	 *            类id
	 * @return 对象
	 */
	public T findByIdLoad(Serializable id) {

		return (T) handler.findObjByIdLoad(clazz, id);

	}

	/**
	 * 查询集合
	 * 
	 * @param sql
	 *            要保存的对象
	 * @return 集合
	 */
	public List<T> findList(String hql) {

		return handler.findListOfObj(hql);

	}

	/**
	 * 根据hql查找限定条数的list
	 * @author wei.luo
	 * @createTime 2012-4-30
	 * @param hql
	 * @param first
	 * @param max
	 * @return
	 */
	public List<T> findListWithLimt(final String hql, final int first,final int max) {
		return this.handler.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				if (null == hql || "".equals(hql)) {
					return null;
				}
				Query query = session.createQuery(hql);
				query.setFirstResult(first);
				query.setMaxResults(max);
				return query.list();
			}
		});

	}
	
	/**
	 * 根据页数和条数查询集合
	 * 
	 * @param sql
	 *            要保存的对象
	 * @param page
	 *            页数
	 * @param count
	 *            显示条数
	 * @return 集合
	 */
	public List<T> findList(String hql, int page, int count) {

		return handler.findListOfObj(hql, page, count);

	}
	
	/**
	 * 根据hql查找集合的条数
	 * 
	 * @param hql
	 *            hql语句
	 * @return 返回查询总数
	 */
	public int findCountBySql(String hql) {

		return handler.findCountBySql(hql);

	}

	/**
	 * 获取聚合函数的值
	 * 
	 * @param hql
	 *            
	 * @param function 聚合函数 count(*)   sum(*)      
	 * @return 返回个数
	 */
	public int findCountByHql(String hql,String function) {
		return handler.findCountBySql(hql, function);
	}
	
	/**
	 * 根据sql查询语句返回ResultSet对象
	 * @author wei.luo
	 * @createTime 2012-4-20
	 * @param sql sql
	 * @return resultSet
	 */
	public ResultSet findResultBySql(String sql){
		this.handler.getHibernateTemplate().flush();
		return handler.findResultBySql(sql);
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
		ResultSet rs=this.findResultBySql("select count(*) totalCount from "+sql);
		try {
			if(rs.next()){
				totalCount=rs.getInt("totalCount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalCount;
	}
	
	//-------------------sql语句-----------------------//


	/**
	 * sql語句操作數據庫
	 * 
	 * @param sql
	 * @return boolean
	 */
	public boolean executeSql(String sql) {
		return handler.executeSql(sql);
	}
	/**
	 * sql語句查询集合
	 * 
	 * @param sql
	 * @return boolean
	 */
	public List findListBySql(final String sql,Class className){
		return handler.findListBySql(sql, className);
	}
	public IHandler getHandler() {
		return handler;
	}

	
	public void setHandler(IHandler handler) {
		this.handler = handler;
	}
	
	/**
	 * 查寻记录数
	 * @author wei.luo
	 * @createTime 2012-5-5
	 * @param page
	 * @param pfList
	 * @param hql
	 * @return
	 */
	public Page<T> findAll(Page<T> page,List<PropertyFilter> pfList,String hql){
		//初始化hql
		StringBuffer thql=new StringBuffer(hql);
		//设置查询条件
		String condition= QueryUtil.toSqlString(pfList, true);
		int totalCount= 0;
		//查找所有用户的总条数
		thql.append(condition);
		totalCount=this.handler.findCountByHql("select count(*) "+thql.toString());
		page.setTotalCount(totalCount);
		
		//是否存在排序
		if(page.isOrderBySetted()){
			thql.append(page.getOrder());
		}
		List<T> list= this.findList(thql.toString(), page.getPageNo(), page.getPageSize());
		//查出结果集
		page.setResult(list);
		return page;
	}
}
