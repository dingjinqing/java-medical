package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 团购、多人平团
 *
 * @author 孔德成
 * @date 2019/7/18 14:27
 */
@ResponseBody
@RequestMapping("/api")
public class GroupBuyController extends AdminBaseController {


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
    public JsonResult addGroupBuy(@RequestBody GroupBuyParam parm) {
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
    public JsonResult deleteGroupBuy(@RequestBody GroupBuyParam parm) {
        shop().groupBuy.deleteGroupBuy(parm);
        return success();
    }


    /**
     * 修改拼团设置
     *
     * @param param
     * @return
     */
    @PostMapping("/admin/market/groupbuy/edit")
    public JsonResult editGroupBuy(@RequestBody GroupBuyParam param) {
        shop().groupBuy.editGroupBuy();
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
    public JsonResult stopGroupBuy(@RequestBody GroupBuyParam param){
        shop().groupBuy.stopGroupBuy(param);
        return success();
    }

    /**
     * 参团明细
     * @param param
     * @return
     */
    @PostMapping("/admin/market/groupbuy/detail")
    public JsonResult detailGroupBuy(@RequestBody GroupBuyParam param){
        shop().groupBuy.detailGroupBuy();
        return success();
    }




}
