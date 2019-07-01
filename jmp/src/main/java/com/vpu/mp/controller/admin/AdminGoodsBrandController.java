package com.vpu.mp.controller.admin;

import com.vpu.mp.db.shop.tables.pojos.GoodsBrand;
import com.vpu.mp.service.shop.ShopApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
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
    public JsonResult getPageList(GoodsBrandPageListParam param) {

        PageResult pageResult = shop().goods.goodsBrand.getPageList(param);

        return  success(pageResult);
    }
    
    /**
     * 	新增
     * @param goodsBrand
     * @return
     */
    @PostMapping("/admin/goods/goodsBrand")
    @ResponseBody
    public JsonResult insert(@RequestBody GoodsBrand goodsBrand) {
    	
    	shop().goods.goodsBrand.insert(goodsBrand);
    	
    	return success();
    }
    
    /**
     * 	删除
     * @param goodsBrand
     * @return
     */
    @DeleteMapping("/admin/goods/goodsBrand")
    @ResponseBody
    public JsonResult delete(@RequestBody GoodsBrand goodsBrand){
    	
    	  if(goodsBrand.getId()==null){
              return fail(JsonResultCode.CODE_PARAM_ERROR);
          }
    	  
          shop().goods.goodsBrand.delete(goodsBrand);
          
          return success();          
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
        	return fail(JsonResultCode.CODE_PARAM_ERROR);
        }

        shop().goods.goodsBrand.update(goodsBrand);
        
        return success();
    }

}
