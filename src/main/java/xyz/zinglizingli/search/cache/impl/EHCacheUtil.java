package xyz.zinglizingli.search.cache.impl;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.zinglizingli.search.cache.CommonCacheUtil;

@Service
public class EHCacheUtil implements CommonCacheUtil {
	
	@Autowired
	private  CacheManager cacheManager ;
 
	private static final String CACHE_NAME = "utilCache";
 
	
	/**
	 * 获得一个Cache，没有则创建一个。
	 * @param cacheName
	 * @return
	 */
	private Cache getCache(){
	
		/*Cache cache = cacheManager.getCache(cacheName);
		if (cache == null){
			cacheManager.addCache(cacheName);
			cache = cacheManager.getCache(cacheName);
			CacheConfiguration config = cache.getCacheConfiguration();
			config.setEternal(false);
			config.internalSetTimeToIdle(0);
			config.internalSetTimeToIdle(0);
		}*/
		Cache cache = cacheManager.getCache("util_cache");
		return cache;
	}
	
 
	public  CacheManager getCacheManager() {
		return cacheManager;
	}
	
	



	@Override
	public String get(String key) {
		Element element = getCache().get(key);
		return element==null?null:(String)element.getObjectValue();
	}

	@Override
	public void set(String key, String value) {
		Element element = new Element(key, value);
		Cache cache = getCache();
		cache.getCacheConfiguration().setEternal(true);//不过期
		cache.put(element);

	}

	@Override
	public void set(String key, String value, long timeout) {
		Element element = new Element(key, value);
		element.setTimeToLive((int) timeout);
		Cache cache = getCache();
		cache.put(element);

	}

	@Override
	public void del(String key) {
		getCache().remove(key);


	}

	@Override
	public boolean contains(String key) {
		return getCache().isKeyInCache(key);
	}

	@Override
	public void expire(String key, long timeout) {
		Element element = getCache().get(key);
		if (element != null) {
			Object value = element.getValue();
			element = new Element(key, value);
			element.setTimeToLive((int)timeout);
			Cache cache = getCache();
			cache.put(element);
		}
	}


	/**
	 * 根据key获取缓存的Object类型数据
	 */
	@Override
	public Object getObject(String key) {
		Element element = getCache().get(key);
		return element==null?null:element.getObjectValue();
	}


	/**
	 * 设置Object类型的缓存
	 * @param <T>
	 */
	@Override
	public void setObject(String key, Object value) {
		Element element = new Element(key, value);
		Cache cache = getCache();
		cache.getCacheConfiguration().setEternal(true);//不过期
		cache.put(element);
		
	}


	/**
	 * 设置一个有过期时间的Object类型的缓存,单位秒
	 */
	@Override
	public void setObject(String key, Object value, long timeout) {
		Element element = new Element(key, value);
		element.setTimeToLive((int) timeout);
		Cache cache = getCache();
		cache.put(element);
		
	}


	@Override
	public void refresh(String key) {
		Element element = getCache().get(key);
		if (element != null) {
			Object value = element.getValue();
			int timeToLive = element.getTimeToLive();
			element = new Element(key, value);
			element.setTimeToLive(timeToLive);
			Cache cache = getCache();
			cache.put(element);
		}
		
	}



}
