package com.vvvv.lamps.service;

import java.util.List;

import com.vvvv.common.service.IBaseService;
import com.vvvv.common.tool.page.Page;
import com.vvvv.lamps.model.News;

/**
 * @className:INewsService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4 
 */
public interface INewsService extends IBaseService<News>{
	/**
	 * 更新新闻
	 * @author wei.luo
	 * @createTime 2012-4-17
	 * @param news news 对象
	 * @return 成功或失败
	 */
	public boolean update(News news);
	
	/**
	 * 合并对象
	 * @author wei.luo
	 * @createTime 2012-4-26
	 * @param news
	 * @return
	 */
	public boolean merge(News news);
	
	/**
	 * 根据sql语句查找歌曲列表
	 * @author wei.luo
	 * @createTime 2012-4-25
	 * @param sql sql语句
	 * @param page 页面对象
	 * @return 歌曲列表
	 */
	@SuppressWarnings("unchecked")
	public List<News>  findMusicsBySql(final String sql, Page<News> page);
	
	/**
	 * 修改排序
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param newOrder
	 * @param oldOrder
	 * @return
	 */
	public int changeOrder(int newOrder, int oldOrder);
}
