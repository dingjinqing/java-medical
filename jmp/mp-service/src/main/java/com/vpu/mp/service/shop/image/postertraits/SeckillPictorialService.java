package com.vpu.mp.service.shop.image.postertraits;

import com.upyun.UpException;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.db.shop.tables.records.SecKillDefineRecord;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareInfo;
import com.vpu.mp.service.pojo.wxapp.share.PictorialConstant;
import com.vpu.mp.service.pojo.wxapp.share.PictorialImgPx;
import com.vpu.mp.service.pojo.wxapp.share.PictorialRule;
import com.vpu.mp.service.pojo.wxapp.share.seckill.SeckillShareInfoParam;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.market.seckill.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;

/**
 * 秒杀活动分享图片生成器
 * @author 李晓冰
 * @date 2020年04月22日
 */
@Service
public class SeckillPictorialService extends ShopBaseService {
    @Autowired
    private SeckillService seckillService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PictorialService pictorialService;
    @Autowired
    private QrCodeService qrCodeService;

    /**
     * 秒杀活动-分享图片生成
     *
     * @param param 秒杀分享参数
     * @return 秒杀分享图片信息
     */
    public GoodsShareInfo getSeckillShareInfo(SeckillShareInfoParam param) {
        GoodsShareInfo shareInfoVo = new GoodsShareInfo();

        SecKillDefineRecord secKillDefineRecord = seckillService.getSeckillActById(param.getActivityId());
        // 拼团活动信息不可用
        if (secKillDefineRecord == null) {
            seckillLog("分享", "秒杀活动信息不可用");
            shareInfoVo.setShareCode(PictorialConstant.ACTIVITY_DELETED);
            return shareInfoVo;
        }

        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());
        // 拼团商品信息不可用
        if (goodsRecord == null) {
            seckillLog("分享", "秒杀商品信息不可用");
            shareInfoVo.setShareCode(PictorialConstant.GOODS_DELETED);
            return shareInfoVo;
        }

        PictorialShareConfig shareConfig = Util.parseJson(secKillDefineRecord.getShareConfig(), PictorialShareConfig.class);

        // 用户自定义分享样式
        if (shareConfig!=null&&PictorialShareConfig.CUSTOMER_STYLE.equals(shareConfig.getShareAction())) {
            if (PictorialShareConfig.DEFAULT_IMG.equals(shareConfig.getShareImgAction())) {
                shareInfoVo.setImgUrl(goodsRecord.getGoodsImg());
            } else {
                shareInfoVo.setImgUrl(shareConfig.getShareImg());
            }
            shareInfoVo.setShareDoc(shareConfig.getShareDoc());
        } else {
            // 使用默认分享图片样式
            String imgPath = createSeckillShareImg(secKillDefineRecord, goodsRecord, param);
            if (imgPath == null) {
                shareInfoVo.setShareCode(PictorialConstant.GOODS_PIC_ERROR);
                return shareInfoVo;
            }
            shareInfoVo.setImgUrl(imgPath);
            ShopRecord shop = saas.shop.getShopById(getShopId());
            String shareDoc = null;
            shareDoc = pictorialService.getCommonConfigDoc(param.getUserName(), goodsRecord.getGoodsName(), param.getRealPrice(), shop.getShopLanguage(), false);
            if (shareDoc == null) {
                shareDoc = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_SECKILL_DOC, "", "messages",  param.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            }
            shareInfoVo.setShareDoc(shareDoc);
        }
        shareInfoVo.setImgUrl(imageService.getImgFullUrl(shareInfoVo.getImgUrl()));
        return shareInfoVo;
    }

    /**
     * 拼团分享图片地址
     */
    private static final String SECKILL_BG_IMG = "image/wxapp/sec_kill_bg.jpg";

    @SuppressWarnings("all")
    private String createSeckillShareImg(SecKillDefineRecord secKillDefineRecord, GoodsRecord goodsRecord, SeckillShareInfoParam param) {
        PictorialRecord pictorialRecord = pictorialService.getPictorialDao(goodsRecord.getGoodsId(), param.getActivityId(), PictorialConstant.SECKILL_ACTION_SHARE, param.getUserId());
        // 已存在生成的图片
        if (pictorialRecord != null && pictorialService.isGoodsSharePictorialRecordCanUse(pictorialRecord.getRule(), goodsRecord.getUpdateTime(), secKillDefineRecord.getUpdateTime())) {
            return pictorialRecord.getPath();
        }

        try (InputStream bgInputStream = Util.loadFile(SECKILL_BG_IMG)) {
            BufferedImage bgBufferImg = ImageIO.read(bgInputStream);
            BufferedImage goodsBufferImg = ImageIO.read(new URL(imageService.getImgFullUrl(goodsRecord.getGoodsImg())));

            int goodsWidth = 162;
            int toTop = 85;
            int toLeft = 80;

            goodsBufferImg = ImageUtil.resizeImage(goodsWidth, goodsWidth, goodsBufferImg);
            ImageUtil.addTwoImage(bgBufferImg, goodsBufferImg, toTop, toLeft);

            ShopRecord shop = saas.shop.getShopById(getShopId());
            String moneyFlag = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages");
            String realPrice = moneyFlag+param.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            String linePrice = moneyFlag+param.getLinePrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();

            int textStartX = toLeft + goodsWidth + 20;

            // "秒杀" 文字
            String seckillText = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_SECKILL, null, "messages");
            ImageUtil.addFontWithRect(bgBufferImg,  textStartX, toTop + 20, seckillText, ImageUtil.SourceHanSansCN(Font.PLAIN, 16), PictorialImgPx.REAL_PRICE_COLOR, PictorialImgPx.SHARE_IMG_RECT_INNER_COLOR, PictorialImgPx.REAL_PRICE_COLOR);

            // 添加秒杀价￥
            ImageUtil.addFont(bgBufferImg, realPrice, ImageUtil.SourceHanSansCN(Font.PLAIN, 20), textStartX, toTop + 80, PictorialImgPx.REAL_PRICE_COLOR);
            // 添加划线价￥
            ImageUtil.addFontWithLine(bgBufferImg, textStartX, toTop + 100, linePrice, ImageUtil.SourceHanSansCN(Font.PLAIN, 17),PictorialImgPx.LINE_PRICE_COLOR);

            // 上传u盘云并缓存入库
            String relativePath = createFilePath(secKillDefineRecord.getSkId(), "share");
            PictorialRule pictorialRule = new PictorialRule(goodsRecord.getUpdateTime(), secKillDefineRecord.getUpdateTime());
            pictorialService.uploadToUpanYun(bgBufferImg, relativePath, pictorialRule, goodsRecord.getGoodsId(), param.getActivityId(),PictorialConstant.SECKILL_ACTION_SHARE,pictorialRecord, param.getUserId());

            return relativePath;
        } catch (IOException e) {
            seckillLog("分享", "图片生成错误：" + e.getMessage());
        } catch (UpException e) {
            seckillLog("分享", "UpanYun上传错误：" + e.getMessage());
        }
        return null;
    }
    /**
     * 创建云盘上的相对路径
     *
     * @param activityId       活动Id
     * @param shareOrPictorial "share" 或 "pictorial"
     * @return 相对路径
     */
    private String createFilePath(Integer activityId, String shareOrPictorial) {
        return String.format("/upload/%s/%s/seckill/%s.jpg", getShopId(), shareOrPictorial, activityId + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
    }

    private void seckillLog(String share, String msg) {
        logger().debug("小程序-秒杀{}-{}", share, msg);
    }
}
