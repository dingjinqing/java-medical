package com.vpu.mp.service.shop.order.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.member.address.UserAddressVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam.Goods;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeVo;
import com.vpu.mp.service.shop.config.TradeService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.member.AddressService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.no;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.yes;

import jodd.util.StringUtil;

/**
 * 下单逻辑处理
 *
 * @author: ws
 * @create: 2019-10-23 16:15
 **/
@Service
public class CreateService extends ShopBaseService implements IorderOperate<OrderBeforeParam, OrderOperateQueryParam> {

	@Autowired
	private OrderInfoService orderInfo;

	@Autowired
	private AddressService address;

	@Autowired
	private TradeService trade;

	@Autowired
	private UserCardService userCard;

	@Autowired
	private GoodsSpecProductService goodsSpecProduct;
	
	@Autowired
	private GoodsService goodsService;
	
	@Override
	public OrderServiceCode getServiceCode() {
		return OrderServiceCode.CREATE;
	}

	/**
	 * 订单创建前，结算页面数据查询
	 */
	@Override
	public Object query(OrderBeforeParam param) throws MpException {
		OrderBeforeVo result = OrderBeforeVo.builder().
		// 地址
				address(getDefaultAddress(param.getWxUserInfo().getUserId(), param.getAddressId())).
				// 支持的配送方式
				expressList(getExpressList()).build();
		// goods info init
		initGoods(param.getGoods());
		// process
		processParam(param);

		return result;
	}

	@Override
	public JsonResultCode execute(OrderOperateQueryParam obj) {
		return null;
	}

	/**
	 * 获取默认地址
	 * 
	 * @param userId
	 * @param addressId
	 * @return
	 */
	public UserAddressVo getDefaultAddress(Integer userId, Integer addressId) {
		UserAddressVo defaultAddress = null;
		if (addressId != null) {
			// 输入addressId
			defaultAddress = address.get(addressId, userId);
		}
		// 输入地址无效
		if (defaultAddress == null) {
			// 没输入addressId
			defaultAddress = orderInfo.getLastOrderAddress(userId);
			// TODO 更新经纬度
		}
		return defaultAddress;
	}

	/**
	 * 配送方式
	 * 
	 * @return
	 */
	public byte[] getExpressList() {
		byte[] expressList = new byte[3];
		// 快递
		expressList[OrderConstant.DELIVER_TYPE_COURIER] = trade.getExpress();
		// 自提
		expressList[OrderConstant.DELIVER_TYPE_COURIER] = trade.getFetch();
		// TODO同城配送
		// expressList[OrderConstant.DELIVER_TYPE_COURIER] =
		// trade.getCityExpressService();
		return expressList;
	}

	/**
	 * 初始化购买商品信息
	 * 
	 * @param goods
	 * @return
	 */
	public void initGoods(List<Goods> goods) {
		// TODO 以下参数为模拟参数
		for (Goods temp : goods) {
			// 商品参与的促销活动id
			temp.setStraId(0);
			// 购买价格id
			temp.setPurchasePriceId(0);
			// 购买规则id
			temp.setPurchasePriceRuleId(0);
			// ?crm?
			temp.setPromoteInfo(null);
		}
	}

	public void processParam(OrderBeforeParam param) throws MpException {
		if (Boolean.FALSE) {
			// 营销
		} else {
			// 未参与营销时进入
			if (Boolean.FALSE) {
				// 非购物车
				if (Boolean.FALSE) {
					// 送礼 或 批量换购
				} else {
					//单次购买
					purchase(param, param.getWxUserInfo().getUserId(), param.getStoreId());
				}
			}
		}
	}
	public void purchase(OrderBeforeParam param, Integer userId, Integer storeId) throws MpException {
		//TODO 返利信息
		Boolean isNewUser = orderInfo.isNewUser(userId, true);
		//规格信息,key proId
		Map<Integer, GoodsSpecProductRecord> productInfo = goodsSpecProduct.selectSpecByProIds(param.getProductIds(), storeId );
		//goods type,key goodsId
		Map<Integer, Byte> goodsTypes = goodsService.getGoodsType(param.getGoodsIds());
		//赋值
		for (Goods temp : param.getGoods()) {
			GoodsSpecProductRecord product = productInfo.get(temp.getProductId());
			Byte goodsType = goodsTypes.get(temp.getGoodsId());
			if(product == null || goodsType == null) {
				//商品规格不存在（逻辑上不会出现）
				throw new MpException(JsonResultCode.CODE_ORDER);
			}
			//方便计算，临时存储
			temp.setProductInfo(product);
			//规格价格
			temp.setProductPrice(product.getPrdPrice());
			//规格数量
			temp.setProductNumbers(product.getPrdNumber());
			//商品goodsType
			temp.setGoodsType(goodsType);
			//首单特惠处理
			if(isNewUser) {
				//TODO
				temp.setIsFirstSpecial(yes);
				temp.setIsFirstSpecial(no);
			}
		}
		initOrderGoods(param, param.getGoods(), userId, param.getMemberCardNo(), storeId);
	}
	/**
	 * 
	 * @param goods        商品
	 * @param userId       会员id
	 * @param memberCardNo 会员卡no
	 * @param storeId      门店id
	 * @throws MpException 
	 */
	public void initOrderGoods(OrderBeforeParam param, List<Goods> goods, Integer userId, String memberCardNo, Integer storeId) throws MpException {
		logger().info("initOrderGoods开始");
		// 会员卡类型
		Byte cardType = userCard.getCardType(memberCardNo);
		// 会员等级
		String userGrade = userCard.getUserGrade(userId);
		//
		Map<Integer, GoodsRecord> goodsRecords = goodsService.getGoodsToOrder(param.getGoodsIds());
		for (Goods temp : goods) {
			GoodsRecord goodsRecord = goodsRecords.get(temp.getGoodsId());
			//TODO 去别的class写：不满足需要推出11111
			//分销返利（return）
			//首单特惠（return）、
			//会员等级->限时降价/等级会员卡专享价格/商品价格（三取一）return
			//限时降价
			
			//商品校验
			checkGoodsAndProduct(goodsRecord, temp);
			//会员专享校验
			
			//预售商品，不支持现购买
			
			//非加价购 && 非限次卡，限购
			
			//price 副本
			
			//if else 加价购-straid
			
			//非加价购 复制 11111分支
			
			//判断副本与实际计算价格大小、
			
			//初始化ordergoods
			OrderGoodsBo.builder().
				goodsId(temp.getGoodsId()).
				goodsName(goodsRecord.getGoodsName()).
				goodsSn(goodsRecord.getGoodsSn()).
				productId(temp.getProductId()).
				productSn(temp.getProductInfo().getPrdSn()).
				goodsNumber(temp.getGoodsNumber()).
				
			build();
			
		}
	}
	
	/**
	 * 校验
	 * @param goodsRecord
	 * @param goods
	 * @throws MpException
	 */
	public void checkGoodsAndProduct(GoodsRecord goodsRecord, Goods goods) throws MpException {
		if(goodsRecord == null || goodsRecord.getDelFlag() == DelFlag.DISABLE.getCode()) {
			logger().error("checkGoodsAndProduct,商品不存在");
			throw new MpException(JsonResultCode.CODE_ORDER_GOODS_NOT_EXIST , goodsRecord.getGoodsName());
		}
		if(goodsRecord.getIsOnSale() != GoodsConstant.ON_SALE) {
			logger().error("checkGoodsAndProduct,商品已下架,id:"+goodsRecord.getGoodsId());
			throw new MpException(JsonResultCode.CODE_ORDER_GOODS_NO_SALE, null, goodsRecord.getGoodsName());
		}
		if(goods.getGoodsNumber() > goods.getProductNumbers()) {
			logger().error("checkGoodsAndProduct,库存不足,id:"+goods.getProductId());
			throw new MpException(JsonResultCode.CODE_ORDER_GOODS_NOT_EXIST, goodsRecord.getGoodsName());
		}
		if(goods.getGoodsNumber() == null || goods.getGoodsNumber() <= 0) {
			logger().error("checkGoodsAndProduct,商品数量不能为0,id:"+goods.getProductId());
			throw new MpException(JsonResultCode.CODE_ORDER_GOODS_NO_ZERO, goodsRecord.getGoodsName());
		}
	}
}
