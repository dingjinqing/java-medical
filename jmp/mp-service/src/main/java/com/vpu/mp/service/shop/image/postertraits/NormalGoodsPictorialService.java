package com.vpu.mp.service.shop.image.postertraits;

import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareBaseParam;
import com.vpu.mp.service.shop.goods.GoodsService;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 李晓冰
 * @date 2020年01月17日
 */
@Service
public class NormalGoodsPictorialService extends RebateNormalPictorialBaseService {
    @Autowired
    GoodsService goodsService;

    @Override
    Record getActivityRecord(Integer activityId) {
        return null;
    }


    @Override
    String createShareImage(Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        return goodsRecord.getGoodsImg();
    }

    @Override
    String createMpQrCode(Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        return qrCodeService.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM, String.format("uid=%d&gid=%d&aid=&atp=", baseParam.getUserId(), goodsRecord.getGoodsId()));
    }

    @Override
    String createDefaultShareDoc(String lang, Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
       String shareDoc = null;
         shareDoc = pictorialService.getCommonConfigDoc(baseParam.getUserName(), goodsRecord.getGoodsName(), baseParam.getRealPrice(),lang, true);
        if (shareDoc == null) {
            shareDoc =  Util.translateMessage(lang, JsonResultMessage.WX_MA_NORMAL_GOODS_SHARE_INFO, "", "messages", baseParam.getUserName(), goodsRecord.getGoodsName());
        }
        return shareDoc;
    }

    @Override
    protected String getActivityName() {
        return "normal_goods";
    }
}
