package com.vpu.mp.service.wechat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.util.Util;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.open.api.impl.WxOpenInRedisConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;


/**
 * 
 * @author 新国
 *
 */
public class OpenPlatform extends WxOpenServiceImpl {
	final String AES = "aes";
	private Logger logger = LoggerFactory.getLogger(getClass());
	private WxOpenMessageRouter wxOpenMessageRouter;
	
	private static ThreadLocal<OpenPlatform> openThreadLocal = new ThreadLocal<OpenPlatform>() {
		@Override
		protected OpenPlatform initialValue() {
			OpenPlatform open = new OpenPlatform();
			open.init();
			return open;
		}
	};

	/**
	 * 线程内单例
	 */
	public static OpenPlatform instance() {
		return openThreadLocal.get();
	}

	/**
	 * 初始化
	 */
	public void init() {
		WxOpenInRedisConfigStorage inRedisConfigStorage = new WxOpenInRedisConfigStorage(JedisManager.instance().getJedisPool());
		inRedisConfigStorage.setComponentAppId(Util.getProperty("wx.open.app_id"));
		inRedisConfigStorage.setComponentAppSecret(Util.getProperty("wx.open.app_secret"));
		inRedisConfigStorage.setComponentToken(Util.getProperty("wx.open.token"));
		inRedisConfigStorage.setComponentAesKey(Util.getProperty("wx.open.aes_key"));
		setWxOpenConfigStorage(inRedisConfigStorage);
		this.wxOpenMessageRouter = new WxOpenMessageRouter(this);
		messageRouter();
	}

	/**
	 * 公众号或小程序消息路由
	 */
	protected void messageRouter() {
		wxOpenMessageRouter.rule().handler((wxMpXmlMessage, map, wxMpService, wxSessionManager) -> {
			logger.info("\n接收到 {} 公众号请求消息，内容：{}", wxMpService.getWxMpConfigStorage().getAppId(), wxMpXmlMessage);
			return null;
		}).next();
	}

	public WxOpenMessageRouter getWxOpenMessageRouter() {
		return wxOpenMessageRouter;
	}

	/**
	 * 开放平台回调，包含ticket、授权、取消授权、更新授权回调
	 */
	public String componetCallback(String requestBody, String timestamp,
			String nonce, String signature, String encType, String msgSignature) {
		 this.logger.info(
	                "\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}],"
	                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
	                signature, encType, msgSignature, timestamp, nonce, requestBody);

	        if (!StringUtils.equalsIgnoreCase(AES, encType)
	                || !this.getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
	            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
	        }

	        // aes加密的消息
	        WxOpenXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedXml(requestBody,
	                this.getWxOpenConfigStorage(), timestamp, nonce, msgSignature);
	        this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
	        try {
	            String out = this.getWxOpenComponentService().route(inMessage);
	            this.logger.debug("\n组装回复信息：{}", out);
	        } catch (WxErrorException e) {
	            this.logger.error("receive_ticket", e);
	        }

	        return "success";
	}

	

	/**
	 * 公众号或小程序事件处理
	 * 
	 * @param requestBody
	 * @param appId
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param openid
	 * @param encType
	 * @param msgSignature
	 * @return
	 */
	public String appEvent(String requestBody, String appId, String signature, String timestamp,
			String nonce, String openid, String encType, String msgSignature) {

		this.logger.info(
				"\n接收微信请求：[appId=[{}], openid=[{}], signature=[{}], encType=[{}], msgSignature=[{}],"
						+ " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
				appId, openid, signature, encType, msgSignature, timestamp, nonce, requestBody);
		if (!StringUtils.equalsIgnoreCase(AES, encType)
				|| !this.getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
			throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
		}

		// 解密
		WxMpXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedMpXml(requestBody,
				this.getWxOpenConfigStorage(), timestamp, nonce, msgSignature);
		this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
		String out = "";
		if (isGlobalTestAppId(appId)) {
			globalTest(appId, inMessage);
		} else {
			WxMpXmlOutMessage outMessage = this.getWxOpenMessageRouter().route(inMessage, appId);
			if (outMessage != null) {
				out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(outMessage, this.getWxOpenConfigStorage());
			}
		}
		return out;
	}

	/**
	 * 全网测试
	 * 
	 * @param appId
	 * @param message
	 * @return
	 */
	protected String globalTest(String appId, WxMpXmlMessage message) {
		String out = "";
		String mstTypeEvent = "event";
		String mstTypeText = "text";
		String testMsgTypeContent = "TESTCOMPONENT_MSG_TYPE_TEXT";
		String testMsgTypeContentCallback = "TESTCOMPONENT_MSG_TYPE_TEXT_callback";
		String queryAuthCodePrefix =  "QUERY_AUTH_CODE:";
		String fromApiSuffix  = "_from_api";
		String fromCallbackSuffix  = "from_callback";
		if (isGlobalTestAppId(appId)) {
			try {
				if (StringUtils.equals(message.getMsgType(),mstTypeText)) {
					if (StringUtils.equals(message.getContent(), testMsgTypeContent)) {
						out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(
								WxMpXmlOutMessage.TEXT().content(testMsgTypeContentCallback)
										.fromUser(message.getToUser())
										.toUser(message.getFromUser())
										.build(),
								this.getWxOpenConfigStorage());
					} else if (StringUtils.startsWith(message.getContent(),queryAuthCodePrefix)) {
						String msg = message.getContent().replace(queryAuthCodePrefix, "") + fromApiSuffix;
						WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(msg).toUser(message.getFromUser())
								.build();
						this.getWxOpenComponentService().getWxMpServiceByAppid(appId).getKefuService()
								.sendKefuMessage(kefuMessage);
					}
				} else if (StringUtils.equals(message.getMsgType(), mstTypeEvent)) {
					WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(message.getEvent() + fromCallbackSuffix)
							.toUser(message.getFromUser()).build();
					this.getWxOpenComponentService().getWxMpServiceByAppid(appId).getKefuService()
							.sendKefuMessage(kefuMessage);
				}
			} catch (WxErrorException e) {
				logger.error("callback", e);
			}
		}
		return out;
	}

	/**
	 * 是否用于全网发布测试appId
	 * 
	 * @param appId
	 * @return
	 */
	protected boolean isGlobalTestAppId(String appId) {
		return (StringUtils.equalsAnyIgnoreCase(appId, "wxd101a85aa106f53e", "wx570bc396a51b8ff8"));
	}
}
