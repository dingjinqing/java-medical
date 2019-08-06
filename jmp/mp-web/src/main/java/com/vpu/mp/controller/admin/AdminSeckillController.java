package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.seckill.SeckillAddParam;
import com.vpu.mp.service.pojo.shop.market.seckill.SeckillPageListQueryParam;
import com.vpu.mp.service.pojo.shop.market.seckill.SeckillUpdateParam;
import com.vpu.mp.service.pojo.shop.market.seckill.SeckillVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public JsonResult getSeckillPageList(@RequestBody @Valid SeckillPageListQueryParam param) {
        return success(shop().seckill.getPageList(param));
    }

    /**
     *添加 秒杀活动
     *
     */
    @PostMapping(value = "/api/admin/market/seckill/add")
    public JsonResult addSeckill(@RequestBody @Valid SeckillAddParam param) {
        shop().seckill.addSeckill(param);
        return success();
    }

    /**
     *更新 秒杀活动
     *
     */
    @PostMapping(value = "/api/admin/market/seckill/update")
    public JsonResult updateSeckill(@RequestBody @Valid SeckillUpdateParam param) {
        shop().seckill.updateSeckill(param);
        return success();
    }

    /**
     *删除 秒杀活动
     *
     */
    @PostMapping(value = "/api/admin/market/seckill/del")
    public JsonResult delSeckill(@RequestBody @Valid SeckillUpdateParam param) {
        shop().seckill.delSeckill(param.getSkId());
        return success();
    }

    /**
     *取单个秒杀活动信息
     *
     */
    @PostMapping(value = "/api/admin/market/seckill/get")
    public JsonResult getSeckillByIsd(@RequestBody @Valid SeckillUpdateParam param) {
        SeckillVo seckillVo = shop().seckill.getSeckillById(param.getSkId());
        if(seckillVo != null) {
            return success(seckillVo);
        }else {
            return fail();
        }
    }
}
