package com.vpu.mp.service.shop.user.cart;

import com.vpu.mp.db.shop.tables.records.CartRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.wxapp.cart.CartConstant;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartGoods;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.shop.activity.factory.CartProcessorContext;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.member.UserCardService;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.Tables.STORE_GOODS;
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
    private GoodsSpecProductService goodsSpecProductService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CartProcessorContext cartProcessor;
    /**
     * 用户会员卡
     */
    @Autowired
    private UserCardService userCardService;


    /**
     * 购物车列表
     *
     * @param userId 用户id
     * @return 购物车列表
     */
    public WxAppCartBo getCartList(Integer userId) {
        return getCartList(userId,null);
    }

    /**
     * 购物车列表
     * @param userId userId 用户ID
     * @param goodsIds goodsId 活动商品
     * @return null
     */
    public WxAppCartBo getCartList(Integer userId, List<Integer> goodsIds){
        List<Integer> productIdList ;
        List<Integer> goodsIdList;
        // 查询购物车记录
        Result<CartRecord> cartRecords = getCartRecordsByUserId(userId);
        List<WxAppCartGoods> appCartGoods = cartRecords.into(WxAppCartGoods.class);
        //商品
        goodsIdList =cartRecords.getValues(CART.GOODS_ID).stream().distinct().collect(Collectors.toList());
        if (goodsIds!=null){
            goodsIdList.retainAll(goodsIds);
            productIdList =new ArrayList<>(goodsIdList.size());
            cartRecords.forEach(cartRecord -> {
                if (goodsIdList.contains(cartRecord.getGoodsId())){
                    productIdList.add(cartRecord.getProductId());
                }
            });
            appCartGoods = appCartGoods.stream().filter(cartGoods -> goodsIdList.contains(cartGoods.getGoodsId())).collect(Collectors.toList());
        }else {
            productIdList =cartRecords.getValues(CART.PRODUCT_ID);
        }
        Map<Integer, GoodsRecord> goodsRecordMap = goodsService.getGoodsRecordByIds(goodsIdList);
        //规格
        Map<Integer, GoodsSpecProductRecord> productRecordMap = goodsSpecProductService.goodsSpecProductByIds(productIdList);
        //初始化购物车数据
        appCartGoods.forEach(cartGoods->{
            cartGoods.setGoodsRecord(goodsRecordMap.get(cartGoods.getGoodsId()));
            cartGoods.setProductRecord(productRecordMap.get(cartGoods.getProductId()));
        });
        //购物车业务数据
        WxAppCartBo cartBo = WxAppCartBo.builder()
                .userId(userId).date(DateUtil.getLocalDateTime())
                .productIdList(productIdList).goodsIdList(goodsIdList)
                .cartGoodsList(appCartGoods).invalidCartList(new ArrayList<>()).build();
        if (0 == appCartGoods.size()) {
            return null;
        }
        cartProcessor.executeCart(cartBo);
        return cartBo;
    }

    /**
     * 获取购物车记录
     * @param userId
     * @return
     */
    private Result<CartRecord> getCartRecordsByUserId(Integer userId) {
        return db().selectFrom(CART).where(CART.USER_ID.eq(userId)).orderBy(CART.CART_ID.desc()).fetch();
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
        if (product == null) {
            return ResultMessage.builder().jsonResultCode(JsonResultCode.CODE_CART_GOODS_NO_LONGER_VALID).message(1).message(2).build();
        }
        // 库存不足
        if (product.getPrdNumber() < goodsNumber) {
            return ResultMessage.builder().jsonResultCode(JsonResultCode.CODE_CART_THERE_IS_STILL_INVENTORY).message(product.getPrdNumber()).build();
        }
        //最小购买限制
        GoodsRecord goodsRecord = db().select(GOODS.LIMIT_BUY_NUM, GOODS.LIMIT_MAX_NUM, GOODS.UNIT).from(GOODS).where(GOODS.GOODS_ID.eq(product.getGoodsId())).fetchOneInto(GoodsRecord.class);
        if (goodsRecord.getLimitBuyNum() > 0 && goodsRecord.getLimitBuyNum() > goodsNumber) {
            return ResultMessage.builder().jsonResultCode(JsonResultCode.CODE_CART_MINIMUM_PURCHASE).message(goodsRecord.getLimitBuyNum()).message(goodsRecord.getUnit()).build();
        }
        // 最大购买限制
        if (goodsRecord.getLimitMaxNum() > 0 && goodsRecord.getLimitMaxNum() < goodsNumber) {
            return ResultMessage.builder().jsonResultCode(JsonResultCode.CODE_CART_MAXIMUM_PURCHASE).message(goodsRecord.getLimitMaxNum()).message(goodsRecord.getUnit()).build();
        }
        if (goodsNumber==0){
            return ResultMessage.builder().jsonResultCode(JsonResultCode.CODE_CART_MINIMUM_PURCHASE).message(0).message(goodsRecord.getUnit()).build();
        }
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
        Record1<Short> product = db().select(CART.CART_NUMBER).from(CART).where(CART.USER_ID.eq(userId)).and(CART.PRODUCT_ID.eq(prdId)).and(CART.EXTEND_ID.eq(0))
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
    public Integer addSpecProduct(Integer userId, Integer prdId, Integer goodsNumber) {
        CartRecord cartRecord = db().selectFrom(CART).where(CART.USER_ID.eq(userId).and(CART.PRODUCT_ID.eq(prdId))).fetchOne();
        if (cartRecord == null) {
            Record goodsProduct = goodsService.getGoodsByProductId(prdId);
            GoodsRecord goodsRecord = goodsProduct.into(GoodsRecord.class);
            GoodsSpecProductRecord productRecord = goodsProduct.into(GoodsSpecProductRecord.class);
            cartRecord = db().newRecord(CART);
            cartRecord.setUserId(userId);
            cartRecord.setGoodsSn(goodsRecord.getGoodsSn());
            cartRecord.setCartNumber(goodsNumber.shortValue());
            cartRecord.setGoodsId(goodsRecord.getGoodsId());
            cartRecord.setGoodsName(goodsRecord.getGoodsName());
            cartRecord.setPrdDesc(productRecord.getPrdDesc());
            cartRecord.setProductId(prdId);
            cartRecord.setGoodsPrice(productRecord.getPrdPrice());
            cartRecord.setOriginalPrice(productRecord.getPrdPrice());
            cartRecord.setIsChecked(CartConstant.CART_IS_CHECKED);
            cartRecord.insert();
        } else {
            cartRecord.setCartNumber((short) (goodsNumber + cartRecord.getCartNumber()));
            cartRecord.update();
        }
        return cartRecord.getCartId();
    }

    /**
     * 删除购物车商品
     *
     * @param userId
     * @param cartId
     */
    public void removeCartProductById(Integer userId, Integer cartId) {
        db().delete(CART).where(CART.USER_ID.eq(userId)).and(CART.CART_ID.eq(cartId)).execute();
    }

    public int removeCartProductByIds(Integer userId, List<Integer> recIds) {
        return db().delete(CART).where(CART.USER_ID.eq(userId)).and(CART.CART_ID.in(recIds)).execute();
    }

    /**
     * 删除购物车商品
     *
     * @param userId     用户
     * @param productIds 规格id
     * @return 1
     */
    public int removeCartByProductIds(Integer userId, List<Integer> productIds) {
        return db().delete(CART).where(CART.USER_ID.eq(userId)).and(CART.PRODUCT_ID.in(productIds)).execute();
    }

    /**
     * 删除购物车
     * @param userId 用户
     * @param storeId 门店
     * @return
     */
    public int removeCartIsCheckedGoods(Integer userId,Integer storeId){
        return db().delete(CART).where(CART.USER_ID.eq(userId)).execute();
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
            db().update(CART).set(CART.CART_NUMBER, goodsNumber.shortValue()).set(CART.IS_CHECKED, (byte) 1).where(CART.USER_ID.eq(userId))
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
        BigDecimal goodsNum = db().select(DSL.sum(CART.CART_NUMBER)).from(CART).leftJoin(STORE_GOODS).on(CART.PRODUCT_ID.eq(STORE_GOODS.PRD_ID))
                .where(STORE_GOODS.IS_ON_SALE.eq((byte) 1)).and(STORE_GOODS.STORE_ID.eq(storeId))
                .and(CART.STORE_ID.eq(storeId)).and(CART.USER_ID.eq(user)).fetchOne().component1();
        return goodsNum.intValue();
    }

    /**
     * 根据recid获取所有信息
     *
     * @param carId
     * @return
     */
    public CartRecord getInfoByRecid(Integer carId) {
        return db().selectFrom(CART).where(CART.CART_ID.eq(carId)).fetchAny();
    }

    /**
     * 购物车中的商品选择状态
     *
     * @param userId
     * @param carId
     * @return
     */
    public int switchCheckedProduct(Integer userId, Integer carId, Byte isChecked) {
        return db().update(CART).set(CART.IS_CHECKED, isChecked)
                .where(CART.USER_ID.eq(userId))
                .and(CART.CART_ID.eq(carId)).execute();

    }
    /**
     * 购物车中的商品选择状态
     *
     * @param userId
     * @param productId
     * @return
     */
    public int switchCheckedByProductId(Integer userId, Integer productId, Byte isChecked) {
        return db().update(CART).set(CART.IS_CHECKED, isChecked)
                .where(CART.USER_ID.eq(userId))
                .and(CART.PRODUCT_ID.eq(productId)).execute();

    }

    /**
     * 修改选中状态
     *
     * @param userId
     * @param carIds    id
     * @param isChecked 1 选中 0 没选中
     * @return
     */
    public int switchCheckedProduct(Integer userId, List<Integer> carIds, Byte isChecked) {
        return db().update(CART).set(CART.IS_CHECKED, isChecked)
                .where(CART.USER_ID.eq(userId))
                .and(CART.CART_ID.in(carIds)).execute();
    }

    /**
     * 获取商品数量
     *
     * @param userId
     * @param goodsId 商品id
     * @return num
     */
    public Integer cartGoodsNum(Integer userId, Integer goodsId) {
        return db().select(DSL.sum(CART.CART_NUMBER)).from(CART)
                .where(CART.USER_ID.eq(userId))
                .and(CART.GOODS_ID.eq(goodsId)).fetchOneInto(Integer.class);
    }

    /**
     * 购物车商品数量
     * @param userId 用户ID
     * @return 商品数量
     */
    public Integer cartGoodsNum(Integer userId){
        return db().select(DSL.sum(CART.CART_NUMBER)).from(CART)
                .where(CART.USER_ID.eq(userId)).fetchOneInto(Integer.class);
    }

    /**
     * 获取当前购物车选中商品
     */
    public List<OrderBeforeParam.Goods> getCartCheckedData(Integer userId, Integer storeId){
        //TODO 后期加参数
        return db().select(CART.GOODS_ID, CART.PRODUCT_ID, CART.CART_NUMBER.as("goodsNumber"))
                .from(CART)
                .where(CART.IS_CHECKED.eq(CartConstant.CART_IS_CHECKED))
                .and(CART.USER_ID.eq(userId))
                .and(CART.STORE_ID.eq(storeId))
                .orderBy(CART.CART_ID.desc())
                .fetchInto(OrderBeforeParam.Goods.class);
    }
}
