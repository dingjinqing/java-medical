package com.vpu.mp.service.shop.task.market;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyDetailVo;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyProductVo;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.es.EsDataUpdateMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.GroupBuyDefine.GROUP_BUY_DEFINE;
import static com.vpu.mp.db.shop.tables.GroupBuyProductDefine.GROUP_BUY_PRODUCT_DEFINE;
import static java.util.stream.Collectors.toList;

/**
 * 拼团的定时任务
 * @author: 王兵兵
 * @create: 2019-12-05 10:34
 **/
@Service
public class GroupBuyTaskService  extends ShopBaseService {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private EsDataUpdateMqService esDataUpdateMqService;

    public void monitorGoodsType(){
        //目前Goods表里还是拼团类型的商品
        List<Integer> pastGroupBuyGoodsIdList = getPastGroupBuyGoodsId();
        //在活动有效期内的拼团记录的ID列表
        List<Integer> currentGroupBuyGoodsIdList = getCurrentGroupBuyGoodsIdList();

        //求差集
        List<Integer> changeToNormalGoodsIds = Util.diffList(pastGroupBuyGoodsIdList,currentGroupBuyGoodsIdList);
        List<Integer> changeToActGoodsIds = Util.diffList(currentGroupBuyGoodsIdList,pastGroupBuyGoodsIdList);

        if(changeToNormalGoodsIds != null && changeToNormalGoodsIds.size() > 0){
            //活动已失效，将goodsType改回去
            goodsService.changeToNormalType(changeToNormalGoodsIds);
            //异步更新ES
            esDataUpdateMqService.addEsGoodsIndex(changeToNormalGoodsIds,getShopId());
            //TODO 记录变动
        }

        if(changeToActGoodsIds != null && changeToActGoodsIds.size() > 0){
            //有新的活动生效，商品goodsType标记活动类型
            changeToGroupBuyType(changeToActGoodsIds);
            //刷新拼团库存
            updateGroupBuyProcudtStock(changeToActGoodsIds);
            //异步更新ES
            esDataUpdateMqService.addEsGoodsIndex(changeToActGoodsIds,getShopId());
            //TODO 记录变动
        }

    }

    /**
     * 当前所有goodsType还是拼团的商品ID（某些商品可能已经过期，需要更新回普通商品）
     * @return
     */
    private List<Integer> getPastGroupBuyGoodsId(){
        return db().select(GOODS.GOODS_ID).from(GOODS).where(GOODS.GOODS_TYPE.eq(BaseConstant.ACTIVITY_TYPE_GROUP_BUY)).fetchInto(Integer.class);
    }

    /**
     * 当前有效、有库存的拼团活动下属的goodsId列表
     * @return
     */
    private List<Integer> getCurrentGroupBuyGoodsIdList(){
        List<Integer> res =  db().selectDistinct(GROUP_BUY_DEFINE.GOODS_ID).from(GROUP_BUY_DEFINE).leftJoin(GOODS).on(GROUP_BUY_DEFINE.GOODS_ID.eq(GOODS.GOODS_ID)).where(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(GROUP_BUY_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(GROUP_BUY_DEFINE.START_TIME.lt(DateUtil.getLocalDateTime())).and(GROUP_BUY_DEFINE.END_TIME.gt(DateUtil.getLocalDateTime())).and(GROUP_BUY_DEFINE.STOCK.gt((short)0)).and(GOODS.GOODS_NUMBER.gt(0))).fetchInto(Integer.class);
        return res;
    }

    /**
     * 批量将活动商品改成拼团商品
     * @param goodsIds
     */
    private void changeToGroupBuyType(List<Integer> goodsIds){
        //比拼团优先级高的活动，不覆盖goodsType是这些的商品（3、5、10）
        List<Byte> highPriorityAct = Stream.of(BaseConstant.ACTIVITY_TYPE_BARGAIN,BaseConstant.ACTIVITY_TYPE_SEC_KILL,BaseConstant.ACTIVITY_TYPE_PRE_SALE).collect(toList());
        db().update(GOODS).set(GOODS.GOODS_TYPE, BaseConstant.ACTIVITY_TYPE_GROUP_BUY).where(GOODS.GOODS_TYPE.notIn(highPriorityAct).and(GOODS.GOODS_ID.in(goodsIds))).execute();
    }

    /**
     * 检查规格库存，更新拼团规格库存以保证拼团库存不大于规格库存
     * @param goodsIds
     */
    private void updateGroupBuyProcudtStock(List<Integer> goodsIds){
        List<GroupBuyDetailVo> activeGroupBuyList = getGroupBuyWithMonitor(goodsIds);
        for(GroupBuyDetailVo  groupBuy : activeGroupBuyList){
            for(GroupBuyProductVo groupBuyProduct : groupBuy.getProductList()){
                int prdNumber = goodsService.goodsSpecProductService.getPrdNumberByPrdId(groupBuyProduct.getProductId());
                if(prdNumber < groupBuyProduct.getStock()){
                    db().update(GROUP_BUY_PRODUCT_DEFINE).set(GROUP_BUY_PRODUCT_DEFINE.STOCK,(short)prdNumber).execute();
                }
            }
        }
    }

    /**
     * 当前有效的进行中拼团
     * @return
     */
    private List<GroupBuyDetailVo> getGroupBuyWithMonitor(List<Integer> goodsIds){
        List<GroupBuyDetailVo> res = db().select(GROUP_BUY_DEFINE.STOCK,GROUP_BUY_DEFINE.GOODS_ID,GROUP_BUY_DEFINE.ID).from(GROUP_BUY_DEFINE).where(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(GROUP_BUY_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(GROUP_BUY_DEFINE.START_TIME.lt(DateUtil.getLocalDateTime())).and(GROUP_BUY_DEFINE.END_TIME.gt(DateUtil.getLocalDateTime()))).and(GROUP_BUY_DEFINE.GOODS_ID.in(goodsIds)).fetchInto(GroupBuyDetailVo.class);
        for(GroupBuyDetailVo groupBuy : res){
            List<GroupBuyProductVo> groupBuyProduct = db().select(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID,GROUP_BUY_PRODUCT_DEFINE.STOCK,GROUP_BUY_PRODUCT_DEFINE.PRODUCT_ID).from(GROUP_BUY_PRODUCT_DEFINE).where(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID.eq(groupBuy.getId())).fetchInto(GroupBuyProductVo.class);
            groupBuy.setProductList(groupBuyProduct);
        }
        return res;
    }
}
