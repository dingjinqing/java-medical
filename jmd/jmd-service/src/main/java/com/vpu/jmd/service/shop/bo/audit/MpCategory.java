package com.vpu.jmd.service.shop.bo.audit;

import lombok.Data;

import java.util.List;

/**
 * Category解析类
 * @author zhaojianqiang
 * @date 2020年6月3日下午1:58:59
 */
@Data
public class MpCategory {
	private List<MpCategoryInner> category;

}
