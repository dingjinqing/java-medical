package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.GroupBuyDefineRecord;
import com.vpu.mp.db.shop.tables.records.GroupBuyListRecord;
import com.vpu.mp.db.shop.tables.records.GroupBuyProductDefineRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.groupbuy.GroupBuyMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.groupbuy.GroupBuyPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GroupBuyListMpVo;
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

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyConstant.*;

/**
 * 商品列表,下单
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Slf4j
@Service
public class GroupBuyProcessor extends ShopBaseService implements Processor,GoodsDetailProcessor,ActivityGoodsListProcessor ,CreateOrderProcessor{

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

    /*****************商品详情处理*******************/
    @Override
    public void processGoodsDetail(GoodsDetailMpBo capsule, GoodsDetailCapsuleParam param) {
        if (param.getActivityId() == null || !BaseConstant.ACTIVITY_TYPE_GROUP_BUY.equals(param.getActivityType())) {
            return;
        }
        log.debug("小程序-商品详情-拼团信息获取开始");
        GroupBuyMpVo groupBuyInfo = groupBuyProcessorDao.getGroupBuyInfo(param.getUserId(), param.getActivityId());
        log.debug("小程序-商品详情-拼团规格信息获取开始");
        List<GroupBuyPrdMpVo> groupBuyPrdInfos = groupBuyProcessorDao.getGroupBuyPrdInfo(param.getActivityId());
        groupBuyInfo.setGroupBuyPrdMpVos(groupBuyPrdInfos);

        Map<Integer, GoodsPrdMpVo> prdMap = capsule.getProducts().stream().collect(Collectors.toMap(GoodsPrdMpVo::getPrdId, x -> x));

        int goodsNum = 0;
        for (int i = 0; i < groupBuyPrdInfos.size(); i++) {
            // 商品拼团规格
            GroupBuyPrdMpVo vo = groupBuyPrdInfos.get(i);
            //商品原规格
            GoodsPrdMpVo goodsPrdMpVo = prdMap.get(vo.getProductId());

            // 避免admin拼团存在逻辑bug而导致此处产生空指针
            if (goodsPrdMpVo != null) {
                // 设置拼团规格对应的原价，便于前端使用
                vo.setPrdPrice(goodsPrdMpVo.getPrdRealPrice());
                // 处理商品数量不足情况
                if (goodsPrdMpVo.getPrdNumber() < vo.getStock()) {
                    vo.setStock(goodsPrdMpVo.getPrdNumber());
                }
            }

            goodsNum+=vo.getStock();
        }
        capsule.setGoodsNumber(goodsNum);
        if (goodsNum == 0) {
            log.debug("小程序-商品详情-拼团商品数量已用完");
            groupBuyInfo.setActState(BaseConstant.ACTIVITY_STATUS_NOT_HAS_NUM);
        }

        capsule.setActivity(groupBuyInfo);
    }


    //*********** 下单 *****************
    /**
     *  下单 初始化参数 校验
     * @param param OrderBeforeParam
     */
    @Override
    public void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException {
        //拼团不使用优惠券和会员卡
        param.setMemberCardNo(null);
        param.setCouponSn(null);
        //团长,团id
        Byte isGrouper =param.getGroupId()==null?IS_GROUPER_Y:IS_GROUPER_N;
        log.debug("拼团订单");
        if (isGrouper.equals(IS_GROUPER_Y)){
            param.setIsGrouper(IS_GROUPER_Y);
        }else {
            param.setIsGrouper(IS_GROUPER_N);
        }
        log.info("团长-IsGrouper:{}-groupId:{}",param.getIsGrouper(),param.getGroupId());
        //校验活动
        ResultMessage resultMessage = groupBuyListService.canCreatePinGroupOrder(param.getWxUserInfo().getUserId(), param.getDate(), param.getActivityId(), param.getGroupId(), isGrouper);
        if (!resultMessage.getFlag()) {
            throw new MpException(resultMessage.getJsonResultCode(), null, resultMessage.getMessages().toArray(new String[0]));
        }
        GroupBuyDefineRecord groupBuyRecord = groupBuyProcessorDao.getGroupBuyRecord(param.getActivityId());
        for (OrderBeforeParam.Goods goods :param.getGoods()){
            //拼团规格库存校验
            GroupBuyProductDefineRecord groupBuyProduct = groupBuyProcessorDao.getGroupBuyProduct(param.getActivityId(), goods.getProductId());
            if (goods.getGoodsNumber()>groupBuyProduct.getStock()){
                throw new MpException(JsonResultCode.GROUP_BUY_ACTIVITY_GROUP_JOIN_LIMIT_MAX);
            }
            if (isGrouper.equals(IS_GROUPER_Y)&&groupBuyRecord.getIsGrouperCheap().equals(IS_GROUPER_CHEAP_Y)){
                goods.setProductPrice(groupBuyProduct.getGrouperPrice());
            }else {
                goods.setProductPrice(groupBuyProduct.getGroupPrice());
            }
            goods.setGoodsPriceAction(param.getActivityType());
        }
    }

    /**
     * 保存订单
     * @param param OrderBeforeParam
     * @param order OrderInfoRecord
     * @throws MpException
     */
    @Override
    public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
        for (OrderBeforeParam.Goods goods :param.getGoods()){
            GroupBuyListRecord groupBuyProductList = db().newRecord(GROUP_BUY_LIST);
            groupBuyProductList.setActivityId(param.getActivityId());
            groupBuyProductList.setGoodsId(goods.getGoodsId());
            groupBuyProductList.setGroupId(param.getGroupId()==null?0:param.getGroupId());
            groupBuyProductList.setOrderSn(order.getOrderSn());
            groupBuyProductList.setUserId(param.getWxUserInfo().getUserId());
            groupBuyProductList.setIsGrouper(param.getIsGrouper());
            groupBuyProductList.setStartTime(param.getDate());
            if (order.getOrderStatus()>= OrderConstant.ORDER_WAIT_DELIVERY){
                groupBuyProductList.setStatus(STATUS_WAIT_PAY);
            }else {
                groupBuyProductList.setStatus(STATUS_ONGOING);
            }
            int save = groupBuyProductList.insert();
            if (save!=1){
                throw new MpException(JsonResultCode.GROUP_BUY_ACTIVITY_GROUP_JOIN_LIMIT_MAX);
            }
            groupBuyProductList.refresh();
            log.debug("开团成功,团长useri:d{},团groupId:{}",groupBuyProductList.getUserId(),groupBuyProductList.getId());
            if (groupBuyProductList.getIsGrouper().equals(IS_GROUPER_Y)){
                groupBuyProductList.setGroupId(groupBuyProductList.getId());
                groupBuyProductList.update();
            }

        }
    }

    /**
     * 改库存
     * @param param CreateParams
     * @throws MpException
     */
    @Override
    public void processStockAndSales(OrderBeforeParam param) throws MpException {
        for (OrderBeforeParam.Goods goods :param.getGoods()){
            boolean b = groupBuyProcessorDao.updateGroupBuyStock(param.getActivityId(), goods.getProductId(), goods.getGoodsNumber());
            if (!b){
                throw new MpException(JsonResultCode.GROUP_BUY_ACTIVITY_GROUP_JOIN_LIMIT_MAX);
            }
        }

    }
}
