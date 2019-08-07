package com.vpu.mp.service.shop.qrcode;

import com.vpu.mp.db.shop.tables.records.CodeRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.shop.image.ImageService;
import org.apache.commons.io.FileUtils;
import org.jooq.Record1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;

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

    @Value("http://${domain.image}/")
    private String imageDomain;

    public QRCodeService(ImageService imageService) {
        this.imageService = imageService;
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
    public String getMpQRCode(String pageUrl, Short typeId, Integer paramId) throws Exception {
        Record1<String> record = shopDb().select(CODE.QRCODE_IMG).from(CODE).where(CODE.TYPE_URL.eq(pageUrl))
            .or(CODE.TYPE.eq(typeId).and(CODE.PARAM_ID.eq(String.valueOf(paramId))))
            .fetchAny();
        if (null == record) {
            String buffer = open().getMpQRCodeBuffer(pageUrl);
            File tempFile = File.createTempFile("tmp", "");
            FileUtils.writeByteArrayToFile(tempFile, buffer.getBytes());
            String relativePath = format("upload/%s/qrcode/%s/T%sP%s_%s.jpg", typeId, getShopId(), typeId, paramId,
                new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
            imageService.uploadToUpYun(relativePath, tempFile);
            CodeRecord codeRecord = new CodeRecord(null, typeId, typeId.toString(), pageUrl,
                relativePath, (byte) 0, "0", null, null);
            shopDb().insertInto(CODE).set(codeRecord).execute();
            return relativePath;
        }
        String imageUrl = record.get(CODE.QRCODE_IMG);
        return imageDomain + imageUrl;
    }
}
