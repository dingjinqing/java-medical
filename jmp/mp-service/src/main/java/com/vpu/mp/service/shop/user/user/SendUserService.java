package com.vpu.mp.service.shop.user.user;

import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.message.CustomRuleInfo;
import com.vpu.mp.service.pojo.shop.market.message.UserInfoQuery;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.db.shop.Tables.ORDER_INFO;
import static org.jooq.impl.DSL.*;

/**
 * 发送人群实现
 * @author 卢光耀
 * @date 2019-08-12 10:10
 *
*/
@Service
public class SendUserService extends ShopBaseService {
    private final JedisManager jedisManager;

    public SendUserService(JedisManager jedisManager){
        this.jedisManager = jedisManager;
    }

    public String getKeyWithRedisBySendUser(){
        return JedisKeyConstant.SEND_USER_KEY+getShopId()+"."+Util.randomId();
    }

    /**
     * 从redis获取数据并删除
     * @param key 标识
     * @return 从redis获取的数据
     */
    public List<Integer> getSendUserIdByRedisKey(String key){
        List<String> result =  jedisManager.getList(key);
        jedisManager.delete(key);
        return result.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    /**
     * 获取发送人群，返回存入redis的key，key是复用的
     * @param query 发送人群的筛选条件
     * @param key 调用{@link SendUserService#getKeyWithRedisBySendUser()}获得
     * @return sendUserId的size
     */
    public Integer getSendUserByQuery(UserInfoQuery query,String key){
        Set<String> userIdSet = new HashSet<>();
        if(query.getOnClickNoPay()){
            userIdSet.addAll(getSendUserByShoppingcartNoPay());
        }
        if(query.getOnClickGoods() && !query.getGoodsIdList().isEmpty()){
            userIdSet.addAll(getSendUserByGoodsIdList(query.getGoodsIdList()));
        }
        if (query.getOnClickCard() && !query.getCardIdsList().isEmpty()){
            userIdSet.addAll(getSendUserByMemberCardList(query.getCardIdsList()));
        }
        if (query.getOnClickTag() && !query.getTagIdList().isEmpty()){
            userIdSet.addAll(getSendUserByUserTagList(query.getTagIdList()));
        }
        if (query.getOnClickCustomRule()){
            CustomRuleInfo info = query.getCustomRuleInfo();
            if (info != null){
                Date today = new Date();
                if ( null != info.getPayedDay()){
                    userIdSet.addAll(getSendUserByPayedDay(info.getPayedDay(),today));
                }
                if ( null != info.getNoPayDay()){
                    userIdSet.addAll(getSendUserByNoPayDay(info.getNoPayDay(),today));
                }
                if ( null != info.getBuyTimesMore()){
                    userIdSet.addAll(getSendUserByBuyTimesMore(info.getBuyTimesMore()));
                }
                if ( null != info.getBuyTimesLess()){
                    userIdSet.addAll(getSendUserByBuyTimesLess(info.getBuyTimesLess()));
                }
                if ( null != info.getMoneyAvgMore()){
                    userIdSet.addAll(getSendUserByMoneyAvgMore(info.getMoneyAvgMore()));
                }
                if ( null != info.getMoneyAvgLess()){
                    userIdSet.addAll(getSendUserByMoneyAvgLess(info.getMoneyAvgLess()));
                }
                if ( null != info.getLoginStart() || null != info.getLoginEnd()){
                    Date min = new Date(Long.MIN_VALUE);
                    userIdSet.addAll(getSendUserByLoginTime(
                        null!=info.getLoginStart()?info.getLoginStart():new Timestamp(min.getTime()),
                        null!=info.getLoginEnd()?info.getLoginEnd():new Timestamp(today.getTime())
                    ));
                }
            }
        }
        if (!userIdSet.isEmpty()){
            jedisManager.delete(key);
            jedisManager.lpush(key,userIdSet.stream().toArray(size->new String[size]));
        }
        return jedisManager.getListSize(key).intValue();
    }

    private List<String> getSendUserByShoppingcartNoPay(){
        Date today = new Date();
        Timestamp cartDay = Util.getEarlyTimeStamp(today, -30);
        return db().select(CART.USER_ID).
            from(CART).
            where(CART.CREATE_TIME.greaterOrEqual(cartDay)).
            fetch().into(String.class);
    }
    private List<String> getSendUserByGoodsIdList(List<Integer> goodsIdList){
        return db().select(ORDER_INFO.USER_ID).
            from(ORDER_INFO).
            leftJoin(ORDER_GOODS).on(ORDER_GOODS.ORDER_ID.eq(ORDER_INFO.ORDER_ID)).
            where(ORDER_INFO.ORDER_STATUS.greaterOrEqual(OrderConstant.ORDER_CLOSED)).
            and(ORDER_GOODS.GOODS_ID.in(goodsIdList)).
            fetch().into(String.class);
    }
    private List<String> getSendUserByMemberCardList(List<Integer> cardList){
        return db().selectDistinct(USER_CARD.USER_ID).
            from(USER_CARD).
            where(USER_CARD.FLAG.eq((byte) 0)).and(USER_CARD.CARD_ID.in(cardList)).
            fetch().into(String.class);
    }
    private List<String> getSendUserByUserTagList(List<Integer> tagList){
        return db().select(USER_TAG.USER_ID).
            from(USER_TAG).
            where(USER_TAG.IS_DELETE.eq((byte) 0)).
            and(USER_TAG.TAG_ID.in(tagList)).
            fetch().into(String.class);
    }
    private List<String> getSendUserByPayedDay(Integer days,Date today){
        Timestamp havePayDay = Util.getEarlyTimeStamp(today, -days);
        return db().select(ORDER_INFO.USER_ID).from(ORDER_INFO).
            where(ORDER_INFO.ORDER_STATUS.greaterOrEqual(OrderConstant.ORDER_CLOSED)).
            and(ORDER_INFO.CREATE_TIME.greaterOrEqual(havePayDay)).
            fetch().into(String.class);
    }
    private List<String> getSendUserByNoPayDay(Integer days,Date today){
        Timestamp noPayDay = Util.getEarlyTimeStamp(today, -days);
        return db().select(USER.USER_ID).from(USER).
            where(
                notExists(
                    select(ORDER_INFO.USER_ID).from(ORDER_INFO).
                    where(ORDER_INFO.ORDER_STATUS.greaterOrEqual(OrderConstant.ORDER_CLOSED).
                        and(ORDER_INFO.CREATE_TIME.greaterOrEqual(noPayDay))))
            ).
            fetch().into(String.class);
    }
    private List<String> getSendUserByBuyTimesMore(Integer times){
        return db().select(ORDER_INFO.USER_ID).from(ORDER_INFO).
            where(ORDER_INFO.ORDER_STATUS.greaterOrEqual(OrderConstant.ORDER_CLOSED)).
            groupBy(ORDER_INFO.USER_ID).
            having(count(ORDER_INFO.ORDER_ID).greaterThan(times)).
            fetch().into(String.class);
    }
    private List<String> getSendUserByBuyTimesLess(Integer times){
        return db().select(ORDER_INFO.USER_ID).from(ORDER_INFO).
            where(ORDER_INFO.ORDER_STATUS.greaterOrEqual(OrderConstant.ORDER_CLOSED)).
            groupBy(ORDER_INFO.USER_ID).
            having(count(ORDER_INFO.ORDER_ID).lessThan(times)).
            fetch().into(String.class);
    }
    private List<String> getSendUserByMoneyAvgMore(BigDecimal money){
        return db().select(ORDER_INFO.USER_ID).from(ORDER_INFO).
            where(ORDER_INFO.ORDER_STATUS.greaterOrEqual(OrderConstant.ORDER_CLOSED)).
            groupBy(ORDER_INFO.USER_ID).
            having((sum(ORDER_INFO.ORDER_AMOUNT).divide(sum(ORDER_INFO.GOODS_AMOUNT))).greaterThan(money)).
            fetch().into(String.class);
    }
    private List<String> getSendUserByMoneyAvgLess(BigDecimal money){
        return db().select(ORDER_INFO.USER_ID).from(ORDER_INFO).
            where(ORDER_INFO.ORDER_STATUS.greaterOrEqual(OrderConstant.ORDER_CLOSED)).
            groupBy(ORDER_INFO.USER_ID).
            having((sum(ORDER_INFO.ORDER_AMOUNT).divide(sum(ORDER_INFO.GOODS_AMOUNT))).lessThan(money)).
            fetch().into(String.class);
    }
    private List<String> getSendUserByLoginTime(Timestamp start,Timestamp end){
        return db().select(USER_LOGIN_RECORD.USER_ID).from(USER_LOGIN_RECORD)
            .where(USER_LOGIN_RECORD.CREATE_TIME.between(start,end))
            .fetch().into(String.class);
    }







}
