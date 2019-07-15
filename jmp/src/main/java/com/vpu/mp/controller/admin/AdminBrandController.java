package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.goods.brand.BrandVo;

/**
 * 品牌
 * @author 常乐
 * 2019年7月15日
 */
@RestController
@RequestMapping("api")
public class AdminBrandController extends AdminBaseController{
	
	/**
	 * 品牌分类列表
	 * @return
	 */
	@GetMapping("/admin/brand/list")
	public JsonResult brandClassifyList() {
		List<BrandVo> chooseBrandList = shop().brand.getBrandClassifyList();
		return this.success(chooseBrandList);
    }
}
