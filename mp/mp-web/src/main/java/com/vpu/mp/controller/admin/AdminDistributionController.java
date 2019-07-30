package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributionStrategyParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributionStrategyVo;
import com.vpu.mp.service.pojo.shop.distribution.DistributorGroupListParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributorGroupListVo;
import com.vpu.mp.service.pojo.shop.distribution.DistributorLevelCfgVo;

/**
 * 分销模块
 * @author 常乐
 * 2019年7月17日
 */
@RestController
@RequestMapping("/api")
public class AdminDistributionController extends AdminBaseController{
	//分销配置
	/**
	 * 获取分销配置
	 * @return
	 */
	@GetMapping("/admin/distribution/get")
	public JsonResult distributionCfg() {
		DistributionParam result = shop().config.distributionCfg.getDistributionCfg();
		return this.success(result);
	}
	
	/**
	 *设置分销配置
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/set")
	public JsonResult setDistributionCfg(@RequestBody DistributionParam param) {
		int result = shop().config.distributionCfg.setDistributionCfg(param);
		return this.success(result);
	}
	
	//返利策略配置
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
	
	//分销员分组
	/**
	 * 分销员分组列表
	 * @param param
	 */
	@PostMapping("/admin/distribution/group/list")
	public JsonResult distributorGroupList(@RequestBody DistributorGroupListParam param) {
		PageResult<DistributorGroupListVo> groupList = shop().distributorGroup.getDistributorGroupList(param);
		return this.success(groupList);
	}
	
	/**
	 * 添加分销员分组
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/group/add")
	public JsonResult distributorGroupAdd(DistributorGroupListParam param) {
		//判断是否存在该分组
		boolean isExists = shop().distributorGroup.isExistGroup(param);
		if(isExists) {
			return this.fail(JsonResultCode.DISTRIBUTOR_GROUP_NAME_EXIST);
		}
		
		boolean result = shop().distributorGroup.adddistributorGroup(param);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}
	
	
	/**
	 * 设置默认分组
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/group/default")
	public JsonResult setDefaultGroup(Integer id) {
		boolean result = shop().distributorGroup.setDefault(id);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}
	
	/**
	 * 取消默认分组
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/group/cancle")
	public JsonResult cancleDefaultGroup(Integer id) {
		boolean result = shop().distributorGroup.cancleDefault(id);
		return this.success(result);
	}
	
	/**
	 * 编辑分组，获取单条信息
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/group/edit")
	public JsonResult distributorGroupEdit(Integer id) {
		List<DistributorGroupListVo> info = shop().distributorGroup.getOneInfo(id);
		return this.success(info);
	}
	
	/**
	 * 删除分组
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/group/del")
	public JsonResult distributorGroupDel(Integer id) {
		boolean result = shop().distributorGroup.delGroup(id);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}
	
	//分销员等级配置
	/**
	 * 分销员等级配置列表
	 * @return
	 */
	@GetMapping("/admin/distribution/level/config")
	public JsonResult distributorLevelConfig() {
		DistributorLevelCfgVo levelCfg = shop().distributorLevel.levelConfig();
		return this.success(levelCfg);
	}
	
}
