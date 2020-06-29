package com.vpu.jmd.service.config;

import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributionDocumentParam;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.image.QrCodeService;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.db.shop.Tables.USER;

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

    /**
     * 开启分销开关更新返利到期时间和邀请保护到期时间
     */
    public void updateBaseDate(){
        //分销配置
        DistributionParam cfg = getDistributionCfg();
        //查询全部有邀请关系，但没有返利时间｜邀请保护时间的
        Result<Record> record = db().select().from(USER).where(USER.INVITE_ID.gt(0)).and(USER.INVITE_EXPIRY_DATE.isNull()).and(USER.INVITE_PROTECT_DATE.isNull()).fetch();
        if(record != null){
            List<UserRecord> users = record.into(UserRecord.class);
            for (UserRecord user:users){
                if(user.getInviteTime() != null){
                    //邀请保护时间
                    Timestamp protectDate = DateUtil.getTimeStampPlus(user.getInviteTime(),cfg.getProtectDate(), ChronoUnit.DAYS);
                    if(cfg.getProtectDate() == -1){  //-1为永久保护
                        Date foreverDate = new Date(946656000);
                        protectDate = Util.getEarlyTimeStamp(foreverDate,1);
                    }
                    if(cfg.getProtectDate() == 0){ //没有保护期
                        protectDate =Util.currentTimeStamp();
                    }

                    //邀请失效时间
                    Timestamp inviteExpiryDate = DateUtil.getTimeStampPlus(user.getInviteTime(),cfg.getVaild(), ChronoUnit.DAYS);
                    if(cfg.getVaild() == 0){  //永久返利有效
                        Date foreverDate = new Date(946656000);
                        inviteExpiryDate = Util.getEarlyTimeStamp(foreverDate,1);
                    }
                    Date ed = new Date(inviteExpiryDate.getTime());
                    db().update(USER).set(USER.INVITE_EXPIRY_DATE,ed)
                        .set(USER.INVITE_PROTECT_DATE,protectDate)
                        .where(USER.USER_ID.eq(user.getUserId())).execute();
                }
            }

        }
    }
}
