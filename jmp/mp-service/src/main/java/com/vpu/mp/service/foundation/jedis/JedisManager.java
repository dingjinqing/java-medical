package com.vpu.mp.service.foundation.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.List;

/**
 * 
 * JedisPool是线程安全的
 * 
 * @author 新国
 *
 */
@Service
public class JedisManager {

    private static final Long RELEASE_SUCCESS = 1L;

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

	@Autowired
	private JedisPool pool;


	/**
	 * 获取缓存连接池
	 * 
	 * @return
	 */
	public JedisPool getJedisPool() {
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
     *
     * @param key
     * @param value
     */
    public void lpush(String key, String[] value) {
        try (Jedis jedis = getJedisPool().getResource()){
            jedis.lpush(key, value);
        }
    }
    /**
     *
     * @param key
     */
    public Long getListSize(String key) {
        try (Jedis jedis = getJedisPool().getResource()){
           return jedis.llen(key);
        }
    }
    /**
     * 获取当前key下的所有list
     * @param key
     */
    public List<String> getList(String key) {
        try (Jedis jedis = getJedisPool().getResource()){
            return jedis.lrange(key,0,-1);
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

    /**
     * redis加锁
     * @param lockKey
     * @param requestId
     * @param timeOut
     * @return
     */
	public boolean  addLock(String lockKey,String requestId,Integer timeOut){
	    try( Jedis jedis = getJedisPool().getResource() ){
            String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, timeOut);
            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        }

    }


    /**
     * redis释放锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public  boolean releaseLock( String lockKey, String requestId) {
        try( Jedis jedis = getJedisPool().getResource() ){
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        }
    }
}
