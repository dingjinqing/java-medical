package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.payaward.*;
import com.vpu.mp.service.pojo.shop.market.payaward.record.PayAwardRecordListParam;
import com.vpu.mp.service.pojo.shop.market.payaward.record.PayAwardRecordListVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author 孔德成
 * @date 2019/8/12 18:12
 */
@RestController
@RequestMapping("/api/admin/market/payaward")
public class AdminPayAwardController  extends AdminBaseController{


    /**
     *  添加支付有礼活动
     * @param param payAward 添加参数
     * @return 成功失败
     */
    @PostMapping("/add")
    public JsonResult addPayAward(@RequestBody PayAwardParam param){
        Boolean flag = shop().payAward.addPayAward(param);
        if(!flag){
            return fail();
        }
        return success();
    }


    /**
     * 删除活动
     * @return  失败信息
     */
    @PostMapping("/delete")
    public JsonResult deletePayAward(@RequestBody PayAwardIdParam param){
        shop().payAward.deletePayAward(param.getId());
        return success();
    }

    /**
     * 更新活动信息
     * @return 失败信息
     */
    @PostMapping("/update")
    public JsonResult updatePayAward(@RequestBody PayAwardParam param){
        shop().payAward.updatePayAward(param);
        return success();
    }


    /**
     * 根据id获取活动信息
     * @param param id
     * @return 活动信息
     */
    @PostMapping("/get")
    public JsonResult getPayAward(@RequestBody PayAwardIdParam param){
        PayAwardVo payAward = shop().payAward.getPayAwardId(param.getId());
        return success(payAward);
    }

    /**
     * 支付有礼列表
     * @param param 查询参数
     * @return JsonResult
     */
    @PostMapping("/list")
    public JsonResult getPayAwardList(@RequestBody PayAwardListParam param){
        PageResult<PayAwardListVo> payAwardList = shop().payAward.getPayAwardList(param);
        return success(payAwardList);
    }

    /**
     * 活动停用启用
     * @param param id
     * @return JsonResult
     */
    @PostMapping("/change/status")
    public JsonResult changeStatus(@RequestBody PayAwardIdParam param){
        shop().payAward.changeStatus(param);
        return success();
    }


    /**
     * 活动停用启用
     * @param param id
     * @return JsonResult
     */
    @PostMapping("/record/list")
    public JsonResult getPayRewardRecordList(@RequestBody @Valid PayAwardRecordListParam param){
        PageResult<PayAwardRecordListVo> payRewardRecordList = shop().payAward.getPayRewardRecordList(param);
        return success(payRewardRecordList);
    }



}
