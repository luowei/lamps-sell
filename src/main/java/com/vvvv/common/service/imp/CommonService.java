package com.vvvv.common.service.imp;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvvv.common.tool.cache.EHCacheUtil;
import com.vvvv.common.service.ICommonService;

/**
 * @className:CommonService.java
 * @classDescription:
 * @author:wangjia
 */
@Service
public class CommonService implements ICommonService {
	//@Autowired
	protected MemcachedClient memcachedClient;

	/**
	 * 根据 key 获取MemCache缓存的对象
	 * @author huangweiwei
	 * @createTime 2011-7-27
	 * @param key
	 * @return
	 */
	public Object getMemCache(String key) {
		try {
			return this.memcachedClient.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据key ,value 重新设置MemCache缓存
	 * @author huangweiwei
	 * @createTime 2011-7-27
	 * @param key
	 * @param value
	 */
	public void setMemCache(String key, Object value) {
		try {
			this.memcachedClient.set(key, 60 * 60 * 1, value);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 清除memCache 缓存
	 * 
	 * @author huangweiwei
	 * @createTime 2011-7-27
	 * @param key
	 */

	public void removeMemCache(String key) {
		try {
			this.memcachedClient.delete(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
	public void changeFields(Class c, String configName, String configVaule) {
		Field[] fs = c.getDeclaredFields();
		for (Field f : fs) {
			// 获取参数名进行比较
			if (f.getName().equals(configName)) {
				try {
					// 对参数传值
					f.set(configName, configVaule);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 把类属性名和值封装到map
	 * 
	 * @author huangweiwei
	 * @createTime 2011-7-29
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getFields(Class c) {
		Field[] fs = c.getDeclaredFields();
		try {
			Map<String, String> map = new HashMap<String, String>();
			// 把参数名和值封装到map中
			for (Field f : fs) {
				String configName = f.getName();
				String configValue = (String) f.get(f.getName());
				map.put(configName, configValue);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 把类的属性名和值封装到map,不包括私有属性
	 * @author wei.luo
	 * @createTime 2012-3-30
	 * @param c  类的类型
	 * @return  封装了属性名和值的map
	 */
	public Map<String, String> getFields2(Class c) {
		Field[] fs = c.getFields();
		try {
			Map<String, String> map = new HashMap<String, String>();
			// 把参数名和值封装到map中
			for (Field f : fs) {
				String configName = f.getName();
				String configValue = (String) f.get(configName);
				map.put(configName, configValue);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据类的属性名，得到它的值
	 * @author wei.luo
	 * @createTime 2012-4-5
	 * @param c	类
	 * @param key	属性名
	 * @return 属性值
	 */
	public String getFieldValue(Class c,String key){
		Field[] fs = c.getFields();
		try {
			for (Field f : fs) {
				String configName = f.getName();
				if(configName.equals( key)){
					return (String) f.get(configName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 方案1：清除 缓存 EhCache和重置 memCache 缓存
	 * 
	 * @author jianyuan.yang
	 * @createTime 2011-7-13
	 * @param list
	 */
	public void removeALlCache() {
		try {
			// 清除页脚音乐缓存
			this.removeMemCache("ayyc_recommendMusicList");
			//清楚页脚广告缓存
			this.removeMemCache("ayyc_recommendAtjList");
			removeEhcache();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据清除 EnCache(details)整个房间
	 * 
	 * @author jianyuan.yang
	 * @createTime 2011-6-30
	 */
	public void removeEhcache() {
		try {
			EHCacheUtil.initCacheManager();
			EHCacheUtil.initCache("details");
			EHCacheUtil.removeCache("details");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据 key 获取缓存EhCache的对象 *
	 * 
	 * @author jianyuan.yang
	 * @createTime 2011-7-13
	 * @param key
	 *            缓存key
	 * @return 返回缓存的对象
	 */
	public Object getEhCache(String key) {
		try {
			EHCacheUtil.initCacheManager();
			EHCacheUtil.initCache("details");
			return EHCacheUtil.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

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
	public void setEhCache(String key, Object value) {
		try {
			EHCacheUtil.initCacheManager();
			EHCacheUtil.initCache("details");
			EHCacheUtil.put(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据key ,time, value 重新设置MemCache缓存
	 * @author wandonghai
	 * @createTime 2011-9-6
	 * @param key
	 * @param value
	 */
	public void setMemCache(String key, int time,Object value) {
		try {
			this.memcachedClient.set(key, time, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查json数据格式
	 * @author wei.luo
	 * @createTime 2012-4-8
	 * @param json
	 * @return
	 */
	public boolean checkJSON(String json){
		if(StringUtils.isBlank(json)||json.length()>500){
			return false;
		} else{
			return true;
		}   
	}

}
