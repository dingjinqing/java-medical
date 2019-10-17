package com.vpu.mp.service.shop.user.cart;

import com.vpu.mp.db.shop.tables.UserRebatePrice;
import com.vpu.mp.db.shop.tables.records.CartRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.wxapp.cart.WxAppCartListParam;
import com.vpu.mp.service.shop.config.FirstSpecialConfigService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.market.firstspecial.FirstSpecialService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.Cart.CART;


/**
 *  购物车
 * @author 孔德成
 * @date 2019/10/14 16:39
 */
public class CartService extends ShopBaseService {

    @Autowired
    private   UserCartService userCartService;
    @Autowired
    private GoodsSpecProductService goodsSpecProductService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private FirstSpecialService firstSpecialService;
    @Autowired
    private FirstSpecialConfigService firstSpecialConfigService;

    public void getCartList(WxAppCartListParam param) {
        Result<? extends Record> records = db().select(CART.REC_ID,CART.USER_ID, CART.GOODS_ID, CART.GOODS_NAME,
                 CART.PRODUCT_ID, CART.IS_CHECKED, CART.GOODS_SPECS, CART.GOODS_PRICE, CART.ACTION, CART.IDENTITY_ID,
                CART.EXTEND_ID, CART.GOODS_NUMBER, GOODS.GOODS_IMG, GOODS.LIMIT_BUY_NUM, GOODS.LIMIT_MAX_NUM, GOODS.GOODS_TYPE,
                GOODS.DEL_FLAG, GOODS.IS_ON_SALE, GOODS_SPEC_PRODUCT.PRD_NUMBER, GOODS_SPEC_PRODUCT.DEL_FLAG, GOODS_SPEC_PRODUCT.PRD_IMG).from(CART)
                .leftJoin(GOODS_SPEC_PRODUCT).on(GOODS_SPEC_PRODUCT.PRD_ID.eq(CART.PRODUCT_ID))
                .leftJoin(GOODS).on(GOODS.GOODS_ID.eq(CART.GOODS_ID))
                .where(CART.USER_ID.eq(param.getUserId())).orderBy(CART.CREATE_TIME.asc()).fetch();
        cartProductToGroup(records,param.getUserId(),param.getStoreId());

    }


    /**
     *  拼装购物车数据
     * @param records  购物车商品数据
     * @param userId   用户id
     * @param storeId  门店id
     */
    private void cartProductToGroup(Result<? extends  Record> records,Integer userId,Integer storeId){
        // 商品id
        List<Integer> productIds = records.getValues(CART.PRODUCT_ID);
        List<Integer> goodsIds = records.getValues(CART.GOODS_ID).stream().distinct().collect(Collectors.toList());
        // todo  用户返利 分销
        List<UserRebatePrice> rebatePrices;
        List<Integer> rebateProducts;
        // 店铺价格 数量
        Result<? extends Record> storeProducts= goodsSpecProductService.getStoreProductAll(productIds, storeId);
        Map<Integer, Integer> prdGoodsIdMap = storeProducts.intoMap(GOODS_SPEC_PRODUCT.PRD_ID, GOODS_SPEC_PRODUCT.GOODS_ID);
        Map<Integer, BigDecimal> prdPriceMap = storeProducts.intoMap(GOODS_SPEC_PRODUCT.PRD_ID, GOODS_SPEC_PRODUCT.PRD_PRICE);
        Map<Integer, Integer> prdNumberMap = storeProducts.intoMap(GOODS_SPEC_PRODUCT.PRD_ID, GOODS_SPEC_PRODUCT.PRD_NUMBER);
        // 商品类型
        Map<Integer, Byte> goodsIdsMap = goodsService.getGoodsByIdsAndFields(goodsIds,GOODS.GOODS_ID,GOODS.GOODS_TYPE).intoMap(GOODS.GOODS_ID,GOODS.GOODS_TYPE);

        // 首单特惠 firstSpecial
        /*获取用户是否是新用户*/
        boolean isNewUser = orderInfoService.isNewUser(userId);
        if (isNewUser){
            // 活动商品规格
            List<Integer> fsProductIds = firstSpecialService.getOnGoingPrdIds();
            // 首单数量限制
            Integer fsLimit = firstSpecialConfigService.getFirstLimitGoods();
            records.stream().forEach(r->{

            });
        }

    }


    /**
     *  检查商品库存
     * @param productId
     * @param goodsNumber
     * @param storeId
     */
    public ResultMessage checkProductNumber(Integer productId, Integer goodsNumber, Integer storeId){

        GoodsSpecProductRecord product = goodsSpecProductService.getStoreProductByProductIdAndStoreId(productId, storeId);
        // 商品失效
        if (product==null|| DelFlag.DISABLE_VALUE.equals(product.getDelFlag())){
            return ResultMessage.builder().jsonResultCode(JsonResultCode.WX_MA_NEED_UPLOADCODE).message(1).message(2).build();
        }
        // 库存不足
        if (product.getPrdNumber()<goodsNumber){
            return ResultMessage.builder().jsonResultCode(JsonResultCode.WX_MA_NEED_UPLOADCODE).message(product.getPrdNumber()).build();
        }
        //最小购买限制
        GoodsRecord goodsRecord = (GoodsRecord) db().select(GOODS.LIMIT_BUY_NUM, GOODS.LIMIT_MAX_NUM, GOODS.UNIT).from(GOODS).where(GOODS.GOODS_ID.eq(product.getGoodsId())).fetchOne();
        if (goodsRecord.getLimitBuyNum()>0&&goodsRecord.getLimitBuyNum()>goodsNumber){
            return ResultMessage.builder().jsonResultCode(JsonResultCode.WX_MA_NEED_UPLOADCODE).message(1).message(goodsRecord.getUnit()).build();
        }
        // 最大购买限制
        if (goodsRecord.getLimitMaxNum()>0&&goodsRecord.getLimitMaxNum()<goodsNumber){
            return ResultMessage.builder().jsonResultCode(JsonResultCode.WX_MA_NEED_UPLOADCODE).message(1).message(goodsRecord.getUnit()).build();
        }
         // todo    首单限购
        return ResultMessage.builder().flag(true).build();
    }


    /**
     * 获取订单指定商品数量
     * @param userId
     * @param prdId
     * @return
     */
    public Short getCartProductNumber(Integer userId, Integer prdId) {
        Record1<Short> product = db().select(CART.GOODS_NUMBER).from(CART).where(CART.USER_ID.eq(userId)).and(CART.PRODUCT_ID.eq(prdId)).and(CART.EXTEND_ID.eq(0))
                .and(CART.STORE_ID.eq(0)).fetchOne();
        if (product!=null){
            return  product.component1();
        }
        return 0;
    }

    /**
     * 添加商品到购物车
     * @param userId
     * @param prdId
     * @param goodsNumber
     * @return
     */
    public Long addSpecProduct(Integer userId, Integer prdId, Integer goodsNumber) {
        CartRecord cartRecord = db().selectFrom(CART).where(CART.USER_ID.eq(userId).and(CART.PRODUCT_ID.eq(prdId))).fetchOne();
        if (cartRecord==null){
            Record goodsProduct = goodsService.getGoodsByProductId(prdId);
            GoodsRecord goodsRecord =goodsProduct.into(GoodsRecord.class);
            GoodsSpecProductRecord productRecord =goodsProduct.into(GoodsSpecProductRecord.class);
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
        }else {
            cartRecord.setGoodsNumber((short) (goodsNumber+cartRecord.getGoodsNumber()));
            cartRecord.update();
        }
        return cartRecord.getRecId();
    }

    /**
     * 删除购物车商品
     * @param userId
     * @param recId
     * @return
     */
    public int removeCartProductById(Integer userId, Integer recId) {
        return db().delete(CART).where(CART.USER_ID.eq(userId)).and(CART.REC_ID.eq(recId.longValue())).execute();
    }

    /**
     * 改变购物车商品数量
     * @param userId
     * @param storeId
     * @param productId
     * @param goodsNumber
     * @return
     */
    public ResultMessage changeGoodsNumber(Integer userId, Integer storeId, Integer productId, Integer goodsNumber) {
        //校验
        ResultMessage resultMessage = checkProductNumber(productId, goodsNumber, 0);
        if (resultMessage.getFlag()){
             db().update(CART).set(CART.GOODS_NUMBER,goodsNumber.shortValue()).set(CART.IS_CHECKED,(byte)1).where(CART.USER_ID.eq(userId))
                    .and(CART.STORE_ID.eq(storeId)).and(CART.PRODUCT_ID.eq(productId)).execute();
        }
        return resultMessage;
    }
}
