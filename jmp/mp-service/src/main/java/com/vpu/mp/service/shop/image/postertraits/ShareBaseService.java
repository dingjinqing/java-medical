package com.vpu.mp.service.shop.image.postertraits;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareBaseParam;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareInfo;
import com.vpu.mp.service.pojo.wxapp.share.PictorialConstant;
import com.vpu.mp.service.shop.image.ImageService;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;

/**
 * 海报下载分享基类
 * @author 李晓冰
 * @date 2020年04月23日
 */
public abstract class ShareBaseService extends ShopBaseService {
    @Autowired
    protected PictorialService pictorialService;
    @Autowired
    protected ImageService imageService;
    /**
     * 解析分享配置，生成分享结果信息
     * @param shareConfig 分享配置
     * @param activityRecord 分享活动
     * @param goodsRecord 分享的商品
     * @param param 分享请求参数
     * @return 商品分享解析结果
     */
    protected GoodsShareInfo parsePictorialShareConfig(PictorialShareConfig shareConfig, Record activityRecord, GoodsRecord goodsRecord, GoodsShareBaseParam param){
        GoodsShareInfo shareInfoVo = new GoodsShareInfo();
        // 用户自定义分享样式
        if (PictorialShareConfig.CUSTOMER_STYLE.equals(shareConfig.getShareAction())) {
            if (PictorialShareConfig.DEFAULT_IMG.equals(shareConfig.getShareImgAction())) {
                shareInfoVo.setImgUrl(goodsRecord.getGoodsImg());
            } else {
                shareInfoVo.setImgUrl(shareConfig.getShareImg());
            }
            shareInfoVo.setShareDoc(shareConfig.getShareDoc());
        } else {
            String imgPath = createShareImage(activityRecord, goodsRecord, param);
            if (imgPath == null) {
                shareInfoVo.setShareCode(PictorialConstant.GOODS_PIC_ERROR);
                return shareInfoVo;
            }
            shareInfoVo.setImgUrl(imgPath);
            String shareDoc = null;
            ShopRecord shop = saas.shop.getShopById(getShopId());
            shareDoc = pictorialService.getCommonConfigDoc(param.getUserName(), goodsRecord.getGoodsName(), param.getRealPrice(),shop.getShopLanguage(),false);
            if (shareDoc == null) {
                shareDoc = createDefaultShareDoc(shop.getShopLanguage(),activityRecord,goodsRecord,param);
            }
            shareInfoVo.setShareDoc(shareDoc);
        }
        shareInfoVo.setImgUrl(imageService.getImgFullUrl(shareInfoVo.getImgUrl()));
        return shareInfoVo;
    }

    /**
     * 创建分享图片
     * @param aRecord 分享活动
     * @param goodsRecord 分享的商品
     * @param baseParam 分享请求参数
     * @return 图片路径
     */
    protected abstract String createShareImage(Record aRecord,GoodsRecord goodsRecord,GoodsShareBaseParam baseParam);

    /**
     * 创建默认宣传语
     * @param lang 语言
     * @param  aRecord 活动record
     * @param goodsRecord 商品record
     * @param baseParam 请求参数
     * @return 宣传语
     */
    protected abstract String createDefaultShareDoc(String lang,Record aRecord,GoodsRecord goodsRecord,GoodsShareBaseParam baseParam);
    /**
     * 创建云盘上的相对路径
     * @param activityId  活动Id
     * @return 相对路径
     */
    protected String createFilePath(Integer activityId) {
        return String.format("/upload/%s/share/%s/%s.jpg", getShopId(),getActivityName(), activityId + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
    }

    /**
     * 活动名称
     * @return 名称字符串
     */
    protected abstract String getActivityName();

    /**
     * 分享打印日志
     * @param tag 活动名
     * @param msg 日志信息
     */
    protected void shareLog(String tag,String msg){
        printLog("share", tag, msg);
    }

    /**
     * 海报打印日志
     * @param tag 活动名
     * @param msg 日志信息
     */
    protected void pictorialLog(String tag,String msg){
        printLog("pictorial", tag, msg);
    }

    /**
     * 日志
     * @param type share或pictorial
     * @param tag 活动名
     * @param msg 日志信息
     */
    protected void printLog(String type, String tag, String msg) {
        logger().debug("小程序-{}-{}-{}",type,tag,msg);
    }

}
