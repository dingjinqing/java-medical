package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.integration.ChangeStatusParam;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationDefineEditVo;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationDefinePageParam;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationDefineParam;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationListDetailParam;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationListParticipationVo;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationSuccessParam;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationSuccessVo;

/**
 * @author huangronggang
 * @date 2019年8月5日
 */
@RestController
@RequestMapping("/api/admin/market/integration")
public class AdminGroupIntegrationController extends AdminBaseController {
    /**
     * 瓜分积分下拉框
     * @return id name
     */
    @GetMapping("/selectlist")
    public JsonResult getSelectList(){
        return success(shop().groupIntegration.getActSelectList());
    }
	/**
	 * 分页查询瓜分积分活动
	 * @param param
	 * @return
	 */
	@PostMapping("/list")
	public JsonResult getPageList(@RequestBody GroupIntegrationDefinePageParam param) {
		return success(shop().groupIntegration.getPageList(param));
	}
	/**
	 * 添加瓜分积分活动
	 * @param param
	 * @return
	 */
	@PostMapping("/add")
	public JsonResult insert(@RequestBody @Valid GroupIntegrationDefineParam param) {
		shop().groupIntegration.insertDefine(param);
		return success();
	}
	/**
	 * 查指定ID的瓜分积分活动
	 * @param id
	 * @return
	 */
	@GetMapping("/select/{id}")
	public JsonResult select(@PathVariable Integer id) {
		GroupIntegrationDefineEditVo defineVo = shop().groupIntegration.selectGroupIntegrationDefineById(id);
		return success(defineVo);
	}
	/**
	 * 删除指定iD 的瓜分积分活动
	 * @param id
	 * @return
	 */
	@PostMapping("/delete/{id}")
	public JsonResult delete(@PathVariable Integer id) {
		int delete = shop().groupIntegration.deleteDefine(id);
		if(delete>0) {
			return success();
		}
		return fail();
	}
	/**
	 * 更新指定ID的瓜分积分活动
	 * @param param
	 * @return
	 */
	@PostMapping("/update")
	public JsonResult update(@RequestBody @Valid GroupIntegrationDefineParam param) {
		if(param.getId() == null) {
			return fail();
		}
		int result = shop().groupIntegration.updateDefine(param);
		if(result>0) {
			return success();
		}
		return fail();
	}
	/**
	 * 停用或启用活动
	 * @param param
	 * @return
	 */
	@PostMapping("/change/status")
	public JsonResult changeStatus(@RequestBody ChangeStatusParam param) {
		int result = shop().groupIntegration.changDefineStatus(param.getId(), param.getStatus());
		if(result>0) {
			return success();
		}else {
			return fail();
		}
	}
	/**
	 * 参团明细
	 * @param param
	 * @return
	 */
	@PostMapping("/detail")
	public JsonResult getDetailPageList(@RequestBody @Valid GroupIntegrationListDetailParam param) {
		PageResult<GroupIntegrationListParticipationVo> result = shop().groupIntegration.groupIntegrationList.getDetailPageList(param);
		return success(result);
	}
	/**
	 * 成团明细
	 * @param param
	 * @return
	 */
	@PostMapping("/success")
	public JsonResult getSuccessPageList(@RequestBody @Valid GroupIntegrationSuccessParam param) {
		PageResult<GroupIntegrationSuccessVo> result = shop().groupIntegration.groupIntegrationList.getSuccessPageList(param);
		return success(result);
	}
	/**
	 * 获取分享活动的小程序码
	 * @param actId
	 * @return
	 */
	@PostMapping("/getqrcode/{actId}")
	public JsonResult getQrcode(@PathVariable Integer actId) {
		GroupIntegrationShareQrCodeVo maQrCode = shop().groupIntegration.getMaQrCode(actId);
		if(maQrCode != null) {
			return success(maQrCode);
		}
		return fail();
	}
	
}

