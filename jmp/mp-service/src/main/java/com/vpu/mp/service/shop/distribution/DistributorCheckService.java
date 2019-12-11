package com.vpu.mp.service.shop.distribution;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.distribution.DistributorCheckListParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributorCheckListVo;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * 分销员审核service
 * @author 常乐
 * 2019年9月20日
 */
@Service
public class DistributorCheckService extends ShopBaseService{

    /**
     * 分销员审核列表
     * @param param
     * @return
     */
	public PageResult<DistributorCheckListVo> getDistributorCheckList(DistributorCheckListParam param) {
        SelectConditionStep<Record> select = db().select(DISTRIBUTOR_APPLY.fields()).select(USER.USERNAME, USER.MOBILE, USER.IS_DISTRIBUTOR, USER.INVITE_ID,
            USER_DETAIL.REAL_NAME,USER_DETAIL.SEX,USER_DETAIL.BIRTHDAY_YEAR,USER_DETAIL.BIRTHDAY_MONTH,USER_DETAIL.BIRTHDAY_DAY,USER_DETAIL.MARITAL_STATUS,
            USER_DETAIL.CID,USER_DETAIL.EDUCATION,USER_DETAIL.INDUSTRY_INFO)
            .from(DISTRIBUTOR_APPLY.leftJoin(USER).on(DISTRIBUTOR_APPLY.USER_ID.eq(USER.USER_ID))
                .leftJoin(USER_DETAIL).on(USER.USER_ID.eq(USER_DETAIL.USER_ID))).where(DSL.trueCondition());
        buildOptions(select,param);
        PageResult<DistributorCheckListVo> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), DistributorCheckListVo.class);
        return pageResult;
	}


    /**
     * 分销员列表审核列表条件查询
     * @param select
     * @param param
     */
	public void buildOptions(SelectConditionStep<Record> select,DistributorCheckListParam param){
        if(isNotEmpty(param.getMobile())){
            select.and(USER.MOBILE.eq(param.getMobile()));
        }
        if(isNotEmpty(param.getUsername())){
            select.and(USER.USERNAME.contains(param.getUsername()));
        }
        if(param.getStartTime() !=null){
            select.and(DISTRIBUTOR_APPLY.CREATE_TIME.le(param.getStartTime()));
        }
        if(param.getEndTime() != null){
            select.and(DISTRIBUTOR_APPLY.CREATE_TIME.ge(param.getEndTime()));
        }
        select.and(DISTRIBUTOR_APPLY.STATUS.eq(param.getNav()));
        select.orderBy(DISTRIBUTOR_APPLY.CREATE_TIME.desc());
    }

    /**
     * Distribution review timeout integer.分销审核超过N天未处理数量
     *
     * @param nDays the n days
     * @return the integer
     */
    public Integer distributionReviewTimeout(Integer nDays) {
        return db().fetchCount(DISTRIBUTOR_APPLY, DISTRIBUTOR_APPLY.CREATE_TIME.add(nDays).lessThan(Timestamp.valueOf(LocalDateTime.now())));
    }
}
