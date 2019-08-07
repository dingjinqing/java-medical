package com.vpu.mp.service.wechat;

import com.google.gson.JsonObject;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.wechat.api.impl.WxOpenMaServiceExtraImpl;
import com.vpu.mp.service.wechat.api.impl.WxOpenMpServiceExtraImpl;
import lombok.Getter;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.open.api.impl.WxOpenInRedisConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import me.chanjar.weixin.open.bean.WxOpenCreateResult;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
 * 
 * @author 新国
 *
 */
@Service
public class OpenPlatform extends WxOpenServiceImpl {

	@Value(value = "${wx.open.app_id}")
	protected String appId;

	@Value(value = "${wx.open.app_secret}")
	protected String appSecret;

	@Value(value = "${wx.open.token}")
	protected String token;

	@Value(value = "${wx.open.aes_key}")
	protected String aesKey;

	static final String AES = "aes";

	private Logger logger = LoggerFactory.getLogger(getClass());

	private WxOpenMessageRouter wxOpenMessageRouter;

	@Autowired
	protected JedisManager jedis;

	@Getter
	protected WxOpenMaServiceExtraImpl maExtService = new WxOpenMaServiceExtraImpl(this);

	@Getter
	protected WxOpenMpServiceExtraImpl mpExtService = new WxOpenMpServiceExtraImpl(this);

	String CREATE_OPEN_GET_URL="https://api.weixin.qq.com/cgi-bin/open/get";
	String GET_AUTHORIZER_LIST_URL="https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_list";
	String BIND_OPEN_PLATFORM="https://api.weixin.qq.com/cgi-bin/open/bind";
    String UNBIND_OPEN_PLATFORM="https://api.weixin.qq.com/cgi-bin/open/unbind";
    String GET_MP_QR_CODE="https://api.weixin.qq.com/wxa/getwxacode";
	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		WxOpenInRedisConfigStorage inRedisConfigStorage = new WxOpenInRedisConfigStorage(jedis.getJedisPool());
		inRedisConfigStorage.setComponentAppId(appId);
		inRedisConfigStorage.setComponentAppSecret(appSecret);
		inRedisConfigStorage.setComponentToken(token);
		inRedisConfigStorage.setComponentAesKey(aesKey);
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
		String queryAuthCodePrefix = "QUERY_AUTH_CODE:";
		String fromApiSuffix = "_from_api";
		String fromCallbackSuffix = "from_callback";
		if (isGlobalTestAppId(appId)) {
			try {
				if (StringUtils.equals(message.getMsgType(), mstTypeText)) {
					if (StringUtils.equals(message.getContent(), testMsgTypeContent)) {
						out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(
								WxMpXmlOutMessage.TEXT().content(testMsgTypeContentCallback)
										.fromUser(message.getToUser())
										.toUser(message.getFromUser())
										.build(),
								this.getWxOpenConfigStorage());
					} else if (StringUtils.startsWith(message.getContent(), queryAuthCodePrefix)) {
						String msg = message.getContent().replace(queryAuthCodePrefix, "") + fromApiSuffix;
						WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(msg).toUser(message.getFromUser())
								.build();
						this.getWxOpenComponentService().getWxMpServiceByAppid(appId).getKefuService()
								.sendKefuMessage(kefuMessage);
					}
				} else if (StringUtils.equals(message.getMsgType(), mstTypeEvent)) {
					WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT()
							.content(message.getEvent() + fromCallbackSuffix)
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


	public WxOpenCreateResult queryOpenAccount(String appId) throws WxErrorException {
		JsonObject param = new JsonObject();
		param.addProperty("appid", appId);
		String uri = CREATE_OPEN_GET_URL;
		String componentAccessToken = this.getWxOpenComponentService().getComponentAccessToken(false);
		System.out.println(componentAccessToken);
		//componentAccessToken="24_d9IYiG0o6UFeKAL1RhfKuAzF-4bE2v0aDbb-l_BKlddXP9xWdduYG0_MGB0JsrbJgn6nXS23KCUyXSMvO6JbV2v2rwfYN-lH5qobhpyBx03jOlE93I93WFoKTWQNDYdAEAEUP";
		String uriWithComponentAccessToken = uri + (uri.contains("?") ? "&" : "?") + "access_token" + "="
				+ componentAccessToken;
		String json = post(uriWithComponentAccessToken, param.toString());

		return WxOpenCreateResult.fromJson(json);
	}

	//第三方平台可以使用接口拉取当前所有已授权的帐号基本信息。
	public WxOpenCreateResult getAuthorizerList(String componentAppid,Integer offset,Integer count) throws WxErrorException {
		JsonObject param = new JsonObject();
		param.addProperty("component_appid", componentAppid);
		param.addProperty("offset", offset);
		param.addProperty("count", count);
		String uri = GET_AUTHORIZER_LIST_URL;
		String componentAccessToken = this.getWxOpenComponentService().getComponentAccessToken(false);
		String uriWithComponentAccessToken = uri + (uri.contains("?") ? "&" : "?") + "component_access_token" + "="+ componentAccessToken;
		String json = post(uriWithComponentAccessToken, param.toString());
		return WxOpenCreateResult.fromJson(json);

	}

	//获取公众号/小程序所绑定的开放平台帐号
	public WxOpenCreateResult bindOpenAppId(String appId,String openAppId) throws WxErrorException {
		JsonObject param = new JsonObject();
		param.addProperty("appid", appId);
		param.addProperty("open_appid", openAppId);
		String uri = BIND_OPEN_PLATFORM;
		String componentAccessToken = this.getWxOpenComponentService().getComponentAccessToken(false);
		String uriWithComponentAccessToken = uri + (uri.contains("?") ? "&" : "?") + "access_token" + "="
				+ componentAccessToken;
		String json = post(uriWithComponentAccessToken, param.toString());
		return WxOpenCreateResult.fromJson(json);
	}

	//将公众号/小程序从开放平台帐号下解绑
	public WxOpenCreateResult unBindOpenAppId(String appId,String openAppId) throws WxErrorException {
		JsonObject param = new JsonObject();
		param.addProperty("appid", appId);
		param.addProperty("open_appid", openAppId);
		String uri = UNBIND_OPEN_PLATFORM;
		String componentAccessToken = this.getWxOpenComponentService().getComponentAccessToken(false);
		String uriWithComponentAccessToken = uri + (uri.contains("?") ? "&" : "?") + "access_token" + "="
				+ componentAccessToken;
		String json = post(uriWithComponentAccessToken, param.toString());
		return WxOpenCreateResult.fromJson(json);
	}

    /**
     * 获取小程序码
     */
    public String getMpQRCodeBuffer(String path) throws WxErrorException {
        JsonObject param = new JsonObject();
        param.addProperty("path", path);
        String uri = GET_MP_QR_CODE + path;
        String componentAccessToken = this.getWxOpenComponentService().getComponentAccessToken(false);
        String uriWithComponentAccessToken = uri + (uri.contains("?") ? "&" : "?") + "access_token" + "="
            + componentAccessToken;
        return post(uriWithComponentAccessToken, param.toString());
    }
}
