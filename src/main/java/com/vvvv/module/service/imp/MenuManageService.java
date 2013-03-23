package com.vvvv.module.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vvvv.common.service.imp.BaseService;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.tool.query.QueryUtil;
import com.vvvv.module.dao.MenuDAO;
import com.vvvv.module.model.Menu;
import com.vvvv.module.service.IMenuManageService;


/**
 * @className:MenuManageService.java
 * @classDescription:菜单管理类
 * @author:xiayingjie
 * @createTime:2010-7-8
 */
@Service
public class MenuManageService extends BaseService<Menu> implements IMenuManageService{
	@Resource(name="menuDAO")
	private  MenuDAO menuDAO;
	
	//====方法定义区====//
	/**
	 * 查询所有的权限
	 * @param page
	 * @param pfList 查询条件
	 * @return
	 */
	public Page findAllMenus(Page page,List<PropertyFilter> pfList){
		//初始化hql
		StringBuffer hql=new StringBuffer("from Menu");
		//设置查询条件
		String condition= QueryUtil.toSqlString(pfList, true);
		hql.append(condition);
		//查找所有用户的总条数
		int totalCount=this.menuDAO.findCountBySql(hql.toString());
		page.setTotalCount(totalCount);
		
		
		//是否存在排序
		if(page.isOrderBySetted()){
			hql.append(page.getOrder());
		}
		List<Menu> list=this.menuDAO.findList(hql.toString(), page.getPageNo(), page.getPageSize());
		//查出结果集
		page.setResult(list);
	
		return page;
	}
	/**
	 * 查询所有的菜单
	 * @return
	 */
	public List<Menu> findAllMenus(){
		String hql="from Menu order by orders";
		return this.menuDAO.findList(hql);
	}
	public MenuDAO getMenuDAO() {
		return menuDAO;
	}
	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	
	
	
	
	//==========//	





}
