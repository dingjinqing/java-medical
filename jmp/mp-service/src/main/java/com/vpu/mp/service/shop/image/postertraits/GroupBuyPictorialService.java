package com.vpu.mp.service.shop.image.postertraits;

import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GroupBuyDefineRecord;
import com.vpu.mp.db.shop.tables.records.GroupBuyListRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.pojo.wxapp.account.UserInfo;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyListService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyService;
import com.vpu.mp.service.shop.user.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * 拼团图片
 * @author 孔德成
 * @date 2019/12/12 18:00
 */
@Service
public class GroupBuyPictorialService  extends ShopBaseService {

    @Autowired
    private UserService userService;
    @Autowired
    private GroupBuyService groupBuyService;
    @Autowired
    private GroupBuyListService groupBuyListService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private DomainConfig domainConfig;
    /**
     * 拼团海报
     * @return
     */
    public String getGroupBuyShareBase64Pictorial(Integer userId, Integer groupId){
        UserInfo userInfo = userService.getUserInfo(userId);
        logger().debug("拼团海报");
        //活动
        GroupBuyListRecord groupBuyList = groupBuyListService.getGrouperByGroupId(groupId);
        GroupBuyDefineRecord groupBuyRecord = groupBuyService.getGroupBuyRecord(groupBuyList.getActivityId());
        //商品
        GoodsRecord goods = goodsService.getGoodsById(groupBuyRecord.getGoodsId()).get();
        //图片排版
        BufferedImage backgroundImage = groupBuyImageComposing(goods);
        return ImageUtil.toBase64(backgroundImage);
    }

    /**
     * 排版
     * @param goods
     * @return
     */
    private BufferedImage groupBuyImageComposing(GoodsRecord goods) {
        //北京图片
        ClassPathResource resource = new ClassPathResource("image/wxapp/pin_group_bg.jpg");
        BufferedImage backgroundImage =null;
        try (InputStream inputStream =resource.getInputStream()){
            //背景
            backgroundImage = ImageIO.read(inputStream);
            //商品
            BufferedImage goodsImage = ImageIO.read(new URL(domainConfig.imageUrl(goods.getGoodsImg())));
            BufferedImage bufferedImage = ImageUtil.resizeImage(200, 200, goodsImage);
            ImageUtil.addTwoImage(backgroundImage, bufferedImage, 100, 260);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return backgroundImage;
    }


    private String groupBuyShareImage(Integer userId, Integer groupId){
        UserInfo userInfo = userService.getUserInfo(userId);
        logger().debug("拼团海报");
        //活动
        GroupBuyListRecord groupBuyList = groupBuyListService.getGrouperByGroupId(groupId);
        GroupBuyDefineRecord groupBuyRecord = groupBuyService.getGroupBuyRecord(groupBuyList.getActivityId());
        //商品
        GoodsRecord goods = goodsService.getGoodsById(groupBuyRecord.getGoodsId()).get();
        //图片排版
        BufferedImage backgroundImage = groupBuyImageComposing(goods);
        return null;
    }

}
