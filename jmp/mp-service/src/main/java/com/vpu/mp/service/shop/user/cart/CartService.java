package com.vpu.mp.service.shop.user.cart;

import com.vpu.mp.db.shop.tables.UserRebatePrice;
import com.vpu.mp.db.shop.tables.records.CartRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.member.bo.UserCardGradePriceBo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartGoods;
import com.vpu.mp.service.pojo.wxapp.cart.WxAppCartListParam;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartListVo;
import com.vpu.mp.service.shop.config.FirstSpecialConfigService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.market.firstspecial.FirstSpecialService;
import com.vpu.mp.service.shop.market.presale.PreSaleService;
import com.vpu.mp.service.shop.market.seckill.SeckillService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.db.shop.tables.Cart.CART;


/**
 * 购物车
 *
 * @author 孔德成
 * @date 2019/10/14 16:39
 */
@Service
public class CartService extends ShopBaseService {

    @Autowired
    private UserCartService userCartService;
    @Autowired
    private GoodsSpecProductService goodsSpecProductService;
    @Autowired
    private GoodsService goodsService;
    /**
     * 用户会员卡
     */
    @Autowired
    private UserCardService userCardService;
    @Autowired
    private OrderInfoService orderInfoService;
    /**
     * 首单特惠
     */
    @Autowired
    private FirstSpecialService firstSpecialService;
    /**
     * 秒杀
     */
    @Autowired
    private SeckillService seckillService;
    /**
     * 预售
     */
    @Autowired
    private PreSaleService preSaleService;
    /**
     * 首单特惠
     */
    @Autowired
    private FirstSpecialConfigService firstSpecialConfigService;

    public WxAppCartListVo getCartList(Integer userId) {
        WxAppCartListVo cartListVo;
        // 查询购物车记录
        Result<? extends Record> records = db().select(CART.REC_ID, CART.GOODS_NAME, CART.IS_CHECKED, CART.GOODS_SPECS,
                CART.IDENTITY_ID, CART.GOODS_PRICE, CART.EXTEND_ID, CART.GOODS_NUMBER,
                GOODS.GOODS_ID,GOODS.SORT_ID,GOODS.CAT_ID,GOODS.BRAND_ID, GOODS.GOODS_IMG, GOODS.LIMIT_BUY_NUM,
                GOODS.LIMIT_MAX_NUM, GOODS.GOODS_TYPE, GOODS.DEL_FLAG, GOODS.IS_ON_SALE,GOODS.IS_CARD_EXCLUSIVE,
                GOODS_SPEC_PRODUCT.PRD_ID, GOODS_SPEC_PRODUCT.PRD_PRICE, GOODS_SPEC_PRODUCT.PRD_NUMBER, GOODS_SPEC_PRODUCT.PRD_IMG)
                .from(CART)
                .innerJoin(GOODS).on(GOODS.GOODS_ID.eq(CART.GOODS_ID))
                .innerJoin(GOODS_SPEC_PRODUCT).on(GOODS_SPEC_PRODUCT.PRD_ID.eq(CART.PRODUCT_ID))
                .where(CART.USER_ID.eq(userId)).orderBy(CART.CREATE_TIME.asc()).fetch();
        List<WxAppCartGoods> cartGoodsList = records.into(WxAppCartGoods.class);
        List<Integer> productIdList = records.getValues(GOODS_SPEC_PRODUCT.PRD_ID);
        List<Integer> goodsIdList = records.getValues(GOODS.GOODS_ID).stream().distinct().collect(Collectors.toList());

        // 数据校验计算
        cartListVo = cartProductToGroup(cartGoodsList,productIdList, goodsIdList,userId);


        return cartListVo;

    }


    /**
     * 拼装购物车数据
     *
     *
     * @param cartGoodsList
     * @param productIdList
     * @param goodsIdList
     * @param userId        用户id
     * @return
     */
    private WxAppCartListVo cartProductToGroup(List<WxAppCartGoods> cartGoodsList, List<Integer> productIdList, List<Integer> goodsIdList, Integer userId) {
        WxAppCartListVo cartListVo = new WxAppCartListVo();
        List<WxAppCartGoods> invalidCartList;
        List<WxAppCartGoods> validCartList;
        // 在售的,未删除的
        validCartList = cartGoodsList.stream().filter(cartGoods ->
                cartGoods.getDelFlag().equals(DelFlag.NORMAL_VALUE)&& cartGoods.getIsOnSale().equals(Integer.valueOf(1).byteValue()))
                .collect(Collectors.toList());
        // 不存在,已下架
        invalidCartList = cartGoodsList.stream().filter(cartGoods -> {
            if (cartGoods.getGoodsId() == null || cartGoods.getPrdId() == null) {
                return true;
            }
            return cartGoods.getDelFlag() == 1 || cartGoods.getIsOnSale() == 0;
        }).collect(Collectors.toList());
        // 会员相关
        List<UserCardGradePriceBo> userCartGradePrice = userCardService.getUserCartGradePrice(userId, productIdList);
        Set<Integer> userCardExclusive = userCardService.getUserCardExclusiveGoodsIds(userId, cartGoodsList);
        for (int i=0 ;i<validCartList.size();i++){
            WxAppCartGoods cartGoods =validCartList.get(i);
            //会员专享
            if (cartGoods.getIsCardExclusive().equals(GoodsConstant.CARD_EXCLUSIVE)){
                if (!userCardExclusive.contains(cartGoods.getGoodsId())){
                    removeCartProductById(userId,cartGoods.getRecId());
                    validCartList.remove(cartGoods);
                    i--;
                    continue;
                }
            }
            // 已售罄
            if (cartGoods.getPrdNumber() < cartGoods.getLimitBuyNum() || cartGoods.getPrdNumber() <= 0 || cartGoods.getIsOnSale() == 0) {
                cartGoods.setIsChecked((byte) 0);
            }
            // 会员等级
            userCartGradePrice.forEach(gradePrice -> {
                if (cartGoods.getPrdId().equals(gradePrice.getPrdId())) {
                    cartGoods.setActivityPrice(gradePrice.getGradePrice());
                    cartGoods.setActivityType((byte) 1);
                }
            });
        }
        cartListVo.setCartGoodsList(validCartList);
        cartListVo.setInvalidCartList(invalidCartList);
        return cartListVo;
    }

    public int setCartGoodsFail(Integer recId) {
        return db().update(CART).set(CART.ACTION, (byte) 0).set(CART.IDENTITY_ID, 0).set(CART.EXTEND_ID, 0).set(CART.IS_CHECKED, (byte) 0)
                .where(CART.REC_ID.eq(recId.longValue())).execute();
    }

    /**
     * 检查商品
     *
     * @param productId
     * @param goodsNumber
     * @param storeId
     */
    public ResultMessage checkProductNumber(Integer productId, Integer goodsNumber, Integer storeId) {
        GoodsSpecProductRecord product = goodsSpecProductService.getStoreProductByProductIdAndStoreId(productId, storeId);
        // 商品失效
        if (product == null || DelFlag.DISABLE_VALUE.equals(product.getDelFlag())) {
            return ResultMessage.builder().jsonResultCode(JsonResultCode.CODE_CART_GOODS_NO_LONGER_VALID).message(1).message(2).build();
        }
        // 库存不足
        if (product.getPrdNumber() < goodsNumber) {
            return ResultMessage.builder().jsonResultCode(JsonResultCode.CODE_CART_THERE_IS_STILL_INVENTORY).message(product.getPrdNumber()).build();
        }
        //最小购买限制
        GoodsRecord goodsRecord = db().select(GOODS.LIMIT_BUY_NUM, GOODS.LIMIT_MAX_NUM, GOODS.UNIT).from(GOODS).where(GOODS.GOODS_ID.eq(product.getGoodsId())).fetchOneInto(GoodsRecord.class);
        if (goodsRecord.getLimitBuyNum() > 0 && goodsRecord.getLimitBuyNum() > goodsNumber) {
            return ResultMessage.builder().jsonResultCode(JsonResultCode.CODE_CART_MINIMUM_PURCHASE).message(1).message(goodsRecord.getUnit()).build();
        }
        // 最大购买限制
        if (goodsRecord.getLimitMaxNum() > 0 && goodsRecord.getLimitMaxNum() < goodsNumber) {
            return ResultMessage.builder().jsonResultCode(JsonResultCode.CODE_CART_MAXIMUM_PURCHASE).message(1).message(goodsRecord.getUnit()).build();
        }
        // todo    首单限购
        return ResultMessage.builder().flag(true).build();
    }

    /**
     * 检查商品
     *
     * @param productId
     * @param goodsNumber
     * @return
     */
    public ResultMessage checkProductNumber(Integer productId, Integer goodsNumber) {
        return checkProductNumber(productId, goodsNumber, 0);
    }

    /**
     * 获取订单指定商品数量
     *
     * @param userId
     * @param prdId
     * @return
     */
    public Short getCartProductNumber(Integer userId, Integer prdId) {
        Record1<Short> product = db().select(CART.GOODS_NUMBER).from(CART).where(CART.USER_ID.eq(userId)).and(CART.PRODUCT_ID.eq(prdId)).and(CART.EXTEND_ID.eq(0))
                .and(CART.STORE_ID.eq(0)).fetchOne();
        if (product != null) {
            return product.component1();
        }
        return 0;
    }

    /**
     * 添加商品到购物车
     *
     * @param userId
     * @param prdId
     * @param goodsNumber
     * @return
     */
    public Long addSpecProduct(Integer userId, Integer prdId, Integer goodsNumber) {
        CartRecord cartRecord = db().selectFrom(CART).where(CART.USER_ID.eq(userId).and(CART.PRODUCT_ID.eq(prdId))).fetchOne();
        if (cartRecord == null) {
            Record goodsProduct = goodsService.getGoodsByProductId(prdId);
            GoodsRecord goodsRecord = goodsProduct.into(GoodsRecord.class);
            GoodsSpecProductRecord productRecord = goodsProduct.into(GoodsSpecProductRecord.class);
            cartRecord = db().newRecord(CART);
            cartRecord.setUserId(userId);
            cartRecord.setGoodsSn(goodsRecord.getGoodsSn());
            cartRecord.setGoodsNumber(goodsNumber.shortValue());
            cartRecord.setGoodsId(goodsRecord.getGoodsId());
            cartRecord.setGoodsName(goodsRecord.getGoodsName());
            cartRecord.setGoodsNumber(goodsRecord.getGoodsNumber().shortValue());
            cartRecord.setProductId(prdId);
            cartRecord.setMarketPrice(productRecord.getPrdMarketPrice());
            cartRecord.setGoodsPrice(productRecord.getPrdPrice());
            cartRecord.setIsChecked((byte) 1);
            cartRecord.insert();
        } else {
            cartRecord.setGoodsNumber((short) (goodsNumber + cartRecord.getGoodsNumber()));
            cartRecord.update();
        }
        return cartRecord.getRecId();
    }

    /**
     * 删除购物车商品
     *
     * @param userId
     * @param recId
     * @return
     */
    public int removeCartProductById(Integer userId, Long recId) {
        return db().delete(CART).where(CART.USER_ID.eq(userId)).and(CART.REC_ID.eq(recId)).execute();
    }

    /**
     * 改变购物车商品数量
     *
     * @param userId
     * @param storeId
     * @param productId
     * @param goodsNumber
     * @return
     */
    public ResultMessage changeGoodsNumber(Integer userId, Integer storeId, Integer productId, Integer goodsNumber) {
        //校验
        ResultMessage resultMessage = checkProductNumber(productId, goodsNumber);
        if (resultMessage.getFlag()) {
            db().update(CART).set(CART.GOODS_NUMBER, goodsNumber.shortValue()).set(CART.IS_CHECKED, (byte) 1).where(CART.USER_ID.eq(userId))
                    .and(CART.STORE_ID.eq(storeId)).and(CART.PRODUCT_ID.eq(productId)).execute();
        }
        return resultMessage;
    }


    /**
     * 根据用户和店铺回去购物车商品数量
     *
     * @param user
     * @param storeId
     * @return
     */
    public int getUserCartGoodsNumber(Integer user, Integer storeId) {
        BigDecimal goodsNum = db().select(DSL.sum(CART.GOODS_NUMBER)).from(CART).leftJoin(STORE_GOODS).on(CART.PRODUCT_ID.eq(STORE_GOODS.PRD_ID))
                .where(STORE_GOODS.IS_ON_SALE.eq((byte) 1)).and(STORE_GOODS.STORE_ID.eq(storeId))
                .and(CART.STORE_ID.eq(storeId)).and(CART.USER_ID.eq(user)).fetchOne().component1();
        return goodsNum.intValue();
    }
}
