package com.vpu.mp.service.shop.market.fullcut;

import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.records.MrkingStrategyConditionRecord;
import com.vpu.mp.db.shop.tables.records.MrkingStrategyRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPriceBo;
import com.vpu.mp.service.pojo.shop.market.fullcut.*;
import com.vpu.mp.service.pojo.shop.member.card.ValidUserCardBean;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartGoods;
import com.vpu.mp.service.pojo.wxapp.market.fullcut.MrkingStrategyGoodsListParam;
import com.vpu.mp.service.pojo.wxapp.market.fullcut.MrkingStrategyGoodsListVo;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.member.GoodsCardCoupleService;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.user.cart.CartService;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.MrkingStrategy.MRKING_STRATEGY;
import static com.vpu.mp.db.shop.tables.MrkingStrategyCondition.MRKING_STRATEGY_CONDITION;

/**
 * @author: 王兵兵
 * @create: 2019-08-09 18:47
 **/
@Primary
@Service
public class MrkingStrategyService extends ShopBaseService {

    /**
     * 启用状态
     */
    public static final byte STATUS_NORMAL = 1;
    /**
     * 停用状态
     */
    public static final byte STATUS_DISABLED = 0;

    //活动选定的商品范围
    /**
     * 全部商品
     */
    public final static Byte ACT_TYPE_ALL = 0;
    /**
     * 部分商品
     */
    public final static Byte ACT_TYPE_SECTION = 1;

    @Autowired
    private ShopCommonConfigService shopCommonConfigService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsCardCoupleService goodsCardCoupleService;
    @Autowired
    private DomainConfig domainConfig;
    @Autowired
    private CartService cartService;
    @Autowired
    private MemberCardService memberCardService;

    /**
     * 新建满折满减活动
     *
     */
    public void addMrkingStrategy(MrkingStrategyAddParam param){
        this.transaction(()->{
            MrkingStrategyRecord record = db().newRecord(MRKING_STRATEGY);
            assign(param,record);
            record.insert();
            Integer id = record.getId();
            for(MrkingStrategyCondition condition : param.getConditionAddParams()){
                MrkingStrategyConditionRecord conditionRecord = new MrkingStrategyConditionRecord();
                assign(condition,conditionRecord);
                conditionRecord.setStrategyId(id);
                db().executeInsert(conditionRecord);
            }
        });
    }

    /**
     * 满折满减活动列表分页查询
     *
     */
    public PageResult<MrkingStrategyPageListQueryVo> getPageList(MrkingStrategyPageListQueryParam param) {
        SelectWhereStep<? extends Record> select = db().select(MRKING_STRATEGY.ID,MRKING_STRATEGY.ACT_NAME,MRKING_STRATEGY.TYPE,MRKING_STRATEGY.START_TIME,MRKING_STRATEGY.END_TIME, MRKING_STRATEGY.STATUS,MRKING_STRATEGY.STRATEGY_PRIORITY).
            from(MRKING_STRATEGY);
        if(param.getState() > 0) {
            /** 状态过滤*/
            Timestamp now = DateUtil.getLocalDateTime();
            switch(param.getState()) {
                case (byte)1:
                    select.where(MRKING_STRATEGY.STATUS.eq(STATUS_NORMAL)).and(MRKING_STRATEGY.START_TIME.lt(now)).and(MRKING_STRATEGY.END_TIME.gt(now));
                    break;
                case (byte)2:
                    select.where(MRKING_STRATEGY.STATUS.eq(STATUS_NORMAL)).and(MRKING_STRATEGY.START_TIME.gt(now));
                    break;
                case (byte)3:
                    select.where(MRKING_STRATEGY.STATUS.eq(STATUS_NORMAL)).and(MRKING_STRATEGY.END_TIME.lt(now));
                    break;
                case (byte)4:
                    select.where(MRKING_STRATEGY.STATUS.eq(STATUS_DISABLED));
                    break;
                default:
            }
        }
        select.where(MRKING_STRATEGY.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(MRKING_STRATEGY.STRATEGY_PRIORITY.desc(),MRKING_STRATEGY.CREATE_TIME.desc());
        PageResult<MrkingStrategyPageListQueryVo> res =  getPageResult(select,param.getCurrentPage(),param.getPageRows(),MrkingStrategyPageListQueryVo.class);
        if(res.dataList != null){
            for(MrkingStrategyPageListQueryVo mrkingStrategy : res.dataList){
                mrkingStrategy.setCondition(this.getMrkingStrategyCondition(mrkingStrategy.getId()));
                mrkingStrategy.setCurrentState(Util.getActStatus(mrkingStrategy.getStatus(),mrkingStrategy.getStartTime(),mrkingStrategy.getEndTime()));
            }
        }

        return res;
    }

    /**
     * 取某满折满减活动下的优惠规则
     *
     */
    public List<MrkingStrategyCondition> getMrkingStrategyCondition(Integer strategyId){
        return db().select(MRKING_STRATEGY_CONDITION.FULL_MONEY,MRKING_STRATEGY_CONDITION.REDUCE_MONEY,MRKING_STRATEGY_CONDITION.AMOUNT,MRKING_STRATEGY_CONDITION.DISCOUNT).
            from(MRKING_STRATEGY_CONDITION).
            where(MRKING_STRATEGY_CONDITION.STRATEGY_ID.eq(strategyId)).
            fetchInto(MrkingStrategyCondition.class);
    }

    /**
     * 取单个满折满减活动信息
     *
     */
    public MrkingStrategyVo getMrkingStrategyById(Integer id){
        MrkingStrategyRecord record = db().selectFrom(MRKING_STRATEGY).where(MRKING_STRATEGY.ID.eq(id)).fetchAny();
        MrkingStrategyVo res = record.into(MrkingStrategyVo.class);

        if(StringUtil.isNotEmpty(record.getRecommendGoodsId())){
            res.setRecommendGoodsIds(Util.splitValueToList(record.getRecommendGoodsId()));
            res.setRecommendGoods(saas().getShopApp(getShopId()).goods.selectGoodsViewList(Util.stringToList(record.getRecommendGoodsId())));
        }
        if(StringUtil.isNotEmpty(record.getCardId())){
            res.setCardIds(Util.splitValueToList(record.getCardId()));
            res.setMemberCards(saas().getShopApp(getShopId()).member.card.getMemberCardByCardIds(Util.splitValueToList(record.getCardId())));
        }
        if(StringUtil.isNotEmpty(record.getRecommendCatId())){
            res.setRecommendCatIds(Util.splitValueToList(record.getRecommendCatId()));
            res.setRecommendCat(saas.sysCate.getList(res.getRecommendCatIds()));
        }
        if(StringUtil.isNotEmpty(record.getRecommendBrandId())){
            res.setRecommendBrandIds(Util.splitValueToList(record.getRecommendBrandId()));
            res.setRecommendBrand(saas.getShopApp(getShopId()).goods.goodsBrand.getGoodsBrandVoById(res.getRecommendBrandIds()));
        }
        if(StringUtil.isNotEmpty(record.getRecommendSortId())){
            res.setRecommendSortIds(Util.splitValueToList(record.getRecommendSortId()));
            res.setRecommendSort(saas.getShopApp(getShopId()).goods.goodsSort.getListByIds(res.getRecommendSortIds()));
        }

        res.setCondition(this.getMrkingStrategyCondition(record.getId()));

        return res;
    }

    /**
     * 更新满折满减活动
     *
     */
    public void updateMrkingStrategy(MrkingStrategyUpdateParam param) {
        MrkingStrategyRecord record = new MrkingStrategyRecord();
        assign(param,record);
        db().executeUpdate(record);
    }

    /**
     * 删除满折满减活动
     *
     */
    public void delMrkingStrategy(Integer id) {
        db().update(MRKING_STRATEGY).
            set(MRKING_STRATEGY.DEL_FLAG,DelFlag.DISABLE.getCode()).
            where(MRKING_STRATEGY.ID.eq(id)).
            execute();
    }

    public MrkingStrategyGoodsListVo getWxAppGoodsList(MrkingStrategyGoodsListParam param,Integer userId){
        MrkingStrategyGoodsListVo vo = new MrkingStrategyGoodsListVo();

        //校验活动
        MrkingStrategyRecord MrkingStrategyAct = db().selectFrom(MRKING_STRATEGY).where(MRKING_STRATEGY.ID.eq(param.getStrategyId())).fetchAny();
        if(MrkingStrategyAct == null || MrkingStrategyAct.getDelFlag().equals(DelFlag.DISABLE_VALUE)){
            vo.setState((byte)1);
            return vo;
        }else if(MrkingStrategyAct.getStartTime().after(DateUtil.getLocalDateTime())){
            vo.setState((byte)2);
            return vo;
        }else if(MrkingStrategyAct.getEndTime().before(DateUtil.getLocalDateTime())){
            vo.setState((byte)3);
            return vo;
        }

        if(StringUtil.isNotEmpty(MrkingStrategyAct.getCardId())){
            //设置了持有会员卡才可以参与活动
            List<Integer> cardIds = Util.splitValueToList(MrkingStrategyAct.getCardId());
            List<ValidUserCardBean> cards = saas.getShopApp(getShopId()).userCard.userCardDao.getValidCardList(userId);
            List<Integer> validCardIds = cards.stream().map(ValidUserCardBean::getCardId).collect(Collectors.toList());
            validCardIds.retainAll(cardIds);

            if(validCardIds == null || validCardIds.size() == 0){
                vo.setState((byte)4);
                vo.setCardList(memberCardService.getMemberCardByCardIds(cardIds));
            }
        }

        PageResult<MrkingStrategyGoodsListVo.Goods> goodsPageResult;
        //过滤掉其中用户不能买的会员专享商品
        if (MrkingStrategyAct.getActType().equals(ACT_TYPE_SECTION)) {
            List<Integer> inGoodsIds = getMrkingStrategyGoodsIds(MrkingStrategyAct);
            List<Integer> userExclusiveGoodsIds = goodsCardCoupleService.getGoodsUserNotExclusive(userId);
            inGoodsIds.removeAll(userExclusiveGoodsIds);
            goodsPageResult = getGoods(inGoodsIds,Collections.emptyList(),param.getSearch(),param.getCurrentPage(),param.getPageRows());
        }else {
            List<Integer> notInGoodsIds = goodsCardCoupleService.getGoodsUserNotExclusive(userId);
            goodsPageResult = getGoods(Collections.emptyList(),notInGoodsIds,param.getSearch(),param.getCurrentPage(),param.getPageRows());
        }

        goodsPageResult.getDataList().forEach(goods -> {
            if(StringUtil.isNotEmpty(goods.getGoodsImg())){
                goods.setGoodsImg(domainConfig.imageUrl(goods.getGoodsImg()));
            }
            if(goods.getIsDefaultProduct() == 1){
                goods.setPrdId(goodsService.goodsSpecProductService.getDefaultPrdId(goods.getGoodsId()));
            }

            //处理限时降价、首单特惠、会员等级价对商品价格的覆盖
            GoodsPriceBo goodsPriceBo = saas.getShopApp(getShopId()).reducePrice.parseGoodsPrice(goods.getGoodsId(),userId);
            goods.setGoodsPriceAction(goodsPriceBo.getGoodsPriceAction());
            goods.setGoodsPrice(goodsPriceBo.getGoodsPrice());
            goods.setMaxPrice(goodsPriceBo.getMaxPrice());
            goods.setMarketPrice(goodsPriceBo.getMaxPrice());
        });
        vo.setGoods(goodsPageResult);

        //Conditions
        List<MrkingStrategyCondition> conditions = getMrkingStrategyCondition(param.getStrategyId());
        vo.setCondition(conditions);
        vo.setType(MrkingStrategyAct.getType());

        //根据购物车里的商品计算底边条的提醒文案
        WxAppCartBo cartBo = cartService.getCartList(userId,null, BaseConstant.ACTIVITY_TYPE_FULL_REDUCTION,param.getStrategyId());
        vo.setFullPriceDoc(getStrategyGoodsDoc(cartBo,MrkingStrategyAct.getType(),conditions));
        vo.setTotalPrice(cartBo.getTotalPrice());

        return vo;
    }

    /**
     * 活动包含的所有商品ID（指定部分商品类型的活动）
     * @param record
     * @return
     */
    private List<Integer> getMrkingStrategyGoodsIds(MrkingStrategyRecord record){
        List<Integer> res = new ArrayList<>();

        if(StringUtil.isNotBlank(record.getRecommendGoodsId())){
            List<Integer> goodsIds = Util.splitValueToList(record.getRecommendGoodsId());
            res.removeAll(goodsIds);
            res.addAll(goodsIds);
        }

        if(StringUtil.isNotBlank(record.getRecommendCatId()) || StringUtil.isNotBlank(record.getRecommendSortId()) || StringUtil.isNotBlank(record.getRecommendBrandId())){
            List<Integer> goodsIds = goodsService.getOnShelfGoodsIdList(Util.splitValueToList(record.getRecommendCatId()),Util.splitValueToList(record.getRecommendSortId()),Util.splitValueToList(record.getRecommendBrandId()));
            res.removeAll(goodsIds);
            res.addAll(goodsIds);
        }


        return res;
    }

    /**
     * 查出goods列表
     * @param inGoodsIds
     * @param search
     * @param currentPage
     * @param pageRows
     * @return
     */
    private PageResult<MrkingStrategyGoodsListVo.Goods> getGoods(List<Integer> inGoodsIds,List<Integer> notInGoodsIds,String search,Integer currentPage,Integer pageRows){
        Byte soldOutGoods = shopCommonConfigService.getSoldOutGoods();
        SelectWhereStep<? extends Record> select = db().select(GOODS.GOODS_ID,GOODS.GOODS_NAME,GOODS.GOODS_IMG,GOODS.SHOP_PRICE,GOODS.MARKET_PRICE,GOODS.CAT_ID,GOODS.GOODS_TYPE,GOODS.SORT_ID,GOODS.IS_CARD_EXCLUSIVE,GOODS.IS_DEFAULT_PRODUCT).from(GOODS);
        select.where(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
        select.where(GOODS.IS_ON_SALE.eq(GoodsConstant.ON_SALE));
        if(!NumberUtils.BYTE_ONE.equals(soldOutGoods)){
            select.where(GOODS.GOODS_NUMBER.gt(0));
        }
        if(StringUtil.isNotEmpty(search)){
            select.where(GOODS.GOODS_NAME.contains(search));
        }
        if(CollectionUtils.isNotEmpty(inGoodsIds)){
            select.where(GOODS.GOODS_ID.in(inGoodsIds));
        }
        if(CollectionUtils.isNotEmpty(notInGoodsIds)){
            select.where(GOODS.GOODS_ID.notIn(notInGoodsIds));
        }
        return getPageResult(select,currentPage,pageRows,MrkingStrategyGoodsListVo.Goods.class);
    }

    /**
     * 满折满减主商品文案
     * @param cartBo
     * @param strategyType  类型,1每满减 2满减 3满折 4仅第X件打折
     *       @param               conditions
     * @return
     */
    private MrkingStrategyGoodsListVo.FullPriceDoc getStrategyGoodsDoc(WxAppCartBo cartBo,Byte strategyType,List<MrkingStrategyCondition> conditions){
        MrkingStrategyGoodsListVo.FullPriceDoc doc = new MrkingStrategyGoodsListVo.FullPriceDoc();
        if(cartBo.getTotalPrice().compareTo(BigDecimal.ZERO) <= 0){
            doc.setDocType((byte)0);
            return doc;
        }

        switch (strategyType){
            case 1:
                //每满减，只有一条condition
                MrkingStrategyCondition condition = conditions.get(0);
                if(condition.getFullMoney() != null && condition.getFullMoney().compareTo(BigDecimal.ZERO) > 0){
                    //满金额类型
                    if(cartBo.getTotalPrice().compareTo(condition.getFullMoney()) >= 0){
                        BigDecimal reduceMoney = (cartBo.getTotalPrice().divide(condition.getFullMoney(),0,BigDecimal.ROUND_DOWN)).multiply(condition.getReduceMoney()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        doc.setDocType((byte)1);
                        doc.setReduceMoney(reduceMoney);
                        return doc;
                    }else{
                        BigDecimal diffPrice = condition.getFullMoney().subtract(cartBo.getTotalPrice()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        doc.setDocType((byte)2);
                        doc.setReduceMoney(condition.getReduceMoney());
                        doc.setDiffPrice(diffPrice);
                        return doc;
                    }
                }else if(condition.getAmount() != null && condition.getAmount() > 0){
                    //满件数类型
                    int totalGoodsNum = cartBo.getCartGoodsList().stream().mapToInt(WxAppCartGoods::getCartNumber).sum();
                    if(totalGoodsNum >= condition.getAmount()){
                        BigDecimal reduceMoney = condition.getReduceMoney().multiply(BigDecimal.valueOf(totalGoodsNum/condition.getAmount())).setScale(2,BigDecimal.ROUND_HALF_UP);
                        doc.setDocType((byte)1);
                        doc.setReduceMoney(reduceMoney);
                        return doc;
                    }else{
                        int diffNumber = condition.getAmount() - totalGoodsNum;
                        doc.setDocType((byte)4);
                        doc.setDiffNumber(diffNumber);
                        doc.setReduceMoney(condition.getReduceMoney());
                        return doc;
                    }
                }
                break;

            case 2:
                //满减，可以有多条condition
                if(conditions.get(0).getFullMoney() != null && conditions.get(0).getFullMoney().compareTo(BigDecimal.ZERO) > 0){
                    //满金额类型
                    conditions = conditions.stream().sorted(Comparator.comparing(MrkingStrategyCondition::getFullMoney).reversed()).collect(Collectors.toList());
                    BigDecimal reduceMoney = null;
                    for(MrkingStrategyCondition c:conditions){
                        if(cartBo.getTotalPrice().compareTo(c.getFullMoney()) >= 0){
                            reduceMoney = c.getReduceMoney();
                            break;
                        }
                    }
                    if(reduceMoney != null && reduceMoney.compareTo(BigDecimal.ZERO) > 0){
                        doc.setDocType((byte)1);
                        doc.setReduceMoney(reduceMoney);
                        return doc;
                    }else {
                        BigDecimal diffPrice = conditions.get(conditions.size() - 1).getFullMoney().subtract(cartBo.getTotalPrice()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        doc.setDocType((byte)2);
                        doc.setReduceMoney(conditions.get(conditions.size() - 1).getReduceMoney());
                        doc.setDiffPrice(diffPrice);
                        return doc;
                    }

                }else if(conditions.get(0).getAmount() != null && conditions.get(0).getAmount() > 0){
                    //满件数类型
                    int totalGoodsNum = cartBo.getCartGoodsList().stream().mapToInt(WxAppCartGoods::getCartNumber).sum();
                    conditions = conditions.stream().sorted(Comparator.comparing(MrkingStrategyCondition::getAmount).reversed()).collect(Collectors.toList());
                    BigDecimal reduceMoney = null;
                    for(MrkingStrategyCondition c:conditions){
                        if(totalGoodsNum >= c.getAmount()){
                            reduceMoney = c.getReduceMoney();
                            break;
                        }
                    }
                    if(reduceMoney != null && reduceMoney.compareTo(BigDecimal.ZERO) > 0){
                        doc.setDocType((byte)1);
                        doc.setReduceMoney(reduceMoney);
                        return doc;
                    }else {
                        int diffNumber = conditions.get(conditions.size() - 1).getAmount() - totalGoodsNum;
                        doc.setDocType((byte)4);
                        doc.setReduceMoney(conditions.get(conditions.size() - 1).getReduceMoney());
                        doc.setDiffNumber(diffNumber);
                        return doc;
                    }
                }
                break;

            case 3:
                //满折，可以有多条condition
                if(conditions.get(0).getFullMoney() != null && conditions.get(0).getFullMoney().compareTo(BigDecimal.ZERO) > 0) {
                    //满金额类型
                    conditions = conditions.stream().sorted(Comparator.comparing(MrkingStrategyCondition::getFullMoney).reversed()).collect(Collectors.toList());
                    BigDecimal discount = null;
                    for(MrkingStrategyCondition c:conditions){
                        if(cartBo.getTotalPrice().compareTo(c.getFullMoney()) >= 0){
                            discount = c.getDiscount();
                            break;
                        }
                    }
                    if(discount != null && discount.compareTo(BigDecimal.ZERO) > 0){
                        BigDecimal reduceMoney = cartBo.getTotalPrice().multiply(BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(10)))).setScale(2,BigDecimal.ROUND_HALF_UP);
                        doc.setDocType((byte)1);
                        doc.setReduceMoney(reduceMoney);
                        return doc;
                    }else {
                        BigDecimal diffPrice = conditions.get(conditions.size() - 1).getFullMoney().subtract(cartBo.getTotalPrice()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        doc.setDocType((byte)3);
                        doc.setDiffPrice(diffPrice);
                        doc.setDiscount(conditions.get(conditions.size() - 1).getDiscount());
                        return doc;
                    }
                }else if(conditions.get(0).getAmount() != null && conditions.get(0).getAmount() > 0){
                    //满件数类型
                    int totalGoodsNum = cartBo.getCartGoodsList().stream().mapToInt(WxAppCartGoods::getCartNumber).sum();
                    conditions = conditions.stream().sorted(Comparator.comparing(MrkingStrategyCondition::getAmount).reversed()).collect(Collectors.toList());
                    BigDecimal discount = null;
                    for(MrkingStrategyCondition c:conditions){
                        if(totalGoodsNum >= c.getAmount()){
                            discount = c.getDiscount();
                            break;
                        }
                    }
                    if(discount != null && discount.compareTo(BigDecimal.ZERO) > 0){
                        BigDecimal reduceMoney = cartBo.getTotalPrice().multiply(BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(10)))).setScale(2,BigDecimal.ROUND_HALF_UP);
                        doc.setDocType((byte)1);
                        doc.setReduceMoney(reduceMoney);
                        return doc;
                    }else {
                        int diffNumber = conditions.get(conditions.size() - 1).getAmount() - totalGoodsNum;
                        doc.setDocType((byte)5);
                        doc.setDiscount(conditions.get(conditions.size() - 1).getDiscount());
                        doc.setDiffNumber(diffNumber);
                        return doc;
                    }
                }
                break;
            case 4:
                //第amount件，打discount折，只有一条condition
                MrkingStrategyCondition c = conditions.get(0);
                BigDecimal reduceMoney = BigDecimal.ZERO;
                for (WxAppCartGoods g : cartBo.getCartGoodsList()){
                    if(g.getCartNumber() >= c.getAmount()){
                        reduceMoney = reduceMoney.add(g.getGoodsPrice().multiply(BigDecimal.ONE.subtract(c.getDiscount().divide(BigDecimal.valueOf(10))))).setScale(2,BigDecimal.ROUND_HALF_UP);
                    }
                }
               if(reduceMoney.compareTo(BigDecimal.ZERO) <= 0){
                   doc.setDocType((byte)0);
                   return doc;
               }else {
                   doc.setDocType((byte)1);
                   doc.setReduceMoney(reduceMoney);
                   return doc;
               }
            default:

        }

        return doc;
    }

    public List<WxAppCartGoods> getWxAppCheckedGoodsList(MrkingStrategyGoodsListParam param,Integer userId){
        WxAppCartBo cartBo = cartService.getCartList(userId,null, BaseConstant.ACTIVITY_TYPE_FULL_REDUCTION,param.getStrategyId());
        return cartBo.getCartGoodsList();
    }



}
