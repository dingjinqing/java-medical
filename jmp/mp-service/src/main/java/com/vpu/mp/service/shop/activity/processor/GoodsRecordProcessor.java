package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.order.record.GoodsOrderRecordSmallVo;
import com.vpu.mp.service.shop.activity.dao.GoodsPrdProcessorDao;
import com.vpu.mp.service.shop.activity.dao.GoodsRecordDao;
import com.vpu.mp.service.shop.config.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;

/**
 * @author 孔德成
 * @date 2020年3月31日
 */
@Service
@Slf4j
public class GoodsRecordProcessor implements Processor,GoodsDetailProcessor{

    @Autowired
    private GoodsRecordDao goodsRecordDao;
    @Autowired
    private ConfigService configService;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return 0;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_GENERAL;
    }


    /*****************商品记录******************/
    @Override
    public void processGoodsDetail(GoodsDetailMpBo goodsDetailMpBo, GoodsDetailCapsuleParam param) {
        log.debug("商品详情-商品购买记录--开始");
        Byte goodsRecordFlag = configService.shopCommonConfigService.getGoodsRecord();
        if (goodsRecordFlag.equals(BaseConstant.YES)){
            List<GoodsOrderRecordSmallVo> goodsRecord = goodsRecordDao.getGoodsRecord(param.getGoodsId());
            if (goodsRecord!=null){
                log.debug("商品详情-商品购买记录：{}", Util.toJson(goodsRecord));
                goodsDetailMpBo.setGoodsRecord(goodsRecord);
            }
        }
        log.debug("商品详情-商品购买记录--结束");
    }
}
