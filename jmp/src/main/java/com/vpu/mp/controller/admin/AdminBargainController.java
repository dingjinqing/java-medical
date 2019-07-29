package com.vpu.mp.controller.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.bargain.Bargain;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainAddParam;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainPageListQueryParam;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainPageListQueryVo;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainRecordPageListQueryParam;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainRecordPageListQueryVo;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainUpdateParam;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainUserListQueryParam;
import com.vpu.mp.service.shop.market.bargain.BargainRecordService;

/**
 * @author 王兵兵
 *
 * 2019年7月24日
 */
@RestController
public class AdminBargainController extends AdminBaseController {

	/**
	 * 砍价活动分页查询列表
	 * @return
	 */
	@PostMapping(value = "/api/admin/market/bargain/list")
	public JsonResult getBargainPageList(@RequestBody @Valid BargainPageListQueryParam param) {
		PageResult<BargainPageListQueryVo>  res = shop().bargain.getPageList(param);
		for(BargainPageListQueryVo vo : res.dataList) {
			vo.setSuccessNumber(shop().bargain.bargainRecord.getBargainRecordNumberByStatus(vo.getId(), BargainRecordService.STATUS_SUCCESS));
			vo.setBargainUserNumber(shop().bargain.bargainRecord.getBargainRecordNumber(vo.getId()));
		}
		return success(res);
	}
	
	/**
	 *添加 砍价活动
	 * @return
	 */
	@PostMapping(value = "/api/admin/market/bargain/add")
	public JsonResult addBargain(@RequestBody @Valid BargainAddParam param) {
		if(shop().bargain.addBargain(param)) {
			return success();
		}else {
			return fail();
		}
	}
	
	/**
	 *更新  砍价活动
	 * @return
	 */
	@PostMapping(value = "/api/admin/market/bargain/update")
	public JsonResult updateBargain(@RequestBody @Valid BargainUpdateParam param) {
		if(shop().bargain.updateBargain(param)) {
			return success();
		}else {
			return fail();
		}
	}
	
	/**
	 *取单个砍价活动信息
	 * @return
	 */
	@PostMapping(value = "/api/admin/market/bargain/get")
	public JsonResult getBargainByIsd(@RequestBody @Valid Bargain param) {
		Bargain bargain = shop().bargain.getBargainByIsd(param.getId());
		if(bargain != null) {
			return success(bargain);
		}else {
			return fail();
		}
	}
	
	/**
	 *取砍价取单日可帮助砍价的次数
	 * @return
	 */
	@GetMapping(value = "/api/admin/market/bargain/cut/times/get")
	public JsonResult getDailyCutTimes() {
		return success(shop().config.bargainCfg.getDailyCutTimes());
	}
	
	/**
	 *取砍价取单日可帮助砍价的次数
	 * @return
	 */
	@GetMapping(value = "/api/admin/market/bargain/cut/times/set")
	public JsonResult setDailyCutTimes(Integer dailyCutTimes) {
		if(shop().config.bargainCfg.setDailyCutTimes(dailyCutTimes) > 0) {
			return success();
		}else {
			return fail();
		}
	}
	
	/**
	 * 发起砍价的用户列表
	 * @return
	 */
	@PostMapping(value = "/api/admin/market/bargain/record/list")
	public JsonResult getRecordPageList(@RequestBody @Valid BargainRecordPageListQueryParam param) {
		PageResult<BargainRecordPageListQueryVo> res = shop().bargain.bargainRecord.getRecordPageList(param);
		for(BargainRecordPageListQueryVo vo : res.dataList) {
			vo.setSurplusMoney(shop().bargain.bargainRecord.getBargainRecordSurplusMoney(vo));
		}
		return success(res);
	}
	
	/**
	 * 导出发起砍价的用户列表
	 * @return
	 */
	@PostMapping(value = "/api/admin/market/bargain/record/list/export")
	public void exportBargainRecordList(@RequestBody @Valid BargainRecordPageListQueryParam param, HttpServletResponse response) throws IOException {
		Workbook workbook =shop().bargain.bargainRecord.exportBargainRecordList(param,getLang());
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	    response.setHeader("Content-Disposition", "attachment;filename=xxx.xls");
		workbook.write(response.getOutputStream());
	}
	
	/**
	 * 帮忙砍价的用户列表
	 * @return
	 */
	@PostMapping(value = "/api/admin/market/bargain/record/detail")
	public JsonResult getBargainUserPageList(@RequestBody @Valid BargainUserListQueryParam param) {
		return success(shop().bargain.bargainUser.getPageList(param));
	}
}
