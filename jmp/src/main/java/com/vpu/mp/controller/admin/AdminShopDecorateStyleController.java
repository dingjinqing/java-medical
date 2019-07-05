package com.vpu.mp.controller.admin;


import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.decoration.DecorateStyleParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 	店铺风格
 *
 * @author 孔德成
 * @date 2019年7月3日
 */
@RestController
public class AdminShopDecorateStyleController  extends AdminBaseController{


    /**
     * 设置店铺风格
     * @param param
     * @return
     */
    @PostMapping("/api/admin/manage/decorate/updateStyle")
    public JsonResult shopStyle(DecorateStyleParam param){
        shop().mpDecoration.updateShopStyle(param);
        return success();
    }



}
