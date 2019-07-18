package com.vpu.mp.service.foundation;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * JedisPool是线程安全的
 * 
 * @author 新国
 *
 */
public class JedisManager {

	private static JedisManager jedisManager = new JedisManager();

	/**
	 * 单例
	 */
	public static JedisManager instance() {
		return jedisManager;
	}

	/**
	 * redis缓存池
	 */
	private JedisPool pool;

	private JedisManager() {
	}

	/**
	 * 获取缓存连接池
	 * 
	 * @return
	 */
	public JedisPool getJedisPool() {
		if (pool == null) {
			synchronized (JedisManager.class) {
				if (pool == null) {
					JedisPoolConfig poolConfig = new JedisPoolConfig();
					String host = Util.getProperty("spring.redis.host");
					int port = Util.getInteger(Util.getProperty("spring.redis.port")).intValue();
					int timeout = Util.getInteger(Util.getProperty("spring.redis.timeout")).intValue();
					String password = Util.getProperty("spring.redis.password");
					int maxIdle = Util.getInteger(Util.getProperty("spring.redis.lettuce.pool.max-idle")).intValue();
					int maxWaitMillis = Util.getInteger(Util.getProperty("spring.redis.lettuce.pool.max-wait"))
							.intValue();
					poolConfig.setMaxIdle(maxIdle);
					poolConfig.setMaxWaitMillis(maxWaitMillis);
					pool = new JedisPool(poolConfig, host, port, timeout, password);
				}
			}
		}
		return pool;
	}

	/**
	 * 按时间设置缓存
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public void set(String key, String value, int seconds) {
		try (Jedis jedis = getJedisPool().getResource()){
			jedis.set(key, value);
			jedis.expire(key, seconds);
		}
	}

	/**
	 * 永久设置缓存
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		try (Jedis jedis = getJedisPool().getResource()){
			jedis.set(key, value);
		}
	}

	/**
	 * 删除key的缓存
	 * 
	 * @param key
	 */
	public void delete(String key) {
		try (Jedis jedis = getJedisPool().getResource()){
			jedis.del(key);
		}
	}

	/**
	 * 得到key的缓存
	 * 
	 * @param  key
	 * @return
	 */
	public String get(String key) {
		try (Jedis jedis = getJedisPool().getResource()){
			return jedis.get(key);
		}
	}

	/**
	 * 重设key缓存时间
	 * 
	 * @param key
	 * @param seconds
	 */
	public void expire(String key, int seconds) {
		try (Jedis jedis = getJedisPool().getResource()){
			jedis.expire(key, seconds);
		}
	}
	/**
	 * 从redis读取数据，取不到从自定义的查询里取，并存到redis里
	 * @author: 卢光耀
	 * @date: 2019-07-17 14:13
	 *
	*/
	public String getValueAndSave(String key, Integer timeOut, JedisGetProcess function){
		String value;
		try (Jedis jedis = getJedisPool().getResource()){
			value = jedis.get(key);
			if( value != null ){
				return value;
			}else {
				value = function.getByRedis();
				jedis.set(key,value);
				jedis.expire(key,timeOut);
				return value;
			}
		}
	}
}
