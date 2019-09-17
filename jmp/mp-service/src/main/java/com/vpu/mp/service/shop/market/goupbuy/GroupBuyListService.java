package com.vpu.mp.service.shop.market.goupbuy;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListVo;
import com.vpu.mp.service.pojo.shop.market.MarketSourceUserListParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyDetailParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyListParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyDetailListVo;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyListVo;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.service.shop.order.OrderReadService;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.db.shop.Tables.GROUP_BUY_LIST;

/**
 * @author 孔德成
 * @date 2019/7/29 14:54
 */
@Service
public class GroupBuyListService  extends ShopBaseService {

    private static final Byte USE_STATUS = 1;
    private static final Byte STOP_STATUS = 0;
    private static final String GROUP_ORDER_NUM = "groupOrderNum";

    @Autowired
    private MemberService memberService;


    /**
     * 查询团购列表
     *
     * @param param
     * @return
     */
    public PageResult<GroupBuyListVo> getListGroupBuy(GroupBuyListParam param) {
        //子查询
        SelectHavingStep<Record2<Integer, Integer>> table = db()
                .select(GROUP_BUY_LIST.ACTIVITY_ID, DSL.count(GROUP_BUY_LIST.ID).as(GROUP_ORDER_NUM))
                .from(GROUP_BUY_LIST)
                .where(GROUP_BUY_LIST.STATUS.eq(USE_STATUS))
                .groupBy(GROUP_BUY_LIST.ACTIVITY_ID);

        SelectConditionStep<? extends Record> records = db().select(GROUP_BUY_DEFINE.ID, GROUP_BUY_DEFINE.NAME, GOODS.GOODS_NAME, GROUP_BUY_DEFINE.ACTIVITY_TYPE,
                GROUP_BUY_DEFINE.START_TIME, GROUP_BUY_DEFINE.END_TIME, GROUP_BUY_DEFINE.STATUS, GROUP_BUY_DEFINE.LIMIT_AMOUNT,
                DSL.ifnull(table.field(GROUP_ORDER_NUM),0).as(GROUP_ORDER_NUM))
                .from(GROUP_BUY_DEFINE)
                .leftJoin(GOODS).on(GROUP_BUY_DEFINE.GOODS_ID.eq(GOODS.GOODS_ID))
                .leftJoin(table).on(table.field(GROUP_BUY_LIST.ACTIVITY_ID).eq(GROUP_BUY_DEFINE.ID))
                .where(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        records.orderBy(GROUP_BUY_DEFINE.ID.desc());
        this.buildOptions(param, records);
        return getPageResult(records, param.getCurrentPage(), param.getPageRows(), GroupBuyListVo.class);

    }

    private void buildOptions(GroupBuyListParam param, SelectConditionStep<? extends Record> records) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (param.getType()!=null){
            switch (param.getType()) {
                case 1:
                    //全部活动
                    break;
                case 2:
                    //正在活动
                    records.and(GROUP_BUY_DEFINE.START_TIME.lt(timestamp))
                            .and(GROUP_BUY_DEFINE.END_TIME.gt(timestamp))
                            .and(GROUP_BUY_DEFINE.STATUS.eq(USE_STATUS));
                    break;
                case 3:
                    //还未开始
                    records.and(GROUP_BUY_DEFINE.START_TIME.gt(timestamp))
                            .and(GROUP_BUY_DEFINE.STATUS.eq(USE_STATUS));
                    break;
                case 4:
                    //已经结束
                    records.and(GROUP_BUY_DEFINE.END_TIME.lt(timestamp))
                            .and(GROUP_BUY_DEFINE.STATUS.eq(USE_STATUS));
                    break;
                case 5:
                    //停用
                    records.and(GROUP_BUY_DEFINE.STATUS.eq(STOP_STATUS));
                    break;
                default:
            }
        }

    }


    /**
     * 拼团订单
     *
     *
     * @return
     */
	public PageResult<MarketOrderListVo> groupBuyOrderList(MarketOrderListParam param ) {
        return saas().getShopApp(getShopId()).readOrder.getMarketOrderList(param,OrderConstant.GOODS_TYPE_PIN_GROUP);

    }


    /**
     * 活动新增用户
     *
     * @param param
     */
    public PageResult<MemberInfoVo> groupBuyNewUserList(MarketSourceUserListParam param) {
        MemberPageListParam pageListParam = new MemberPageListParam();
        pageListParam.setCurrentPage(param.getCurrentPage());
        pageListParam.setPageRows(param.getPageRows());
        pageListParam.setMobile(param.getMobile());
        pageListParam.setUsername(param.getUserName());
        pageListParam.setInviteUserName(param.getInviteUserName());
        return memberService.getSourceActList(pageListParam, MemberService.INVITE_SOURCE_GROUPBUY, param.getActivityId());
    }


    /**
     * 参团明细
     *
     * @param param
     * @return
     */
    public PageResult<GroupBuyDetailListVo> detailGroupBuyList(GroupBuyDetailParam param) {
        SelectConditionStep<Record3<Integer, String, String>> table = db().select(GROUP_BUY_LIST.GOODS_ID,USER.MOBILE,USER.USERNAME).from(GROUP_BUY_LIST).leftJoin(USER).on(USER.USER_ID.eq(GROUP_BUY_LIST.USER_ID))
                .where(GROUP_BUY_LIST.IS_GROUPER.eq((byte) 1));
        SelectConditionStep<? extends Record> select = db().select(
                GROUP_BUY_LIST.STATUS,
                GROUP_BUY_LIST.ORDER_SN,
                GROUP_BUY_LIST.START_TIME,
                GROUP_BUY_LIST.END_TIME,
                table.field(USER.MOBILE).as(GroupBuyDetailListVo.COMMANDER_MOBILE),
                table.field(USER.USERNAME).as(GroupBuyDetailListVo.COMMANDER_NAME),
                USER.USERNAME,
                USER.MOBILE,
                GROUP_BUY_DEFINE.NAME,
                GROUP_BUY_DEFINE.IS_DEFAULT,
                GROUP_BUY_DEFINE.DEL_FLAG)
                .from(GROUP_BUY_LIST)
                .leftJoin(USER).on(GROUP_BUY_LIST.USER_ID.eq(USER.USER_ID))
                .leftJoin(table).on(table.field(GROUP_BUY_LIST.GOODS_ID).eq(GROUP_BUY_LIST.GOODS_ID))
                .leftJoin(GROUP_BUY_DEFINE).on(GROUP_BUY_LIST.ACTIVITY_ID.eq(GROUP_BUY_DEFINE.ID))
                .where(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        builderQuery(select, param);

        select.orderBy(GROUP_BUY_LIST.GOODS_ID.desc(), GROUP_BUY_LIST.IS_GROUPER.desc(), GROUP_BUY_LIST.ID.desc());

        return getPageResult(select, param.getCurrentPage(), param.getPageRows(), GroupBuyDetailListVo.class);
    }

    private void builderQuery(SelectConditionStep<? extends Record> select, GroupBuyDetailParam param) {
        select.and(GROUP_BUY_LIST.ACTIVITY_ID.eq(param.getActivityId()));
        if (param.getMobile() != null && !param.getMobile().isEmpty()) {
            select.and(USER.MOBILE.eq(param.getMobile()));
        }
        if (param.getNickName() != null && !param.getNickName().isEmpty()) {
            select.and(USER.USERNAME.eq(param.getNickName()));
        }
        if (param.getStatus()!=null) {
            if (param.getStatus() > 0&&param.getStatus()<3) {
                select.and(GROUP_BUY_LIST.STATUS.eq(param.getStatus()));
            }
        }

    }

}
