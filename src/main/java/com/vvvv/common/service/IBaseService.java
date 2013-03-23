package com.vvvv.common.service;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.query.PropertyFilter;

/**
 * @className:ServiceInteface.java
 * @classDescription:通用Service接口
 * @author:wei.luo
 * @createTime:2011-5-25
 */
public interface IBaseService<T>{
	/**
	 * 保存对象
	 * 
	 * @param obj
	 *            要保存的对象
	 * @return 布尔值
	 */
	public boolean save(T obj);

	/**
	 * 删除对象
	 * 
	 * @param obj
	 *            要删除的对象
	 * @return 布尔值
	 */
	public boolean delete(T obj);

	/**
	 * 删除对象
	 * 
	 * @param obj
	 *            要删除的对象
	 * @return 布尔值
	 */
	public boolean deleteById(Serializable id);

	/**
	 * 修改对象
	 * 
	 * @param obj
	 *            要修改的对象
	 * @return 布尔值
	 */
	public boolean alter(T obj) ;

	/**
	 * 刷新缓存
	 * @author wei.luo
	 * @createTime 2012-4-23
	 */
	public void flush();
	/**
	 * 合并对象
	 * @author wei.luo
	 * @createTime 2012-4-26
	 * @param obj
	 * @return
	 */
	public boolean merge(T obj);
	/**
	 * 更新对象
	 * @author wei.luo
	 * @createTime 2012-4-26
	 * @param obj
	 * @return
	 */
	public boolean update(T obj);
	
	/**
	 * 查询对象
	 * 
	 * @param hql
	 *            查找语句
	 * @return 对象
	 */
	public T find(final String hql);
	/**
	 * 查询对象(防止依赖注入)
	 * 
	 * @param hql
	 *            查找语句
	 * @param map 参数           
	 * @return 对象
	 */
	public T findOfMap(final String hql,Map<Serializable,Serializable> map);
	/**
	 * 主要用于防止sql依赖注入
	 * 
	 * @param hql
	 *            hql语句
	 * @param map
	 * @return List集合
	 */
	public List<T> findListOfMap(final String hql,
			final Map<Serializable, Serializable> map);

	/**
	 * 根据id查询对象
	 * 
	 * @param className
	 *            类名
	 * @param id
	 *            类id
	 * @return 对象
	 */
	public T findById(Serializable id) ;
	/**
	 * 根据id查询缓存对象
	 * 
	 * @param className
	 *            类名
	 * @param id
	 *            类id
	 * @return 对象
	 */
	public T findByIdLoad(Serializable id);

	/**
	 * 查询集合
	 * 
	 * @param sql
	 *            要保存的对象
	 * @return 集合
	 */
	public List<T> findList(String hql);

	/**
	 * 根据hql查找限定条数的list
	 * @author wei.luo
	 * @createTime 2012-4-30
	 * @param hql
	 * @param first
	 * @param max
	 * @return
	 */
	public List<T> findListWithLimt(final String hql, final int first,final int max);
	
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
	public List<T> findList(String hql, int page, int count);

	/**
	 * 根据hql查找集合的条数
	 * 
	 * @param hql
	 *            hql语句
	 * @return 返回查询总数
	 */
	public int findCountBySql(String hql) ;

	/**
	 * 根据sql查询数量
	 * @author wei.luo
	 * @createTime 2012-5-1
	 * @param sql
	 * @return
	 */
	public Integer findTotalCountBySql(String sql);
	
	/**
	 * 查寻记录数
	 * @author wei.luo
	 * @createTime 2012-5-5
	 * @param page
	 * @param pfList
	 * @param hql
	 * @return
	 */
	public Page<T> findAll(Page<T> page,List<PropertyFilter> pfList,String hql);
	
	//-------------------sql语句-----------------------//


	/**
	 * sql語句操作數據庫
	 * 
	 * @param sql
	 * @return boolean
	 */
	public boolean executeSql(String sql) ;
	/**
	 * sql語句查询集合
	 * 
	 * @param sql
	 * @return boolean
	 */
	public List findListBySql(final String sql,Class className);
	
}
