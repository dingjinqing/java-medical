package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketSourceUserListParam;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertAddParam;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertGoodsParam;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertGoodsVo;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertListParam;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertListVo;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertOrderVo;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertSelectParam;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertSelectVo;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertSwitchParam;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertUserParam;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertUserVo;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;

/**
 * 积分兑换控制器
 * @author liangchen
 * @date 2019年8月14日
 */
@RestController
@RequestMapping("/api/admin/market/integral/convert")
public class AdminIntegralConvertController extends AdminBaseController{

	/**
	 * 查询积分兑换活动分页列表
	 *
	 * @param IntegralConvertListParam
	 * @return
	 */
	@PostMapping("/list")
	public JsonResult getList(@RequestBody IntegralConvertListParam param) {
		
		PageResult<IntegralConvertListVo> pageResult = shop().integralConvertService.getList(param);
		return success(pageResult);
		
	}
	
	/**
	 * 停用或启用活动
	 *
	 * @param IntegralConvertSwitchParam
	 * @return
	 */
	@PostMapping("/switch")
	public JsonResult startOrStop(@RequestBody IntegralConvertSwitchParam param) {
		
		shop().integralConvertService.startOrStop(param);
		return success();
		
	}
	
	/**
	 * 删除单个活动
	 *
	 * @param param
	 * @return
	 */
	@PostMapping("/delete")
	public JsonResult deleteAct(@RequestBody IntegralConvertSwitchParam param) {
		shop().integralConvertService.deleteAct(param);
		return success();
	}
	
	/**
	 * 积分兑换用户列表
	 *
	 * @param 
	 * @return
	 */
	@PostMapping("/user")
	public JsonResult userList(@RequestBody IntegralConvertUserParam param) {
		
		PageResult<IntegralConvertUserVo> pageResult = shop().integralConvertService.userList(param);
		return success(pageResult);
	}
	
	/**
	 * 添加积分兑换活动
	 *
	 * @param 
	 * @return
	 */
	@PostMapping("/add")
	public JsonResult addAction(@RequestBody IntegralConvertAddParam param) {
		
		shop().integralConvertService.addAction(param);
		return success();
	}
	
	/**
	 * 返回指定商品规格详情
	 *
	 * @param 
	 * @return
	 */
	@PostMapping("/product")
	public JsonResult getproduct(@RequestBody IntegralConvertGoodsParam param) {
		
		List<IntegralConvertGoodsVo> vo = shop().integralConvertService.getproduct(param);
		return success(vo);
	}
	
	/**
	 * 查询指定积分兑换活动详情
	 *
	 * @param 
	 * @return
	 */
	@PostMapping("/select")
	public JsonResult selectOne(@RequestBody IntegralConvertSelectParam param) {
		
		IntegralConvertSelectVo vo = shop().integralConvertService.selectOne(param);
		return success(vo);
	}
	/**
	 * 添加积分兑换活动
	 *
	 * @param 
	 * @return
	 */
	@PostMapping("/update")
	public JsonResult updateAction(@RequestBody IntegralConvertAddParam param) {
		
		shop().integralConvertService.updateAction(param);
		return success();
	}
	 /**
     * 新增用户列表
     *
     * @param
     * @return JsonResult
     */
    @PostMapping("/newuser")
    public JsonResult integralNewUserList(@RequestBody MarketSourceUserListParam param) {
        PageResult<MemberInfoVo> pageResult = shop().integralConvertService.integralNewUserList(param);
        return success(pageResult);
    }

    /**
     * 查看积分兑换订单
     * 
     * @param
     * @return JsonResult
     */
    @PostMapping("/order")
    public JsonResult integralOrderList(@RequestBody MarketOrderListParam param) {
        PageResult<IntegralConvertOrderVo> pageList = shop().integralConvertService.interalOrderList(param);
        return success(pageList);
    }
}
