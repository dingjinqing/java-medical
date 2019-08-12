package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.fullcut.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 王兵兵
 * @create: 2019-08-09 19:00
 **/
@RestController
public class AdminMrkingStrategyController extends AdminBaseController {

    /**
     * 满折满减活动分页查询列表
     *
     */
    @PostMapping(value = "/api/admin/market/fullcut/list")
    public JsonResult getMrkingStrategyPageList(@RequestBody @Validated MrkingStrategyPageListQueryParam param) {
        return success(shop().mrkingStrategy.getPageList(param));
    }

    /**
     * 添加满折满减活动
     * @return
     */
    @PostMapping("/api/admin/market/fullcut/add")
    public JsonResult addMrkingStrategy(@RequestBody @Validated MrkingStrategyAddParam param) {
        shop().mrkingStrategy.addMrkingStrategy(param);
        return this.success();
    }

    /**
     *取单个满折满减活动信息
     *
     */
    @PostMapping(value = "/api/admin/market/fullcut/get")
    public JsonResult getMrkingStrategyById(@RequestBody @Validated SimpleMrkingStrategyParam param) {
        MrkingStrategyVo mrkingStrategyVo = shop().mrkingStrategy.getMrkingStrategyById(param.getId());
        if(mrkingStrategyVo != null) {
            return success(mrkingStrategyVo);
        }else {
            return fail();
        }
    }

    /**
     *更新 满折满减活动
     *
     */
    @PostMapping(value = "/api/admin/market/fullcut/update")
    public JsonResult updateMrkingStrategy(@RequestBody @Validated MrkingStrategyUpdateParam param) {
        shop().mrkingStrategy.updateMrkingStrategy(param);
        return success();
    }

    /**
     *删除 满折满减活动
     *
     */
    @PostMapping(value = "/api/admin/market/fullcut/del")
    public JsonResult delMrkingStrategy(@RequestBody @Validated SimpleMrkingStrategyParam param) {
        shop().mrkingStrategy.delMrkingStrategy(param.getId());
        return success();
    }
}
