package com.vpu.mp.service.shop.image;

import static com.vpu.mp.db.shop.tables.Code.CODE;
import static java.lang.String.format;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.upyun.UpException;
import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.db.shop.tables.records.CodeRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;

import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 小程序码
 *
 * @author 郑保乐
 */
@Service
public class QrCodeService extends ShopBaseService {

    private final ImageService imageService;

    public QrCodeService(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     *  获取小程序分享码，无url参数
     * @param typeEnum 活动类型
     * @return null 无法获取，否则对应二维码地址
     */
    public String getMpQrCode(QrCodeTypeEnum typeEnum) {
        return getMpQrCode(typeEnum, "");
    }

    /**
     *  获取小程序分享码，无url参数
     * @param typeEnum 活动类型
     * @param  paramStr url参数
     * @return null 无法获取，否则对应二维码地址
     */
    public String getMpQrCode(QrCodeTypeEnum typeEnum, String paramStr) {
        String typeUrl = StringUtils.isBlank(paramStr) ? typeEnum.getUrl() : typeEnum.getUrl() + "?" + paramStr;
        String paramId = Util.md5(typeUrl);

        return getMpQrCode(typeUrl,typeEnum.getType(),paramId);
    }

    /**
     * 获取小程序码
     * @param typeUrl 带参数的小程序页面url
     * @param type  类型id
     * @param paramId 记录的唯一值，由typeUrl加密后产生
     * @return 小程序码图片url，null表示无法获取相应二维码
     */
    private String getMpQrCode(String typeUrl, Short type, String paramId) {
    	logger().info("获取小程序码typeUrl"+typeUrl);
        String relativePath = db().select(CODE.QRCODE_IMG).from(CODE)
            .where(CODE.PARAM_ID.eq(paramId)).and(CODE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .fetchAny(CODE.QRCODE_IMG);


        //数据库存在该图片路劲
        if (!StringUtils.isBlank(relativePath)) {

            String fullPath=this.imageService.imageUrl(relativePath);

            try {
                //判断upYun上是否有该图片
                Map<String, String> fileInfo = this.imageService.getUpYunClient().getFileInfo(relativePath);
                if (fileInfo != null) {
                    //有图片则直接返回图片全路径
                    return fullPath;
                }
            } catch (IOException | UpException e) {
                //如果失败则认为图片不存在
                logger().warn("upYun 获取图片信息失败："+e.getMessage());
            }
            //upYun不存在则将该记录设置为删除状态
            db().update(CODE).set(CODE.DEL_FLAG,DelFlag.DISABLE.getCode())
                .where(CODE.PARAM_ID.eq(paramId)).execute();
        }

        //获取小程序分享码
        Integer shopId = getShopId();
        MpAuthShopRecord mp = saas.shop.mp.getAuthShopByShopId(shopId);
        String appId =  mp.getAppId();

        //二维码图片大小
        int qrcodWidth = 430;

        try {
            byte[] qrcodeBytes = open().getWxOpenComponentService().getWxMaServiceByAppid(appId)
                .getQrcodeService().createWxaCodeUnlimitBytes(null,typeUrl, qrcodWidth,true,null,true);

            relativePath = format("upload/%s/qrcode/%s/T%sP%s_%s.jpg", type, getShopId(), type, paramId,
                new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));

            this.imageService.getUpYunClient().writeFile(relativePath, qrcodeBytes, true);
        } catch (WxErrorException e) {
            logger().warn("获取小程序分享码错误：" + e.getMessage());
            return null;
        } catch (Exception e) {
            logger().warn("upYun上传文件错误：" + e.getMessage());
            return null;
        }

        CodeRecord codeRecord = db().newRecord(CODE);
        codeRecord.setType(type);
        codeRecord.setParamId(paramId);
        codeRecord.setTypeUrl(typeUrl);
        codeRecord.setQrcodeImg(relativePath);
        codeRecord.insert();

        return this.imageService.imageUrl(relativePath);
    }
}
