package com.vpu.mp.service.pojo.saas.article.category;

import javax.validation.constraints.NotBlank;

import com.vpu.mp.service.foundation.data.JsonResultMessage;

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
	/**是否用于底部导航  ok:使用。cancel：不使用 */
	private String useFooterNav;
}
