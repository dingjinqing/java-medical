package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareBaseParam;
import com.vpu.mp.service.pojo.wxapp.share.bargain.BargainShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.groupbuy.GroupBuyShareInfoParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李晓冰
 * @date 2020年02月03日
 */
@RestController
public class WxAppPictorialController extends WxAppBaseController  {

    /**
     * 获取普通商品分享图片信息
     * @param param 分享参数
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/goods/share/info")
    public JsonResult getGoodsShareImage(@RequestBody GoodsShareBaseParam param) {
        WxAppSessionUser user = wxAppAuth.user();
        param.setUserName(user.getUsername());
        param.setUserId(user.getUserId());
        return success(shop().pictorialIntegrationService.getNormalGoodsShareInfo(param));
    }

    /**
     * 获取普通商品下载海报信息
     * @param param 参数信息
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/goods/pictorial/info")
    public JsonResult getGoodsPictorial(@RequestBody GoodsShareBaseParam param){
        WxAppSessionUser user = wxAppAuth.user();
        param.setUserId(user.getUserId());
        return success(shop().pictorialIntegrationService.getNormalPictorialInfo(param));
    }

    /**
     * 获取拼团分享图片信息
     * @param param 分享参数
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/groupbuy/share/info")
    public JsonResult getGroupBuyShareImage(@RequestBody GroupBuyShareInfoParam param){
        WxAppSessionUser user = wxAppAuth.user();
        param.setUserId(user.getUserId());
        return success(shop().pictorialIntegrationService.getGroupBuyShareInfo(param));
    }

    /**
     * 获取拼团下载海报信息
     * @param param 分享参数
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/groupbuy/pictorial/info")
    public JsonResult getGroupBuyPictorial(@RequestBody GroupBuyShareInfoParam param){
        WxAppSessionUser user = wxAppAuth.user();
        param.setUserId(user.getUserId());
        return success(shop().pictorialIntegrationService.getGroupBuyPictorialInfo(param));
    }

    /**
     * 获取砍价分享图片信息
     * @param param 分享参数
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/bargain/share/info")
    public JsonResult getBargainShareImage(@RequestBody BargainShareInfoParam param){
        WxAppSessionUser user = wxAppAuth.user();
        param.setUserId(user.getUserId());
        return success(shop().pictorialIntegrationService.getBargainShareInfo(param));
    }

    /**
     * 获取砍价活动下载海报信息
     * @param param 分享参数
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/bargain/pictorial/info")
    public JsonResult getBargainPictorial(@RequestBody BargainShareInfoParam param){
        WxAppSessionUser user = wxAppAuth.user();
        param.setUserId(user.getUserId());
        return success(shop().pictorialIntegrationService.getBargainPictorialInfo(param));
    }

}
