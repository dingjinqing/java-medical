package com.vpu.mp.service.shop.config;

import com.vpu.mp.service.foundation.jedis.data.DBOperating;
import com.vpu.mp.service.pojo.shop.distribution.DistributionDocumentParam;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.image.QrCodeService;
import org.jooq.Record1;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.vpu.mp.db.shop.Tables.*;

import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;

/**
 * 分销配置
 * @author 常乐
 * 2019年7月17日
 */
@Service

public class DistributionConfigService extends BaseShopConfigService {
    /**
     * 分销开关
     */
    final public static String K_FANLI = "fanli";
    final public static Byte ENABLE_STATUS = 1;

    final public static String INVITE_DOCUMENT = "invite_document";

    @Autowired
    private QrCodeService qrCode;

    /**
     * 获取返利配置
     *
     * @return
     */
    public DistributionParam getDistributionCfg() {
        DistributionParam jsonObject = this.getJsonObject(K_FANLI, DistributionParam.class);
        if(jsonObject == null){
            return null;
        }else{
            //是否有分销员
            Integer hasDistributor = hasDistributor();
            if(hasDistributor > 0){
                jsonObject.setHasDistributor(1);
            }else{
                jsonObject.setHasDistributor(0);
            }
            return jsonObject;
        }
    }

    /**
     * 设置返利配置
     *
     * @param config
     * @return
     */
    public int setDistributionCfg(DistributionParam config) {
        return this.setJsonObject(K_FANLI, config);
    }

    /**
     * 获取推广文案分享二维码
     */
    public ShareQrCodeVo getShareQrCode() {

//        String pathParam = Null;
        String imageUrl = qrCode.getMpQrCode(QrCodeTypeEnum.REBATE_POPULARIZE_DOCUMENT);

        ShareQrCodeVo vo = new ShareQrCodeVo();
        vo.setImageUrl(imageUrl);
        vo.setPagePath(QrCodeTypeEnum.REBATE_POPULARIZE_DOCUMENT.getPathUrl(null));
        return vo;
    }

    /**
     * 获取分销推广文案
     *
     * @return
     */
    public DistributionDocumentParam getDistributionDocument() {
        return this.getJsonObject(INVITE_DOCUMENT, DistributionDocumentParam.class);
    }

    /**
     * 设置分销推广文案
     *
     * @param param
     * @return
     */
    public int setDistributionDocument(DistributionDocumentParam param) {
        return this.setJsonObject(INVITE_DOCUMENT, param);
    }

    /**
     * 店铺已有分销员数量
     * @return
     */
    public Integer hasDistributor() {
        Integer hasDistributor = db().selectCount().from(USER).where(USER.IS_DISTRIBUTOR.eq((byte) 1)).fetchOne().into(Integer.class);
        return hasDistributor;
    }
}