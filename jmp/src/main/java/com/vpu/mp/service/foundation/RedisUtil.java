package com.vpu.mp.service.foundation;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
/**
 * 
 * @author zhao
 *
 */
@Component
public class RedisUtil {
	@Value("${token.system.time}")
	private String tokenTime;

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	public void set(String key, String value) {
		redisTemplate.opsForValue().set(key, value);

	}

	public void setex(String key, String value, int seconds) {
		redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);

	}

	public void setHashTime(String key, Map<String, Object> map, int seconds) {
		redisTemplate.opsForHash().putAll(key, map);
		redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
	}

	public void setHashTimeAuto(String key, Map<String, Object> map) {
		redisTemplate.opsForHash().putAll(key, map);
		redisTemplate.expire(key, Long.parseLong(tokenTime), TimeUnit.SECONDS);
	}

	public void setHashAdd(String key, String hashKey, String value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
		redisTemplate.expire(key, Long.parseLong(tokenTime), TimeUnit.SECONDS);
	}

	public Map<Object, Object> getHashValue(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 更新key的生存时间
	 * @param key
	 */
	public void updateRedis(String key) {
		redisTemplate.expire(key, Long.parseLong(tokenTime), TimeUnit.SECONDS);

	}

	public void deleteToken(String key) {
		redisTemplate.delete(key);
	}

	public String getValue(String key) {
		Object object = redisTemplate.opsForValue().get(key);
		if (object != null) {
			return object.toString();
		}
		return null;
	}

}
