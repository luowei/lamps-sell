package com.vvvv.lamps.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.vvvv.common.dao.BaseDAO;
import com.vvvv.common.tool.page.Page;
import com.vvvv.lamps.model.News;

/**
 * @className:NewsDAO.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4下午12:47:44 
 */
@Repository
public class NewsDAO extends BaseDAO<News>{
	/**
	 * 根据sql语句查找歌曲列表
	 * @author wei.luo
	 * @createTime 2012-4-25
	 * @param sql sql语句
	 * @param page 页面对象
	 * @return 歌曲列表
	 */
	@SuppressWarnings("unchecked")
	public List<News>  findMusicsBySql(final String sql, Page<News> page){
		if(page==null){
			return getHandler().getHibernateTemplate().executeFind(new HibernateCallback<Object>() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					SQLQuery query = session.createSQLQuery(sql).addEntity("music", News.class);
					return query.list();
				}
			});
		}
		final int maxResuts = page.getPageSize();
		final int firstResult = (page.getPageNo() - 1) * maxResuts;
		return getHandler().getHibernateTemplate().executeFind(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql).addEntity("music", News.class);
				query.setParameter(0, firstResult);
				query.setParameter(1, maxResuts);
				return query.list();
			}
		});
	}
	
	/**
	 * 更新新闻
	 * @author wei.luo
	 * @createTime 2012-4-17
	 * @param news news 对象
	 * @return 成功或失败
	 */
	public boolean update(News news){
		try {
			this.getHandler().getHibernateTemplate().update(news);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 合并对象
	 * @author wei.luo
	 * @createTime 2012-4-26
	 * @param news
	 * @return
	 */
	public boolean merge(News news){
		try {
			this.getHandler().getHibernateTemplate().merge(news);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
}
