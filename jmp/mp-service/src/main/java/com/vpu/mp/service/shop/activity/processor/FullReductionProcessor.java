package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.market.fullcut.MrkingStrategyPageListQueryVo;
import com.vpu.mp.service.pojo.shop.market.fullcut.MrkingStrategyVo;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.card.ValidUserCardBean;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.fullreduce.OrderFullReduce;
import com.vpu.mp.service.shop.activity.dao.FullReductionProcessorDao;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;
import com.vpu.mp.service.shop.order.action.base.Calculate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 满折满减
 * @author 李晓冰
 * @date 2019年10月30日
 */
@Service
@Slf4j
public class FullReductionProcessor implements Processor,ActivityGoodsListProcessor, CreateOrderProcessor {

    @Autowired
    FullReductionProcessorDao fullReductionProcessorDao;
    @Autowired
    UserCardService userCard;
    @Autowired
    Calculate calculate;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_FULL_REDUCTION_PRIORITY;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_FULL_REDUCTION;
    }

    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<GoodsListMpBo> capsules, Integer userId) {
        List<GoodsListMpBo> availableCapsules = capsules.stream().filter(x -> !GoodsConstant.isGoodsTypeIn13510(x.getActivityType())).collect(Collectors.toList());

        Timestamp now =DateUtil.getLocalDateTime();
        availableCapsules.forEach(capsule->{
            boolean has = fullReductionProcessorDao.getIsFullReductionListInfo(capsule.getGoodsId(), capsule.getCatId(), capsule.getSortId(), capsule.getBrandId(), now);
            if (has) {
                GoodsActivityBaseMp activity = new GoodsActivityBaseMp();
                activity.setActivityType(BaseConstant.ACTIVITY_TYPE_FULL_REDUCTION);
                capsule.getGoodsActivities().add(activity);
            }
        });
    }

    /**订单处理start**/
    /**
     * 不可共存活动
     */
    private static List<Byte> NOT_PERMIT_ACTIVITY;
    static {
        NOT_PERMIT_ACTIVITY = Arrays.asList(
            BaseConstant.ACTIVITY_TYPE_GROUP_BUY,
            BaseConstant.ACTIVITY_TYPE_BARGAIN,
            BaseConstant.ACTIVITY_TYPE_INTEGRAL,
            BaseConstant.ACTIVITY_TYPE_GROUP_DRAW,
            BaseConstant.ACTIVITY_TYPE_SEC_KILL,
            BaseConstant.ACTIVITY_TYPE_PACKAGE_SALE,
            BaseConstant.ACTIVITY_TYPE_EXCHANG_ORDER,
            BaseConstant.ACTIVITY_TYPE_PROMOTE_ORDER,
            BaseConstant.ACTIVITY_TYPE_ASSESS_ORDER
        );
    }

    @Override
    public void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException {
        //满折满减校验（只有购物车计算此时会存在straid>0）,校验把不通过straId=0
        if(!OrderConstant.CART_Y.equals(param.getIsCart())){
            return;
        }
        List<Integer> straIds = param.getGoods().stream().filter(x -> (x.getStraId() != null && x.getStraId() > 0)).map(OrderBeforeParam.Goods::getStraId).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(straIds)) {
            return;
        }
        Map<Integer, MrkingStrategyPageListQueryVo> processingActivity = fullReductionProcessorDao.getProcessingActivity(param.getDate(), straIds.toArray(new Integer[straIds.size()])).stream().collect(Collectors.toMap(MrkingStrategyPageListQueryVo::getId, Function.identity()));

        for (OrderBeforeParam.Goods goods : param.getGoods()) {
            if(goods.getStraId() != null && goods.getStraId() > 0){
                MrkingStrategyPageListQueryVo activity = processingActivity.get(goods.getStraId());
                if(activity == null){
                    goods.setStraId(0);
                    continue;
                }
                //活动详情
                MrkingStrategyVo activityInfo = fullReductionProcessorDao.getMrkingStrategyById(activity.getId());
                //可用会员卡
                List<ValidUserCardBean> validCardList = userCard.userCardDao.getValidCardList(param.getWxUserInfo().getUserId(), new Byte[]{CardConstant.MCARD_TP_NORMAL, CardConstant.MCARD_TP_GRADE}, UserCardDaoService.CARD_ONLINE);
                Set<Integer> cardIds = validCardList.stream().map(ValidUserCardBean::getCardId).collect(Collectors.toSet());
                //check
                if(fullReductionProcessorDao.checkMemberCard(activityInfo, cardIds) &&
                    fullReductionProcessorDao.checkGoods(activityInfo, goods.getGoodsInfo().into(OrderGoodsBo.class))) {
                    if(Boolean.FALSE){
                        //TODO 孔德成提供购物车方法
                        goods.setStraId(0);
                    }
                }else {
                    goods.setStraId(0);
                }
            }
        }
    }

    @Override
    public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
        //无
    }

    @Override
    public void processOrderEffective(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
        //无
    }

    /**
     * 处理满折满减活动（金额计算）
     * @param param
     * @param bos
     * @throws MpException
     * @return 折扣详情
     */
    public List<OrderFullReduce> calculate(OrderBeforeParam param, List<OrderGoodsBo> bos) {
        List<OrderFullReduce> result = new ArrayList<>();
        if(NOT_PERMIT_ACTIVITY.contains(param.getActivityType())){
            List<MrkingStrategyPageListQueryVo> processingActivity = fullReductionProcessorDao.getProcessingActivity(param.getDate());
            if (CollectionUtils.isEmpty(processingActivity)) {
                return result;
            }
            HashMap<MrkingStrategyVo, List<OrderGoodsBo>> joinActivity = new HashMap<>();
            HashSet<Integer> used = new HashSet<>();
            //可用会员卡
            List<ValidUserCardBean> validCardList = userCard.userCardDao.getValidCardList(param.getWxUserInfo().getUserId(), new Byte[]{CardConstant.MCARD_TP_NORMAL, CardConstant.MCARD_TP_GRADE}, UserCardDaoService.CARD_ONLINE);
            Set<Integer> cardIds = validCardList.stream().map(ValidUserCardBean::getCardId).collect(Collectors.toSet());
            for(MrkingStrategyPageListQueryVo activity : processingActivity) {
                MrkingStrategyVo activityInfo = fullReductionProcessorDao.getMrkingStrategyById(activity.getId());
                if(!fullReductionProcessorDao.checkMemberCard(activityInfo, cardIds)) {
                    continue;
                }
                for (OrderGoodsBo goods : bos) {
                    if(used.contains(goods.getProductId()) || !fullReductionProcessorDao.checkGoods(activityInfo, goods)) {
                        //参与满折满减、活动不包含该商品
                        continue;
                    }
                    if(!OrderConstant.CART_Y.equals(param.getIsCart()) || param.getStoreId() != null || (activity.getId().equals(goods.getStraId()))) {
                        //购物车可选是否参加活动需要特殊处理
                        if(joinActivity.get(activityInfo) == null){
                            joinActivity.put(activityInfo, new ArrayList<>());
                        }
                        //goods.setStraId(activity.getId());
                        joinActivity.get(activityInfo).add(goods);
                        used.add(goods.getProductId());
                    }
                }
            }
            for(Map.Entry<MrkingStrategyVo, List<OrderGoodsBo>> entry : joinActivity.entrySet()) {
                BigDecimal[] tolalNumberAndPrice = calculate.getTolalNumberAndPriceByType(entry.getValue(), OrderConstant.D_T_FULL_REDUCE, null);
                BigDecimal discount = fullReductionProcessorDao.calculateFullReduce(entry.getKey(), entry.getValue(), tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_NUMBER].intValue(), tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_PRICE]);
                OrderFullReduce orderFullReduce = new OrderFullReduce();
                orderFullReduce.setInfo(entry.getKey());
                orderFullReduce.setTotalDiscount(discount);
                orderFullReduce.setTotalPrice(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_PRICE]);
                orderFullReduce.setTotalGoodsNumber(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_NUMBER]);
                orderFullReduce.setIdentity(entry.getKey().getId().toString());
                orderFullReduce.setBos(entry.getValue());
                orderFullReduce.initRatio();
                result.add(orderFullReduce);
                log.info("满折满减计算成功，详情：{}", orderFullReduce);
            }
        }
        return result;
    }
    /**订单处理end**/
}
