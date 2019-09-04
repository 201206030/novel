package xyz.zinglizingli.search.cache;

public interface CommonCacheUtil {

	/**
	 * 根据key获取缓存的String类型数据
	 */
	String get(String key);

	/**
	 * 设置String类型的缓存
	 */
	void set(String key, String value);

	/**
	 * 设置一个有过期时间的String类型的缓存,单位秒
	 */
	void set(String key, String value, long timeout);
	
	/**
	 * 根据key获取缓存的Object类型数据
	 */
	Object getObject(String key);
	
	/**
	 * 设置Object类型的缓存
	 */
	void setObject(String key, Object value);
	
	/**
	 * 设置一个有过期时间的Object类型的缓存,单位秒
	 */
    void setObject(String key, Object value, long timeout);

	/**
	 * 根据key删除缓存的数据
	 */
	void del(String key);

	
	/**
	 * 判断是否存在一个key
	 * */
	boolean contains(String key);
	
	/**
	 * 设置key过期时间
	 * */
	void expire(String key, long timeout);

	/**
	 * 刷新缓存
	 * */
	void refresh(String key);

}
