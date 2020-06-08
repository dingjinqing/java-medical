package com.vpu.mp.service.shop.card.wxapp;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.records.CheckedGoodsCartRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.card.dao.CardFullDetail;
import com.vpu.mp.service.pojo.wxapp.card.param.CardAddExchangeGoodsParam;
import com.vpu.mp.service.pojo.wxapp.card.param.CardExchaneGoodsJudgeParam;
import com.vpu.mp.service.pojo.wxapp.card.param.CardExchangeGoodsParam;
import com.vpu.mp.service.pojo.wxapp.card.vo.CardCheckedGoodsVo;
import com.vpu.mp.service.pojo.wxapp.card.vo.CardExchangeGoodsVo;
import com.vpu.mp.service.pojo.wxapp.user.UserCheckedGoodsParam;
import com.vpu.mp.service.pojo.wxapp.user.UserCheckedGoodsVo;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.user.user.UserCheckedGoodsService;
import static com.vpu.mp.db.shop.Tables.CHECKED_GOODS_CART;
/**
 * 	会员卡兑换商品服务
 * @author 黄壮壮
 *
 */
@Service
public class WxCardExchangeService extends ShopBaseService {
	@Autowired private MemberCardService mCardSvc;
	@Autowired private ShopCommonConfigService shopCommonCfgSvc;
	@Autowired private GoodsService goodsSvc;
	@Autowired private UserCheckedGoodsService userCheckedGoodsSvc;
	@Autowired private DomainConfig domainConfig;
	
	/**
	 * 兑换商品列表
	 * @param param
	 */
	public CardExchangeGoodsVo changeGoodsList(CardExchangeGoodsParam param) {
		logger().info("兑换商品列表");
		CardExchangeGoodsVo vo = new CardExchangeGoodsVo();
		if(StringUtils.isBlank(param.getCardNo())) {
			return vo;
		}
		CardFullDetail cardDetail = mCardSvc.getCardDetailByNo(param.getCardNo());
		if(cardDetail!= null) {
			UserCardRecord userCard = cardDetail.getUserCard();
			MemberCardRecord memberCard = cardDetail.getMemberCard();
			
			if(userCard.getActivationTime() == null 
						|| !CardUtil.isLimitCard(memberCard.getCardType()) 
						|| !CardUtil.canExchangGoods(memberCard.getIsExchang())) {
				logger().info("该会员卡不可兑换");
				return vo;
			}
			
			if(userCard.getExchangSurplus()<=0) {
				logger().info("该会员卡已无兑换次数");
				return vo;
			}
			
			Byte soldOutGoods = shopCommonCfgSvc.getSoldOutGoods();
			List<Integer> goodsIds = Util.splitValueToList(memberCard.getExchangGoods());
			GoodsPageListParam goodsPageListParam = new GoodsPageListParam();
			goodsPageListParam.setGoodsIds(goodsIds);
			goodsPageListParam.setIsSaleOut(NumberUtils.BYTE_ONE.equals(soldOutGoods));
			goodsPageListParam.setGoodsName(param.getSearch());
			goodsPageListParam.setCurrentPage(param.getCurrentPage());
			goodsPageListParam.setPageRows(param.getPageRows());
			
			PageResult<GoodsPageListVo> goodsPageResult = goodsSvc.getProductPageList(goodsPageListParam);
			vo.setGoodsPageResult(goodsPageResult);
			
			//	Integer count = userCard.getExchangSurplus();
			// TODO 兑换商品数量配置，时间限制
			
			//	获取用户已勾选的
			UserCheckedGoodsParam checkedGoodsParam = new UserCheckedGoodsParam();
			checkedGoodsParam.setAction(CardConstant.MCARD_TP_LIMIT);
			checkedGoodsParam.setIdentityId(userCard.getCardNo());
			checkedGoodsParam.setUserId(param.getUserId());
			Integer totalNumber = userCheckedGoodsSvc.getUserCheckedCount(checkedGoodsParam);
			vo.setTotalNumber(totalNumber);
			
			Map<String, Object> cardInfo = userCard.intoMap();
			vo.setCardInfo(cardInfo);
		}
		return vo;
	}
	
	
	
	/**
	 * 判断是否可以兑换或加入兑换列表
	 */
	public void judgeCardGoods(CardExchaneGoodsJudgeParam param) {
		//	todo 兑换商品数量配置与使用时间
		
		return;
	}
	
	/**
	 * 是否满足兑换购买兑换条件
	 */
	public void canExchangeToBuy(CardExchaneGoodsJudgeParam param) {
		//	todo 兑换商品数量配置与使用时间
		return;
	}


	/**
	 * 添加活动商品
	 */
	public void addExchangeGoods(CardAddExchangeGoodsParam param) {
		logger().info("兑换商品加购");
		Optional<GoodsRecord> record = goodsSvc.getGoodsById(param.getGoodsId());
		GoodsRecord goodsRecord = record.get();
		if(goodsRecord == null) {
			return;
		}
		UserCheckedGoodsParam checkedParam = new UserCheckedGoodsParam();
		checkedParam.setAction(CardConstant.MCARD_TP_LIMIT);
		checkedParam.setUserId(param.getUserId());
		checkedParam.setProductId(param.getProductId());
		checkedParam.setIdentityId(param.getCardNo());
		checkedParam.setGoodsId(param.getGoodsId());
		
		CheckedGoodsCartRecord userCheckedGoods = userCheckedGoodsSvc.getUserCheckedGoods(checkedParam);
		if(userCheckedGoods == null) {
			// 添加
			CheckedGoodsCartRecord newRecord = db().newRecord(CHECKED_GOODS_CART);
			newRecord.setUserId(param.getUserId());
			newRecord.setGoodsId(param.getGoodsId());
			newRecord.setProductId(param.getProductId());
			newRecord.setGoodsNumber(param.getPrdNumber());
			newRecord.setAction(CardConstant.MCARD_TP_LIMIT);
			newRecord.setIdentityId(param.getCardNo());
			newRecord.insert();
		}else {
			// 更新
			userCheckedGoods.setGoodsNumber(param.getPrdNumber());
			db().executeUpdate(userCheckedGoods);
		}
	}


	/**
	 * 已选商品列表
	 * @return 
	 */
	public CardCheckedGoodsVo changeCheckedGoodsList(UserCheckedGoodsParam param) {
		param.setAction(CardConstant.MCARD_TP_LIMIT);
		CardCheckedGoodsVo vo = new CardCheckedGoodsVo();
		PageResult<UserCheckedGoodsVo> usercheckedList = userCheckedGoodsSvc.getUsercheckedList(param);
		
		// 处理图片
		for(UserCheckedGoodsVo goods: usercheckedList.dataList) {
			if(!StringUtils.isBlank(goods.getPrdImg())) {
				goods.setPrdImg(domainConfig.imageUrl(goods.getPrdImg()));
			}
			if(!StringUtils.isBlank(goods.getGoodsImg())) {
				goods.setGoodsImg(domainConfig.imageUrl(goods.getGoodsImg()));
			}
		}
		
		vo.setGoodsList(usercheckedList);
		//		获取用户已勾选的
		UserCheckedGoodsParam checkedGoodsParam = new UserCheckedGoodsParam();
		checkedGoodsParam.setAction(CardConstant.MCARD_TP_LIMIT);
		checkedGoodsParam.setIdentityId(param.getIdentityId());
		checkedGoodsParam.setUserId(param.getUserId());
		Integer totalNumber = userCheckedGoodsSvc.getUserCheckedCount(checkedGoodsParam);
		vo.setTotalNumber(totalNumber);
		return vo;
	}


	/**
	 * 删除兑换商品
	 * @param param
	 */
	public void removeChoosedGoods(UserCheckedGoodsParam param) {
		userCheckedGoodsSvc.removeChoosedGoods(param);
	}
}
