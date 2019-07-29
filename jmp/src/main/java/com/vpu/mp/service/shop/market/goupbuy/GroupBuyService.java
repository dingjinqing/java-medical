package com.vpu.mp.service.shop.market.goupbuy;


import com.vpu.mp.db.shop.tables.records.GroupBuyDefineRecord;
import com.vpu.mp.db.shop.tables.records.GroupBuyProductDefineRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.OrderConstant;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.groupbuy.*;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.service.shop.order.OrderReadService;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.TableLike;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * @author 孔德成
 * @date 2019/7/18 15:55
 */

@Service
public class GroupBuyService extends ShopBaseService {

    private static final byte USE_STATUS=1;
    private static final byte STOP_STATUS=0;
    /**
     * 订单成团人数
     */
    private static  final String GROUP_ORDER_NUM ="groupOrderNum";

    private static  final String GROUP_BUY="groupbuy";
    @Autowired
    private OrderReadService orderReadService;

    @Autowired
    private MemberService memberService;



    /**
     * 添加拼团活动
     *
     * @param groupBuy
     */
    public int addGroupBuy(GroupBuyParam groupBuy) {
        db().transaction((configuration)->{
            DSLContext db = DSL.using(configuration);

            //分享配置转json
            GroupBuyShareConfigParam a =  groupBuy.getShare();
            groupBuy.setShareConfig(Util.toJson(groupBuy.getShare()));
            groupBuy.setStatus((byte) USE_STATUS);

            //订单总库存
            groupBuy.setStock((short) 0);
            groupBuy.getProduct().forEach((product)->{
                groupBuy.setStock((short) (product.getStock()+groupBuy.getStock()));
            });
            //拼团信息
            GroupBuyDefineRecord groupBuyDefineRecord =db.newRecord(GROUP_BUY_DEFINE,groupBuy);
            groupBuyDefineRecord.insert();
            //拼团商品规格价格信息
            for (GroupBuyProductParam product :groupBuy.getProduct()){
                GroupBuyProductDefineRecord productDefineRecord =db.newRecord(GROUP_BUY_PRODUCT_DEFINE,product);
                productDefineRecord.setActivityId(groupBuyDefineRecord.getId());
                productDefineRecord.insert();
            }
        });

        return 0;
    }


    public PageResult<GroupBuyListVo> getListGroupBuy(GroupBuyListParam param) {

        //子查询
        TableLike<?> table =db()
                .select(GROUP_BUY_LIST.ACTIVITY_ID,DSL.count(GROUP_BUY_LIST.ACTIVITY_ID).as(GROUP_ORDER_NUM))
                .from(GROUP_BUY_LIST)
                .where(GROUP_BUY_LIST.STATUS.eq((byte) 1))
                .groupBy(GROUP_BUY_LIST.ACTIVITY_ID)
                .asTable();
        SelectConditionStep<? extends Record> records = db().select(GROUP_BUY_DEFINE.ID,GROUP_BUY_DEFINE.NAME, GOODS.GOODS_NAME, GROUP_BUY_DEFINE.ACTIVITY_TYPE,
                GROUP_BUY_DEFINE.START_TIME, GROUP_BUY_DEFINE.END_TIME, GROUP_BUY_DEFINE.STATUS, GROUP_BUY_DEFINE.LIMIT_AMOUNT,
                table.field(1))
                .from(GROUP_BUY_DEFINE)
                .leftJoin(GOODS).on(GROUP_BUY_DEFINE.GOODS_ID.eq(GOODS.GOODS_ID))
                .leftJoin(table).on(table.field(0,Integer.class).eq(GROUP_BUY_DEFINE.ID))
                .where(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        records.orderBy(GROUP_BUY_DEFINE.ID.desc());

        Timestamp  timestamp = new Timestamp(System.currentTimeMillis());
        switch (param.getType()){
            case 1:
                //全部活动
                break;
            case 2:
                //正在活动
                records.and(GROUP_BUY_DEFINE.START_TIME.lt(timestamp))
                        .and(GROUP_BUY_DEFINE.END_TIME.gt(timestamp))
                        .and(GROUP_BUY_DEFINE.STATUS.in((byte)1,(byte)2));
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

        return  getPageResult(records,param.getCurrentPage(),param.getPageRows(),GroupBuyListVo.class);

    }

    /**
     * 删除
     * @param id
     */
    public int deleteGroupBuy(Integer  id) {
        return  db().update(GROUP_BUY_DEFINE)
                .set(GROUP_BUY_DEFINE.DEL_FLAG, DelFlag.DISABLE.getCode())
                .where(GROUP_BUY_DEFINE.ID.eq(id))
                .execute();
    }

    /**
     * 编辑测试
     * @param param
     * @return
     */
    public int editGroupBuy(GroupBuyEditParam param) {
        param.setShareConfig(Util.toJson(param.getShare()));
       return db().update(GROUP_BUY_DEFINE)
                .set(GROUP_BUY_DEFINE.NAME,param.getName())
                .set(GROUP_BUY_DEFINE.SHARE_CONFIG,param.getShareConfig())
                .execute();

    }

    public void shareGroupBuy() {
    }

    /**
     * 停用或者启用
     * @param param
     */
    public void stopGroupBuy(GroupBuyIdParam param) {
        if (param.getStatus()==STOP_STATUS){
            db().update(GROUP_BUY_DEFINE)
                    .set(GROUP_BUY_DEFINE.STATUS,USE_STATUS)
                    .where(GROUP_BUY_DEFINE.ID.eq(param.getId()))
                    .and(GROUP_BUY_DEFINE.STATUS.eq(USE_STATUS))
                    .execute();
        }else if (param.getStatus()==USE_STATUS){
            db().update(GROUP_BUY_DEFINE)
                    .set(GROUP_BUY_DEFINE.STATUS, STOP_STATUS)
                    .where(GROUP_BUY_DEFINE.ID.eq(param.getId()))
                    .and(GROUP_BUY_DEFINE.STATUS.eq(STOP_STATUS))
                    .execute();
        }

    }


    /**
     * 查询拼团详情
     * @param id
     * @return
     */
    public GroupBuyDetailVo detailGroupBuy(Integer id) {
        System.out.println(getShopId()+"");
        logger().debug(getShopId()+"---------------");
//        switchShopDb(getShopId());
        Record record= db().select(GROUP_BUY_DEFINE.asterisk()).from(GROUP_BUY_DEFINE).where(GROUP_BUY_DEFINE.ID.eq(id)
                        .and(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
                        .and(GROUP_BUY_DEFINE.STATUS.eq(USE_STATUS))
                        ).fetchOne();
        if (record==null){
            return null;
        }
        GroupBuyDetailVo groupBuy =record.into(GroupBuyDetailVo.class);
        List<GroupBuyProductVo> products=db().fetch(GROUP_BUY_PRODUCT_DEFINE,
                GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID.eq(id)
                ).into(GroupBuyProductVo.class);
        groupBuy.setProduct(products);
        groupBuy.setShare(Util.parseJson(groupBuy.getShareConfig(),GroupBuyShareConfigVo.class));
        groupBuy.setShareConfig(null);
        return groupBuy;
    }

    /**
     * 校验商品是否有叠加
     * @param parm
     * @return 0
     */
    public Boolean validGroupGoods(GroupBuyParam parm) {
         return db().fetchCount(GROUP_BUY_DEFINE,
                GROUP_BUY_DEFINE.GOODS_ID.eq(parm.getGoodsId())
                        .and(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
                        .and(GROUP_BUY_DEFINE.STATUS.eq(USE_STATUS))
                        .and(GROUP_BUY_DEFINE.ID.notEqual(parm.getGoodsId()))
                        .and(GROUP_BUY_DEFINE.START_TIME.lt(parm.getEndTime()))
                        .and(GROUP_BUY_DEFINE.END_TIME.gt(parm.getStartTime())))== 0;

    }

    /**
     * 参团明细
     * @param param
     * @return
     */
    public PageResult<GroupBuyDetailListVo> detailGroupBuyList(GroupBuyDetailParam param) {
        SelectConditionStep<Record> select = db().select(GROUP_BUY_LIST.asterisk(),GROUP_BUY_DEFINE.NAME,USER.USERNAME,USER.MOBILE,GROUP_BUY_DEFINE.DEL_FLAG)
                .from(GROUP_BUY_LIST)
                .leftJoin(USER).on(GROUP_BUY_LIST.USER_ID.eq(USER.USER_ID))
                .leftJoin(GROUP_BUY_DEFINE).on(GROUP_BUY_LIST.ACTIVITY_ID.eq(GROUP_BUY_DEFINE.ID))
                .where(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        builderQuery(select,param);

        select.orderBy(GROUP_BUY_LIST.GOODS_ID.desc(),GROUP_BUY_LIST.IS_GROUPER.desc(),GROUP_BUY_LIST.ID.desc());

        return getPageResult(select,param.getPage().getCurrentPage(),param.getPage().getPageRows(),GroupBuyDetailListVo.class);
    }

    private void builderQuery(SelectConditionStep<Record> select, GroupBuyDetailParam param){
        if (param.getMobile()!=null&&!param.getMobile().isEmpty()){
            select.and(USER.MOBILE.eq(param.getMobile()));
        }
        if (param.getNickName()!=null&&!param.getNickName().isEmpty()){
            select.and(USER.USERNAME.eq(param.getNickName()));
        }
        if (param.getStatus()<0){
            select.and(GROUP_BUY_LIST.STATUS.le((byte) 0));
        }else {
            select.and(GROUP_BUY_LIST.STATUS.eq(param.getStatus()));
        }

    }

    /**
     * 拼团订单
     *
     *
     * @return
     */
    public PageResult<OrderListInfoVo> groupBuyOrderList(GroupBuyOrderListParam param ) {
        OrderPageListQueryParam orderParam =new OrderPageListQueryParam();
        orderParam.setPage(param.getPage());

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
     * @param param
     */
    public PageResult<MemberInfoVo> groupBuyNewUaerList(GroupBuyMenberParam param) {
        MemberPageListParam pageListParam =new MemberPageListParam();
        pageListParam.setPage(param.getPage());

        pageListParam.setMobile(param.getMobile());
        pageListParam.setUsername(param.getUserName());
        pageListParam.setInviteUserName(param.getInviteUserName());

        return memberService.getSourceActList(pageListParam,GROUP_BUY,param.getActivityId());
    }
}
