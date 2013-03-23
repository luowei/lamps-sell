package com.vvvv.user.service;

import java.util.List;

import com.vvvv.common.service.IBaseService;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.user.model.Action;



/**
 * @className:IActionManageService.java
 * @classDescription:动作权限管理接口
 * @author:xiayingjie
 * @createTime:2010-7-8
 */
public interface IActionManageService extends IBaseService<Action>{
	/**
	 * 查询所有的权限
	 * @param page
	 * @param pfList 查询条件
	 * @return
	 */
	public Page findAllActions(Page page,List<PropertyFilter> pfList);
}
