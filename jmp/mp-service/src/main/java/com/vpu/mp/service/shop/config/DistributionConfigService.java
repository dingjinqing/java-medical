package com.vpu.mp.service.shop.config;

import com.vpu.mp.service.pojo.shop.distribution.DistributionDocumentParam;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.image.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;

/**
 * 分销配置
 * @author 常乐
 * 2019年7月17日
 */
@Service

public class DistributionConfigService extends BaseShopConfigService{
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
	 * @return
	 */
	public DistributionParam getDistributionCfg() {
		return this.getJsonObject(K_FANLI, DistributionParam.class);
	}

	/**
	 * 设置返利配置
	 * 
	 * @param  config
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
	 * @return
	 */
	public DistributionDocumentParam getDistributionDocument(){
		return this.getJsonObject(INVITE_DOCUMENT,DistributionDocumentParam.class);
	}

	/**
	 * 设置分销推广文案
	 * @param param
	 * @return
	 */
	public int setDistributionDocument(DistributionDocumentParam param){
		return this.setJsonObject(INVITE_DOCUMENT, param);
	}
}
