package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.GoodsDetailMpCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.shop.activity.dao.SecKillProcessorDao;
import org.jooq.Record3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.SecKillDefine.SEC_KILL_DEFINE;
import static com.vpu.mp.db.shop.tables.SecKillProductDefine.SEC_KILL_PRODUCT_DEFINE;

/**
 * 秒杀
 * @author 李晓冰
 * @date 2019年11月01日
 * 秒杀
 */
@Service
public class SecKillProcessor implements ActivityGoodsListProcessor,GoodsDetailProcessor,ActivityCartListStrategy ,ProcessorPriority{
    @Autowired
    SecKillProcessorDao secKillProcessorDao;
    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_SEC_KILL_PRIORITY;
    }
    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<ActivityGoodsListCapsule> capsules, Integer userId) {
        List<ActivityGoodsListCapsule> availableCapsules = capsules.stream().filter(x -> GoodsConstant.ACTIVITY_TYPE_SEC_KILL.equals(x.getGoodsType())).collect(Collectors.toList());
        List<Integer> goodsIds = availableCapsules.stream().map(ActivityGoodsListCapsule::getGoodsId).collect(Collectors.toList());
        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> goodsSecKillListInfo = secKillProcessorDao.getGoodsSecKillListInfo(goodsIds, DateUtil.getLocalDateTime());

        availableCapsules.forEach(capsule->{
            if (goodsSecKillListInfo.get(capsule.getGoodsId()) == null) {
                return;
            }
            Record3<Integer, Integer, BigDecimal> record3 = goodsSecKillListInfo.get(capsule.getGoodsId()).get(0);

            capsule.setRealPrice(record3.get(SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE));
            GoodsActivityBaseMp activity = new GoodsActivityBaseMp();
            activity.setActivityId(record3.get(SEC_KILL_DEFINE.SK_ID));
            activity.setActivityType(GoodsConstant.ACTIVITY_TYPE_SEC_KILL);
            capsule.getActivities().add(activity);
            capsule.getProcessedTypes().add(GoodsConstant.ACTIVITY_TYPE_SEC_KILL);
        });
    }

    /**
     * 商品详情页-秒杀
     * @param
     */
    @Override
    public void processGoodsDetail(GoodsDetailMpCapsule capsule, GoodsDetailCapsuleParam param) {
        if(param.getActivityId() != null && param.getActivityType().equals(GoodsConstant.ACTIVITY_TYPE_SEC_KILL)){
            //处理之前capsule中需要有商品的基本信息
            capsule.setActivity(secKillProcessorDao.getDetailSeckillInfo(param.getActivityId(),param.getUserId(),capsule.getGoodsNumber()));
        }
    }
    //*********************************购物车

    /**
     * 购物车-秒杀
     * @param cartBo 业务数据类
     */
    @Override
    public void doCartOperation(WxAppCartBo cartBo) {
        List<Integer> secProductList = new ArrayList<>();
        //秒杀商品
        cartBo.getCartGoodsList().forEach(goods->{
            if (goods.getGoodsStatus().equals(GoodsConstant.ACTIVITY_TYPE_SEC_KILL)){
                secProductList.add(goods.getPrdId());
            }
        });
        //活动信息
        secKillProcessorDao.getSecKillInfoList(secProductList,cartBo.getDate());
    }
}
