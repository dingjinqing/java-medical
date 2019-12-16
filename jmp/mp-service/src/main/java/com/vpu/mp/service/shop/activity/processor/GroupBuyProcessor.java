package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GroupBuyListMpVo;
import com.vpu.mp.service.pojo.wxapp.order.CreateParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeVo;
import com.vpu.mp.service.shop.activity.dao.GroupBuyProcessorDao;
import org.jooq.Record3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GROUP_BUY_DEFINE;
import static com.vpu.mp.db.shop.Tables.GROUP_BUY_PRODUCT_DEFINE;

/**
 * 商品列表,下单
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Service
public class GroupBuyProcessor implements ProcessorPriority,ActivityGoodsListProcessor ,OrderCreatePayBeforeProcessor{

    @Autowired
    GroupBuyProcessorDao groupBuyProcessorDao;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_GROUP_BUY_PRIORITY;
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

    /**
     *  初始化参数
     * @param param
     */
    @Override
    public void initMarketOrderCreateParam(CreateParam param) {

    }

    /**
     *  保存订单数据
     * @param order
     * @throws MpException
     */
    @Override
    public void processAfterOrderCreate(OrderBeforeVo order) throws MpException {

    }
}
