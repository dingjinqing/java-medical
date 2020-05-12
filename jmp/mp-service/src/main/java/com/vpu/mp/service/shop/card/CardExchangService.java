package com.vpu.mp.service.shop.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.card.create.CardExchangGoods;
import com.vpu.mp.service.pojo.shop.member.card.create.CardExchangGoods.GoodsCfg;
/**
 * 	会员卡兑换商品服务
 * @author 黄壮壮
 *
 */
@Service
public class CardExchangService extends ShopBaseService {
	/**
	 * 	兑换商品数量无限制
	 */
	public final static Integer NUM_INFINITE = NumberUtils.INTEGER_ZERO;
	/**
	 * 获取会员卡兑换商品的配置信息
	 * @return CardExchangGoods 兑换商品配置数据信息
	 */
	public CardExchangGoods getCardExchangGoodsService(MemberCardRecord card){
		logger().info("获取会员卡的兑换商品配置信息");
		CardExchangGoods cardExGoods = new CardExchangGoods();
		//	是否可兑换商品
		Byte isExchang = card.getIsExchang();
		cardExGoods.setIsExchange(isExchang);
		
		//	商品兑换总次数
		cardExGoods.setExchangCount(card.getExchangCount());
		//	兑换时间类型
		cardExGoods.setExchangTimeType(card.getPeriodLimit());
		//	兑换商品周期内次数
		cardExGoods.setExchangTimeNum(card.getPeriodNum());
		//	运费策略
		cardExGoods.setExchangFreight(card.getExchangFreight());
		
		String exchangGoods = card.getExchangGoods();
		if(CardUtil.canExchangGoods(isExchang) && !StringUtils.isBlank(exchangGoods)) {
			if(CardUtil.isExchangPartGoods(isExchang)) {
				//	兑换部分商品
				if(exchangGoods.startsWith("{")) { 
					//	目前存储的数据格式为map
					Map<String,Integer> map = Util.json2Object(exchangGoods, new TypeReference<Map<String,Integer>>() {}, false);
					List<GoodsCfg> goodsCfgList = new ArrayList<GoodsCfg>();
					for(Map.Entry<String, Integer> entry: map.entrySet()) {
						GoodsCfg goodsCfg = new GoodsCfg();
						String key = entry.getKey();
						List<Integer> goodsId = Util.stringToList(key);
						goodsCfg.setGoodsId(goodsId);
						goodsCfg.setMaxNum(entry.getValue());
						goodsCfgList.add(goodsCfg);
					}
					cardExGoods.setExchangGoods(goodsCfgList);
				}else {
					//	逗号分隔的数据，直接解析
					GoodsCfg goodsCfg = new GoodsCfg();
					goodsCfg.setGoodsId(Util.stringToList(exchangGoods));
					goodsCfg.setMaxNum(NUM_INFINITE);
					
					List<GoodsCfg> goodsCfgList = new ArrayList<GoodsCfg>() {{
						add(goodsCfg);
					}};
					cardExGoods.setExchangGoods(goodsCfgList);
				}				
			}else if(CardUtil.isExchangAllGoods(isExchang)) {
				//	兑换全部商品
				cardExGoods.setEveryGoodsMaxNum(Integer.parseInt(exchangGoods));
			}
		}
		return cardExGoods;
	}
}
