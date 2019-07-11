package com.vpu.mp.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopChildRoleRecord;
import com.vpu.mp.db.main.tables.records.ShopRoleRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.saas.shop.ShopPojo;
import com.vpu.mp.service.pojo.shop.config.ShopBaseConfig;
import com.vpu.mp.service.pojo.shop.config.ShopCommonCfgInfo;
import com.vpu.mp.service.pojo.shop.config.group.ShopRoleDelParam;
import com.vpu.mp.service.pojo.shop.config.group.ShopRoleParam;
import com.vpu.mp.service.pojo.shop.config.group.ShopRoleUpdateParam;
import com.vpu.mp.service.pojo.shop.config.pledge.PledgeInfo;
import com.vpu.mp.service.pojo.shop.config.pledge.PledgeParam;
import com.vpu.mp.service.pojo.shop.config.pledge.PledgeStateUpdateParam;
import com.vpu.mp.service.pojo.shop.config.pledge.PledgeVo;
import com.vpu.mp.service.pojo.shop.config.pledge.group.PledgeStateUpdateGroup;
import com.vpu.mp.service.pojo.shop.config.pledge.group.UpdateGroup;

/**
 * 商家--基础配置
 * @author: 卢光耀
 * @date: 2019-07-09 15:05
 *
*/
@RestController
@RequestMapping(value = "/api/admin/config")
public class AdminBasicConfigController extends AdminBaseController{
    /**
     * 服务承诺--列表
     * @return JsonResult
     */
    @GetMapping(value = "/pledge/list")
    public JsonResult getPledgeList(){
        PledgeVo vo = new PledgeVo();
        List<PledgeInfo> result = shop().shopBasicConfig.shopPledge.getPledgeList();
        String value = shop().config.pledgeCfg.getPledgeConfig();
        vo.setList(result);
        vo.setState(Byte.valueOf(value));
        return success(vo);
    }

    /**
     * 服务承诺--新增
     * @param param
     * @return JsonResult
     */
    @PostMapping(value = "/pledge/add")
    public JsonResult insertPledge(@RequestBody @Validated PledgeParam param){

        boolean canInsert = shop().shopBasicConfig.shopPledge.judgeInsertParam();
        if ( canInsert ){
            shop().shopBasicConfig.shopPledge.insertPledge(param);
            return success();
        }else{
            return fail(JsonResultCode.CODE_CONFIG_PLEDGE_EXCEED);
        }
    }
    /**
     * 服务承诺--修改
     * @param param
     * @return JsonResult
     */
    @PostMapping(value = "/pledge/updateInfo")
    public JsonResult updatePledge(@RequestBody @Validated({UpdateGroup.class}) PledgeParam param){
        shop().shopBasicConfig.shopPledge.updatePledge(param);
        return success();
    }
    /**
     * 服务承诺--开启/关闭服务(单个)
     * @param param
     * @return JsonResult
     */
    @PostMapping(value = "/pledge/updateState")
    public JsonResult updatePledgeState(
            @RequestBody @Validated({PledgeStateUpdateGroup.class}) PledgeStateUpdateParam param){
        shop().shopBasicConfig.shopPledge.updatePledgeState(param);
        return success();
    }
    /**
     * 服务承诺--删除(逻辑删除)
     * @param param
     * @return JsonResult
     */
    @PostMapping(value = "/pledge/delete")
    public JsonResult deletePledge(
            @RequestBody @Validated PledgeStateUpdateParam param){
        shop().shopBasicConfig.shopPledge.deletePledgeState(param);
        return success();
    }
    /**
     * 服务承诺--开启/关闭服务(总)
     * @return JsonResult
     */
    @GetMapping(value = "/pledge/updateTotalSwitch")
    public JsonResult closePledge(String state){
        shop().config.pledgeCfg.setPledgeConfig(state);
        return success();
    }
    
    
    
	/**
	 * 基础配置 / 权限组管理/ 添加权限组
	 * 
	 * @return
	 */
	@PostMapping(value = "/role/add")
	public JsonResult addRole(@RequestBody ShopRoleParam param) {
		if (!StringUtils.isEmpty(param.getPrivilegePass())) {
			if (StringUtils.isEmpty(param.getLoginPass()) || StringUtils.isEmpty(param.getRolePass())) {
				// 请输入密码
				return fail(JsonResultCode.CODE_MSG_ACCOUNT_PASSWD_NOT_NULL);
			}
			// 验证登陆密码
			ShopAccountRecord shopRecord = saas.shop.accout.verify(adminAuth.user().getUserName(),
					param.getLoginPass());
			if (shopRecord == null) {
				// 管理员登陆密码错误
				return fail(JsonResultCode.CODE_ACCOUNT_PASSWD_ERROR);
			}
		}
		int num = saas.shop.role.insertRole(param, adminAuth.user());
		if (num != 1) {
			return fail(JsonResultCode.CODE_FAIL);
		}
		return success(JsonResultCode.CODE_SUCCESS);
	}

	/**
	 * 基础配置 / 权限组管理/ 添加权限组 ->查询
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/role/group/list")
	public JsonResult listRole() {
		return success(saas.shop.role.getInfo(adminAuth.user().getSysId()));
	}

	/**
	 * 基础配置 / 权限组管理/ 添加权限组 ->删除
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/role/group/del")
	public JsonResult delRole(@RequestBody ShopRoleDelParam param) {
		ShopRoleRecord sRecord = saas.shop.role.getRoleByIdAndSysId(param.getRoleId(), adminAuth.user().getSysId());
		if (sRecord == null) {
			// 您没有权限操作此角色
			return fail(JsonResultCode.CODE_ACCOUNT_SHOP_ROLE_INSUFFICIENT);
		}
		// 是否有子账户在用这个角色
		ShopChildRoleRecord sChildRecord = saas.shop.subAccount.checkByRecode(param.getRoleId(), adminAuth.user());
		if (sChildRecord != null) {
			// 有子账户正在使用此角色，请修改后再删除
			return fail(JsonResultCode.CODE_ACCOUNT_SHOP_ROLE_OCCUPY);
		}
		int delNum = saas.shop.role.deleteById(param.getRoleId(), adminAuth.user().getSysId(),
				adminAuth.user().getLoginShopId());
		if (delNum != 1) {
			// 删除失败
			return fail(JsonResultCode.CODE_FAIL);
		}
		return success(JsonResultCode.CODE_SUCCESS);
	}

	
	/**
	 * 基础配置 / 权限组管理/ 添加权限组 ->编辑，先返回角色对应的权限
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/role/group/editView")
	public JsonResult editRole(@RequestBody ShopRoleDelParam param) {
		ShopRoleRecord sRecord = saas.shop.role.getRoleByIdAndSysId(param.getRoleId(), adminAuth.user().getSysId());
		if (sRecord == null) {
			// 您没有权限操作此角色
			return fail(JsonResultCode.CODE_ACCOUNT_SHOP_ROLE_INSUFFICIENT);
		}
		ShopRoleParam vo=new ShopRoleParam();
		vo.setRoleName(sRecord.getRoleName());
		vo.setPrivilegeList(Util.parseJson(sRecord.getPrivilegeList(), List.class));
		vo.setPrivilegePass(Util.parseJson(sRecord.getPrivilegePass(), List.class));
		return success(vo);
	}
	
	/**
	 * 基础配置 / 权限组管理/ 添加权限组 ->编辑，编辑后提交
	 * @param upParam
	 * @return
	 */
	@PostMapping(value = "/role/group/editUpdate")
	public JsonResult updateRole(@RequestBody ShopRoleUpdateParam upParam) {
		if (!StringUtils.isEmpty(upParam.getPrivilegePass())) {
			if (StringUtils.isEmpty(upParam.getLoginPass()) || StringUtils.isEmpty(upParam.getRolePass())) {
				// 请输入密码
				return fail(JsonResultCode.CODE_MSG_ACCOUNT_PASSWD_NOT_NULL);
			}
			// 验证登陆密码
			ShopAccountRecord shopRecord = saas.shop.accout.verify(adminAuth.user().getUserName(),
					upParam.getLoginPass());
			if (shopRecord == null) {
				// 管理员登陆密码错误
				return fail(JsonResultCode.CODE_ACCOUNT_PASSWD_ERROR);
			}
		}
		ShopRoleRecord sRecord = saas.shop.role.getRoleByIdAndSysId(upParam.getRoleId(), adminAuth.user().getSysId());
		if (sRecord == null) {
			// 您没有权限操作此角色
			return fail(JsonResultCode.CODE_ACCOUNT_SHOP_ROLE_INSUFFICIENT);
		}
		int updateNum=saas.shop.role.updateRole(upParam, adminAuth.user());
		if(updateNum!=1) {
			//更新失败
			return fail(JsonResultCode.CODE_FAIL);
		}
		return success(JsonResultCode.CODE_SUCCESS);
	}
	
	/**
	 * 店铺基础配置-取店铺通用设置信息
	 * @return
	 */
	@GetMapping(value = "/shop/common/get")
	public JsonResult getShopCommonInfo()   {
		return success(shop().config.shopCommonConfigService.getShopCommonCfg());
	}
	
	/**
	 * 店铺基础配置-取店铺基础信息
	 * @return
	 */
	@GetMapping(value = "/shop/base/get")
	public JsonResult getShopBaseInfo() {
		ShopBaseConfig shopBaseCfgInfo = new ShopBaseConfig();
		ShopPojo shop = saas.shop.getShopBaseInfoById(this.shopId());
		shopBaseCfgInfo.setExpireTime(saas.shop.renew.getShopRenewExpireTime(this.shopId()));
		shopBaseCfgInfo.setShopName(shop.getShopName());
		shopBaseCfgInfo.setShopAvatar(shop.getShopAvatar());
		shopBaseCfgInfo.setCreated(shop.getCreated());
		shopBaseCfgInfo.setBusinessState(shop.getBusinessState());
		return this.success(shopBaseCfgInfo);
	}
	
	/**
	 * 店铺基础配置-店铺基础信息更新
	 * @return
	 */
	@PostMapping(value = "/shop/base/update")
	public JsonResult updateShopBaseInfo(@RequestBody @Valid ShopPojo shop) {
		shop.setShopId(this.shopId());
		Integer res = saas.shop.updateShopBaseInfo(shop);
		if(res > 0) {
			return this.success();
		}else {
			return this.fail();
		}
	}
	
	/**
	 * 店铺基础配置-店铺通用配置更新
	 * @return
	 */
	@PostMapping(value = "/shop/common/update")
	public JsonResult updateShopCommonInfo(@RequestBody @Valid ShopCommonCfgInfo commonCfg) {
		System.out.println(commonCfg);
		if(shop().config.shopCommonConfigService.updateShopCommonInfo(commonCfg)) {
			return this.success();
		}else {
			return this.fail(JsonResultCode.CODE_FAIL);
		}
	}
	

}
