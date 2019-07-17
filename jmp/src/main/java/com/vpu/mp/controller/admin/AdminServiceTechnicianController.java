package com.vpu.mp.controller.admin;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.JsonResultMessage;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.store.postsale.ServiceTechnicianPageListParam;
import com.vpu.mp.service.pojo.shop.store.postsale.ServiceTechnicianParam;
import com.vpu.mp.service.pojo.shop.store.postsale.ServiceTechnicianPojo;

/**
 * @author 黄荣刚
 * @date 2019年7月15日
 *
 */
@RestController
@RequestMapping("/api/admin/store")
public class AdminServiceTechnicianController extends AdminBaseController {
	/**
	 * 分页查询售后信息，支持售后姓名、售后分组、售后电话模糊查询
	 * @param param
	 * @return
	 */
	@PostMapping("/services/technician/list")
	public JsonResult getList(@RequestBody @Valid ServiceTechnicianPageListParam param) {
		PageResult<ServiceTechnicianPojo> list = shop().serviceTechnician.getPageList(param);
		return success(list);
	}
	/**
	 * 根据ID查一条售后信息
	 * @param id
	 * @return
	 */
	@GetMapping("/services/technician/select/{id}")
	public JsonResult select(@PathVariable Integer id) {
		ServiceTechnicianPojo pojo = shop().serviceTechnician.select(id);
		if(pojo == null) {
			return fail(JsonResultCode.CODE_FAIL);
		}else {
			return success(pojo);
		}
	}
	/**
	 * 添加售后
	 * @param param
	 * @return
	 */
	@PostMapping("/services/technician/add")
	public JsonResult insert(@RequestBody @Valid ServiceTechnicianParam param) {
		int result = shop().serviceTechnician.insert(param);
		if(result == 0) {
			return fail(JsonResultCode.CODE_FAIL);
		}
		return success(JsonResultCode.CODE_SUCCESS);
	}
	
	/**
	 * 修改售后
	 * @param param
	 * @return
	 */
	@PostMapping("/services/technician/update")
	public JsonResult update(@RequestBody @Valid ServiceTechnicianParam param) {
		if(param.getId()== null) {
			return fail(JsonResultCode.STORE_STORE_ID_NULL,JsonResultMessage.STORE_STORE_ID_NULL);
		}
		int result = shop().serviceTechnician.update(param);
		if(result == 0) {
			return fail(JsonResultCode.CODE_FAIL);
		}
		return success(JsonResultCode.CODE_SUCCESS);
	}
	
	/**
	 * 删除售后
	 * @param id
	 * @return
	 */
	@PostMapping("/services/technician/delete/{id}")
	public JsonResult delete(@PathVariable @NotNull Integer id) {
		int result = shop().serviceTechnician.delete(id);
		if(result>0) {
			return success(JsonResultCode.CODE_SUCCESS);
		}
		return fail(JsonResultCode.CODE_FAIL);
	}
}
