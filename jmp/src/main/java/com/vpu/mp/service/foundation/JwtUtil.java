package com.vpu.mp.service.foundation;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * 
 * @author zhao
 *
 */
@Component
public class JwtUtil {

	private static String SECRET;
	/**
	 * 发行人
	 */
	private static String ISSUER;

	@Value("${jwt.ISSUER}")
	public  void setISSUER(String issUer) {
		ISSUER = issUer;
	}
	@Value("${jwt.SECRET}")
	public  void setSECRET(String sEcret) {
		SECRET = sEcret;
	}

	/*
	 * @Value("${jwt.SECRET}") private static String SECRET;
	 * 
	 * @Value("${jwt.ISSUER}") private static String ISSUER;
	 */
	/**
	 * 生成token
	 *
	 * @param claims
	 * @return
	 */
	public static String genToken(Map<String, String> claims, String outTime) {

		try {
			// 使用HMAC256进行加密
			Algorithm algorithm = Algorithm.HMAC256(SECRET);

			// 创建jwt
			JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER).
					withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(outTime) * 1000));
			// 传入参数
			claims.forEach((key, value) -> {
				builder.withClaim(key, value);
			});
			// 签名加密
			return builder.sign(algorithm);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解密jwt
	 *
	 * @param token
	 * @return
	 * @throws RuntimeException
	 */
	public static Map<String, String> verifyToken(String token) {
		Algorithm algorithm = null;
		try {
			// 使用HMAC256进行加密
			algorithm = Algorithm.HMAC256(SECRET);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		}

		// 解密
		JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
		DecodedJWT jwt = verifier.verify(token);
		Map<String, Claim> map = jwt.getClaims();
		Map<String, String> resultMap = new HashMap<>(0);
		map.forEach((k, v) -> resultMap.put(k, v.asString()));
		return resultMap;
	}
}
