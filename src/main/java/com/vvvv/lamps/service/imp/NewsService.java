package com.vvvv.lamps.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vvvv.common.service.imp.BaseService;
import com.vvvv.common.tool.page.Page;
import com.vvvv.lamps.dao.NewsDAO;
import com.vvvv.lamps.model.News;
import com.vvvv.lamps.service.INewsService;

/**
 * @className:NewsService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4
 */
@Service
public class NewsService extends BaseService<News> implements INewsService{
	@Resource(name = "newsDAO")
	private NewsDAO newsDAO;
	
	
	/**
	 * 更新新闻
	 * @author wei.luo
	 * @createTime 2012-4-17
	 * @param news news 对象
	 * @return 成功或失败
	 */
	public boolean update(News news){
		return this.newsDAO.update(news);
	}
	
	/**
	 * 合并对象
	 * @author wei.luo
	 * @createTime 2012-4-26
	 * @param news
	 * @return
	 */
	public boolean merge(News news){
		return this.newsDAO.merge(news);
	}
	
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
		return this.newsDAO.findMusicsBySql(sql, page);
	}

	public int changeOrder(int newOrder, int oldOrder) {
		int max = this.newsDAO.findCountByHql("from News", "max(orders)");
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
			hql = "update News set orders=orders-1 where orders <= " + newOrder + " and orders > " + oldOrder;
		} else if (newOrder < oldOrder) {
			hql = "update News set orders=orders+1 where orders >= " + newOrder + " and orders < " + oldOrder;
		} else {
			return newOrder;
		}
		this.newsDAO.executeSql(hql);
		return newOrder;
	}
}
