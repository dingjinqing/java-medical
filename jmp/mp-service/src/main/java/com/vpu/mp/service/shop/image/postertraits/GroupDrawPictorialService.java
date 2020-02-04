package com.vpu.mp.service.shop.image.postertraits;

import com.upyun.UpException;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GroupDrawRecord;
import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsSharePostConfig;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareInfo;
import com.vpu.mp.service.pojo.wxapp.share.PictorialConstant;
import com.vpu.mp.service.pojo.wxapp.share.PictorialRule;
import com.vpu.mp.service.pojo.wxapp.share.group.GroupDrawShareInfoParam;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.market.groupdraw.GroupDrawService;
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
 * 拼团抽将分享图片生成器
 * @author 李晓冰
 * @date 2020年02月03日
 */
@Service
public class GroupDrawPictorialService extends ShopBaseService {
    @Autowired
    private GroupDrawService groupDrawService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PictorialService pictorialService;
    @Autowired
    private QrCodeService qrCodeService;

    @SuppressWarnings("all")
    public GoodsShareInfo getGroupDrawShareInfo(GroupDrawShareInfoParam param) {
        GoodsShareInfo goodsShareInfo = new GoodsShareInfo();

        GroupDrawRecord groupDrawRecord = groupDrawService.getById(param.getActivityId());

        // 拼团活动信息不可用
        if (groupDrawRecord == null) {
            groupDrawLog("分享", "拼团抽奖活动信息不可用");
            goodsShareInfo.setShareCode(GoodsShareInfo.ACTIVITY_DELETED);
            return goodsShareInfo;
        }

        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());
        GoodsSharePostConfig shareConfig = Util.parseJson(goodsRecord.getShareConfig(), GoodsSharePostConfig.class);
        // 拼团商品信息不可用
        if (goodsRecord == null) {
            groupDrawLog("分享", "拼团抽奖商品信息不可用");
            goodsShareInfo.setShareCode(GoodsShareInfo.GOODS_DELETED);
            return goodsShareInfo;
        }

        goodsShareInfo.setShareAction(shareConfig.getShareAction());


        // 用户自定义分享样式
        if (PictorialShareConfig.CUSTOMER_STYLE.equals(shareConfig.getShareAction())) {
            if (PictorialShareConfig.DEFAULT_IMG.equals(shareConfig.getShareImgAction())) {
                goodsShareInfo.setImgUrl(goodsRecord.getGoodsImg());
            } else {
                // 此时分享配置的shareImgUrl是直接获取数据库里面的内容，是相对路径
                goodsShareInfo.setImgUrl(shareConfig.getShareImgUrl());
            }
            goodsShareInfo.setShareDoc(shareConfig.getShareDoc());
        } else {
            // 使用默认分享图片样式
            ShopRecord shop = saas.shop.getShopById(getShopId());
            String imgPath = createBargainShareImg(groupDrawRecord, goodsRecord, param);
            String doc = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_GROUP_DRAW_SHARE_DOC, "messages",param.getRealPrice());
            goodsShareInfo.setImgUrl(imgPath);
            goodsShareInfo.setShareDoc(doc);
        }
        goodsShareInfo.setImgUrl(imageService.getImgFullUrl(goodsShareInfo.getImgUrl()));

        return goodsShareInfo;
    }

    /**
     * 拼团抽奖分享背景图片地址
     */
    private static final String GROUP_DRAW_BG_IMG = "image/wxapp/group_draw.png";

    /**
     * 生成商品详情分享图
     * @param groupDrawRecord 活动信息
     * @param goodsRecord 商品信息
     * @param param 图片参数
     * @return
     */
    private String createBargainShareImg(GroupDrawRecord groupDrawRecord,GoodsRecord goodsRecord,GroupDrawShareInfoParam param){
        PictorialRecord pictorialRecord = pictorialService.getPictorialDao(goodsRecord.getGoodsId(), PictorialConstant.GROUP_DRAW_ACTION_SHARE,null);
        // 已存在生成的图片
        if (pictorialRecord != null&&pictorialService.isGoodsSharePictorialRecordCanUse(pictorialRecord.getRule(),goodsRecord.getUpdateTime(),groupDrawRecord.getUpdateTime())) {
            return pictorialRecord.getPath();
        }

        try (InputStream bgInputStream = Util.loadFile(GROUP_DRAW_BG_IMG)){

            BufferedImage bgBufferImg = ImageIO.read(bgInputStream);
            BufferedImage goodsBufferImg = ImageIO.read(new URL(imageService.getImgFullUrl(goodsRecord.getGoodsImg())));

            int goodsWidth = 162;
            int toTop = 85;
            int toLeft = 80;

            goodsBufferImg = ImageUtil.resizeImage(goodsWidth, goodsWidth, goodsBufferImg);
            ImageUtil.addTwoImage(bgBufferImg, goodsBufferImg, toTop, toLeft);

            ShopRecord shop = saas.shop.getShopById(getShopId());

            int textStartX = toLeft+goodsWidth+20;
            Color lineColor = new Color(255, 102, 102);
            //添加拼团抽奖文字
            String msg = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_GROUP_DRAW_SHARE_INFO, "messages");
            ImageUtil.addFontWithRect(bgBufferImg,textStartX,toTop+20,msg,ImageUtil.SourceHanSansCN(Font.PLAIN, 16),lineColor,new Color(255, 238, 238),lineColor);

            //添加真实价格
            String moneyFlag = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages");
            String realPrice = param.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            ImageUtil.addFont(bgBufferImg,moneyFlag+realPrice,ImageUtil.SourceHanSansCN(Font.PLAIN, 20),textStartX,toTop+80,lineColor);
            //添加划线价格
            String linePrice = param.getLinePrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            ImageUtil.addFontWithLine(bgBufferImg,textStartX,toTop+100,linePrice,ImageUtil.SourceHanSansCN(Font.PLAIN, 18),new Color(153,153,153));

            // 上传u盘云并缓存入库
            String relativePath = createFilePath(groupDrawRecord.getId(), "share");
            PictorialRule pictorialRule =new PictorialRule(goodsRecord.getUpdateTime(),groupDrawRecord.getUpdateTime());
            pictorialService.uploadToUpanYun(bgBufferImg, relativePath,pictorialRule, goodsRecord.getGoodsId(),pictorialRecord, param.getUserId());

            return relativePath;

        } catch (IOException e) {
            groupDrawLog("分享", "图片生成错误：" + e.getMessage());
        } catch (UpException e) {
            groupDrawLog("分享", "UpanYun上传错误：" + e.getMessage());
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
        return String.format("/upload/%s/%s/groupdraw/%s.jpg", getShopId(), shareOrPictorial, activityId + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
    }

    private void groupDrawLog(String share,String msg){
        logger().debug("小程序-拼团抽奖{}-{}", share, msg);
    }
}
