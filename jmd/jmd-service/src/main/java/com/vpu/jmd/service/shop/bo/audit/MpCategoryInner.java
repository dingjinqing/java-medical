package com.vpu.jmd.service.shop.bo.audit;

import lombok.Data;
import lombok.ToString;

/**
 * Category解析类
 * @author zhaojianqiang
 * @date 2020年6月3日下午1:58:59
 */
@Data
@ToString
public class MpCategoryInner {
	private String firstClass;
	private String secondClass;
	private String thirdClass;
	private Integer firstId;
	private Integer secondId;
	private Integer thirdId;
}
