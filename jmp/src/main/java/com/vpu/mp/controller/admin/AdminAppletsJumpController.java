package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.applets.AppletsJumpAddPrarm;
import com.vpu.mp.service.pojo.shop.applets.AppletsJumpUpdatePrarm;
import com.vpu.mp.service.pojo.shop.applets.AppletsJumpVo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 小程序跳转
 * @author 孔德成
 * @date 2019/7/11 16:51
 */
@RestController
@RequestMapping("/api")
public class AdminAppletsJumpController  extends AdminBaseController {

    /**
     * 小程序跳转列表
     * @return
     */
    @GetMapping("/admin/applets/jump/list")
    public JsonResult getAppletsJump(){
        List<AppletsJumpVo> appletsJump = shop().appletsJump.getAppletsJump();
        return success(appletsJump);
    }

    /**
     * 提交版本申请
     *
     * @return
     */
    @PostMapping("/admin/applets/jump/version")
    public JsonResult mpJumpAddVersion(){
        Boolean usable =  shop().appletsJump.usableAppletsJumpValid();
        if (!usable){
            //不能申请，原因：最新的都可用
            return fail();
        }
        //获取最新的一跳记录
        Integer count  =shop().appletsJump.getAppletsJumpAddVersion();
        if (count>0){
            //已申请,不用重复申请，请等待此版本审核结果
            return fail();
        }
        shop().appletsJump.appletsJumpAddVersion();
        return success();
    }

    /**
     * 添加小程序跳转
     * @param param
     * @return
     */
    @PostMapping("/admin/applets/jump/add")
    public JsonResult addAppletsJump(@RequestBody @Valid AppletsJumpAddPrarm param){
        shop().appletsJump.addAppletsJump(param);
        return success();
    }


    /**
     * 删除小程序跳转
     * @param param
     * @return
     */
    @PostMapping("/admin/applets/jump/delete")
    public JsonResult deleteAppletsJump(@RequestBody @Valid AppletsJumpUpdatePrarm param){
        shop().appletsJump.deleteAppletsJump(param);
        return success();
    }

    /**
     * 停用或启用小程序跳转
     * @return
     */
    @PostMapping("/admin/applets/jump/update")
    public JsonResult updateProgramJump(@RequestBody @Valid AppletsJumpUpdatePrarm param){
        shop().appletsJump.updateProgramJump(param);
        return success();
    }
}
