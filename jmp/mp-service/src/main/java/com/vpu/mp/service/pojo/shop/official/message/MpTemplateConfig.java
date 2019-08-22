package com.vpu.mp.service.pojo.shop.official.message;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;

/**
 * 公众号模版消息
 * @author 卢光耀
 * @date 2019-08-22 10:22
 *
*/
@Getter
@Builder
public class MpTemplateConfig {
	/**
	 * 模板编号
	 */
	String templateNo;
	
	/**
	 * 标题
	 */
	String title;
	
	/**
	 * 内容，样例： 您好，您已购买成功。商品信息：{{name.DATA}}{{remark.DATA}}
	 */
	String content;
}
