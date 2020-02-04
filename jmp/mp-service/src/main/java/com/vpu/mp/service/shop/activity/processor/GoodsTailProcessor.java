package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.config.UpYunConfig;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.pojo.shop.config.pledge.PledgeBo;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.cart.CartConstant;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartGoods;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import com.vpu.mp.service.shop.activity.dao.TailProcessorDao;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.order.action.base.Calculate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 小程序-商品列表-处理最终价格信息
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
@Slf4j
public class GoodsTailProcessor implements Processor,ActivityGoodsListProcessor,GoodsDetailProcessor,ActivityCartListStrategy {
    @Autowired
    TailProcessorDao tailProcessorDao;
    @Autowired
    ImageService imageService;
    @Autowired
    private UpYunConfig upYunConfig;
    @Autowired
    private Calculate calculate;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return Byte.MAX_VALUE;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_GENERAL;
    }

    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<GoodsListMpBo> capsules, Integer userId) {
        capsules.forEach(capsule->{
            // 被活动处理了的话商品价就是活动价（已被活动设置），否则就是商品的规格最低价(新增商品时该字段存的就是最低价)
            if (isRegardAsNormalGoods(capsule.getProcessedTypes())) {
                capsule.setRealPrice(capsule.getShopPrice());
                capsule.setLinePrice(capsule.getMarketPrice());
            } else {
                capsule.setLinePrice(capsule.getPrdMaxPrice());
            }
            // 销量处理
            capsule.setGoodsSaleNum(capsule.getGoodsSaleNum()+capsule.getBaseSale());
            // 图片处理
            capsule.setGoodsImg(imageService.getImgFullUrl(capsule.getGoodsImg()));
            // 商品最后活动信息设置
            Optional<GoodsActivityBaseMp> first = capsule.getGoodsActivities().stream().filter(x -> GoodsConstant.isNeedReturnActivity(x.getActivityType())).findFirst();
            if (first.isPresent()) {
                GoodsActivityBaseMp activity = first.get();
                capsule.setActivityType(activity.getActivityType());
                capsule.setActivityId(activity.getActivityId());
            }
        });
    }

    /**
     * 商品是否按照普通商品设置最终真实价格和划线价格
     * @param processedTypes 商品被处理的活动信息
     * @return true 是 false 否
     */
    private boolean isRegardAsNormalGoods(List<Byte> processedTypes) {
        if (processedTypes == null || processedTypes.size() == 0) {
            return true;
        }
        if (processedTypes.size() == 1 && BaseConstant.ACTIVITY_TYPE_MEMBER_EXCLUSIVE.equals(processedTypes.get(0))) {
            return true;
        }
        return false;
    }
    /*****************商品详情处理******************/
    @Override
    public void processGoodsDetail(GoodsDetailMpBo goodsDetailMpBo, GoodsDetailCapsuleParam param) {
        List<GoodsPrdMpVo> products = goodsDetailMpBo.getProducts();

        // 规格会员价和图片路径处理
        log.debug("小程序-商品详情-规格图片处理");
        products.forEach(prd-> prd.setPrdImg(imageService.getImgFullUrl(prd.getPrdImg())));
        // 商品图片和视频路径地址处理
        List<String> goodsImgs = new ArrayList<>(goodsDetailMpBo.getGoodsImgs().size()+1);
        goodsDetailMpBo.getGoodsImgs().add(0,goodsDetailMpBo.getGoodsImg());
        goodsDetailMpBo.getGoodsImgs().forEach(img-> goodsImgs.add(imageService.getImgFullUrl(img)));
        goodsDetailMpBo.setGoodsImgs(goodsImgs);
        goodsDetailMpBo.setGoodsVideo(getVideoFullUrlUtil(goodsDetailMpBo.getGoodsVideo(),true));
        goodsDetailMpBo.setGoodsVideoImg(getVideoFullUrlUtil(goodsDetailMpBo.getGoodsVideoImg(),false));

        // 处理运费信息
        Integer defaultNum  = Integer.valueOf(0).equals(goodsDetailMpBo.getLimitBuyNum())? 1:goodsDetailMpBo.getLimitBuyNum();
        BigDecimal deliverPrice = calculate.calculateShippingFee(param.getUserId(),param.getLon(), param.getLat(), param.getGoodsId(), goodsDetailMpBo.getDeliverTemplateId(), defaultNum,goodsDetailMpBo.getProducts().get(0).getPrdRealPrice(),goodsDetailMpBo.getGoodsWeight());
        goodsDetailMpBo.setDeliverPrice(deliverPrice);

        // 判断是否已收藏商品
        boolean collectedGoods = tailProcessorDao.isCollectedGoods(param.getUserId(), param.getGoodsId());
        goodsDetailMpBo.setIsCollected(collectedGoods);
        //服务承诺
        PledgeBo pledgeList = tailProcessorDao.getPledgeList();
        goodsDetailMpBo.setPledgeSwitch(Integer.parseInt(pledgeList.getPledgeSwitch()));
        goodsDetailMpBo.setPledgeList(pledgeList.getPledgeList());

    }

    /**
     *  商品视频和快照图片相对路径转换全路径
     * @param relativePath 相对路径
     * @param videoOrSnapShop true: 视频，false: 快照
     * @return 全路径
     */
    private String getVideoFullUrlUtil(String relativePath,boolean videoOrSnapShop){
        if (StringUtils.isBlank(relativePath)) {
            return null;
        } else {
            return videoOrSnapShop ? upYunConfig.videoUrl(relativePath) : upYunConfig.imageUrl(relativePath);
        }
    }

    //**********************GWC********************************
    /**
     * 购物车
     * @param cartBo 业务数据类
     */
    @Override
    public void doCartOperation(WxAppCartBo cartBo) {
        BigDecimal totalPrice  =new BigDecimal(0);
        byte isAllCheck  = 1;
        for (WxAppCartGoods goods : cartBo.getCartGoodsList()) {
            if (goods.getPrdPrice()==null){
                goods.setPrdPrice(goods.getGoodsPrice());
            }
            if (goods.getIsChecked().equals(CartConstant.CART_IS_CHECKED)){
                totalPrice = totalPrice.add(goods.getPrdPrice().multiply(BigDecimal.valueOf(goods.getCartNumber())));
            }else {
                isAllCheck=0;
            }
        }
        cartBo.setTotalPrice(totalPrice);
        cartBo.setIsAllCheck(isAllCheck);


    }


}
