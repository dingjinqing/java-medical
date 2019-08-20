package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.gift.GiftDetailListParam;
import com.vpu.mp.service.pojo.shop.market.gift.GiftListParam;
import com.vpu.mp.service.pojo.shop.market.gift.GiftParam;
import com.vpu.mp.service.pojo.shop.market.gift.LevelParam;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 赠品
 *
 * @author 郑保乐
 */
@RestController
@RequestMapping("/api/admin/market/gift")
public class AdminGiftController extends AdminBaseController {

    /**
     * 创建活动
     */
    @PostMapping("/add")
    public JsonResult addGift(@RequestBody @Valid GiftParam param) {
        shop().gift.addGift(param);
        return success();
    }

    /**
     * 编辑 - 查询明细
     */
    @PostMapping("/detail/{id}")
    public JsonResult getDetail(@PathVariable Integer id) {
        return success(shop().gift.getGiftDetail(id));
    }

    /**
     * 修改活动
     */
    @PostMapping("/update")
    public JsonResult updateGift(@RequestBody @Valid GiftParam param) {
        shop().gift.updateGift(param);
        return success();
    }

    /**
     * 停用活动
     */
    @PostMapping("/disable/{id}")
    public JsonResult disableGift(@PathVariable Integer id) {
        shop().gift.disableGift(id);
        return success();
    }

    /**
     * 删除活动
     */
    @PostMapping("/delete/{id}")
    public JsonResult deleteGift(@PathVariable Integer id) {
        shop().gift.deleteGift(id);
        return success();
    }

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public JsonResult getPageList(@RequestBody GiftListParam param) {
        return success(shop().gift.getPageList(param));
    }

    /**
     * 修改优先级
     */
    @PostMapping("/level/update")
    public JsonResult updateLevel(@RequestBody @Valid LevelParam param) {
        shop().gift.updateLevel(param);
        return success();
    }

    /**
     * 赠品明细
     */
    @PostMapping("/gift_detail")
    public JsonResult giftDetail(@RequestBody @Valid GiftDetailListParam param) {
        return success(shop().gift.getGiftDetailPageList(param));
    }
}
