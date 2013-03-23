package com.vvvv.common.service;

import java.util.Map;

/**
 * @className:ICommonService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-8
 */
public interface ICommonService {
	/**
	 * 根据 key 获取MemCache缓存的对象
	 * @author huangweiwei
	 * @createTime 2011-7-27
	 * @param key
	 * @return
	 */
	public Object getMemCache(String key);

	/**
	 * 根据key ,time, value 重新设置MemCache缓存
	 * @author wandonghai
	 * @createTime 2011-9-6
	 * @param key
	 * @param value
	 */
	public void setMemCache(String key,int time ,Object value);
	
	
	/**
	 * 根据key ,value 重新设置MemCache缓存
	 * @author huangweiwei
	 * @createTime 2011-7-27
	 * @param key
	 * @param value
	 */
	public void setMemCache(String key, Object value);

	/**
	 * 清除MemCache 缓存
	 * @author huangweiwei
	 * @createTime 2011-7-27
	 * @param key
	 */
	public void removeMemCache(String key);

	/**
	 * 利用反射根据参数名修改参数值
	 * 
	 * @author huangweiwei
	 * @createTime 2011-7-29
	 * @param c
	 * @param configName
	 * @param configVaule
	 */
	@SuppressWarnings("unchecked")
	public void changeFields(Class c, String configName, String configVaule);

	/**
	 * 把类属性名和值封装到map
	 * 
	 * @author huangweiwei
	 * @createTime 2011-7-29
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getFields(Class c);
	/**
	 * 方案1：清除 缓存 EhCache和重置 memCache 缓存
	 * 
	 * @author jianyuan.yang
	 * @createTime 2011-7-13
	 * @param list
	 */
	public void removeALlCache();
	/**
	 * 根据清除 EnCache(details)整个房间
	 * 
	 * @author jianyuan.yang
	 * @createTime 2011-6-30
	 */
	public void removeEhcache();
	/**
	 * 根据 key 获取缓存EhCache的对象 *
	 * 
	 * @author jianyuan.yang
	 * @createTime 2011-7-13
	 * @param key
	 *            缓存key
	 * @return 返回缓存的对象
	 */
	public Object getEhCache(String key);
	/**
	 * 根据key ,value 重新设置缓存EhCache
	 * 
	 * @author jianyuan.yang
	 * @createTime 2011-7-13
	 * @param key
	 *            缓存主键
	 * @param value
	 *            缓存的value
	 */
	public void setEhCache(String key, Object value);
	
	/**
	 * 检查json数据格式
	 * @author wei.luo
	 * @createTime 2012-4-8
	 * @param json
	 * @return
	 */
	public boolean checkJSON(String json);
	
	/**
	 * 把类的属性名和值封装到map,不包括私有属性
	 * @author wei.luo
	 * @createTime 2012-3-30
	 * @param c	类的类型
	 * @return	封装了属性名和值的map
	 */
	public Map<String, String> getFields2(Class c);
	
	/**
	 * 根据类的属性名，得到它的值
	 * @author wei.luo
	 * @createTime 2012-4-5
	 * @param c	类
	 * @param key	属性名
	 * @return 属性值
	 */
	public String getFieldValue(Class c,String key);
}
