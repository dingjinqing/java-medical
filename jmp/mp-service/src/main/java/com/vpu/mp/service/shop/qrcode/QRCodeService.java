package com.vpu.mp.service.shop.qrcode;

import com.vpu.mp.db.shop.tables.records.CodeRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.shop.image.ImageService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.jooq.Record1;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

import static com.vpu.mp.db.main.Tables.MP_AUTH_SHOP;
import static com.vpu.mp.db.shop.tables.Code.CODE;
import static java.lang.String.format;

/**
 * 小程序码
 *
 * @author 郑保乐
 */
@Service
public class QRCodeService extends ShopBaseService {

    private final ImageService imageService;

    public QRCodeService(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * 获取小程序码
     *
     * @param pageUrl 带参数的小程序页面url
     *
     * @return 小程序码图片url
     */
    public String getMpQRCode(String pageUrl) {
        return getMpQRCode(pageUrl, null, null);
    }

    /**
     * 获取小程序码
     *
     * @param pageUrl 带参数的小程序页面url
     * @param typeId  类型id
     * @param paramId 参数id
     *
     * @return 小程序码图片url
     */
    public String getMpQRCode(String pageUrl, Short typeId, Integer paramId) {
        Record1<String> record = shopDb().select(CODE.QRCODE_IMG).from(CODE).where(CODE.TYPE_URL.eq(pageUrl))
            .or(CODE.TYPE.eq(typeId).and(CODE.PARAM_ID.eq(String.valueOf(paramId))))
            .fetchAny();

        if (null == record) {

            Integer shopId = getShopId();
            String appId = mainDb().select(MP_AUTH_SHOP.APP_ID).from(MP_AUTH_SHOP)
                .where(MP_AUTH_SHOP.SHOP_ID.eq(shopId)).fetchAny(MP_AUTH_SHOP.APP_ID);

            String relativePath = null;
            //二维码图片大小
            int qrcodWidth = 430;
            try {
                byte[] qrcodeBytes = open().getWxOpenComponentService().getWxMaServiceByAppid(appId)
                    .getQrcodeService().createQrcodeBytes(pageUrl, qrcodWidth);

                relativePath = format("upload/%s/qrcode/%s/T%sP%s_%s.jpg", typeId, getShopId(), typeId, paramId,
                    new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));

                this.imageService.getUpYunClient().writeFile(relativePath, qrcodeBytes, true);
            } catch (WxErrorException e) {
                logger().warn("获取小程序分享码错误：" + e.getMessage());
                return null;
            } catch (Exception e) {
                logger().warn("upYun上传文件错误：" + e.getMessage());
                return null;
            }

            CodeRecord codeRecord = new CodeRecord(null, typeId, paramId.toString(), pageUrl,
                relativePath, (byte) 0, "0", null, null);
            shopDb().insertInto(CODE).set(codeRecord).execute();

            return this.imageService.imageUrl(relativePath);
        }
        String imageUrl = record.get(CODE.QRCODE_IMG);
        return this.imageService.imageUrl(imageUrl);
    }
}
