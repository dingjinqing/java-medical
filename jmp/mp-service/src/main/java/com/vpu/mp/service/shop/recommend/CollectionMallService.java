package com.vpu.mp.service.shop.recommend;

import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.db.shop.tables.records.CartRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant.TaskJobEnum;
import com.vpu.mp.service.pojo.shop.recommend.SendCollectBean;
import com.vpu.mp.service.pojo.shop.recommend.collect.JsonCollectBean;
import com.vpu.mp.service.pojo.shop.recommend.collect.SkuInfo;
import com.vpu.mp.service.pojo.shop.recommend.collect.SkuProductList;
import com.vpu.mp.service.saas.categroy.SysCateService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.user.cart.CartService;
import com.vpu.mp.service.wechat.api.impl.WxOpenMaServiceExtraImpl;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenResult;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 好物圈关于收藏
 *
 * @author zhaojianqiang
 *
 *         2019年11月13日 下午4:00:35
 */
@Slf4j
@Service
public class CollectionMallService extends ShopMallBaseService {
	private static final Byte ONE = 1;
	private static final Byte TWO = 2;
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
	public Boolean addCartRows(Integer userId, List<Integer> cartRecIds) {
		String openId = check(userId);
		if (null == openId) {
			return false;
		}

		JsonCollectBean param = new JsonCollectBean();
		List<SkuProductList> skuProductList = new ArrayList<SkuProductList>();
		for (Integer recid : cartRecIds) {
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
			skuInfo.setStatus(ONE.equals(goodsRecord.getIsOnSale()) ? ONE : TWO);
			skuInfo.setVersion((int) DateUtil.getLocalDateTime().getTime());
			skuInfo.setSkuAttrList(goodsService.goodsSpecProductService.getSkuAttrList(productRecord.getPrdDesc()));
			skuProduct.setSkuInfo(skuInfo);
			skuProductList.add(skuProduct);

		}
		logger().info("skuProductList的大小"+skuProductList.size());
		if(skuProductList.size()>0) {
			param.setSkuProductList(skuProductList);
			param.setUserOpenId(openId);
			SendCollectBean bean=new SendCollectBean(1, param,getShopId(),null);
			saas.taskJobMainService.dispatchImmediately(bean,SendCollectBean.class.getName(),getShopId(),TaskJobEnum.WX_ADDSHOPPINGLIST.getExecutionType());
			//WxOpenResult result = addshoppinglistAdd(param);
			//return result.isSuccess();
			return true;
		}
		logger().info("没有数据");
		return false;

	}

	/**
	 * 删除微信购物单商品
	 * @param userId
	 * @param cartRecIds
	 * @return
	 */
	public Boolean clearCartRows(Integer userId, List<Integer> cartRecIds) {
		String openId = check(userId);
		if (null == openId) {
			return false;
		}
		JsonCollectBean param = new JsonCollectBean();
		param.setUserOpenId(openId);
		List<SkuProductList> skuProductList = new ArrayList<SkuProductList>();
		for (Integer recid : cartRecIds) {
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
		SendCollectBean bean=new SendCollectBean(2, param,getShopId(),null);
		saas.taskJobMainService.dispatchImmediately(bean,SendCollectBean.class.getName(),getShopId(),TaskJobEnum.WX_IMPORTORDER.getExecutionType());
//		WxOpenResult result = addshoppinglistDel(param);
//		return result.isSuccess();
		return true;

	}

	/**
	 * 删除收藏，消费者，不要直接调用
	 *
	 * @param jsonRootBean
	 * @return
	 */
	public WxOpenResult addshoppinglistDel(JsonCollectBean jsonRootBean) {
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
	 * 更新收藏，消费者，不要直接调用
	 *
	 * @param jsonRootBean
	 * @return
	 */
	public WxOpenResult addshoppinglistAdd(JsonCollectBean jsonRootBean) {
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

}
