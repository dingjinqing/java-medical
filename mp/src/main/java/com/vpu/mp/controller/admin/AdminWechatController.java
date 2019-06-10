package com.vpu.mp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.service.wechat.OpenPlatform;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;

@Controller
public class AdminWechatController extends AdminBaseController {

	protected OpenPlatform open = OpenPlatform.instance();

	@RequestMapping(value = "/wechat/no/authorization")
	public ModelAndView noAuthorization() {
		ModelMap model = new ModelMap();
		model.addAttribute("title", "小程序管理");
		return view("admin/wx_auth", model);
	}

	/**
	 * 开始小程序授权
	 * 
	 * @return
	 */
	@RequestMapping(value = " /wechat/start/authorization")
	public ModelAndView startAuthorization() {
		String url = this.mainUrl("/wechat/authorization/callback?shop_id=" + this.shopId());
		try {
			String authType = "2";
			String bizAppid = null;
			url = open.getWxOpenComponentService().getPreAuthUrl(url,authType,bizAppid);
		} catch (WxErrorException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return redirect(url);
	}

	/**
	 * 开始公众号授权
	 * 
	 * @return
	 */
	@RequestMapping(value = " /wechat/official/account/authorization")
	public ModelAndView startOfficialAccountAuthorization() {
		String url = this.mainUrl("/wechat/authorization/callback?sys_id=" + this.adminAuth.sysId());
		try {
			String authType = "1";
			String bizAppid = null;
			url = open.getWxOpenComponentService().getPreAuthUrl(url,authType,bizAppid);
		} catch (WxErrorException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return redirect(url);
	}

	/**
	 * 公众号或小程序授权回调
	 * 
	 * @return
	 */
	@RequestMapping(value = " /wechat/authorization/callback")
	@ResponseBody
	public WxOpenQueryAuthResult authorizationCallback(@RequestParam("auth_code") String authorizationCode,
			@RequestParam(name = "sys_id", required = false) Integer sysId,
			@RequestParam(name = "shop_id", required = false) Integer shopId) {
		try {
			WxOpenQueryAuthResult queryAuthResult = open.getWxOpenComponentService().getQueryAuth(authorizationCode);
			String appId = queryAuthResult.getAuthorizationInfo().getAuthorizerAppid();
			WxOpenAuthorizerInfoResult  authorizerInfo = open.getWxOpenComponentService().getAuthorizerInfo(appId);
			if(sysId != null) {
				// 服务号授权 TODO:
			}
			if(shopId != null) {
				// 小程序授权
				
			}
			
			
			logger().info("getQueryAuth", queryAuthResult);
			return queryAuthResult;
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
	@RequestMapping("/wechat/component/event/callback")
	public Object componentEventCb(@RequestBody(required = false) String requestBody,
			@RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("signature") String signature,
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
	@RequestMapping("/wechat/app/event/{appId}/callback")
	public Object appEventCallback(@RequestBody(required = false) String requestBody,
			@PathVariable("appId") String appId,
			@RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce,
			@RequestParam("openid") String openid,
			@RequestParam("encrypt_type") String encType,
			@RequestParam("msg_signature") String msgSignature) {
		return open.appEvent(requestBody, appId, signature, timestamp, nonce, openid, encType, msgSignature);
	}

}
