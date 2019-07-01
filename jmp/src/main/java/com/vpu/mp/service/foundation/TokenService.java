package com.vpu.mp.service.foundation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
/**
 * 
 * @author zhao
 *
 */
@Service("tokenService")
public class TokenService {
	@Autowired
	private RedisUtil redisUtil;

	@Value("${token.system.time}")
	private String tokenTime;

	/**
	 * 生成token
	 * @param map
	 * @return
	 */
	public String generateToken(Map<String, String> map) {
		String token= JwtUtil.genToken(map, tokenTime);
		System.out.println("token---------->"+token);
		return token;
	}

	/**
	 * 把token存到redis中
	 * @param key
	 * @param token
	 */
	public void save(String key, String token) {
		redisUtil.setex(key, token, Integer.parseInt(tokenTime));
	}

	public void saveHash(String key, Map<String, Object> map) {
		redisUtil.setHashTime(key, map, Integer.parseInt(tokenTime));
	}
	public void adminSaveHashMainOrChild(Boolean isChild,String id, Map<String, Object> map) {
		String key="Admin_";
		if(isChild) {
			//子账户
			key=key+"Child_"+id;
		}else {
			//主账户
			key=key+"Main_"+id;
		}
		redisUtil.setHashTime(key, map, Integer.parseInt(tokenTime));
	}
	
	public void systemSaveHashMainOrChild(Boolean isChild,String id, Map<String, Object> map) {
		String key="System_";
		if(isChild) {
			//子账户
			key=key+"Child_"+id;
		}else {
			//主账户
			key=key+"Main_"+id;
		}
		redisUtil.setHashTime(key, map, Integer.parseInt(tokenTime));
	}

	public String get(String key) {
		return redisUtil.getValue(key);
	}

	public Map<Object, Object> getHash(String token) {
		return redisUtil.getHashValue(token);
	}

	public void updateToken(String token) {
		redisUtil.updateRedis(token);
	}

	public void deleteToken(String token) {
		redisUtil.deleteToken(token);
	}

	public Map<Object, Object> getInnerHashInfo(String token) {
		return getHash(token);

	}
	
	public void setHashAdd(String key, String hashKey, String value) {
		redisUtil.setHashAdd(key, hashKey, value);
	}
}
