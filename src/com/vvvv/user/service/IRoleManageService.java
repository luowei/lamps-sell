package com.vvvv.user.service;



import java.util.List;

import com.vvvv.common.service.IBaseService;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.user.model.Role;

/**
 * @className:IRoleManageService.java
 * @classDescription:权限管理接口
 * @author:xiayingjie
 * @createTime:2010-7-8
 */
public interface IRoleManageService extends IBaseService<Role>{
	/**
	 * 查询所有的角色
	 * @param page
	 * @param pfList 查询条件
	 * @return
	 */
	public Page findAllRoles(Page page,List<PropertyFilter> pfList);
	/**
	 * 查询所有的角色
	 * @param page
	 * @param pfList 查询条件
	 * @return
	 */
	public List<Role> findAllRoles();
}
