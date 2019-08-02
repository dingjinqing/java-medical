package com.vpu.mp.service.shop.market.goupbuy;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.OrderConstant;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyDetailParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyListParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyMenberParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyOrderListParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyDetailListVo;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyListVo;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.service.shop.order.OrderReadService;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.TableLike;
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
    private static final String GROUP_BUY = "groupbuy";

    @Autowired
    private OrderReadService orderReadService;
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
        TableLike<?> table = db()
                .select(GROUP_BUY_LIST.ACTIVITY_ID, DSL.count(GROUP_BUY_LIST.ACTIVITY_ID).as(GROUP_ORDER_NUM))
                .from(GROUP_BUY_LIST)
                .where(GROUP_BUY_LIST.STATUS.eq(USE_STATUS))
                .groupBy(GROUP_BUY_LIST.ACTIVITY_ID)
                .asTable("a");
        SelectConditionStep<? extends Record> records = db().select(GROUP_BUY_DEFINE.ID, GROUP_BUY_DEFINE.NAME, GOODS.GOODS_NAME, GROUP_BUY_DEFINE.ACTIVITY_TYPE,
                GROUP_BUY_DEFINE.START_TIME, GROUP_BUY_DEFINE.END_TIME, GROUP_BUY_DEFINE.STATUS, GROUP_BUY_DEFINE.LIMIT_AMOUNT,
                table.field(1))
                .from(GROUP_BUY_DEFINE)
                .leftJoin(GOODS).on(GROUP_BUY_DEFINE.GOODS_ID.eq(GOODS.GOODS_ID))
                .leftJoin(table).on(table.field(0, Integer.class).eq(GROUP_BUY_DEFINE.ID))
                .where(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        records.orderBy(GROUP_BUY_DEFINE.ID.desc());
        this.buildOptions(param, records);
        return getPageResult(records, param.getCurrentPage(), param.getPageRows(), GroupBuyListVo.class);

    }

    private void buildOptions(GroupBuyListParam param, SelectConditionStep<? extends Record> records) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        switch (param.getType()) {
            case 1:
                //全部活动
                break;
            case 2:
                //正在活动
                records.and(GROUP_BUY_DEFINE.START_TIME.lt(timestamp))
                        .and(GROUP_BUY_DEFINE.END_TIME.gt(timestamp))
                        .and(GROUP_BUY_DEFINE.STATUS.in((byte) 1, (byte) 2));
                break;
            case 3:
                //还未开始
                records.and(GROUP_BUY_DEFINE.START_TIME.gt(timestamp))
                        .and(GROUP_BUY_DEFINE.STATUS.eq(STOP_STATUS));
                break;
            case 4:
                //已经结束
                records.and(GROUP_BUY_DEFINE.END_TIME.lt(timestamp))
                        .and(GROUP_BUY_DEFINE.STATUS.lt(USE_STATUS));
                break;
            case 5:
                //停用
                records.and(GROUP_BUY_DEFINE.STATUS.eq(USE_STATUS));
                break;
            default:
        }


    }


    /**
     * 拼团订单
     *
     *
     * @return
     */
    @SuppressWarnings("unchecked")
	public PageResult<OrderListInfoVo> groupBuyOrderList(GroupBuyOrderListParam param ) {
        OrderPageListQueryParam orderParam =new OrderPageListQueryParam();
        orderParam.setCurrentPage(param.getCurrentPage());
        orderParam.setPageRows(param.getPageRows());
        orderParam.setGoodsType(OrderConstant.GOODS_TYPE_PIN_GROUP);
        orderParam.setGoodsName(param.getGoodsName());
        orderParam.setOrderSn(param.getOrderSn());
        orderParam.setOrderStatus(param.getOrderStatus());

        orderParam.setMobile(param.getMobile());
        orderParam.setConsignee(param.getConsignee());
        orderParam.setCreateTimeStart(param.getCreateTimeStart());
        orderParam.setCreateTimeEnd(param.getCreateTimeEnd());

        orderParam.setCountryCode(param.getCountryCode());
        orderParam.setProvinceCode(param.getProvinceCode());
        orderParam.setCityCode(param.getCityCode());
        orderParam.setDistrictCode(param.getDistrictCode());

        PageResult<OrderListInfoVo> pageList = (PageResult<OrderListInfoVo>) orderReadService.getPageList(orderParam);

        pageList.getDataList().forEach(data->{
            data.setChildOrders(null);
            data.setGoods(null);
        });

        return pageList;

    }


    /**
     * 活动新增用户
     *
     * @param param
     */
    public PageResult<MemberInfoVo> groupBuyNewUaerList(GroupBuyMenberParam param) {
        MemberPageListParam pageListParam = new MemberPageListParam();
        pageListParam.setCurrentPage(pageListParam.getCurrentPage());
        pageListParam.setPageRows(pageListParam.getPageRows());
        pageListParam.setPageRows(1);
        pageListParam.setCurrentPage(2);
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
        SelectConditionStep<Record> select = db().select(GROUP_BUY_LIST.asterisk(), GROUP_BUY_DEFINE.NAME, USER.USERNAME, USER.MOBILE, GROUP_BUY_DEFINE.DEL_FLAG)
                .from(GROUP_BUY_LIST)
                .leftJoin(USER).on(GROUP_BUY_LIST.USER_ID.eq(USER.USER_ID))
                .leftJoin(GROUP_BUY_DEFINE).on(GROUP_BUY_LIST.ACTIVITY_ID.eq(GROUP_BUY_DEFINE.ID))
                .where(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        builderQuery(select, param);

        select.orderBy(GROUP_BUY_LIST.GOODS_ID.desc(), GROUP_BUY_LIST.IS_GROUPER.desc(), GROUP_BUY_LIST.ID.desc());

        return getPageResult(select, param.getCurrentPage(), param.getPageRows(), GroupBuyDetailListVo.class);
    }

    private void builderQuery(SelectConditionStep<Record> select, GroupBuyDetailParam param) {
        if (param.getMobile() != null && !param.getMobile().isEmpty()) {
            select.and(USER.MOBILE.eq(param.getMobile()));
        }
        if (param.getNickName() != null && !param.getNickName().isEmpty()) {
            select.and(USER.USERNAME.eq(param.getNickName()));
        }
        if (param.getStatus() < 0) {
            select.and(GROUP_BUY_LIST.STATUS.le((byte) 0));
        } else {
            select.and(GROUP_BUY_LIST.STATUS.eq(param.getStatus()));
        }

    }

}
