package com.vpu.mp.service.shop.market.payaward;

import com.vpu.mp.db.shop.tables.records.PayAwardRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.payaward.*;
import com.vpu.mp.service.pojo.shop.market.payaward.record.PayAwardRecordListParam;
import com.vpu.mp.service.pojo.shop.market.payaward.record.PayAwardRecordListVo;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.PAY_AWARD;
import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_STATUS_NORMAL;
import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_STATUS_DISABLE;

/**
 * 支付有礼
 *
 * @author 孔德成
 * @date 2019/8/12 17:57
 * @update 2019-10-31 13:52:13
 */
@Service
public class PayAwardService extends ShopBaseService {


    @Autowired
    PayAwardRecordService payAwardRecordService;

    /**
     * 添加
     *
     * @param param 参数
     */
    public Boolean addPayAward(PayAwardParam param) {
        PayAwardRecord payAward = db().newRecord(PAY_AWARD, param);
        payAward.setAwardList(Util.toJsonNotNull(param.getAwardList()));
        payAward.setId(null);
        return payAward.insert() > 0;
    }

    /**
     * 获取当前进行的活动
     *
     * @return
     */
    public PayAwardRecord getNowPayAward() {
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        PayAwardRecord record = db().selectFrom(PAY_AWARD)
                .where(PAY_AWARD.STATUS.eq(ACTIVITY_STATUS_NORMAL))
                .and(PAY_AWARD.START_TIME.ge(nowTime))
                .and(PAY_AWARD.END_TIME.le(nowTime))
                .fetchOne();
        return record;
    }

    /**
     * 删除
     *
     * @param id 活动id
     */
    public void deletePayAward(Integer id) {
        db().update(PAY_AWARD).set(PAY_AWARD.DEL_FLAG, DelFlag.DISABLE_VALUE).where(PAY_AWARD.ID.eq(id)).execute();
    }

    /**
     * 更新
     *
     * @param param 参数
     */
    public Boolean updatePayAward(PayAwardParam param) {
        if (param.getId() != null) {
            PayAwardRecord payAward = db().newRecord(PAY_AWARD, param);
            payAward.setAwardList(Util.toJsonNotNull(param.getAwardList()));
            payAward.update();
            return true;
        }
        return false;
    }

    /**
     * 根据id获取活动
     *
     * @param id
     * @return
     */
    public PayAwardVo getPayAwardId(Integer id) {
        PayAwardRecord record = db().selectFrom(PAY_AWARD).where(PAY_AWARD.ID.eq(id)).fetchOne();
        PayAwardVo payAwardVo = record.into(PayAwardVo.class);
        if (record.getAwardList()!=null&&!record.getAwardList().isEmpty()){
            payAwardVo.setAwardContentList(Util.json2Object(record.getAwardList(), new TypeReference<List<PayAwardContentBo>>(){},false));
            payAwardVo.setAwardList(null);
        }
        return payAwardVo;
    }

    /**
     * 获取支付有礼列表
     *
     * @param param
     * @return
     */
    public PageResult<PayAwardListVo> getPayAwardList(PayAwardListParam param) {
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        SelectConditionStep<? extends Record> select = db()
                .select(PAY_AWARD.ID, PAY_AWARD.ACTIVITY_NAMES, PAY_AWARD.TIME_TYPE, PAY_AWARD.START_TIME,
                        PAY_AWARD.END_TIME, PAY_AWARD.ACT_FIRST, PAY_AWARD.STATUS, PAY_AWARD.AWARD_LIST,
                        PAY_AWARD.GOODS_AREA_TYPE,PAY_AWARD.MIN_PAY_MONEY)
                .from(PAY_AWARD)
                .where(PAY_AWARD.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
        switch (param.getNavType()) {
            case 2:
                select.and(PAY_AWARD.START_TIME.lt(nowTime))
                        .and(PAY_AWARD.END_TIME.gt(nowTime))
                        .and(PAY_AWARD.STATUS.eq(ACTIVITY_STATUS_NORMAL));
                break;
            case 3:
                select.and(PAY_AWARD.STATUS.eq(ACTIVITY_STATUS_NORMAL))
                        .and(PAY_AWARD.START_TIME.gt(nowTime));
                break;
            case 4:
                select.and(PAY_AWARD.STATUS.gt(ACTIVITY_STATUS_NORMAL))
                        .and(PAY_AWARD.END_TIME.lt(nowTime));
                break;
            case 5:
                select.and(PAY_AWARD.STATUS.eq(ACTIVITY_STATUS_DISABLE));
                break;
            default:
        }
        select.orderBy(PAY_AWARD.ACT_FIRST.desc());
        PageResult<PayAwardListVo> pageResult = getPageResult(select, param.getCurrentPage(), param.getPageRows(), PayAwardListVo.class);
        pageResult.getDataList().forEach(payAward->{
            if (payAward.getAwardList()!=null&&!payAward.getAwardList().isEmpty()){
                payAward.setAwardContentList(Util.json2Object(payAward.getAwardList(),new TypeReference<List<PayAwardContentBo>>(){},false));
                payAward.setAwardList(null);
            }
        });
        return pageResult;
    }

    /**
     * 停用启用
     *
     * @param param
     */
    public void changeStatus(PayAwardIdParam param) {
        PayAwardRecord record = db().newRecord(PAY_AWARD);
        record.setId(param.getId());
        record.refresh();
        if (ACTIVITY_STATUS_NORMAL.equals(record.getStatus())) {
            record.setStatus(ACTIVITY_STATUS_DISABLE);
        } else {
            record.setStatus(ACTIVITY_STATUS_NORMAL);
        }
        record.update();
    }

    /**
     * 支付有礼列表
     *
     * @param param
     * @return
     */
    public PageResult<PayAwardRecordListVo> getPayRewardRecordList(PayAwardRecordListParam param) {
        return payAwardRecordService.getPayRewardRecordList(param);

    }
}
