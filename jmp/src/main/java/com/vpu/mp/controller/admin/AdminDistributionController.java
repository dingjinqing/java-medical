package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributionStrategyParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributionStrategyVo;

/**
 * 分销模块
 * @author 常乐
 * 2019年7月17日
 */
@RestController
@RequestMapping("/api")
public class AdminDistributionController extends AdminBaseController{
	/**
	 * 获取分销配置
	 * @return
	 */
	@GetMapping("/admin/distribution/get")
	public JsonResult distributionCfg() {
		DistributionParam result = shop().config.distributioncfg.getDistributionCfg();
		return this.success(result);
	}
	
	/**
	 *设置分销配置
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/set")
	public JsonResult setDistributionCfg(@RequestBody DistributionParam param) {
		int result = shop().config.distributioncfg.setDistributionCfg(param);
		return this.success(result);
	}
	
	/**
	 * 添加返利策略
	 * @param info
	 * @return
	 */
	@PostMapping("/admin/distribution/rebate/set")
	public JsonResult setRebateStrategy(@RequestBody DistributionStrategyParam info) {
		boolean result = shop().rebateStrategy.setRebateStrategy(info);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}
	
	/**
	 * 返利策略分页列表
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/rebate/list")
	public JsonResult rebateStrategyList(DistributionStrategyParam param) {
		PageResult<DistributionStrategyVo> list = shop().rebateStrategy.getStrategyList(param);
		return this.success(list);
	}
	
	/**
	 * 编辑返利策略
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/rebate/edit")
	public JsonResult oneRebateStrategyInfo(@RequestBody Integer id) {
		List<DistributionStrategyParam> result = shop().rebateStrategy.getOneInfo(id);
		return this.success(result);
	}
	
	/**
	 * 返利策略编辑保存
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/rebate/save")
	public JsonResult saveRebateStrategy(@RequestBody DistributionStrategyParam param) {
		boolean result = shop().rebateStrategy.saveRebateStrategy(param);
		return this.success(result);
	}
	
	/**
	 * 返利策略停用
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/rebate/pause")
	public JsonResult pauseRebateStrategy(@RequestBody Integer id) {
		boolean result = shop().rebateStrategy.pauseRebate(id);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}
	
	/**
	 * 返利策略删除
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/rebate/delete")
	public JsonResult deleteRebateStrategy(@RequestBody Integer id) {
		boolean result = shop().rebateStrategy.deleteRebate(id);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}
}
