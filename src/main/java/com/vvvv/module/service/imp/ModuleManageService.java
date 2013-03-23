package com.vvvv.module.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vvvv.common.service.imp.BaseService;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.tool.query.QueryUtil;
import com.vvvv.module.dao.ModuleDAO;
import com.vvvv.module.model.Module;
import com.vvvv.module.service.IModuleManageService;


/**
 * @className:ModuleManageService.java
 * @classDescription:模块管理类
 * @author:xiayingjie
 * @createTime:2010-7-8
 */
@Service
public class ModuleManageService extends BaseService<Module> implements IModuleManageService{
	@Resource(name="moduleDAO")
	private  ModuleDAO moduleDAO;
	
	//====方法定义区====//
	/**
	 * 查询所有的模块
	 * @param page
	 * @param pfList 查询条件
	 * @return
	 */
	public Page findAllModules(Page page,List<PropertyFilter> pfList){
		//初始化hql
		StringBuffer hql=new StringBuffer("from Module");
		//设置查询条件
		String condition= QueryUtil.toSqlString(pfList, true);
		hql.append(condition);
		//查找所有用户的总条数
		int totalCount=this.moduleDAO.findCountBySql(hql.toString());
		page.setTotalCount(totalCount);
		
		
		//是否存在排序
		if(page.isOrderBySetted()){
			hql.append(page.getOrder());
		}
		List<Module> list=this.moduleDAO.findList(hql.toString(), page.getPageNo(), page.getPageSize());
		//查出结果集
		page.setResult(list);
	
		return page;
	}

	
	/**
	 * 查询所有的模块
	 * @return
	 */
	public List<Module> findAllMoudles(){
		//初始化hql
		String hql="from Module";
		return this.moduleDAO.findList(hql);
	}


	public ModuleDAO getModuleDAO() {
		return moduleDAO;
	}


	public void setModuleDAO(ModuleDAO moduleDAO) {
		this.moduleDAO = moduleDAO;
	}
	
	
	//==========//	
	


}
