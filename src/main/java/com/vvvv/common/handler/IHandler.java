package com.vvvv.common.handler;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;


/**
 * @className:IHandler.java
 * @classDescription:数据库处理接口
 */
public interface IHandler {

	/**
	 * 获取HibernateTemplate
	 * @author wei.luo
	 * @createTime 2012-4-26
	 * @return 返回HibernateTemplate
	 */
	public HibernateTemplate getHibernateTemplate();
	
	/**
	 * 保存对象
	 * 
	 * @param obj
	 * @return
	 */
	public boolean saveObj(Object obj);

	/**
	 * 删除对象
	 * 
	 * @param obj
	 * @return
	 */
	public boolean deleteObj(Object obj);

	/**
	 * 修改对象
	 * 
	 * @param obj
	 * @return
	 */
	public boolean alterObj(Object obj);

	/**
	 * 合并对对象
	 * @author wei.luo
	 * @createTime 2012-4-26
	 * @param obj
	 * @return
	 */
	public boolean mergeObj(Object obj);
	/**
	 * 更新对象
	 * @author wei.luo
	 * @createTime 2012-4-26
	 * @param obj
	 * @return
	 */
	public boolean updateObj(Object obj);
	
	/**
	 * 查找对象
	 * 
	 * @param hql
	 *            hql语句
	 * @return 对象
	 */
	public Object findObj(String hql);

	/**
	 * 根据id查找对象
	 * 
	 * @param className
	 *            类型名
	 * @param id
	 *            对象id
	 * @return 对象
	 */
	public Object findObjById(Class className, Serializable id);
	/**
	 * 根据id查询延时对象
	 * 
	 * @param className
	 *            类名
	 * @param id
	 *            类id
	 * @return 对象
	 */
	public Object findObjByIdLoad(Class className, Serializable id);

	/**
	 * 根据hql查找集合
	 * 
	 * @param hql
	 *            hql语句
	 * @return 返回集合
	 */
	public List findListOfObj(String hql);
	/**
	 * 查询对象(防止依赖注入)
	 * 
	 * @param hql
	 *            查找语句
	 * @param map 参数       
	 * @return 对象
	 */
	public Object findObj(final String hql,final Map<Serializable, Serializable> map);

	/**
	 * 主要用于防止sql依赖注入
	 * 
	 * @param hql
	 *            hql语句（例如" from UserInfo where id=:id and
	 *            password=:password")query.setParameter("id",userForm.getId())
	 * @param map
	 * @return List集合
	 */
	public List findListOfObj(String hql, Map<Serializable, Serializable> map);

	/**
	 * 根据hql，集合索引，显示条数 查找集合（主要用于分页）
	 * 
	 * @param hql
	 *            hql查询语句
	 * @param page
	 *            当前页数
	 * @param count
	 *            取几条数据
	 * @return 集合对象
	 */
	public List findListOfObj(String hql, int page, int count);

	/**
	 * 根据hql查找集合的条数
	 * 
	 * @param hql
	 *            hql语句
	 * @return 返回查询总数
	 */
	public int findCountBySql(String hql);
	
	/**
	 * 查找总数
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param hql
	 * @return
	 */
	public int findCountByHql( String hql);
	
	/**
	 * 获取聚合函数的值
	 * 
	 * @param hql
	 *            
	 * @param function 聚合函数 count(*)   sum(*)      
	 * @return 返回个数
	 */
	public int findCountBySql(String hql,String function) ;
	
	/**
	 * 根据sql查询语句返回ResultSet对象
	 * @author wei.luo
	 * @createTime 2012-4-20
	 * @param sql sql
	 * @return resultSet
	 */
	public ResultSet findResultBySql(String sql);

	/**
	 * sql語句操作數據庫
	 * 
	 * @param sql
	 * @return boolean
	 */
	public boolean executeSql(String sql);
	/**
	 * sql語句查询集合
	 * 
	 * @param sql
	 * @return boolean
	 */
	public List findListBySql(final String sql,Class className);
	

}
