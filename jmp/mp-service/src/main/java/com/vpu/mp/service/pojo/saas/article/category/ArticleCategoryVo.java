package com.vpu.mp.service.pojo.saas.article.category;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author wangshuai
 *
 */
@Data
@NoArgsConstructor
public class ArticleCategoryVo {
	private Integer categoryId;
	private String categoryName;
	private Byte useFootNav;
	private Timestamp addTime;
	private Timestamp updaTimestamp;
}
