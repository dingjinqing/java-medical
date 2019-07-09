package com.vpu.mp.service.shop.goods;

import com.vpu.mp.service.pojo.shop.goods.comment.GoodsCommentConfig;
import com.vpu.mp.service.shop.config.BaseShopConfigService;

public class CommentConfigService extends BaseShopConfigService{
	
	final public static String K_COMMENT = "comment";
	final public static String K_COMMENT_STATE = "comment_state";
	
	 /**
     * 修改评价审核配置
     *
     * @param goodsCommentConfig
     * @return 
     */
	public int setCheckConfig(GoodsCommentConfig goodsCommentConfig) {
		return this.set(K_COMMENT,goodsCommentConfig.getV());
	}
	
	/**
     * 修改开关配置
     *
     * @param goodsCommentConfig
     * @return 
     */
	public int setSwitchConfig(GoodsCommentConfig goodsCommentConfig) {
		return this.set(K_COMMENT_STATE,goodsCommentConfig.getV());
	}
}
