package com.vvvv.user.service;

import java.util.List;

import com.vvvv.common.service.IBaseService;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.user.model.UserInfo;

/**
 * @className:IUserManageService.java
 * @classDescription:用户管理接口
 * @author:xiayingjie
 * @createTime:2010-7-8
 */
public interface IUserManageService extends IBaseService<UserInfo>{
	/**
	 * 根据用户名密码查找对象
	 */
	public UserInfo login(String userName,String password);
	/**
	 * 查询所有的用户
	 * @param page
	 * @param pfList 查询条件
	 * @return
	 */
	public Page<UserInfo> findAllUsers(Page<UserInfo> page,List<PropertyFilter> pfList);
	/**
	 * 检测用户名是否存在
	 * @param userName
	 * @return
	 */
	public boolean checkUserName(String userName);

}
