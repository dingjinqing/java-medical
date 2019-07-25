package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyDetailVo;
import com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyEditParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyIdParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyParam;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
     * @param param
     * @return
     */
    @PostMapping("/admin/market/groupbuy/list")
    public JsonResult getListGroupBuy(@RequestBody GroupBuyParam param) {
        shop().groupBuy.getListGroupBuy(param);
        return success();
    }


    /**
     * 增加拼团活动
     *
     * @param parm
     * @return
     */
    @PostMapping("/admin/market/groupbuy/add")
    public JsonResult addGroupBuy(@RequestBody @Valid GroupBuyParam parm) {

        //校验参数
        if (parm==null||parm.getProduct()==null
                ||parm.getProduct().size()==0||parm.getGoodsId()==null){
            return fail(JsonResultCode.CODE_PARAM_ERROR);
        }
        //校验商品是否叠加 (并发不安全)
        Boolean falg=  shop().groupBuy.validGroupGoods(parm);
        if (!falg){
            return fail(JsonResultCode.CODE_PARAM_ERROR);
        }
        //插入数据
        shop().groupBuy.addGroupBuy(parm);
        return success();
    }


    /**
     * 删除拼团活动
     *
     * @param parm
     * @return
     */
    @PostMapping("/admin/market/groupbuy/detele")
    public JsonResult deleteGroupBuy(@RequestBody GroupBuyIdParam parm) {
        shop().groupBuy.deleteGroupBuy(parm.getId());
        return success();
    }


    /**
     * 修改拼团设置
     *
     * @param param
     * @return
     */
    @PostMapping("/admin/market/groupbuy/edit")
    public JsonResult editGroupBuy(@RequestBody GroupBuyEditParam param) {
        shop().groupBuy.editGroupBuy(param);
        return success();
    }


    /**
     * 分享拼团
     * @param param
     * @return
     */
    @PostMapping("/admin/market/groupbuy/share")
    public JsonResult shareGroupBuy(@RequestBody GroupBuyParam param) {
        shop().groupBuy.shareGroupBuy();
        return success();
    }

    /**
     * 停用拼团
     * @param param
     * @return
     */
    @PostMapping("/admin/market/groupbuy/stop")
    public JsonResult stopGroupBuy(@RequestBody GroupBuyIdParam param){
        if (param.getStatus()!=null){
            shop().groupBuy.stopGroupBuy(param);
        }
        return fail(JsonResultCode.CODE_PARAM_ERROR);
    }

    /**
     * 参团明细
     * @param param
     * @return
     */
    @PostMapping("/admin/market/groupbuy/detail")
    public JsonResult detailGroupBuy(@RequestBody GroupBuyIdParam param){
        GroupBuyDetailVo vo = shop().groupBuy.detailGroupBuy(param.getId());
        return success(vo);
    }




}
