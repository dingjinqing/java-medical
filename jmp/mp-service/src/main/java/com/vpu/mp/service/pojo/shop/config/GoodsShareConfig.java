package com.vpu.mp.service.pojo.shop.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author liangchen
 * @date 2020.03.27
 */
@Data
@Component
public class GoodsShareConfig {
	public static final Byte DEFAULT_VALUE = 0;
	/**
	 * 直接分享
	 * 0默认文案    1自定义文案
	 */
	@JsonProperty(value = "goods_share_common")
	public Byte goodsShareCommon = 0;
	
	/**
	 * 直接分享自定义文案
	 *  
	 */
	@JsonProperty(value = "common_doc")
	public String commonDoc = "";
	
	/**
	 * 下载海报
	 * 0默认文案    1自定义文案
	 */
	@JsonProperty(value = "goods_share_pictorial")
	public Byte goodsSharePictorial = 0;
	
	/**
	 * 下载海报自定义文案
	 *  
	 */
	@JsonProperty(value = "pictorial_doc")
	public String pictorialDoc = "";
}
