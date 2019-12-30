package com.vpu.mp.controller.wxapp;

import com.vpu.mp.db.shop.tables.records.GroupBuyListRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.market.message.RabbitParamConstant;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateConfig;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateData;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.pojo.wxapp.market.groupbuy.GroupBuyInfoParam;
import com.vpu.mp.service.pojo.wxapp.market.groupbuy.GroupBuyInfoVo;
import com.vpu.mp.service.pojo.wxapp.share.groupbuy.GroupBuyShareInfoParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyConstant.IS_GROUPER_N;
import static com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyConstant.IS_GROUPER_Y;

/**
 * 多人拼团接口
 * @author 孔德成
 * @date 2019/12/10 14:48
 */
@RestController
public class WxAppGroupBuyController extends WxAppBaseController {


    /**
     * 拼团详情
     * @param param
     * @return
     */
    @PostMapping("/api/wxapp/groupbuy/info")
    public JsonResult getGroupBuyInfo(@RequestBody @Valid GroupBuyInfoParam param){
        WxAppSessionUser user = wxAppAuth.user();
        GroupBuyListRecord grouperInfo = shop().groupBuyList.getGrouperByGroupId(param.getGroupId());
        if (Objects.isNull(grouperInfo)){
            logger().debug("拼团不存在或已经删除,[groupId:{}]",param.getGroupId());
            return fail(JsonResultCode.GROUP_BUY_GROUPID_DOES_NOT_EXIST);
        }
        Byte isGroup;
        if (user.getUserId().equals(grouperInfo.getUserId())){
            isGroup=IS_GROUPER_Y;
        }else {
            isGroup =IS_GROUPER_N;
        }
        GroupBuyInfoVo groupBuyInfo = shop().groupBuy.getGroupBuyInfo(user.getUserId(),isGroup,grouperInfo.getCreateTime(), param.getGroupId(),grouperInfo.getActivityId(),getLang());
        return success(groupBuyInfo);
    }

    /**
     * 获取分享图片
     * @return
     */
    @PostMapping("/api/wxapp/groupbuy/share/info")
    public JsonResult getShareImage(@RequestBody GroupBuyShareInfoParam param){
        WxAppSessionUser user = wxAppAuth.user();
        param.setUserId(user.getUserId());
        return success(shop().pictorialService.getGroupBuyShareInfo(param));
    }

    /**
     * 下载海报
     * @return
     */
    @PostMapping("/api/wxapp/groupbuy/pictorial")
    public JsonResult sharaToWx(@RequestBody @Valid GroupBuyInfoParam param){
        WxAppSessionUser user = wxAppAuth.user();
        //TODO:
        return success();
    }


    @PostMapping("/api/wxapp/groupbuy/message")
    public JsonResult testMessage(){
        String officeAppId = saas.shop.mp.findOffcialByShopId(shopId());
        WxAppSessionUser user = wxAppAuth.user();
        if (officeAppId == null) {
            logger().info("店铺" + shopId() + "没有关注公众号");
        }
        String[][] data = new String[][] { { "模板消息测试", "#173177" }, { "", "#173177" },
                { "测试", "#173177" },
                { DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL, DateUtil.getLocalDateTime()), "#173177" },
                { "", "#173177" } };
        String page = "pages/couponlist/couponlist";
        List<Integer> userIdList = new ArrayList<Integer>();
        userIdList.add(user.getUserId());
        RabbitMessageParam param = RabbitMessageParam.builder()
                .mpTemplateData(MpTemplateData.builder().config(MpTemplateConfig.COUPON_EXPIRE).data(data).build())
                .page(page).shopId(shopId()).userIdList(userIdList).type(RabbitParamConstant.Type.MP_TEMPLE_TYPE)
                .build();
        logger().info("准备发");
        saas.taskJobMainService.dispatchImmediately(param, RabbitMessageParam.class.getName(), shopId(),
                TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
        return success();
    }




}
