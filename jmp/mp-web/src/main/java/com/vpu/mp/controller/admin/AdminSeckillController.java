package com.vpu.mp.controller.admin;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketSourceUserListParam;
import com.vpu.mp.service.pojo.shop.market.seckill.SeckillAddParam;
import com.vpu.mp.service.pojo.shop.market.seckill.SeckillDetailPageListQueryParam;
import com.vpu.mp.service.pojo.shop.market.seckill.SeckillPageListQueryParam;
import com.vpu.mp.service.pojo.shop.market.seckill.SeckillUpdateParam;
import com.vpu.mp.service.pojo.shop.market.seckill.SeckillVo;


/**
 * @author 王兵兵
 *
 * 2019年8月6日
 */
@RestController
public class AdminSeckillController extends AdminBaseController {

    /**
     * 秒杀活动分页查询列表
     *
     */
    @PostMapping(value = "/api/admin/market/seckill/list")
    public JsonResult getSeckillPageList(@RequestBody @Validated SeckillPageListQueryParam param) {
        return success(shop().seckill.getPageList(param));
    }

    /**
     *添加 秒杀活动
     *
     */
    @PostMapping(value = "/api/admin/market/seckill/add")
    public JsonResult addSeckill(@RequestBody @Validated SeckillAddParam param) {
        shop().seckill.addSeckill(param);
        return success();
    }

    /**
     *更新 秒杀活动
     *
     */
    @PostMapping(value = "/api/admin/market/seckill/update")
    public JsonResult updateSeckill(@RequestBody @Validated SeckillUpdateParam param) {
        shop().seckill.updateSeckill(param);
        return success();
    }

    /**
     *删除 秒杀活动
     *
     */
    @PostMapping(value = "/api/admin/market/seckill/del")
    public JsonResult delSeckill(@RequestBody @Validated SeckillUpdateParam param) {
        shop().seckill.delSeckill(param.getSkId());
        return success();
    }

    /**
     *取单个秒杀活动信息
     *
     */
    @PostMapping(value = "/api/admin/market/seckill/get")
    public JsonResult getSeckillByIsd(@RequestBody @Validated SeckillUpdateParam param) {
        SeckillVo seckillVo = shop().seckill.getSeckillById(param.getSkId());
        if(seckillVo != null) {
            return success(seckillVo);
        }else {
            return fail();
        }
    }

    /**
     * 秒杀拉新用明细
     *
     */
    @PostMapping(value = "/api/admin/market/seckill/source")
    public JsonResult getSeckillSourceUserList(@RequestBody @Validated MarketSourceUserListParam param) {
        return success(shop().seckill.getSeckillSourceUserList(param));
    }

    /**
     * 秒杀订单
     *
     */
    @PostMapping(value = "/api/admin/market/seckill/order")
    public JsonResult getSeckillOrderList(@RequestBody @Validated MarketOrderListParam param) {
        return success(shop().seckill.getSeckillOrderList(param));
    }

    /**
     * 秒杀参与明细
     *
     */
    @PostMapping(value = "/api/admin/market/seckill/detail")
    public JsonResult getSeckillDetailPageList(@RequestBody @Validated SeckillDetailPageListQueryParam param) {
        return success(shop().seckill.seckillList.getSeckillDetailPageList(param));
    }

    /**
     * 取活动分享二维码
     */
    @GetMapping("/api/admin/market/seckill/share")
    public JsonResult getSeckillShareCode(Integer skId) throws Exception {
        return success(shop().seckill.getMpQRCode(skId));
    }
}
