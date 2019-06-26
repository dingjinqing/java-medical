package com.vpu.mp.controller.admin;

import com.vpu.mp.db.shop.tables.pojos.GoodsBrand;
import com.vpu.mp.service.shop.ShopApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.shop.goods.GoodsBrandService.GoodsBrandPageListParam;
import com.vpu.mp.support.LineConvertHump;

/**
 * 商品品牌控制器
 *
 * @author 李晓冰
 * @date 2019年6月25日
 */
@Controller
public class AdminGoodsBrandController extends AdminBaseController {

	/**
	 *	 商品品牌分页查询
	 * @param param
	 * @return
	 */
    @GetMapping("/admin/goods/goodsBrands")
    @ResponseBody
    public JsonResult getPageList(@LineConvertHump GoodsBrandPageListParam param) {

        PageResult pageResult = shop().goods.goodsBrand.getPageList(param);

        JsonResult jsonResult = JsonResult.success();

        jsonResult.setContent(pageResult);

        return jsonResult;
    }
    
    /**
     * 	新增
     * @param goodsBrand
     * @return
     */
    @PostMapping("/admin/goods/goodsBrand")
    @ResponseBody
    public JsonResult insert(GoodsBrand goodsBrand) {
        int result = shop().goods.goodsBrand.insert(goodsBrand);
        JsonResult jsonResult;

        if (result == 1) {
            jsonResult = JsonResult.success();
        } else {
            jsonResult = JsonResult.fail(JsonResult.INSERT_ERROR);
        }

        return jsonResult;
    }
    
    /**
     * 	删除
     * @param goodsBrandId
     * @return
     */
    @DeleteMapping("/admin/goods/goodsBrand")
    @ResponseBody
    public JsonResult delete(Integer goodsBrandId){

        if(goodsBrandId==null){
            return JsonResult.fail(JsonResult.DELETE_ERROR);
        }

        int result = shop().goods.goodsBrand.delete(goodsBrandId);
        JsonResult jsonResult;

        if (result == 1) {
            jsonResult = JsonResult.success();
        } else {
            jsonResult = JsonResult.fail(JsonResult.DELETE_ERROR);
        }

        return jsonResult;
    }
    
    /**
     *	修改
     * @param goodsBrand
     * @return
     */
    @PutMapping("/admin/goods/goodsBrand")
    @ResponseBody
    public JsonResult update(GoodsBrand goodsBrand){
        if(goodsBrand.getId()==null){
            return JsonResult.fail(JsonResult.UPDATE_ERROR);
        }

        int result = shop().goods.goodsBrand.update(goodsBrand);
        JsonResult jsonResult;

        if (result == 1) {
            jsonResult = JsonResult.success();
        } else {
            jsonResult = JsonResult.fail(JsonResult.UPDATE_ERROR);
        }

        return jsonResult;
    }

}
