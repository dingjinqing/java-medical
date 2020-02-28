package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.market.fullcut.FullReductionGoodsBo;
import com.vpu.mp.service.pojo.shop.market.fullcut.MrkingStrategyPageListQueryVo;
import com.vpu.mp.service.pojo.shop.market.fullcut.MrkingStrategyVo;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.card.ValidUserCardBean;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.wxapp.cart.CartConstant;
import com.vpu.mp.service.pojo.wxapp.cart.list.CartActivityInfo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartGoods;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.promotion.FullReductionPromotion;
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
import java.util.Optional;
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
public class FullReductionProcessor implements Processor, ActivityGoodsListProcessor, CreateOrderProcessor, GoodsDetailProcessor, ActivityCartListStrategy {

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

    /*****************商品详情处理******************/
    @Override
    public void processGoodsDetail(GoodsDetailMpBo capsule, GoodsDetailCapsuleParam param) {
        List<FullReductionPromotion> promotions = fullReductionProcessorDao.getFullReductionInfoForDetail(capsule.getGoodsId(), capsule.getCatId(), capsule.getBrandId(), capsule.getSortId(), DateUtil.getLocalDateTime());
        if (promotions != null && promotions.size() > 0) {
            capsule.getPromotions().put(BaseConstant.ACTIVITY_TYPE_FULL_REDUCTION,promotions);
        }
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
            BaseConstant.ACTIVITY_TYPE_PRE_SALE,
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

    @Override
    public void processReturn(Integer activityId, List<OrderReturnGoodsVo> returnGoods) {

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
        if(!NOT_PERMIT_ACTIVITY.contains(param.getActivityType())){
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
                        joinActivity.computeIfAbsent(activityInfo, k -> new ArrayList<>());
                        goods.setStraId(activity.getId());
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

    /**
     * 订单处理end
     **/

    //*******************购物车--满折满减
    @Override
    public void doCartOperation(WxAppCartBo cartBo) {
        log.info("购物车-满折满减");
        //可用的会员
        List<ValidUserCardBean> validCardList = userCard.userCardDao.getValidCardList(cartBo.getUserId(), new Byte[]{CardConstant.MCARD_TP_NORMAL, CardConstant.MCARD_TP_GRADE}, UserCardDaoService.CARD_ONLINE);
        List<Integer> cardIds = validCardList.stream().map(ValidUserCardBean::getCardId).collect(Collectors.toList());
        //活动和商品信息
        Map<Integer, List<FullReductionGoodsBo>> ruleCartIdMap = new HashMap<>();
        Set<CartActivityInfo> cartActivityInfos = new HashSet<>();
        //获取商品可用的活动
        for (WxAppCartGoods goods : cartBo.getCartGoodsList()) {
            List<CartActivityInfo> cartActivityInfoList = fullReductionProcessorDao.getGoodsFullReductionActivityList(goods.getGoodsId(),
                    goods.getGoodsRecord().getCatId(),
                    goods.getGoodsRecord().getBrandId(),
                    goods.getGoodsRecord().getSortId(),
                    cardIds,
                    cartBo.getDate());
            if (cartActivityInfoList != null && cartActivityInfoList.size() > 0) {
                goods.getCartActivityInfos().addAll(cartActivityInfoList);
                cartActivityInfos.addAll(cartActivityInfoList);
                //商品是否已经选择活动 且商品选中状态
                if (goods.getType().equals(BaseConstant.ACTIVITY_TYPE_FULL_REDUCTION)) {
                    Optional<CartActivityInfo> any = cartActivityInfoList.stream().filter(cartActivityInfo -> cartActivityInfo.getActivityId().equals(goods.getExtendId())).findAny();
                    if (any.isPresent()&&goods.getIsChecked().equals(CartConstant.CART_IS_CHECKED)) {
                        //商品活动
                        goods.setActivityType(BaseConstant.ACTIVITY_TYPE_FULL_REDUCTION);
                        goods.setActivityId(goods.getExtendId());
                        //活动记录
                        getFullReductionGoodsBo(ruleCartIdMap, goods);
                    } else {
                        //活动失效 todo 购物车操作
                    }
                }
            }
        }
        //当前活动选择
        fullReductionRuleOption(ruleCartIdMap, cartActivityInfos);
        for (WxAppCartGoods goods : cartBo.getCartGoodsList()) {
            if (goods.getActivityType()!=null&&goods.getActivityType().equals(BaseConstant.ACTIVITY_TYPE_FULL_REDUCTION)&&goods.getIsChecked().equals(CartConstant.CART_IS_CHECKED)){
                List<FullReductionGoodsBo> fullReductionGoodsBos = ruleCartIdMap.get(goods.getActivityId());
                fullReductionGoodsBos.forEach(fullReductionGoodsBo -> {
                    if (fullReductionGoodsBo.getProductId().equals(goods.getProductId())){
                        goods.getCartActivityInfos().forEach(cartActivityInfo -> {
                            if (cartActivityInfo.getActivityId().equals(goods.getActivityId())){
                                cartActivityInfo.getFullReduction().setRule(fullReductionGoodsBo.getFullReductionRule());
                            }
                        });
                    }
                });
            }
        }
        //国际化
        for (WxAppCartGoods goods : cartBo.getCartGoodsList()) {
            goods.getCartActivityInfos().forEach(cartActivityInfo -> {
                if (cartActivityInfo.getActivityType().equals(BaseConstant.ACTIVITY_TYPE_FULL_REDUCTION)){
                    CartActivityInfo.FullReduction fullReduction = cartActivityInfo.getFullReduction();
                    if (fullReduction!=null){
                        CartActivityInfo.FullReductionRule fullReductionRule = fullReduction.getRule();
                        //当前选中的国际化
                        if (fullReductionRule!=null){
                            String message = fullReductionRuleToString(fullReduction,fullReductionRule);
                            fullReduction.setCondition(message);
                        }
                        fullReduction.getRules().forEach(rule->{
                            rule.setName(fullReductionRuleToString(fullReduction,rule));
                        });
                    }
                }
            });
        }


    }

    /**
     * 国际化
     *
     * @param fullReduction
     * @param rule 满折满减规则
     * @return 国际化
     */
    private String fullReductionRuleToString(CartActivityInfo.FullReduction fullReduction, CartActivityInfo.FullReductionRule rule) {
        //满减
        switch (fullReduction.getFullReductiontype()){
            case 1://每满减
                if (rule.getFullMoney().compareTo(BigDecimal.ZERO)>0&&rule.getReduceMoney()!=null){
                    return "每满"+rule.getFullMoney()+"元,减"+rule.getReduceMoney()+"元";
                }
                if (rule.getAmount()>0&&rule.getReduceMoney()!=null){
                    return "每满"+rule.getAmount()+"件,减"+rule.getReduceMoney()+"元";
                }
                break;
            case 2://满减
                if (rule.getFullMoney().compareTo(BigDecimal.ZERO)>0&&rule.getReduceMoney()!=null){
                    return "满"+rule.getFullMoney()+"元,减"+rule.getReduceMoney()+"元";
                }
                if (rule.getAmount()>0&&rule.getReduceMoney()!=null){
                    return "满"+rule.getAmount()+"件,减"+rule.getReduceMoney()+"元";
                }
                break;
            case 3://3满折
                if (rule.getFullMoney().compareTo(BigDecimal.ZERO)>0&&rule.getReduceMoney()!=null){
                    return "满"+rule.getFullMoney()+"折,打"+rule.getReduceMoney()+"折";
                }
                if (rule.getAmount()>0&&rule.getReduceMoney()!=null){
                    return "满"+rule.getAmount()+"折,打"+rule.getReduceMoney()+"折";
                }
                break;
            case 4://第几件=
                if (rule.getAmount()>0&&rule.getReduceMoney()!=null){
                    return "满"+rule.getAmount()+"折,打"+rule.getReduceMoney()+"折";
                }
                break;
                default:
        }
        return "";
    }

    /**
     * 活动选择
     * @param ruleCartIdMap
     * @param cartActivityInfos
     */
    private void fullReductionRuleOption(Map<Integer, List<FullReductionGoodsBo>> ruleCartIdMap, Set<CartActivityInfo> cartActivityInfos) {
        //判断商品参与活动信息
        for (Map.Entry<Integer, List<FullReductionGoodsBo>> entry : ruleCartIdMap.entrySet()) {
            Integer ruleId = entry.getKey();
            List<FullReductionGoodsBo> fullReductionGoodsList = entry.getValue();
            Optional<CartActivityInfo> rule = cartActivityInfos.stream().filter(cartActivityInfo -> cartActivityInfo.getActivityId().equals(ruleId)).findFirst();
            if (rule.isPresent()) {
                CartActivityInfo cartActivityInfo = rule.get();
                CartActivityInfo.FullReduction fullReduction = cartActivityInfo.getFullReduction();
                CartActivityInfo.FullReductionRule fullReductionRule = fullReduction.getRules().get(0);
                /**活动类型 1每满减 2满减 3满折 4仅第X件打折*/
                switch (fullReduction.getFullReductiontype()) {
                    case 1:
                        break;
                    case 2://满减
                    case 3://3满折
                        List<CartActivityInfo.FullReductionRule> fullReductionRuleList = fullReduction.getRules();
                        CartActivityInfo.FullReductionRule first = fullReductionRuleList.stream().findFirst().get();
                        if (first.getAmount() != null && first.getAmount() > 0) {
                            //满件数
                            int sum = fullReductionGoodsList.stream().mapToInt(FullReductionGoodsBo::getNum).sum();
                            for (CartActivityInfo.FullReductionRule fullRule : fullReductionRuleList) {
                                if (fullRule.getAmount() <= sum) {
                                    if (fullReductionRule.getAmount() < sum) {
                                        if (fullRule.getAmount() > fullReductionRule.getAmount()) {
                                            fullReductionRule = fullRule;
                                        }
                                    } else {
                                        fullReductionRule = fullRule;
                                    }
                                }
                            }
                        } else {//满金额
                            BigDecimal moneySums = fullReductionGoodsList.stream().map(FullReductionGoodsBo::getMoney)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            for (CartActivityInfo.FullReductionRule fullRule : fullReductionRuleList) {
                                if (moneySums.compareTo(fullRule.getFullMoney()) >= 0) {
                                    if (moneySums.compareTo(fullReductionRule.getFullMoney()) >= 0) {
                                        if (fullReductionRule.getFullMoney().compareTo(fullRule.getFullMoney()) > 0) {
                                            fullReductionRule = fullRule;
                                        }
                                    } else {
                                        fullReductionRule = fullRule;
                                    }
                                }
                            }
                        }
                        break;
                    case 4:
                        break;
                    default:
                }
                for (FullReductionGoodsBo goods : fullReductionGoodsList) {
                    goods.setFullReductionRule(fullReductionRule);
                }
            }
        }

    }

    private void getFullReductionGoodsBo(Map<Integer, List<FullReductionGoodsBo>> ruleCartIdMap, WxAppCartGoods goods) {
        List<FullReductionGoodsBo> fullGoods = ruleCartIdMap.get(goods.getExtendId()) != null ? ruleCartIdMap.get(goods.getExtendId()) : new ArrayList<>();
        FullReductionGoodsBo fullReductionGoodsBo =new FullReductionGoodsBo();
        fullReductionGoodsBo.setCartId(goods.getCartId());
        fullReductionGoodsBo.setProductId(goods.getProductId());
        fullReductionGoodsBo.setNum(goods.getCartNumber());
        fullReductionGoodsBo.setMoney(goods.getGoodsPrice().multiply(BigDecimal.valueOf(goods.getCartNumber())));
        fullGoods.add(fullReductionGoodsBo);
        ruleCartIdMap.put(goods.getExtendId(), fullGoods);
    }

}
