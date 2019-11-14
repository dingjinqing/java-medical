package com.vpu.mp.service.shop.recommend;

import static com.vpu.mp.db.shop.tables.ShopCfg.SHOP_CFG;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.db.shop.tables.records.CartRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.recommend.collect.JsonCollectBean;
import com.vpu.mp.service.pojo.shop.recommend.collect.SkuInfo;
import com.vpu.mp.service.pojo.shop.recommend.collect.SkuProductList;
import com.vpu.mp.service.pojo.shop.recommend.order.JsonRootBean;
import com.vpu.mp.service.saas.categroy.SysCateService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.user.cart.CartService;
import com.vpu.mp.service.shop.user.user.UserService;
import com.vpu.mp.service.wechat.api.impl.WxOpenMaServiceExtraImpl;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenResult;

/**
 * 好物圈关于收藏
 * 
 * @author zhaojianqiang
 *
 *         2019年11月13日 下午4:00:35
 */
@Slf4j
@Service
public class CollectionMallService extends ShopBaseService {
	@Autowired
	private UserService userService;
	private static final Byte one = 1;
	private static final Byte two = 1;
	private static final String FORTYONE = "41";
	@Autowired
	private CartService cartService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private SysCateService sysCateService;

	/**
	 * 同步用户添加商品到微信购物单
	 * 
	 * @param userId
	 * @param cartRecIds
	 * @return
	 */
	public Boolean addCartRows(Integer userId, List<Long> cartRecIds) {
		String openId = check(userId);
		if (null == openId) {
			return false;
		}

		JsonCollectBean param = new JsonCollectBean();
		List<SkuProductList> skuProductList = new ArrayList<SkuProductList>();
		for (Long recid : cartRecIds) {
			SkuProductList skuProduct = new SkuProductList();
			CartRecord cart = cartService.getInfoByRecid(recid);
			if (null == cart) {
				continue;
			}
			Record productInfo = goodsService.getGoodsByProductId(cart.getProductId());
			if (null == productInfo) {
				continue;
			}
			GoodsRecord goodsRecord = productInfo.into(GoodsRecord.class);
			GoodsSpecProductRecord productRecord = productInfo.into(GoodsSpecProductRecord.class);
			skuProduct.setItemCode(String.valueOf(cart.getGoodsId()));
			skuProduct.setTitle(cart.getGoodsName());
			skuProduct.setDesc(cart.getGoodsName());
			skuProduct.setCategoryList(sysCateService.getCategories(goodsRecord.getCatId()));
			skuProduct.setImageList(goodsService.getGoodsImageList(goodsRecord.getGoodsId()));
			skuProduct.setSrcWxappPath("/pages/item/item?goods_id=" + goodsRecord.getGoodsId());
			// 非高并发更新数据的场景不建议填写此字段
			skuProduct.setVersion((int) DateUtil.getLocalDateTime().getTime());

			SkuInfo skuInfo = new SkuInfo();
			skuInfo.setSkuId(String.valueOf(productRecord.getPrdId()));
			skuInfo.setPrice(productRecord.getPrdPrice().multiply(new BigDecimal(100)));
			skuInfo.setOriginalPrice(productRecord.getPrdMarketPrice().compareTo(BigDecimal.ZERO) == 1
					? productRecord.getPrdMarketPrice().multiply(new BigDecimal(100))
					: productRecord.getPrdPrice().multiply(new BigDecimal(100)));
			skuInfo.setStatus(goodsRecord.getIsOnSale() == one ? one : two);
			skuInfo.setVersion((int) DateUtil.getLocalDateTime().getTime());
			skuInfo.setSkuAttrList(goodsService.goodsSpecProductService.getSkuAttrList(productRecord.getPrdDesc()));
			skuProduct.setSkuInfo(skuInfo);
			skuProductList.add(skuProduct);

		}
		if(skuProductList.size()>0) {
			param.setSkuProductList(skuProductList);
			param.setUserOpenId(openId);
			WxOpenResult result = addshoppinglistAdd(param);
			return result.isSuccess();			
		}
		return false;

	}

	/**
	 * 删除微信购物单商品
	 * @param userId
	 * @param cartRecIds
	 * @return
	 */
	public Boolean clearCartRows(Integer userId, List<Long> cartRecIds) {
		String openId = check(userId);
		if (null == openId) {
			return false;
		}
		JsonCollectBean param = new JsonCollectBean();
		param.setUserOpenId(openId);
		List<SkuProductList> skuProductList = new ArrayList<SkuProductList>();
		for (Long recid : cartRecIds) {
			SkuProductList skuProduct = new SkuProductList();
			CartRecord cart = cartService.getInfoByRecid(recid);
			if (null == cart) {
				continue;
			}
			skuProduct.setItemCode(String.valueOf(cart.getGoodsId()));
			skuProduct.setSkuId(String.valueOf(cart.getProductId()));
			skuProductList.add(skuProduct);
		}
		param.setSkuProductList(skuProductList);
		WxOpenResult result = addshoppinglistDel(param);
		return result.isSuccess();

	}

	/**
	 * 删除更新收藏
	 * 
	 * @param jsonRootBean
	 * @return
	 */
	private WxOpenResult addshoppinglistDel(JsonCollectBean jsonRootBean) {
		WxOpenMaServiceExtraImpl maService = open.getMaExtService();
		MpAuthShopRecord shop = saas.shop.mp.getAuthShopByShopId(getShopId());
		WxOpenResult result = null;
		try {
			String jsonNotNull = Util.toJsonNotNull(jsonRootBean);
			log.info("发送的报文" + jsonNotNull);
			result = maService.addshoppinglistDel(shop.getAppId(), jsonNotNull);
			log.info(" 添加好物圈订单");
			log.info(result.getErrmsg(), result.getErrcode());
		} catch (WxErrorException e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 更新收藏
	 * 
	 * @param jsonRootBean
	 * @return
	 */
	private WxOpenResult addshoppinglistAdd(JsonCollectBean jsonRootBean) {
		WxOpenMaServiceExtraImpl maService = open.getMaExtService();
		MpAuthShopRecord shop = saas.shop.mp.getAuthShopByShopId(getShopId());
		WxOpenResult result = null;
		try {
			String jsonNotNull = Util.toJsonNotNull(jsonRootBean);
			log.info("发送的报文" + jsonNotNull);
			result = maService.addshoppinglistAdd(shop.getAppId(), jsonNotNull);
			log.info(" 添加好物圈订单");
			log.info(result.getErrmsg(), result.getErrcode());
		} catch (WxErrorException e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	private String get(String key) {
		return db().select().from(SHOP_CFG).where(SHOP_CFG.K.eq(key)).fetchAny(SHOP_CFG.V);
	}

	/**
	 * 校验
	 * 
	 * @param userId
	 * @return
	 */
	private String check(Integer userId) {
		String cfg = get("wx_shopping_list_enbaled");
		if (StringUtils.isEmpty(cfg)) {
			log.info("wx_shopping_list_enbaled是空的");
			return null;
		} else {
			if (cfg.equals("0")) {
				log.info("wx_shopping_list_enbaled为0");
				return null;
			}
		}
		if (!hasShoppingListAuthority()) {
			log.info("没有微信购物单授权权限");
			return null;
		}
		UserRecord user = userService.getUserByUserId(userId);
		String openId = user.getWxOpenid();
		if (StringUtils.isEmpty(openId)) {
			log.info("userId为" + userId + "用户openid为空");
			return null;
		}
		return openId;
	}

	/**
	 * 是否有微信购物单授权权限
	 * 
	 * @return
	 */
	private boolean hasShoppingListAuthority() {
		MpAuthShopRecord mp = saas().shop.mp.getAuthShopByShopId(getShopId());
		if (null == mp) {
			log.info("MpAuthShop是空的");
			return false;
		}
		if (!mp.getIsAuthOk().equals(one)) {
			log.info("IsAuthOk不为1，为" + mp.getIsAuthOk());
			return false;
		}
		String funcInfo = mp.getFuncInfo();
		log.info("权限有" + funcInfo);
		String[] funcInfoList = funcInfo.split(",");
		for (String str : funcInfoList) {
			if (str.equals(FORTYONE)) {
				return true;
			}
		}
		log.info("没有41权限");
		return false;
	}
}
