package com.vpu.mp.service.pojo.saas.article.category;

import javax.validation.constraints.NotBlank;

import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.JsonResultMessage;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author wangshuai
 *
 */
@Data
@NoArgsConstructor
public class ArticleCategoryParam {
	private Integer categoryId;
	@NotBlank(message = JsonResultMessage.ARTICLE_CATEGORY_CATEGORYNAME_ISNULL)
	private String categoryName;
}
