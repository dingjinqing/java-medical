package com.vpu.mp.service.shop.task.market;

import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.SecKillDefine.SEC_KILL_DEFINE;
import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.SecKillProductDefine.SEC_KILL_PRODUCT_DEFINE;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.market.seckill.SecKillProductVo;
import com.vpu.mp.service.pojo.shop.market.seckill.SeckillVo;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.market.seckill.SeckillService;
import org.jooq.Record1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 监控秒杀活动导致的goods表goodsType变化
 * @author: 王兵兵
 * @create: 2019-11-08 16:19
 **/
@Service
public class SeckillTaskService  extends ShopBaseService {
    @Autowired
    private SeckillService seckillService;
    @Autowired
    private GoodsService goodsService;

    public void monitorGoodsType(){
        List<Integer> pastSeckillGoodsIdList = this.getPastSeckillGoodsId();
        List<Integer> currentSeckillGoodsIdList = new ArrayList<>();

        List<SeckillVo> onGoingSeckillList = this.getSecKillWithMonitor();
        for(SeckillVo seckill : onGoingSeckillList){
            int unpaidGoodsNum = 0;
            for(SecKillProductVo secKillProduct : seckill.getSecKillProduct()){
                unpaidGoodsNum += seckillService.seckillList.getUnpaidSeckillNumberByPrd(seckill.getSkId(),secKillProduct.getProductId());
            }
            int goodsNumber = goodsService.getGoodsView(seckill.getGoodsId()).getGoodsNumber();
            if(seckill.getStock() + unpaidGoodsNum > 0 && goodsNumber + unpaidGoodsNum > 0){
                //只有处于进行中的、当前还有库存的秒杀活动所锁定的goodsId
                currentSeckillGoodsIdList.add(seckill.getGoodsId());
            }
        }
        List<Integer> pastSeckillGoodsIdListCopy = new ArrayList<>();
        pastSeckillGoodsIdListCopy.addAll(pastSeckillGoodsIdList);
        pastSeckillGoodsIdList.removeAll(currentSeckillGoodsIdList);

        if(pastSeckillGoodsIdList != null && pastSeckillGoodsIdList.size() > 0){
            //活动已失效，将goodsType改回去
            goodsService.changeToNormalType(pastSeckillGoodsIdList);
            //TODO 记录变动
        }

        currentSeckillGoodsIdList.removeAll(pastSeckillGoodsIdListCopy);
        if(currentSeckillGoodsIdList != null && currentSeckillGoodsIdList.size() > 0){
            //有新的活动生效，商品goodsType标记活动类型
            this.changeToSeckillType(currentSeckillGoodsIdList);
            this.updateSeckillProcudtStock(currentSeckillGoodsIdList);
            //TODO 记录变动
        }

    }

    /**
     * 当前有效的进行中秒杀
     * @return
     */
    private List<SeckillVo> getSecKillWithMonitor(){
        List<SeckillVo> res = db().select(SEC_KILL_DEFINE.STOCK,SEC_KILL_DEFINE.GOODS_ID,SEC_KILL_DEFINE.SK_ID).from(SEC_KILL_DEFINE).where(SEC_KILL_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(SEC_KILL_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(SEC_KILL_DEFINE.START_TIME.lt(DateUtil.getLocalDateTime())).and(SEC_KILL_DEFINE.END_TIME.gt(DateUtil.getLocalDateTime()))).fetchInto(SeckillVo.class);
        for(SeckillVo seckill : res){
            List<SecKillProductVo> seckillProduct = db().select(SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID).from(SEC_KILL_PRODUCT_DEFINE).where(SEC_KILL_PRODUCT_DEFINE.SK_ID.eq(seckill.getSkId())).fetchInto(SecKillProductVo.class);
            seckill.setSecKillProduct(seckillProduct);
        }
        return res;
    }

    /**
     * 当前有效的进行中秒杀
     * @return
     */
    private List<SeckillVo> getSecKillWithMonitor(List<Integer> goodsIds){
        List<SeckillVo> res = db().select(SEC_KILL_DEFINE.STOCK,SEC_KILL_DEFINE.GOODS_ID,SEC_KILL_DEFINE.SK_ID).from(SEC_KILL_DEFINE).where(SEC_KILL_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(SEC_KILL_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(SEC_KILL_DEFINE.START_TIME.lt(DateUtil.getLocalDateTime())).and(SEC_KILL_DEFINE.END_TIME.gt(DateUtil.getLocalDateTime()))).and(SEC_KILL_DEFINE.GOODS_ID.in(goodsIds)).fetchInto(SeckillVo.class);
        for(SeckillVo seckill : res){
            List<SecKillProductVo> seckillProduct = db().select(SEC_KILL_PRODUCT_DEFINE.SKPRO_ID,SEC_KILL_PRODUCT_DEFINE.STOCK,SEC_KILL_PRODUCT_DEFINE.TOTAL_STOCK,SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID).from(SEC_KILL_PRODUCT_DEFINE).where(SEC_KILL_PRODUCT_DEFINE.SK_ID.eq(seckill.getSkId())).fetchInto(SecKillProductVo.class);
            seckill.setSecKillProduct(seckillProduct);
        }
        return res;
    }

    /**
     * 当前所有goodsType还是秒杀的商品ID（某些商品可能已经过期，需要更新回普通商品）
     * @return
     */
    private List<Integer> getPastSeckillGoodsId(){
        return db().select(GOODS.GOODS_ID).from(GOODS).where(GOODS.GOODS_TYPE.eq(BaseConstant.GOODS_TYPE_SECKILL)).fetchInto(Integer.class);
    }

    /**
     * 批量将活动商品改成秒杀商品
     * @param goodsIds
     */
    private void changeToSeckillType(List<Integer> goodsIds){
        db().update(GOODS).set(GOODS.GOODS_TYPE, BaseConstant.GOODS_TYPE_SECKILL).where(GOODS.GOODS_ID.in(goodsIds)).execute();
    }

    /**
     * 检查规格库存，保证秒杀库存不大于规格库存
     * @param goodsIds
     */
    private void updateSeckillProcudtStock(List<Integer> goodsIds){
        List<SeckillVo> activeSeckillList = getSecKillWithMonitor(goodsIds);
        for(SeckillVo  seckill : activeSeckillList){
            for(SecKillProductVo secKillProduct : seckill.getSecKillProduct()){
                Record1<Integer> prdNumberRecord = db().select(GOODS_SPEC_PRODUCT.PRD_NUMBER).from(GOODS_SPEC_PRODUCT).where(GOODS_SPEC_PRODUCT.PRD_ID.eq(secKillProduct.getProductId())).fetchOne();
                int prdNumber;
                if(prdNumberRecord != null && (prdNumber = prdNumberRecord.into(Integer.class)) < secKillProduct.getStock()){
                    db().update(SEC_KILL_PRODUCT_DEFINE).set(SEC_KILL_PRODUCT_DEFINE.STOCK,prdNumber).set(SEC_KILL_PRODUCT_DEFINE.TOTAL_STOCK,secKillProduct.getTotalStock()-(secKillProduct.getStock()-prdNumber)).execute();
                }
            }
        }
    }
}
