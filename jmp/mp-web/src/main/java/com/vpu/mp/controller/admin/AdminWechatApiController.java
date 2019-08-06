package com.vpu.mp.controller.admin;

import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.saas.shop.mp.MpAuthShopVo;
import com.vpu.mp.service.pojo.shop.auth.MenuAuthority;
import com.vpu.mp.service.wechat.OpenPlatform;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixinguo
 */
@RestController
public class AdminWechatApiController extends AdminBaseController {


    @Autowired
    protected MenuAuthority authority;

    @Autowired
    protected OpenPlatform open;

    static final String WX_CALLBACK_URL="/wechat/proxy/authorization/callback";

    /**
     * 开始小程序授权
     *
     * @return 授权页url
     */
    @GetMapping(value = "/wechat/proxy/start/auth")
    public String startAuthorization(@RequestParam(name = "shopId", required = true) Integer shopId) {
        String url = this.mainUrl(WX_CALLBACK_URL+"?shop_id=" + shopId);
        try {
            String authType = "2";
            String bizAppId = null;
            MpAuthShopRecord mp = saas.shop.mp.getAuthShopByShopId(shopId);
            if (mp != null) {
                bizAppId = mp.getAppId();
            }
            url = open.getWxOpenComponentService().getPreAuthUrl(url, authType, bizAppId);
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return url;
    }

    /**
     * 开始公众号授权
     *
     * @return 授权页url
     */
    @RequestMapping(value = "/wechat/proxy/official/account/authorization")
    public String startOfficialAccountAuthorization() {
        String url = this.mainUrl(WX_CALLBACK_URL+"?sys_id=" + this.adminAuth.user().getSysId());
        try {
            String authType = "1";
            String bizAppid = null;
            url = open.getWxOpenComponentService().getPreAuthUrl(url, authType, bizAppid);
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return url;
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
            return fail(JsonResultCode.CODE_PARAM_ERROR);
        }
        return success(record.into(MpAuthShopVo.class));
    }


}
