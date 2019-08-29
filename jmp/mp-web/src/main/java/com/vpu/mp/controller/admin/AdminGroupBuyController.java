package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
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
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;

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
    public JsonResult getListGroupBuy(@RequestBody GroupBuyListParam param) {
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

        //校验参数
        if (param == null || param.getProduct() == null
                || param.getProduct().size() == 0 || param.getGoodsId() == null) {
            return fail(JsonResultCode.CODE_PARAM_ERROR);
        }
        //校验活动商品是否叠加 (并发不安全)
        Boolean flag = shop().groupBuy.validGroupGoods(param);
        if (!flag) {
            return fail(JsonResultCode.CODE_PARAM_ERROR);
        }
        //插入数据
        shop().groupBuy.addGroupBuy(param);
        return success();
    }


    /**
     * 删除拼团活动
     *
     * @param param GroupBuyIdParam
     * @return  JsonResult
     */
    @PostMapping("/admin/market/groupbuy/delete")
    public JsonResult deleteGroupBuy(@RequestBody GroupBuyIdParam param) {
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
        int flag = shop().groupBuy.updateGroupBuy(param);
        if (flag<=0){
            return fail();
        }
        return success();
    }


    /**
     * 参团明细
     *
     * @param param GroupBuyIdParam
     * @return JsonResult
     */
    @PostMapping("/admin/market/groupbuy/detail")
    public JsonResult detailGroupBuy(@RequestBody GroupBuyIdParam param) {
        GroupBuyDetailVo vo = shop().groupBuy.detailGroupBuy(param.getId());
        return success(vo);
    }


    /**
     * 分享拼团
     *
     * @param param GroupBuyParam
     * @return JsonResult
     */
    @PostMapping("/admin/market/groupbuy/share")
    public JsonResult shareGroupBuy(@RequestBody GroupBuyParam param) {
        shop().groupBuy.shareGroupBuy();
        return success();
    }

    /**
     * 停用,启用拼团
     *
     * @param param GroupBuyIdParam
     * @return JsonResult
     */
    @PostMapping("/admin/market/groupbuy/change/status")
    public JsonResult changeStatusActivity(@RequestBody GroupBuyIdParam param) {
        int flag = shop().groupBuy.changeStatusActivity(param.getId());
        if (flag>0){
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
    public JsonResult detailGroupBuyList(@RequestBody GroupBuyDetailParam param) {
        PageResult<GroupBuyDetailListVo> vo = shop().groupBuy.detailGroupBuyList(param);
        return success(vo);
    }


    /**
     * 拼团订单
     * @return JsonResult
     */
    @PostMapping("/admin/market/groupbuy/order/list")
    public JsonResult groupBuyOrderList(@RequestBody MarketOrderListParam param) {
        PageResult<OrderListInfoVo> pageList = shop().groupBuy.groupBuyOrderList(param);
        return success(pageList);
    }

    /**
     * 用户列表
     *
     * @return JsonResult
     */
    @PostMapping("/admin/market/groupbuy/user/list")
    public JsonResult groupBuyNewUserList(@RequestBody MarketSourceUserListParam param) {
        PageResult<MemberInfoVo> pageResult = shop().groupBuy.groupBuyNewUserList(param);
        return success(pageResult);
    }


    /**
     * 拼团活动效果数据
     *
     * @return JsonResult
     */
    @PostMapping("/admin/market/groupbuy/analysis")
    public JsonResult groupBuyAnalysis(@RequestBody GroupBuyAnalysisParam param) {
        return success(shop().groupBuy.groupBuyAnalysis(param));
    }


}
