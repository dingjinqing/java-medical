package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.GroupBuyListRecord;
import com.vpu.mp.db.shop.tables.records.GroupBuyProductDefineRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GroupBuyListMpVo;
import com.vpu.mp.service.pojo.wxapp.order.CreateParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.shop.activity.dao.GroupBuyProcessorDao;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyListService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GROUP_BUY_DEFINE;
import static com.vpu.mp.db.shop.Tables.GROUP_BUY_PRODUCT_DEFINE;
import static com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyConstant.IS_GROUPER_N;
import static com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyConstant.IS_GROUPER_Y;
import static com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyConstant.STATUS_WAIT_PAY;

/**
 * 商品列表,下单
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Slf4j
@Service
public class GroupBuyProcessor implements Processor,ActivityGoodsListProcessor ,CreateOrderProcessor{

    @Autowired
    GroupBuyProcessorDao groupBuyProcessorDao;
    @Autowired
    GroupBuyListService groupBuyListService;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_GROUP_BUY_PRIORITY;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_GROUP_BUY;
    }

    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<GoodsListMpBo> bos, Integer userId) {
        List<GoodsListMpBo> availableBos = bos.stream().filter(x -> BaseConstant.ACTIVITY_TYPE_GROUP_BUY.equals(x.getActivityType())).collect(Collectors.toList());
        List<Integer> goodsIds = availableBos.stream().map(GoodsListMpBo::getGoodsId).collect(Collectors.toList());
        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> goodsGroupBuyListInfo = groupBuyProcessorDao.getGoodsGroupBuyListInfo(goodsIds, DateUtil.getLocalDateTime());

        availableBos.forEach(bo->{
            if (goodsGroupBuyListInfo.get(bo.getGoodsId()) == null) {
                return;
            }
            Record3<Integer, Integer, BigDecimal> record3 = goodsGroupBuyListInfo.get(bo.getGoodsId()).get(0);
            bo.setRealPrice(record3.get(GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE));
            GroupBuyListMpVo activity = new GroupBuyListMpVo();

            activity.setActivityId(record3.get(GROUP_BUY_DEFINE.ID));
            activity.setActivityType(BaseConstant.ACTIVITY_TYPE_GROUP_BUY);
            activity.setDiscountPrice(bo.getShopPrice().subtract(bo.getRealPrice()));
            bo.getGoodsActivities().add(activity);
            bo.getProcessedTypes().add(BaseConstant.ACTIVITY_TYPE_GROUP_BUY);
        });
    }

    //*********** 下单 *****************
    /**
     *  下单 初始化参数 校验
     * @param param CreateParam
     */
    @Override
    public void processInitCheckedOrderCreate(CreateParam param) throws MpException {
        //团长,团id
        Byte isGrouper =param.getGroupId()==null?IS_GROUPER_Y:IS_GROUPER_N;
        log.debug("拼团订单");
        if (isGrouper.equals(IS_GROUPER_Y)){
            param.setIsGrouper(IS_GROUPER_Y);
        }else {
            param.setIsGrouper(IS_GROUPER_N);
        }
        log.debug("团长-IsGrouper:{}-groupId:{}",param.getIsGrouper(),param.getGroupId());
        //校验活动
        ResultMessage resultMessage = groupBuyListService.canCreatePinGroupOrder(param.getWxUserInfo().getUserId(), param.getDate(), param.getActivityId(), param.getGroupId(), isGrouper);
        if (!resultMessage.getFlag()) {
            throw new MpException(resultMessage.getJsonResultCode(), null, resultMessage.getMessages().toArray(new String[0]));
        }
        for (OrderBeforeParam.Goods goods :param.getGoods()){
            //拼团规格库存校验
            GroupBuyProductDefineRecord groupBuyProduct = groupBuyProcessorDao.getGroupBuyProduct(param.getActivityId(), goods.getProductId());
            if (goods.getGoodsNumber()>groupBuyProduct.getStock()){
                throw new MpException(JsonResultCode.GROUP_BUY_ACTIVITY_GROUP_JOIN_LIMIT_MAX);
            }
            if (isGrouper.equals(IS_GROUPER_Y)){
                goods.setProductPrice(groupBuyProduct.getGrouperPrice());
            }else {
                goods.setProductPrice(groupBuyProduct.getGroupPrice());
            }
            goods.setGoodsPriceAction(param.getActivityType());
        }
    }

    /**
     * 保存订单
     * @param param CreateParam
     * @param order OrderInfoRecord
     * @throws MpException
     */
    @Override
    public void processSaveOrderInfo(CreateParam param, OrderInfoRecord order) throws MpException {
        for (OrderBeforeParam.Goods goods :param.getGoods()){
            GroupBuyListRecord groupBuyProductList = new GroupBuyListRecord();
            groupBuyProductList.setActivityId(param.getActivityId());
            groupBuyProductList.setGoodsId(goods.getGoodsId());
            groupBuyProductList.setGroupId(param.getGroupId());
            groupBuyProductList.setOrderSn(order.getOrderSn());
            groupBuyProductList.setUserId(param.getWxUserInfo().getUserId());
            groupBuyProductList.setIsGrouper(param.getIsGrouper());
            groupBuyProductList.setStatus(STATUS_WAIT_PAY);
            groupBuyProductList.setStartTime(param.getDate());
            int save = groupBuyProcessorDao.save(groupBuyProductList);
            if (save!=1){
                throw new MpException(JsonResultCode.GROUP_BUY_ACTIVITY_GROUP_JOIN_LIMIT_MAX);
            }
        }
    }

    /**
     * 改库存
     * @param param CreateParam
     * @throws MpException
     */
    @Override
    public void processStockAndSales(CreateParam param) throws MpException {
        for (OrderBeforeParam.Goods goods :param.getGoods()){
            boolean b = groupBuyProcessorDao.updateGroupBuyStock(param.getActivityId(), goods.getProductId(), goods.getGoodsNumber());
            if (!b){
                throw new MpException(JsonResultCode.GROUP_BUY_ACTIVITY_GROUP_JOIN_LIMIT_MAX);
            }
        }

    }


}
