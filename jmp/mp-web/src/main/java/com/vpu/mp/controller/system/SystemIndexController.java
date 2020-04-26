package com.vpu.mp.controller.system;


import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.saas.question.param.FeedBackParam;
import com.vpu.mp.service.saas.index.param.ShopViewParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author 新国
 *
 */
@RestController
public class SystemIndexController extends SystemBaseController {

    /**
     * system概览-概览
     * @param param {@link ShopViewParam}
     * @return {@link JsonResult}
     */
    @PostMapping("/api/system/index/shopView")
    public JsonResult shopView(@RequestBody ShopViewParam param){
        return success(saas.shopViewService.getShopViewData(param));
    }

    /**
     * system概览-问题反馈
     * @param param {@link FeedBackParam}
     * @return {@link JsonResult}
     */
    @PostMapping("/api/system/index/feedback/list")
    public JsonResult feedbackList(@RequestBody FeedBackParam param){
        return success(saas.questionService.getPageByParam(param));
    }
}
