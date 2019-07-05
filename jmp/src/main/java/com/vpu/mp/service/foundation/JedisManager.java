package com.vpu.mp.service.foundation;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * @author 新国
 *
 */
public class JedisManager {

	private static ThreadLocal<JedisManager> jedisThreadLocal = new ThreadLocal<JedisManager>() {
		@Override
		protected JedisManager initialValue() {
			return new JedisManager();
		}
	};

	/**
	 * 线程内单例
	 */
	public static JedisManager instance() {
		return jedisThreadLocal.get();
	}

	/**
	 * redis缓存池
	 */
	private JedisPool pool = null;

	/**
	 * 获取缓存连接池
	 * 
	 * @return
	 */
	public synchronized JedisPool getJedisPool() {
		if (pool == null) {
			JedisPoolConfig poolConfig = new JedisPoolConfig();
			String host = Util.getProperty("spring.redis.host");
			int port = Util.getInteger(Util.getProperty("spring.redis.port")).intValue();
			int timeout = Util.getInteger(Util.getProperty("spring.redis.timeout")).intValue();
			String password = Util.getProperty("spring.redis.password");
			int maxIdle = Util.getInteger(Util.getProperty("spring.redis.lettuce.pool.max-idle")).intValue();
			int maxWaitMillis = Util.getInteger(Util.getProperty("spring.redis.lettuce.pool.max-wait")).intValue();
			poolConfig.setMaxIdle(maxIdle);
			poolConfig.setMaxWaitMillis(maxWaitMillis);
			pool = new JedisPool(poolConfig, host, port, timeout, password);
		}
		return pool;
	}

	/**
	 * 获取redis缓存资源
	 * 
	 * @return
	 */
	public Jedis getJedis() {
		JedisPool pool = getJedisPool();
		synchronized (JedisManager.class) {
			return pool.getResource();
		}
	}

	public void set(String key, String value, int seconds) {
		Jedis jedis = getJedis();
		jedis.set(key, value);
		jedis.expire(key, seconds);
	}
	
	public void set(String key, String value) {
		Jedis jedis = getJedis();
		jedis.set(key, value);
	}

	public void delete(String key) {
		getJedis().del(key);
	}

	public String get(String key) {
		return getJedis().get(key);
	}

	public void close() {
		JedisPool pool = getJedisPool();
		pool.close();
	}
	
	public void expire(String key, int seconds) {
		Jedis jedis = getJedis();
		jedis.expire(key, seconds);
	}

}
