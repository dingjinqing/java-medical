package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.MarketSourceUserListParam;
import com.vpu.mp.service.pojo.shop.market.lottery.LotteryByIdParam;
import com.vpu.mp.service.pojo.shop.market.lottery.LotteryPageListParam;
import com.vpu.mp.service.pojo.shop.market.lottery.LotteryPageListVo;
import com.vpu.mp.service.pojo.shop.market.lottery.LotteryParam;
import com.vpu.mp.service.pojo.shop.market.lottery.record.LotteryRecordPageListParam;
import com.vpu.mp.service.pojo.shop.market.lottery.record.LotteryRecordPageListVo;

/**
 * @author 孔德成
 * @date 2019/8/5 14:16
 */
@RestController
@RequestMapping("/api/admin/market/lottery")
public class AdminLotteryController extends AdminBaseController {


    /**
     *  抽奖活动列表
     * @return JsonResult
     */
    @PostMapping("/list")
    public JsonResult getLotteryList(@RequestBody LotteryPageListParam param){
        PageResult<LotteryPageListVo> result = shop().lottery.getLotteryList(param);
        return success(result);
    }


    /**
     * 新增抽奖活动
     * @param param
     * @return
     */
    @PostMapping("/add")
    public JsonResult addLottery(@RequestBody LotteryParam param){
        shop().lottery.addLottery(param);
        return success();
    }

    /**
     * 更新抽奖活动
     * @param param
     * @return
     */
    @PostMapping("/update")
    public JsonResult updateLottery(@RequestBody LotteryParam param){
        shop().lottery.updateLottery(param);
        return success();
    }

    /**
     * 改变状态
     * @param param
     * @return
     */
    @PostMapping("/change")
    public JsonResult closeAndRestartById(@RequestBody LotteryByIdParam param){
        shop().lottery.closeAndRestartById(param.getId());
        return success();
    }

    @PostMapping("/delete")
    public JsonResult deleteLottery(@RequestBody LotteryByIdParam param){
        shop().lottery.deleteLottery(param.getId());
        return success();
    }

    /**
     * 获取单个抽象活动信息
     * @param param
     * @return
     */
    @PostMapping("/get")
    public JsonResult getlotteryById(@RequestBody LotteryByIdParam param){
        LotteryPageListVo result = shop().lottery.getLotteryById(param.getId()).into(LotteryPageListVo.class);
        return success(result);
    }

    /**
     * 查询抽奖活动的记录
     * @param param
     * @return
     */
    @PostMapping("/record/list")
    public JsonResult getLotteryRecordList(@RequestBody LotteryRecordPageListParam param){
        PageResult<LotteryRecordPageListVo> result = shop().lottery.getLotteryRecordList(param);
        return success(result);
    }

    /**
     *  查询抽奖用户列表
     * @return
     */
    @PostMapping("/user/list")
    public JsonResult getLotteryUserList(@RequestBody MarketSourceUserListParam param){
        return success(shop().lottery.getLotteryUserList(param));
    }
}
