package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.gift.GiftParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
