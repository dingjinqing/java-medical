package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.pojo.shop.goods.Goods;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@RestController
public class AdminMedicalGoodsController extends AdminBaseController{
    public JsonResult insert(@RequestBody Goods goods){

        return success();
    }
}
