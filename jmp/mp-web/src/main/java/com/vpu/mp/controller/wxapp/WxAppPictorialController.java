package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.pojo.wxapp.share.GoodsPictorialInfo;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareBaseParam;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareInfo;
import com.vpu.mp.service.pojo.wxapp.share.PictorialConstant;
import com.vpu.mp.service.pojo.wxapp.share.bargain.BargainShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.firstspecial.FirstSpecialShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.group.GroupDrawShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.groupbuy.GroupBuyShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.presale.PreSaleShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.reduce.ReducePriceShareInfoParam;
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
     * 小程序-商品详情页-多图下载
     * @param param 只需要{@link GoodsShareBaseParam#targetId}接收商品id，其余参数不需要
     * @return base64 数组
     */
    @PostMapping("/api/wxapp/goods/download/images")
    public JsonResult getGoodsImagesBase64(@RequestBody GoodsShareBaseParam param){
        if (param.getTargetId() == null) {
            return fail(JsonResultCode.GOODS_ID_IS_NULL);
        }
        return success(shop().pictorialIntegrationService.getGoodsImagesBase64(param.getTargetId()));
    }

    /**
     * 获取普通商品分享图片信息
     * @param param 分享参数
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/goods/share/info")
    public JsonResult getGoodsShareImage(@RequestBody GoodsShareBaseParam param) {
        return getActivityShareInfo(param);
    }

    /**
     * 获取拼团分享图片信息
     * @param param 分享参数
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/groupbuy/share/info")
    public JsonResult getGroupBuyShareImage(@RequestBody GroupBuyShareInfoParam param){
        return getActivityShareInfo(param);
    }

    /**
     * 获取砍价分享图片信息
     * @param param 分享参数
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/bargain/share/info")
    public JsonResult getBargainShareImage(@RequestBody BargainShareInfoParam param){
        return getActivityShareInfo(param);
    }

    /**
     * 获取拼团抽奖分享图片信息
     * @param param 参数信息
     * @return  JsonResult
     */
    @PostMapping("/api/wxapp/groupdraw/share/info")
    public JsonResult getGroupDrawShareInfo(@RequestBody GroupDrawShareInfoParam param){
        return getActivityShareInfo(param);
    }

    /**
     * 获取预售活动分享图片
     * @param param 参数信息
     * @return  JsonResult
     */
    @PostMapping("/api/wxapp/presale/share/info")
    public JsonResult getPreSaleShareInfo(@RequestBody PreSaleShareInfoParam param){
        return getActivityShareInfo(param);
    }

    /**
     * 获取限时降价活动分享图片
     * @param param 参数信息
     * @return  JsonResult
     */
    @PostMapping("/api/wxapp/reduceprice/share/info")
    public JsonResult getReducePriceShareInfo(@RequestBody ReducePriceShareInfoParam param){
        return getActivityShareInfo(param);
    }

    /**
     * 获取首单特惠活动分享图片
     * @param param 参数信息
     * @return  JsonResult
     */
    @PostMapping("/api/wxapp/firstspecial/share/info")
    public JsonResult getFirstSpecialShareInfo(@RequestBody FirstSpecialShareInfoParam param){
        return getActivityShareInfo(param);
    }

    private JsonResult getActivityShareInfo(GoodsShareBaseParam param) {
        GoodsShareInfo activityShareInfo = shop().pictorialIntegrationService.getActivityShareInfo(param);
        Byte shareCode = activityShareInfo.getShareCode();
        if (PictorialConstant.ACTIVITY_DELETED.equals(shareCode)) {
            return fail(JsonResultCode.WX_SHARE_ACTIVITY_DELETED);
        } else if (PictorialConstant.GOODS_DELETED.equals(shareCode)) {
            return fail(JsonResultCode.WX_SHARE_GOODS_DELETED);
        } else if (PictorialConstant.GOODS_PIC_ERROR.equals(shareCode)) {
            return fail(JsonResultCode.WX_SHARE_PIC_ERROR);
        } else if (PictorialConstant.QRCODE_ERROR.equals(shareCode)) {
            return fail(JsonResultCode.WX_SHARE_QRCDOE_ERROR);
        } else {
            return success(activityShareInfo);
        }
    }


    /**
     * 获取普通商品下载海报信息
     * @param param 参数信息
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/goods/pictorial/info")
    public JsonResult getGoodsPictorial(@RequestBody GoodsShareBaseParam param){
       return getActivityPictorialInfo(param);
    }

    /**
     * 获取拼团下载海报信息
     * @param param 分享参数
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/groupbuy/pictorial/info")
    public JsonResult getGroupBuyPictorial(@RequestBody GroupBuyShareInfoParam param){
        return getActivityPictorialInfo(param);
    }

    /**
     * 获取砍价活动下载海报信息
     * @param param 分享参数
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/bargain/pictorial/info")
    public JsonResult getBargainPictorial(@RequestBody BargainShareInfoParam param){
        return getActivityPictorialInfo(param);
    }

    /**
     * 获取拼团抽奖海报信息
     * @param param 海报参数信息
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/groupdraw/pictorial/info")
    public JsonResult getGroupDrawPictorial(@RequestBody GroupDrawShareInfoParam param) {
        return getActivityPictorialInfo(param);
    }

    /**
     * 获取预售海报信息
     * @param param 海报参数信息
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/presale/pictorial/info")
    public JsonResult getPreSalePictorial(@RequestBody PreSaleShareInfoParam param) {
        return getActivityPictorialInfo(param);
    }

    /**
     * 获取限时降价海报信息
     * @param param 海报参数信息
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/reduceprice/pictorial/info")
    public JsonResult getReducePricePictorial(@RequestBody ReducePriceShareInfoParam param) {
        return getActivityPictorialInfo(param);
    }

    /**
     * 获取首单特惠海报信息
     * @param param 海报参数信息
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/firstspecial/pictorial/info")
    public JsonResult getFirstSpecialPictorial(@RequestBody FirstSpecialShareInfoParam param) {
        return getActivityPictorialInfo(param);
    }

    /**
     * 获取活动海报信息
     * @param param GoodsShareBaseParam
     * @return JsonResult content 为base64或 null
     */
    private JsonResult getActivityPictorialInfo(GoodsShareBaseParam param) {
        WxAppSessionUser user = wxAppAuth.user();
        param.setUserId(user.getUserId());
        GoodsPictorialInfo activityPictorialInfo = shop().pictorialIntegrationService.getActivityPictorialInfo(param);
        Byte code = activityPictorialInfo.getPictorialCode();
        if (PictorialConstant.ACTIVITY_DELETED.equals(code)) {
            return fail(JsonResultCode.WX_SHARE_ACTIVITY_DELETED);
        } else if (PictorialConstant.GOODS_DELETED.equals(code)) {
            return fail(JsonResultCode.WX_SHARE_GOODS_DELETED);
        } else if (PictorialConstant.GOODS_PIC_ERROR.equals(code)) {
            return fail(JsonResultCode.WX_SHARE_PIC_ERROR);
        } else if (PictorialConstant.QRCODE_ERROR.equals(code)) {
            return fail(JsonResultCode.WX_SHARE_QRCDOE_ERROR);
        } else if (PictorialConstant.USER_PIC_ERROR.equals(code)) {
            return fail(JsonResultCode.WX_SHARE_USER_PIC_ERROR);
        } else {
            return success(activityPictorialInfo.getBase64());
        }
    }

}
