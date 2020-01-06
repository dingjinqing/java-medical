package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.GradePrdRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.member.bo.UserCardGradePriceBo;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.wxapp.cart.activity.GoodsActivityInfo;
import com.vpu.mp.service.pojo.wxapp.cart.activity.OrderCartProductBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.CartActivityInfo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.shop.activity.dao.MemberCardProcessorDao;
import com.vpu.mp.service.shop.member.UserCardService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GRADE_PRD;
import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_TYPE_MEMBER_GRADE;

/**
 * 等级价格
 *
 * @author 李晓冰
 * @date 2019年10月31日
 */
@Service
@Slf4j
public class GradeCardProcessor implements Processor, ActivityGoodsListProcessor, GoodsDetailProcessor, ActivityCartListStrategy,CreateOrderProcessor {

    @Autowired
    MemberCardProcessorDao memberCardProcessorDao;
    @Autowired
    private UserCardService userCardService;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_MEMBER_GRADE_PRIORITY;
    }

    @Override
    public Byte getActivityType() {
        return ACTIVITY_TYPE_MEMBER_GRADE;
    }

    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<GoodsListMpBo> capsules, Integer userId) {
        List<GoodsListMpBo> availableCapsules = capsules.stream().filter(x -> !GoodsConstant.isGoodsTypeIn13510(x.getActivityType()) && !x.getProcessedTypes().contains(BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL))
            .collect(Collectors.toList());
        List<Integer> goodsIds = availableCapsules.stream().map(GoodsListMpBo::getGoodsId).collect(Collectors.toList());

        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> goodsGradeInfos = memberCardProcessorDao.getGoodsGradeCardForListInfo(userId, goodsIds);

        availableCapsules.forEach(capsule -> {
            Integer goodsId = capsule.getGoodsId();
            if (goodsGradeInfos.get(goodsId) == null) {
                return;
            }
            Record3<Integer, Integer, BigDecimal> record3 = goodsGradeInfos.get(goodsId).get(0);

            // 已被限时降价处理
            if (capsule.getProcessedTypes().contains(GoodsConstant.ACTIVITY_REDUCE_PRICE_PRIORITY)) {
                // 会员价比限时降价价格低则将限时降价的处理信息删除
                if (record3.get(GRADE_PRD.GRADE_PRICE).compareTo(capsule.getRealPrice()) < 0) {
                    capsule.getProcessedTypes().remove(GoodsConstant.ACTIVITY_REDUCE_PRICE_PRIORITY);
                    List<GoodsActivityBaseMp> activities = capsule.getGoodsActivities().stream().filter(x -> !GoodsConstant.ACTIVITY_REDUCE_PRICE_PRIORITY.equals(x.getActivityType())).collect(Collectors.toList());
                    capsule.setGoodsActivities(activities);
                } else {// 没有限时降价的价格低,则按照直接返回不加入会员价信息
                    return;
                }
            }

            // 不存在限时降价，或者会员价格比限时降价低则加入会员价活动信息
            capsule.setRealPrice(record3.get(GRADE_PRD.GRADE_PRICE));
            // 如果商品是会员专享的话则价格显示会员价的价格，但是提示信息显示会员专享，就不会执行下面if内语句（ps:filterParam处已经过滤掉了首单特惠）
            if (!capsule.getProcessedTypes().contains(BaseConstant.ACTIVITY_TYPE_MEMBER_EXCLUSIVE)) {
                GoodsActivityBaseMp activity = new GoodsActivityBaseMp();
                activity.setActivityType(ACTIVITY_TYPE_MEMBER_GRADE);
                capsule.getGoodsActivities().add(activity);
            }
            capsule.getProcessedTypes().add(ACTIVITY_TYPE_MEMBER_GRADE);
        });
    }

    /*****************商品详情处理******************/
    @Override
    public void processGoodsDetail(GoodsDetailMpBo goodsDetailMpBo, GoodsDetailCapsuleParam param) {
        List<GoodsDetailMpBo.GradePrd> gradeCards = goodsDetailMpBo.getGradeCardPrice();
        if (!goodsDetailMpBo.getIsDisposedByEs()) {
            log.debug("小程序-会员价格查询");
            List<GradePrdRecord> goodsGradeGradePrice = memberCardProcessorDao.getGoodsGradeGradePrice(param.getUserId(), param.getGoodsId());
            gradeCards = goodsGradeGradePrice.stream().map(x -> x.into(GoodsDetailMpBo.GradePrd.class)).collect(Collectors.toList());
            goodsDetailMpBo.setGradeCardPrice(gradeCards);
        }

        Map<Integer, BigDecimal> gradePriceMap = gradeCards.stream().collect(Collectors.toMap(GoodsDetailMpBo.GradePrd::getPrdId, GoodsDetailMpBo.GradePrd::getGradePrice, (x1, x2) -> x1));

        List<GoodsPrdMpVo> products = goodsDetailMpBo.getProducts();
        products.forEach(prd -> {
            if (gradePriceMap.get(prd.getPrdId()) != null) {
                prd.setPrdLinePrice(prd.getPrdRealPrice());
                prd.setPrdRealPrice(gradePriceMap.get(prd.getPrdId()));
            }
        });

        log.debug("商品会员价：{}", gradeCards.toString());
    }

    //*****************购物车处理************************

    /**
     * 会员等级价
     *
     * @param cartBo 业务数据类
     */
    @Override
    public void doCartOperation(WxAppCartBo cartBo) {
        log.info("WxAppCartBo:" + Util.toJson(cartBo));
        List<UserCardGradePriceBo> userCartGradePrice = userCardService.getUserCartGradePrice(cartBo.getUserId(), cartBo.getProductIdList());
        cartBo.getCartGoodsList().forEach(goods -> {
            // 会员等级
            userCartGradePrice.forEach(gradePrice -> {
                if (goods.getPrdId().equals(gradePrice.getPrdId())) {
                    CartActivityInfo gradePriceInfo = new CartActivityInfo();
                    gradePriceInfo.setActivityType(ACTIVITY_TYPE_MEMBER_GRADE);
                    gradePriceInfo.setMemberPriceType(gradePrice.getGradePrice());
                }
            });
        });
    }

    public void doOrderOperation(OrderCartProductBo productBo) {
        log.info("会员价计算start");
        String grade = userCardService.getUserGrade(productBo.getUserId());
        if(grade.equals(CardConstant.LOWEST_GRADE)) {
            return;
        }
        List<UserCardGradePriceBo> userCartGradePrice = userCardService.getUserCartGradePrice(grade, productBo.getProductIds());
        productBo.getAll().forEach(goods -> {
            // 会员等级
            userCartGradePrice.forEach(gradePrice -> {
                if (goods.getProductId().equals(gradePrice.getPrdId())) {
                    log.info("规格：{},会员价 ：{}",gradePrice.getPrdId(), gradePrice.getGradePrice());
                    GoodsActivityInfo goodsActivityInfo = new GoodsActivityInfo();
                    goodsActivityInfo.setActivityType(ACTIVITY_TYPE_MEMBER_GRADE);
                    goodsActivityInfo.setMemberPrice(gradePrice.getGradePrice());
                    goods.getActivityInfo().put(ACTIVITY_TYPE_MEMBER_GRADE,goodsActivityInfo);
                }
            });
        });
        log.info("会员价计算end");
    }

    @Override
    public void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException {
        doOrderOperation(param.getOrderCartProductBo());
    }

    @Override
    public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {

    }


    @Override
    public void processOrderEffective(OrderBeforeParam param,OrderInfoRecord order) throws MpException {

    }
}
