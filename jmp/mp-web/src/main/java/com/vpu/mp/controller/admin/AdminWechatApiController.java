package com.vpu.mp.controller.admin;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.saas.shop.mp.MpAuthShopVo;
import com.vpu.mp.service.wechat.OpenPlatform;

import me.chanjar.weixin.common.error.WxErrorException;

/**
 * @author lixinguo
 */
@RestController
public class AdminWechatApiController extends AdminBaseController {

    @Autowired
    protected OpenPlatform open;
    /**
     * 开始小程序授权
     *
     * @return
     */
    @RequestMapping(value = "/api/admin/start/auth")
    @ResponseBody
    public JsonResult startAuthorization() {
        Logger logger=logger();

        Integer shopId=this.shopId();

        logger.debug("授权店铺ID:"+shopId);

        String url = this.mainUrl("/wechat/proxy/authorization/callback?shop_id=" + shopId);

        logger.debug("授权回调url:"+url);

        try {
            String authType = "1";
            String bizAppId = null;
            MpAuthShopRecord mp = saas.shop.mp.getAuthShopByShopId(shopId);
            if (mp != null) {
                bizAppId = mp.getAppId();

                logger.debug("授权bizAppId:"+bizAppId);
            }
            url = open.getWxOpenComponentService().getPreAuthUrl(url, null, bizAppId);

            logger.debug("授权二维码页面url:"+url);

            return success(url);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return fail();
        }
    }

    /**
     * 开始公众号授权
     *
     * @return
     */
    @RequestMapping(value = "/api/admin/official/account/authorization")
    @ResponseBody
    public JsonResult startOfficialAccountAuthorization() {

        String url = this.mainUrl("/wechat/proxy/authorization/callback?sys_id="+this.adminAuth.user().getSysId());

        try {
            String authType = "1";
            String bizAppid = null;
            url = open.getWxOpenComponentService().getPreAuthUrl(url, authType, bizAppid);

            return success(url);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return fail();
        }
    }


    /**
     * 得到小程序信息
     *
     * @return
     */
    @GetMapping("/api/admin/mp/get")
    public JsonResult getMp() {
        MpAuthShopRecord record = saas.shop.mp.getAuthShopByShopId(this.shopId());
        if (record == null) {
            return fail(JsonResultCode.WX_MA_NEED_AUTHORIZATION);
        }
        return success(record.into(MpAuthShopVo.class));
    }


}
