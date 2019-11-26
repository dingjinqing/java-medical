package com.vpu.mp.controller.admin;

import java.util.List;

import javax.validation.Valid;

import com.vpu.mp.service.foundation.data.BaseConstant;
import org.jooq.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.shop.tables.records.LotteryPrizeRecord;
import com.vpu.mp.db.shop.tables.records.LotteryRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.MarketSourceUserListParam;
import com.vpu.mp.service.pojo.shop.market.lottery.JoinLotteryParam;
import com.vpu.mp.service.pojo.shop.market.lottery.LotteryByIdParam;
import com.vpu.mp.service.pojo.shop.market.lottery.LotteryPageListParam;
import com.vpu.mp.service.pojo.shop.market.lottery.LotteryPageListVo;
import com.vpu.mp.service.pojo.shop.market.lottery.LotteryParam;
import com.vpu.mp.service.pojo.shop.market.lottery.LotteryVo;
import com.vpu.mp.service.pojo.shop.market.lottery.prize.LotteryPrizeVo;
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
     * 获取可用的抽奖活动列表
     * @return
     */
    @PostMapping("/usableList")
    public JsonResult getLotteryUsableAllList(){
        LotteryPageListParam param = new LotteryPageListParam();
        param.setState(BaseConstant.NAVBAR_TYPE_ONGOING);
        param.setPageRows(Integer.MAX_VALUE);
        PageResult<LotteryPageListVo> result = shop().lottery.getLotteryList(param);
        return success(result);
    }


    /**
     * 新增抽奖活动
     * @param param param
     * @return json
     */
    @PostMapping("/add")
    public JsonResult addLottery(@RequestBody @Valid  LotteryParam param){
        Integer integer = shop().lottery.addLottery(param);
        if (integer<1){
            return fail();
        }
        return success();
    }

    /**
     * 更新抽奖活动
     * @param param param
     * @return json
     */
    @PostMapping("/update")
    public JsonResult updateLottery(@RequestBody @Valid LotteryParam param){
        Integer flag= shop().lottery.updateLottery(param);
        if (flag<1){
            return fail();
        }
        return success();
    }

    /**
     * 改变状态
     * @param param param
     * @return json
     */
    @PostMapping("/change")
    public JsonResult closeAndRestartById(@RequestBody LotteryByIdParam param){
        Integer flag = shop().lottery.closeAndRestartById(param.getId());
        if (flag<1){
            return fail();
        }
        return success();
    }

    @PostMapping("/delete")
    public JsonResult deleteLottery(@RequestBody LotteryByIdParam param){
        Integer flag = shop().lottery.deleteLottery(param.getId());
        if (flag<1){
            return fail();
        }
        return success();
    }

    /**
     * 获取单个抽象活动信息
     * @param param param
     * @return json
     */
    @PostMapping("/get")
    public JsonResult getLotteryById(@RequestBody LotteryByIdParam param){
        LotteryRecord lottery = shop().lottery.getLotteryById(param.getId());
        Result<LotteryPrizeRecord> lotteryPrizeList = shop().lottery.getLotteryPrizeById(param.getId());
        LotteryVo lotteryVo =lottery.into(LotteryVo.class);
        List<LotteryPrizeVo>  lotteryPrizeVoList =lotteryPrizeList.into(LotteryPrizeVo.class);
        lotteryVo.setPrizeList(lotteryPrizeVoList);
        return success(lotteryVo);
    }

    /**
     * 查询抽奖活动的记录
     * @param param param
     * @return json
     */
    @PostMapping("/record/list")
    public JsonResult getLotteryRecordList(@RequestBody LotteryRecordPageListParam param){
        PageResult<LotteryRecordPageListVo> result = shop().lottery.getLotteryRecordList(param);
        return success(result);
    }

    /**
     *  查询抽奖用户列表
     * @return json
     */
    @PostMapping("/user/list")
    public JsonResult getLotteryUserList(@RequestBody MarketSourceUserListParam param){
        return success(shop().lottery.getLotteryUserList(param));
    }

    /**
     * 抽奖模拟
     *
     * @param param JoinLotteryParam
     * @return json
     */
    @PostMapping("/join")
    public JsonResult joinLottery(JoinLotteryParam param){
        return success(shop().lottery.joinLottery(param));
    }
}
