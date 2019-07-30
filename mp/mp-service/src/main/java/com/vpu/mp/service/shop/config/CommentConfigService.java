package com.vpu.mp.service.shop.config;

import org.springframework.stereotype.Service;

@Service

public class CommentConfigService extends BaseShopConfigService{
	
	final public static String K_COMMENT = "comment";
	final public static String K_COMMENT_STATE = "comment_state";
	
	 /**
     * 修改评价审核配置
     *
     * @param goodsCommentConfig
     * @return 
     */
	public int setCheckConfig(String value) {
		return this.set(K_COMMENT,value);
	}
	
	/**
     * 修改开关配置
     *
     * @param goodsCommentConfig
     * @return 
     */
	public int setSwitchConfig(String value) {
		return this.set(K_COMMENT_STATE,value);
	}
}
