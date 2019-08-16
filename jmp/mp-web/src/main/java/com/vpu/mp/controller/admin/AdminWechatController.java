package com.vpu.mp.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.service.pojo.shop.auth.MenuAuthority;
import com.vpu.mp.service.wechat.OpenPlatform;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminWechatController extends AdminBaseController {

	@Autowired
	protected MenuAuthority authority;
	
	@Autowired
	protected OpenPlatform open;


	/**
	 * 公众号或小程序授权回调
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wechat/notify/authorization/callback")
	public String authorizationCallback(@RequestParam("auth_code") String authorizationCode,
			@RequestParam(name = "sys_id", required = false) Integer sysId,
			@RequestParam(name = "shop_id", required = false) Integer shopId) {
		try {
			WxOpenQueryAuthResult queryAuthResult = open.getWxOpenComponentService().getQueryAuth(authorizationCode);
			String appId = queryAuthResult.getAuthorizationInfo().getAuthorizerAppid();
			if (sysId != null) {
				// 服务号授权 TODO:
			}
			if (shopId != null) {
				// 小程序授权
				MpAuthShopRecord mp = saas.shop.mp.getAuthShopByShopId(shopId);
				logger().debug("查询出的值1："+mp);
				if (mp != null && !mp.getAppId().equals(appId)) {
					//小程序上次授权与本次授权AppId不一致，请联系客服！
					logger().debug("appId"+appId+"小程序上次授权与本次授权AppId不一致，请联系客服！");
                    return ("小程序上次授权与本次授权AppId不一致，请联系客服！");
				}
				mp = saas.shop.mp.getAuthShopByAppId(appId);
				if (mp != null && mp.getShopId().intValue() != shopId) {
					//小程序已授权绑定其他账号，请联系客服！
					logger().debug("appId"+appId+"小程序已授权绑定其他账号，请联系客服！");
                    return ("小程序已授权绑定其他账号，请联系客服！");
				}
				logger().debug("查询出的值2："+mp);
				MpAuthShopRecord addMpAuthAccountInfo = saas.shop.mp.addMpAuthAccountInfo(appId, shopId);
				saas.shop.mp.bindAllSamePrincipalOpenAppId(addMpAuthAccountInfo);
				
			}

			logger().info("getQueryAuth", queryAuthResult);
            return "redirect:"+this.mainUrl("/admin/home/main/base_manger/authok");
		} catch (WxErrorException e) {
			logger().error("gotoPreAuthUrl", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 开放平台tick、公众号或小程序授权、更新授权、解除授权事件
	 * 
	 * @param requestBody
	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @param encType
	 * @param msgSignature
	 * @return
	 */
	@RequestMapping("/wechat/notify/component/event/callback")
	@ResponseBody
	public Object componentEventCb(@RequestBody(required = false) String requestBody,
			@RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce,
			@RequestParam("signature") String signature,
			@RequestParam(name = "encrypt_type", required = false) String encType,
			@RequestParam(name = "msg_signature", required = false) String msgSignature) {
		return open.componetCallback(requestBody, timestamp, nonce, signature, encType, msgSignature);
	}

	/**
	 * 公众号或小程序消息处理
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
	@RequestMapping("/wechat/notify/app/event/{appId}/callback")
	@ResponseBody
	public Object appEventCallback(@RequestBody(required = false) String requestBody,
			@PathVariable("appId") String appId, @RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce,
			@RequestParam("openid") String openid, @RequestParam("encrypt_type") String encType,
			@RequestParam("msg_signature") String msgSignature) {
		return open.appEvent(requestBody, appId, signature, timestamp, nonce, openid, encType, msgSignature);
	}

}
