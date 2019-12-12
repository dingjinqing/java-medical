package com.vpu.mp.controller.wxapp;

import com.vpu.mp.db.shop.tables.records.GroupBuyListRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.pojo.wxapp.market.groupbuy.GroupBuyInfoParam;
import com.vpu.mp.service.pojo.wxapp.market.groupbuy.GroupBuyInfoVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 多人拼团接口
 * @author 孔德成
 * @date 2019/12/10 14:48
 */
@RestController
public class WxAppGroupBuyController extends WxAppBaseController {




    @PostMapping("/api/wxapp/groupbuy/info")
    public JsonResult getGroupBuyInfo(@RequestBody @Valid GroupBuyInfoParam param){
        WxAppSessionUser user = wxAppAuth.user();
        GroupBuyListRecord groupBuyList = shop().groupBuyList.getGroupBuyListByGroupId(param.getGroupId());
        if (Objects.isNull(groupBuyList)){
//            logger().debug("拼团不存在或已经删除,[groupId:{}]",param.getGroupId());
            return fail(JsonResultCode.GROUP_BUY_GROUPID_DOES_NOT_EXIST);
        }
        GroupBuyInfoVo groupBuyInfo = shop().groupBuy.getGroupBuyInfo(user.getUserId(),groupBuyList.getCreateTime(), param.getGroupId(),groupBuyList.getActivityId(),getLang());
        return success(groupBuyInfo);
    }
}