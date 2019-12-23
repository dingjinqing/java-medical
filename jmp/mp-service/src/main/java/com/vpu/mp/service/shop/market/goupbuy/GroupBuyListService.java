package com.vpu.mp.service.shop.market.goupbuy;

import com.vpu.mp.db.shop.tables.records.GroupBuyDefineRecord;
import com.vpu.mp.db.shop.tables.records.GroupBuyListRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListVo;
import com.vpu.mp.service.pojo.shop.market.MarketSourceUserListParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyDetailParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyListParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyDetailListVo;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyListVo;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupOrderVo;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.market.groupbuy.GroupBuyUserInfo;
import com.vpu.mp.service.shop.goods.GoodsSpecService;
import com.vpu.mp.service.shop.member.MemberService;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyConstant.*;

/**
 * @author 孔德成
 * @date 2019/7/29 14:54
 */
@Service
public class GroupBuyListService extends ShopBaseService {




    private static final String GROUP_ORDER_NUM = "groupOrderNum";

    @Autowired
    private MemberService memberService;
    @Autowired
    private GroupBuyService groupBuyService;
    @Autowired
    private GoodsSpecService goodsSpecService;

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
                .groupBy(GROUP_BUY_LIST.ACTIVITY_ID);

        SelectConditionStep<? extends Record> records = db().select(GROUP_BUY_DEFINE.ID, GROUP_BUY_DEFINE.NAME, GOODS.GOODS_NAME, GROUP_BUY_DEFINE.ACTIVITY_TYPE,
                GROUP_BUY_DEFINE.START_TIME, GROUP_BUY_DEFINE.END_TIME, GROUP_BUY_DEFINE.STATUS, GROUP_BUY_DEFINE.LIMIT_AMOUNT,
                DSL.ifnull(table.field(GROUP_ORDER_NUM), 0).as(GROUP_ORDER_NUM))
                .from(GROUP_BUY_DEFINE)
                .leftJoin(GOODS).on(GROUP_BUY_DEFINE.GOODS_ID.eq(GOODS.GOODS_ID))
                .leftJoin(table).on(table.field(GROUP_BUY_LIST.ACTIVITY_ID).eq(GROUP_BUY_DEFINE.ID))
                .where(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        records.orderBy(GROUP_BUY_DEFINE.ID.desc());
        this.buildOptions(param, records);

        PageResult<GroupBuyListVo> page = getPageResult(records, param.getCurrentPage(), param.getPageRows(), GroupBuyListVo.class);
        page.dataList.forEach(vo -> {
            vo.setCurrentState(Util.getActStatus(vo.getStatus(), vo.getStartTime(), vo.getEndTime()));
        });
        return page;
    }

    private void buildOptions(GroupBuyListParam param, SelectConditionStep<? extends Record> records) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (param.getType() != null) {
            switch (param.getType()) {
                case BaseConstant.NAVBAR_TYPE_ONGOING:
                    //正在活动
                    records.and(GROUP_BUY_DEFINE.START_TIME.lt(timestamp))
                            .and(GROUP_BUY_DEFINE.END_TIME.gt(timestamp))
                            .and(GROUP_BUY_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL));
                    break;
                case BaseConstant.NAVBAR_TYPE_NOT_STARTED:
                    //还未开始
                    records.and(GROUP_BUY_DEFINE.START_TIME.gt(timestamp))
                            .and(GROUP_BUY_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL));
                    break;
                case BaseConstant.NAVBAR_TYPE_FINISHED:
                    //已经结束
                    records.and(GROUP_BUY_DEFINE.END_TIME.lt(timestamp))
                            .and(GROUP_BUY_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL));
                    break;
                case BaseConstant.NAVBAR_TYPE_DISABLED:
                    //停用
                    records.and(GROUP_BUY_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_DISABLE));
                    break;
                default:
            }
        }

    }


    /**
     * 拼团订单
     *
     * @return
     */
    public PageResult<MarketOrderListVo> groupBuyOrderList(MarketOrderListParam param) {
        return saas().getShopApp(getShopId()).readOrder.getMarketOrderList(param, OrderConstant.GOODS_TYPE_PIN_GROUP);

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
        SelectConditionStep<Record3<Integer, String, String>> table = db().select(GROUP_BUY_LIST.GOODS_ID, USER.MOBILE, USER.USERNAME).from(GROUP_BUY_LIST).leftJoin(USER).on(USER.USER_ID.eq(GROUP_BUY_LIST.USER_ID))
                .where(GROUP_BUY_LIST.IS_GROUPER.eq(IS_GROUPER_Y));
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
        if (param.getStatus() != null) {
            if (param.getStatus() > 0 && param.getStatus() < 3) {
                select.and(GROUP_BUY_LIST.STATUS.eq(param.getStatus()));
            }
        }

    }

    public GroupOrderVo getByOrder(String orderSn) {
        return db().select(GROUP_BUY_LIST.ID, GROUP_BUY_LIST.ACTIVITY_ID, GROUP_BUY_LIST.GOODS_ID, GROUP_BUY_LIST.GROUP_ID, GROUP_BUY_LIST.USER_ID, GROUP_BUY_LIST.IS_GROUPER, GROUP_BUY_LIST.ORDER_SN, GROUP_BUY_LIST.STATUS, GROUP_BUY_LIST.START_TIME, GROUP_BUY_LIST.END_TIME).
                from(GROUP_BUY_LIST).
                where(GROUP_BUY_LIST.ORDER_SN.eq(orderSn)).
                fetchOneInto(GroupOrderVo.class);
    }

    /**
     * 根据拼团
     *
     * @param groupId
     * @return
     */
    public GroupBuyListRecord getGroupBuyListByGroupId(Integer groupId) {
        return db().selectFrom(GROUP_BUY_LIST)
                .where(GROUP_BUY_LIST.STATUS.ge(STATUS_ONGOING))
                .and(GROUP_BUY_LIST.IS_GROUPER.eq(IS_GROUPER_Y))
                .and(GROUP_BUY_LIST.GROUP_ID.eq(groupId)).fetchOne();

    }

    /**
     * 判断是否能创建拼团订单
     *  @param userId
     * @param date
     * @param activityId
     * @param groupId
     * @return
     */
    public ResultMessage canCreatePinGroupOrder(Integer userId, Timestamp date, Integer activityId, Integer groupId, Byte isGrouper) {
        logger().debug("判断是否能创建拼团订单[activityId:{}]",activityId);
        GroupBuyDefineRecord groupBuyRecord = groupBuyService.getGroupBuyRecord(activityId);
        if (Objects.isNull(groupBuyRecord)|| DelFlag.DISABLE_VALUE.equals(groupBuyRecord.getDelFlag())){
            logger().debug("拼团活动未找到,不存在或者已删除[activityId:{}]",activityId);
            return ResultMessage.builder().jsonResultCode(JsonResultCode.GROUP_BUY_GROUPID_DOES_NOT_EXIST).flag(false).build();
        }
        if (IS_GROUPER_Y.equals(isGrouper)){
            if (groupBuyRecord.getStatus().equals(BaseConstant.ACTIVITY_STATUS_DISABLE)){
                logger().debug("该活动未启用[activityId:{}]",activityId);
                return ResultMessage.builder().jsonResultCode(JsonResultCode.GROUP_BUY_ACTIVITY_STATUS_DISABLE).build();
            }
            if (groupBuyRecord.getStartTime().compareTo(date) >0){
                logger().debug("活动未开始[activityId:{}]",activityId);
                return ResultMessage.builder().jsonResultCode(JsonResultCode.GROUP_BUY_ACTIVITY_STATUS_NOTSTART).build();
            }
            if (groupBuyRecord.getEndTime().compareTo(date)<0){
                logger().debug("活动已经结束[activityId:{}]",activityId);
                return ResultMessage.builder().jsonResultCode(JsonResultCode.GROUP_BUY_ACTIVITY_STATUS_END).build();
            }
            Integer count = db().selectCount().from(GROUP_BUY_LIST)
                    .where(GROUP_BUY_LIST.USER_ID.eq(userId))
                    .and(GROUP_BUY_LIST.ACTIVITY_ID.eq(activityId))
                    .and(GROUP_BUY_LIST.IS_GROUPER.eq(isGrouper))
                    .and(GROUP_BUY_LIST.STATUS.in(STATUS_ONGOING, STATUS_SUCCESS)).fetchOneInto(Integer.class);
            if (!groupBuyRecord.getOpenLimit().equals(OPEN_LIMIT_N.shortValue())&&groupBuyRecord.getOpenLimit()<=count){
                logger().debug("该活动开团个数已经达到上限[activityId:{}]",activityId);
                return ResultMessage.builder().jsonResultCode(JsonResultCode.GROUP_BUY_ACTIVITY_GROUP_OPEN_LIMIT_MAX).build();
            }
        }else {
            GroupBuyListRecord groupBuyListRecord = db().selectFrom(GROUP_BUY_LIST).where(GROUP_BUY_LIST.IS_GROUPER.eq(IS_GROUPER_Y))
                    .and(GROUP_BUY_LIST.GROUP_ID.eq(groupId)).fetchOne();
            if (groupBuyListRecord.getStatus().equals(STATUS_FAILED)){
                logger().debug("该团已经取消");
                return ResultMessage.builder().jsonResultCode(JsonResultCode.GROUP_BUY_ACTIVITY_GROUP_STATUS_CANCEL).build();
            }
            if (groupBuyListRecord.getStatus().equals(STATUS_SUCCESS)){
                logger().debug("该团人数已经满了");
                return ResultMessage.builder().jsonResultCode(JsonResultCode.GROUP_BUY_ACTIVITY_GROUP_EMPLOEES_MAX).build();
            }
            Integer count = db().selectCount().from(GROUP_BUY_LIST).where(GROUP_BUY_LIST.IS_GROUPER.eq(isGrouper))
                    .and(GROUP_BUY_LIST.USER_ID.eq(userId))
                    .and(GROUP_BUY_LIST.STATUS.in(STATUS_SUCCESS, STATUS_ONGOING)).fetchOneInto(Integer.class);
            if (!groupBuyRecord.getJoinLimit().equals(JOIN_LIMIT_N.shortValue())&&groupBuyRecord.getJoinLimit()<=count){
                logger().debug("该活动参团个数已经达到上限[activityId:{}]",activityId);
                return ResultMessage.builder().jsonResultCode(JsonResultCode.GROUP_BUY_ACTIVITY_GROUP_JOIN_LIMIT_MAX).build();
            }
        }
        return ResultMessage.builder().jsonResultCode(JsonResultCode.CODE_SUCCESS).flag(true).build();
    }

    /**
     * 获取用或开启的有效拼团数量 进行中或已成功
     * @param userId 用户id
     * @param activityId 活动id
     * @return 已参团的数量
     */
    public int getUserOpenGroupBuyActivityNum(Integer userId,Integer activityId){
        return db().selectCount().from(GROUP_BUY_LIST)
            .where(GROUP_BUY_LIST.USER_ID.eq(userId))
            .and(GROUP_BUY_LIST.ACTIVITY_ID.eq(activityId))
            .and(GROUP_BUY_LIST.IS_GROUPER.eq((byte) 1))
            .and(GROUP_BUY_LIST.STATUS.in(STATUS_ONGOING, STATUS_SUCCESS)).fetchOneInto(Integer.class);
    }


    /**
     * 拼团用户信息
     * @param groupId 拼团id
     * @return
     */
    public List<GroupBuyUserInfo> getPinUserList(Integer groupId){

        List<GroupBuyUserInfo> groupBuyUserInfos = db().select(GROUP_BUY_LIST.USER_ID, USER_DETAIL.USERNAME, USER_DETAIL.USER_AVATAR)
                .from(GROUP_BUY_LIST)
                .leftJoin(USER_DETAIL).on(USER_DETAIL.USER_ID.eq(GROUP_BUY_LIST.USER_ID))
                .where(GROUP_BUY_LIST.STATUS.in(STATUS_SUCCESS, STATUS_DEFAULT_SUCCESS, STATUS_ONGOING))
                .and(GROUP_BUY_LIST.GROUP_ID.eq(groupId))
                .orderBy(GROUP_BUY_LIST.IS_GROUPER.desc(), GROUP_BUY_LIST.CREATE_TIME.desc())
                .fetchInto(GroupBuyUserInfo.class);
        return groupBuyUserInfos;
    }
}
