package com.vpu.mp.controller.official;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.saas.article.ArticleInPut;
import com.vpu.mp.service.pojo.saas.article.ArticleListQueryParam;
import com.vpu.mp.service.pojo.saas.article.ArticleOutPut;

/**
 * 
 * @author wangshuai
 *
 */
@RestController
@RequestMapping("/api/official/article")
public class ArticleOffcialController extends OfficialBaseController{
	
	/**
	 * 官网新闻资讯查询
	 * @param ArticleListQueryParam
	 * @returnJsonResult
	 */
	@PostMapping("/list")
	public JsonResult get(@RequestBody ArticleListQueryParam param) {
		PageResult<ArticleOutPut> pageList = saas.article.getPageList(param);
		return success(pageList);
	}
	
	/**
	 * 官网新闻资讯文章具体查询
	 * @param ArticleInPut
	 * @return JsonResult
	 */
	@PostMapping("/get")
	private JsonResult get(@RequestBody ArticleInPut article ) {
		if(null == article.getArticleId()) {
			return fail(JsonResultCode.CODE_ARTICLE_ARTICLEID_ISNULL);
		}
		return success(saas.article.get(article.getArticleId()));
	}
}
