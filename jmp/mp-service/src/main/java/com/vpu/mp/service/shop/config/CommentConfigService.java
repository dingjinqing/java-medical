package com.vpu.mp.service.shop.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service

public class CommentConfigService extends BaseShopConfigService{
	
	final public static String K_COMMENT = "comment";
	final public static String K_COMMENT_STATE = "comment_state";
	
	 /**
     * 修改评价审核配置
     *
     * @param value
     * @return 
     */
	public int setCheckConfig(String value) {
		return this.set(K_COMMENT,value);
	}

    /**
     * 获取评价审核状态
     * @return 默认为0
     */
	public Byte getCommentConfig() {
        String s = this.get(K_COMMENT);
        return StringUtils.isBlank(s)? 0 : Byte.valueOf(s);
    }
	
	/**
     * 修改开关配置
     *
     * @param value
     * @return 
     */
	public int setSwitchConfig(String value) {
	    return this.set(K_COMMENT_STATE,value);
	}
    /**
     * 获取评价审核状态
     * @return 默认为0
     */
    public Byte getSwitchConfig() {
        String s = this.get(K_COMMENT_STATE);
        return StringUtils.isBlank(s)? 0 : Byte.valueOf(s);
    }
}
