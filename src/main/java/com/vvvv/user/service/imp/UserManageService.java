package com.vvvv.user.service.imp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vvvv.common.service.imp.BaseService;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.tool.query.QueryUtil;
import com.vvvv.user.dao.UserInfoDAO;
import com.vvvv.user.model.UserInfo;
import com.vvvv.user.service.IUserManageService;

/**
 * @className:UserManageService.java
 * @classDescription:用户管理类
 * @author:xiayingjie
 * @createTime:2010-7-8
 */
@Service
public class UserManageService extends BaseService<UserInfo> implements IUserManageService{
	@Resource(name="userInfoDAO")
	private  UserInfoDAO userDAO;
	
		
	//====方法定义区====//
	
	/**
	 * 根据用户名密码查找对象
	 */
	public UserInfo login(String userName,String password){
		String hql="from UserInfo where username=:userName and password=:userPassword";
		Map<Serializable,Serializable> map=new HashMap<Serializable,Serializable>();
		map.put("userName", userName);
		
		map.put("userPassword", password);
		return this.userDAO.findOfMap(hql, map);
	}
	/**
	 * 查询所有的用户
	 * @param page
	 * @param pfList 查询条件
	 * @return
	 */
	public Page<UserInfo> findAllUsers(Page<UserInfo> page,List<PropertyFilter> pfList){
		//初始化hql
		StringBuffer hql=new StringBuffer("from UserInfo");
		//设置查询条件
		String condition= QueryUtil.toSqlString(pfList, true);
		hql.append(condition);
		//查找所有用户的总条数
		int totalCount=this.userDAO.findCountBySql(hql.toString());
		page.setTotalCount(totalCount);
		
		
		//是否存在排序
		if(page.isOrderBySetted()){
			hql.append(page.getOrder());
		}
		List<UserInfo> list=this.userDAO.findList(hql.toString(), page.getPageNo(), page.getPageSize());
		//查出结果集
		page.setResult(list);
	
		return page;
	}
	/**
	 * 检测用户名是否存在
	 * @param userName
	 * @return
	 */
	public boolean checkUserName(String userName){
		boolean flag=true;
		String hql="from UserInfo where userName=:userName";
		Map<Serializable,Serializable> map=new HashMap<Serializable,Serializable>();
		map.put("userName", userName);
		
		UserInfo user= this.userDAO.findOfMap(hql, map);
		if(null==user){
			flag=false;
		}
		return flag;
	}
	//==========//
	public UserInfoDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(UserInfoDAO userDAO) {
		this.userDAO = userDAO;
	}

	




}
