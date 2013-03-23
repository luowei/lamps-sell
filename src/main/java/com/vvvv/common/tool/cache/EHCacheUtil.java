package com.vvvv.common.tool.cache;


import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;


/**
 * @className:EHCacheUtil.java
 * @classDescription:ehcache工具类
 * @author:wei.luo
 * @createTime:2012-2-1
 */

public class EHCacheUtil {

	private  static CacheManager cacheManager = null;
	
	private static Cache cache=null;

//------------------简化---------------------
	
	/**
	 * 初始化缓存管理容器
	 */
	public static CacheManager initCacheManager() {
		try {
			if (cacheManager == null)
				cacheManager = CacheManager.getInstance();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cacheManager;
	}

	/**
	 * 初始化缓存管理容器
	 * 
	 * @param path
	 *            ehcache.xml存放的路徑
	 */
	public static CacheManager initCacheManager(String path) {
		try {
			
			if (cacheManager == null){
				System.out.println("为进来"+path);
				cacheManager = CacheManager.getInstance().create(path);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return cacheManager;
	}
	
	/**
	 * 初始化cache
	 * @param cacheName
	 * @return
	 */
	public static Cache initCache(String cacheName) {
		checkCacheManager();
		
		if(null==cacheManager.getCache(cacheName)){
			cacheManager.addCache(cacheName);
		}
	
		cache=cacheManager.getCache(cacheName);
		return cache;
	}
	
	
	/**
	 * 添加缓存
	 * @param key
	 *            关键字
	 * @param value
	 *            值
	 */
	public static void put(Object key,Object value) {
		checkCache();
		// 创建Element,然后放入Cache对象中
		Element element = new Element(key, value);
		cache.put(element);
	}
	/**
	 * 获取cache
	 * 
	 * @param key
	 *            关键字
	 * @return 

	 */
	public static Object get(Object key){
		checkCache();
		Element element = cache.get(key);
		if(null==element){
			return null;
		}
		return  element.getObjectValue();
	}

	//--------------更加方便使用的----------------
	//private static CacheManager  myManager=null;
	//private static Cache  myCache=null;
	
	/**
	 * 初始化缓存
	 * @author wei.luo
	 * @createTime 2012-4-23
	 * @param cacheName 缓存名称
	 * @param maxElementsInMemory 元素最大数量
	 * @param overflowToDisk 是否持久化到硬盘
	 * @param eternal 是否会死亡
	 * @param timeToLiveSeconds 缓存存活时间
	 * @param timeToIdleSeconds 缓存的间隔时间
	 * @return 缓存
	 * @throws Exception 异常
	 */
	public static Cache initCache(String cacheName,int maxElementsInMemory,
			boolean overflowToDisk,boolean eternal,long timeToLiveSeconds,
			long timeToIdleSeconds) throws Exception{
		try {
			CacheManager singletonManager = CacheManager.create();
			Cache myCache = singletonManager.getCache(cacheName);
			if(myCache!=null){
				CacheConfiguration config = cache.getCacheConfiguration();
				config.setTimeToLiveSeconds(timeToLiveSeconds);
				config.setMaxElementsInMemory(maxElementsInMemory);
				config.setOverflowToDisk(overflowToDisk);
				config.setEternal(eternal);
				config.setTimeToIdleSeconds(timeToIdleSeconds);
			}
			if(myCache==null){
				Cache memoryOnlyCache = new Cache(cacheName, maxElementsInMemory, overflowToDisk, 
						eternal, timeToLiveSeconds, timeToIdleSeconds);
				singletonManager.addCache(memoryOnlyCache);
				myCache = singletonManager.getCache(cacheName);
			}
			return myCache;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("init cache "+cacheName+" failed!!!");
		}
	}
	
	/**
	 * 初始化cache
	 * @author wei.luo
	 * @createTime 2012-4-23
	 * @param cacheName cache的名字
	 * @param timeToLiveSeconds 有效时间
	 * @return cache 缓存
	 * @throws Exception 异常
	 */
	public static Cache initCache(String cacheName,long timeToLiveSeconds) throws Exception{
		try {
			CacheManager myManager = CacheManager.create();
			Cache myCache = myManager.getCache(cacheName);
			if(myCache!=null){
				CacheConfiguration config = myCache.getCacheConfiguration();
				config.setTimeToLiveSeconds(timeToLiveSeconds);
			}
			if(myCache==null){
				myCache = new Cache(
				  new CacheConfiguration(cacheName, EHCacheConfig.MAXELEMENTSINMEMORY)
				    .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
				    .overflowToDisk(EHCacheConfig.OVERFLOWTODISK)
				    .eternal(EHCacheConfig.ETERNAL)
				    .timeToLiveSeconds(timeToLiveSeconds)
				    .timeToIdleSeconds(EHCacheConfig.TIMETOIDLESECONDS)
				    .diskPersistent(EHCacheConfig.DISKPERSISTENT)
				    .diskExpiryThreadIntervalSeconds(0));
				myManager.addCache(myCache);
			}
			return myCache;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("init cache "+cacheName+" failed!!!");
		} 
	}
	
	/**
	 * 初始化Cache
	 * @author wei.luo
	 * @createTime 2012-4-26
	 * @param cacheName cache容器名
	 * @return cache容器
	 * @throws Exception 失败抛出异常
	 */
	public static Cache initMyCache(String cacheName) throws Exception{
		try {
			CacheManager myManager = CacheManager.create();
			Cache myCache = myManager.getCache(cacheName);
			if(myCache==null){
				myCache = new Cache(
				  new CacheConfiguration(cacheName, EHCacheConfig.MAXELEMENTSINMEMORY)
				    .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
				    .overflowToDisk(EHCacheConfig.OVERFLOWTODISK)
				    .eternal(EHCacheConfig.ETERNAL)
				    .timeToLiveSeconds(EHCacheConfig.TIMETOlIVESECONDS)
				    .timeToIdleSeconds(EHCacheConfig.TIMETOIDLESECONDS)
				    .diskPersistent(EHCacheConfig.DISKPERSISTENT)
				    .diskExpiryThreadIntervalSeconds(0));
				myManager.addCache(myCache);
			}
			return myCache;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("init cache "+cacheName+" failed!!!");
		} 
	}
	
	/**
	 * 修改缓存容器配置
	 * @author wei.luo
	 * @createTime 2012-4-23
	 * @param cacheName 缓存名
	 * @param timeToLiveSeconds 有效时间
	 * @param maxElementsInMemory 最大数量
	 * @return 真
	 * @throws Exception 异常
	 */
	public static boolean modifyCache(String cacheName,
			long timeToLiveSeconds,int maxElementsInMemory) throws Exception{
		try {
			if(StringUtils.isNotBlank(cacheName) && timeToLiveSeconds!=0L
					&& maxElementsInMemory!=0){
				CacheManager myManager = CacheManager.create();
				Cache myCache = myManager.getCache(cacheName);
				CacheConfiguration config = myCache.getCacheConfiguration();
				config.setTimeToLiveSeconds(timeToLiveSeconds);
				config.setMaxElementsInMemory(maxElementsInMemory);
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("modify cache "+cacheName+" failed!!!");
		}
	}
	
	/**
	 * 向指定容器中设置值
	 * @author wei.luo
	 * @createTime 2012-4-23
	 * @param vesselName 容器名
	 * @param key 键
	 * @param value 值
	 * @return 返回真
	 * @throws Exception 异常
	 */
	public static boolean setValue(String cacheName,String key, Object value) throws Exception{
		try {
			CacheManager myManager = CacheManager.create();
			Cache myCache = myManager.getCache(cacheName);
			if(myCache==null){
				myCache=initCache(cacheName);
				//myManager.addCache(cacheName);
				//myCache=myManager.getCache(cacheName);
			}
			Element element=new Element(key, value);
			element.updateAccessStatistics();
			myCache.put(element);
			return true;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("set cache "+cacheName+" failed!!!");
		}
	}
	
	/**
	 * 向指定容器中设置值
	 * @author wei.luo
	 * @createTime 2012-4-26
	 * @param cacheName 容器名
	 * @param key 键
	 * @param value 值
	 * @param timeToIdleSeconds 间歇时间
	 * @param timeToLiveSeconds 存活时间
	 * @return 真
	 * @throws Exception 抛出异常
	 */
	public static boolean setValue(String cacheName,String key, Object value,
			Integer timeToLiveSeconds) throws Exception{
		try {
			CacheManager myManager = CacheManager.create();
			Cache myCache = myManager.getCache(cacheName); 
			if(myCache==null){
				initCache(cacheName, timeToLiveSeconds);
				myCache = myManager.getCache(cacheName); 
			}
			myCache.put(new Element(key, value, EHCacheConfig.ETERNAL, 
					EHCacheConfig.TIMETOIDLESECONDS, timeToLiveSeconds));
			return true;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("set cache "+cacheName+" failed!!!");
		}
	}
	
	/**
	 * 从ehcache的指定容器中取值
	 * @author wei.luo
	 * @createTime 2012-4-23
	 * @param key 键
	 * @return 返回Object类型的值
	 * @throws Exception 异常
	 */
	public static Object getValue(String cacheName,String key){
		try {
			CacheManager myManager = CacheManager.create();
			Cache myCache = myManager.getCache(cacheName);
			if(myCache==null){
				myCache=initMyCache(cacheName);
			}
			return myCache.get(key).getValue();
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
			//throw new Exception("get cache "+cacheName+" failed!!!");
		}
	}
	
	/**
	 * 删除指定的ehcache容器
	 * @author wei.luo
	 * @createTime 2012-4-23
	 * @param vesselName
	 * @return 真
	 * @throws Exception 失败抛出异常
	 */
	public static boolean removeEhcache(String cacheName) throws Exception{
		try {
			CacheManager myManager = CacheManager.create();
			myManager.removeCache(cacheName);
			return true;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("remove cache "+cacheName+" failed!!!");
		}
	}
	
	/**
	 * 删除所有的EHCache容器
	 * @author wei.luo
	 * @createTime 2012-4-26
	 * @param cacheName 容器名
	 * @return 返回真
	 * @throws Exception 失败抛出异常
	 */
	public static boolean removeAllEhcache(String cacheName) throws Exception{
		try {
			CacheManager myManager = CacheManager.create();
			myManager.removalAll();
			return true;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("remove cache "+cacheName+" failed!!!");
		}
	}
	
	/**
	 * 删除EHCache容器中的元素
	 * @author wei.luo
	 * @createTime 2012-4-26
	 * @param cacheName	容器名
	 * @param key 键
	 * @return 真
	 * @throws Exception 失败抛出异常
	 */
	public static boolean removeElment(String cacheName,String key) throws Exception{
		try {
			CacheManager myManager = CacheManager.create();
			Cache myCache = myManager.getCache(cacheName);
			if(myCache!=null){
				myCache.remove(key);
			}
			return true;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("remove cache "+cacheName+" failed!!!");
		}
	}
	
	/**
	 * 删除指定容器中的所有元素
	 * @author wei.luo
	 * @createTime 2012-4-26
	 * @param cacheName 容器名
	 * @param key 键
	 * @return 真
	 * @throws Exception 失败抛出异常
	 */
	public static boolean removeAllElment(String cacheName) throws Exception{
		try {
			CacheManager myManager = CacheManager.create();
			Cache myCache = myManager.getCache(cacheName);
			if(myCache!=null){
				myCache.removeAll();
			}
			return true;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("remove cache "+cacheName+" failed!!!");
		}
	}
	
	
	//------------------方便调用------------

	/**
	 * 释放CacheManage
	 * 
	 */
	public static  void shutdown() {
		cacheManager.shutdown();
	}

	/**
	 * 移除cache
	 * @param cacheName
	 */
	public static void removeCache(String cacheName){
		checkCacheManager();
		cache=cacheManager.getCache(cacheName);
		if(null!=cache){
			cacheManager.removeCache(cacheName);
		}
		
	}
	/**
	 * 移除cache中的key
	 * @param cacheName
	 */
	public static void remove(String key){
		checkCache();
		cache.remove(key);

	}
	
	/**
	 * 移除所有cache
	 */
	public static void removeAllCache(){
		checkCacheManager();
		cacheManager.removalAll();
	}
	
	/**
	 * 移除所有Element
	 */
	public static void removeAllKey(){
		checkCache();
		cache.removeAll();
	}
	
	/**
	 * 获取所有的cache名称
	 * @return
	 */
	public static  String[]getAllCaches(){
		checkCacheManager();
		return cacheManager.getCacheNames();
	}
	/**
	 * 获取Cache所有的Keys
	 * @return
	 */
	public  static List getKeys(){
		checkCache();
		return cache.getKeys();
	}

	/**
	 * 检测cacheManager
	 */
	private static void checkCacheManager(){
		if(null==cacheManager){
			throw new IllegalArgumentException("调用前请先初始化CacheManager值：EHCacheUtil.initCacheManager");
		}
		
	}
	
	private static void checkCache(){
		if(null==cache){
			throw new IllegalArgumentException("调用前请先初始化Cache值：EHCacheUtil.initCache(参数)");
		}
	}
	
	
	public static void main(String[]arg){

		//初始化--必须
		EHCacheUtil.initCacheManager();
		String[]caches=EHCacheUtil.getAllCaches();
		for(String cache:caches){
			System.out.println(cache);
		}
//		//放入Test Cache中
//		EHCacheUtil.initCache("ceshi");
//		EHCacheUtil.put("F", "hello world");
//		//--1111
//		System.out.println(EHCacheUtil.get("F"));
//	
//		
//		EHCacheUtil.initCacheManager();
//		EHCacheUtil.initCache("Test");
//		EHCacheUtil.put("F", "hello world1"); 
//		
//		//----2222
//		System.out.println(EHCacheUtil.get("F"));
//		
//		//初始化--必须
//		EHCacheUtil.initCacheManager();
//		//放入Test Cache中
//		EHCacheUtil.initCache("ceshi");
//		//----3333
//		System.out.println(EHCacheUtil.get("F"));
//		
//		
//		//初始化--必须
//		EHCacheUtil.initCacheManager();
//		//放入Test Cache中
//		EHCacheUtil.initCache("cassger");
//		//----4444
//		EHCacheUtil.put("F", "fangs");
//		System.out.println(EHCacheUtil.get("F"));
		
	
	}
}