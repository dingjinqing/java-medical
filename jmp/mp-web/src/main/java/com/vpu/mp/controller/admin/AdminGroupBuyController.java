package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import ch.qos.logback.core.util.InterruptUtil;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.util.IncrSequenceUtil;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.shop.tables.records.GroupBuyDefineRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketSourceUserListParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyAnalysisParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyDetailParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyEditParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyIdParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyListParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyDetailListVo;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyDetailVo;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyParam;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;

/**
 * 团购、多人平团
 *
 * @author 孔德成
 * @date 2019/7/18 14:27
 */
@RestController
@RequestMapping("/api")
public class AdminGroupBuyController extends AdminBaseController {


    /**
     * 查询团购列表
     *
     * @param param GroupBuyListParam
     * @return JsonResult
     */
    @PostMapping("/admin/market/groupbuy/list")
    public JsonResult getListGroupBuy(@RequestBody @Valid GroupBuyListParam param) {
        return success(shop().groupBuy.getListGroupBuy(param));
    }


    /**
     * 增加拼团活动
     *
     * @param param GroupBuyParam
     * @return JsonResult
     */
    @PostMapping("/admin/market/groupbuy/add")
    public JsonResult addGroupBuy(@RequestBody @Valid GroupBuyParam param) {
        //校验活动商品是否叠加 (并发不安全)
        Boolean flag = shop().groupBuy.validGroupGoods(null,param.getGoodsId(),param.getStartTime(),param.getEndTime());
        if (!flag){
            return fail(JsonResultMessage.GROUP_BUY_ACTIVITY_GOODS_OVERLAPPING);
        }
        shop().groupBuy.addGroupBuy(param,flag);
//        return success(Util.translateMessage(getLang(), JsonResultMessage.GROUP_BUY_ADD_ACTIVITY_STOP_STATUS,I18N_RESOURCE));
        return success();
    }


    /**
     * 删除拼团活动
     *
     * @param param GroupBuyIdParam
     * @return  JsonResult
     */
    @PostMapping("/admin/market/groupbuy/delete")
    public JsonResult deleteGroupBuy(@RequestBody @Valid GroupBuyIdParam param) {
        if (param.getId()==null){
            return fail(JsonResultCode.CODE_PARAM_ERROR);
        }
        shop().groupBuy.deleteGroupBuy(param.getId());
        return success();
    }


    /**
     * 修改拼团设置
     *
     * @param param GroupBuyEditParam
     * @return JsonResult
     */
    @PostMapping("/admin/market/groupbuy/update")
    public JsonResult updateGroupBuy(@RequestBody @Valid GroupBuyEditParam param) {
        //校验参数
        if (param == null ||param.getId()==null ||
        param.getProduct() == null || param.getProduct().size() == 0 ) {
            return fail(JsonResultCode.CODE_PARAM_ERROR);
        }
        shop().groupBuy.updateGroupBuy(param);
        return success();
    }


    /**
     * 参团明细
     *
     * @param param GroupBuyIdParam
     * @return JsonResult
     */
    @PostMapping("/admin/market/groupbuy/detail")
    public JsonResult detailGroupBuy(@RequestBody @Valid GroupBuyIdParam param) {
        if (param.getId()==null){
            return fail(JsonResultCode.CODE_PARAM_ERROR);
        }
        GroupBuyDetailVo vo = shop().groupBuy.detailGroupBuy(param.getId());
        return success(vo);
    }


    /**
     * 分享拼团
     *
     * @param param GroupBuyIdParam  活动Id
     * @return JsonResult qrCodeVo 二维码信息
     */
    @PostMapping("/admin/market/groupbuy/share")
    public JsonResult shareGroupBuy(@RequestBody @Valid GroupBuyIdParam param) {
        ShareQrCodeVo qrCodeVo = shop().groupBuy.shareGroupBuy(param.getId());
        return success(qrCodeVo);
    }

    /**
     * 停用,启用拼团
     *
     * @param param GroupBuyIdParam
     * @return JsonResult
     */
    @PostMapping("/admin/market/groupbuy/change/status")
    public JsonResult changeStatusActivity(@RequestBody @Valid GroupBuyIdParam param) {
        GroupBuyDefineRecord groupBuyRecord = shop().groupBuy.getGroupBuyRecord(param.getId());
        if (groupBuyRecord==null){
            return fail(JsonResultCode.CODE_PARAM_ERROR);
        }
        Byte status = groupBuyRecord.getStatus();
        if (param.getStatus().equals(status)){
            return success();
        }
        if(param.getStatus().equals(BaseConstant.ACTIVITY_STATUS_NORMAL)){
            Boolean flag = shop().groupBuy.validGroupGoods(groupBuyRecord.getId(),groupBuyRecord.getGoodsId(),groupBuyRecord.getStartTime(),groupBuyRecord.getEndTime());
            if (!flag){
                return fail(JsonResultMessage.GROUP_BUY_ACTIVITY_GOODS_OVERLAPPING);
            }
        }
        int resFlag = shop().groupBuy.changeStatusActivity(param.getId(),param.getStatus());
        if (resFlag>0){
         return success();
        }
        return fail();
    }

    /**
     * 参团明细列表
     *
     * @param param GroupBuyDetailParam
     * @return JsonResult
     */
    @PostMapping("/admin/market/groupbuy/detail/list")
    public JsonResult detailGroupBuyList(@RequestBody @Valid GroupBuyDetailParam param) {
        PageResult<GroupBuyDetailListVo> vo = shop().groupBuy.detailGroupBuyList(param);
        return success(vo);
    }


    /**
     * 拼团订单
     * @return JsonResult
     */
    @PostMapping("/admin/market/groupbuy/order/list")
    public JsonResult groupBuyOrderList(@RequestBody @Valid MarketOrderListParam param) {
        return success(shop().groupBuy.groupBuyOrderList(param));
    }

    /**
     * 用户列表
     *
     * @return JsonResult
     */
    @PostMapping("/admin/market/groupbuy/user/list")
    public JsonResult groupBuyNewUserList(@RequestBody @Valid MarketSourceUserListParam param) {
        PageResult<MemberInfoVo> pageResult = shop().groupBuy.groupBuyNewUserList(param);
        return success(pageResult);
    }


    /**
     * 拼团活动效果数据
     *
     * @return JsonResult
     */
    @PostMapping("/admin/market/groupbuy/analysis")
    public JsonResult groupBuyAnalysis(@RequestBody @Valid GroupBuyAnalysisParam param) {
        return success(shop().groupBuy.groupBuyAnalysis(param));
    }


}
