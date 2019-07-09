package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.pojo.shop.config.pledge.*;
import com.vpu.mp.service.pojo.shop.config.pledge.group.PledgeStateUpdateGroup;
import com.vpu.mp.service.pojo.shop.config.pledge.group.UpdateGroup;
import com.vpu.mp.service.shop.config.PledgeConfigService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @return
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
     * @return
     */
    @PostMapping(value = "/pledge/add")
    public JsonResult insertPledge(@RequestBody @Validated PledgeParam param,BindingResult result){
        if (result.hasErrors()) {
            return this.fail(result.getFieldError().getDefaultMessage());
        }
        boolean canInsert = shop().shopBasicConfig.shopPledge.judgeInsertParam();
        if ( canInsert ){
            shop().shopBasicConfig.shopPledge.insertPledge(param);
            return success();
        }else{
            return fail(JsonResultCode.CODE_CONFIG_PLEDGE_EXCEED);
        }
    }
    /**
     * 服务承诺--新增
     * @param param
     * @return
     */
    @PostMapping(value = "/pledge/updateInfo")
    public JsonResult updatePledge(@RequestBody @Validated({UpdateGroup.class}) PledgeParam param
            ,BindingResult result){
        if (result.hasErrors()) {
            return this.fail(result.getFieldError().getDefaultMessage());
        }
        shop().shopBasicConfig.shopPledge.updatePledge(param);
        return success();
    }
    /**
     * 服务承诺--开启/关闭服务(单个)
     * @param param
     * @return
     */
    @PostMapping(value = "/pledge/updateState")
    public JsonResult updatePledgeState(
            @RequestBody @Validated({PledgeStateUpdateGroup.class}) PledgeStateUpdateParam param
            ,BindingResult result){
        if (result.hasErrors()) {
            return this.fail(result.getFieldError().getDefaultMessage());
        }
        shop().shopBasicConfig.shopPledge.updatePledgeState(param);
        return success();
    }
    /**
     * 服务承诺--删除(逻辑删除)
     * @param param
     * @return
     */
    @PostMapping(value = "/pledge/del")
    public JsonResult deletePledge(
            @RequestBody @Validated PledgeStateUpdateParam param
            ,BindingResult result){
        if (result.hasErrors()) {
            return this.fail(result.getFieldError().getDefaultMessage());
        }
        shop().shopBasicConfig.shopPledge.deletePledgeState(param);
        return success();
    }
    /**
     * 服务承诺--开启/关闭服务(总)
     * @return
     */
    @GetMapping(value = "/pledge/close")
    public JsonResult closePledge(){
        shop().config.pledgeCfg.setPledgeConfig(PledgeConfigService.V_PLEDGE);
        return success();
    }
}
