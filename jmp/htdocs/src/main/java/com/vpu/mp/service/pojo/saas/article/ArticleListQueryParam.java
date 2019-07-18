package com.vpu.mp.service.pojo.saas.article;

import com.vpu.mp.service.foundation.Page;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author wangshuai
 *
 */
@Data
@NoArgsConstructor
public class ArticleListQueryParam {
	private Integer categoryId;
	private Byte status;
	private String keywords;
	/**排序条件 eg:name,asc;time,desc*/
	private String sortName;
	private Page page;
}
