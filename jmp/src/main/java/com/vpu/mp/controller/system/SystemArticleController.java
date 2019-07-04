package com.vpu.mp.controller.system;

import org.jooq.tools.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.controller.BaseController;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.saas.article.ArticleInPut;
import com.vpu.mp.service.pojo.saas.article.ArticleListQueryParam;
import com.vpu.mp.service.pojo.saas.article.ArticleOutPut;
import com.vpu.mp.service.pojo.saas.article.category.ArtCategoryListQuertParam;
import com.vpu.mp.service.pojo.saas.article.category.ArticleCategoryInPut;
import com.vpu.mp.service.pojo.saas.article.category.ArticleCategoryOutPut;

/**
 * 文章与其分类控制器
 * @author wangshuai
 *
 */
@RestController
@RequestMapping("/api/system/article")
public class SystemArticleController extends BaseController{
	@PostMapping("/list")
	public JsonResult get(@RequestBody ArticleListQueryParam param) {
		PageResult<ArticleOutPut> pageList = saas.article.getPageList(param);
		return success(pageList);
	}
	@PostMapping("/category/list")
	public JsonResult getCategory(@RequestBody ArtCategoryListQuertParam param) {
		PageResult<ArticleCategoryOutPut> pageList = saas.articleCategory.getPageList(param);
		return success(pageList);
	}
	@PostMapping("/category/delete")
	private JsonResult deleteCategory(@RequestBody ArticleCategoryInPut input) {
		if(null == input.getCategoryId()) {
			return fail(JsonResultCode.CODE_ARTICLE_CATEGORY_CATEGORYID_ISNULL);
		}
		return saas.articleCategory.deleteArticleCategory(input)?success():fail();
	}
	
	@PostMapping("/category/add")
	private JsonResult insertCategory(@RequestBody ArticleCategoryInPut input ) {
		if(StringUtils.isEmpty(input.getCategoryName())) {
			return fail(JsonResultCode.CODE_ARTICLE_CATEGORY_CATEGORYNAME_ISNULL);
		}
		if(saas.articleCategory.isExist(input.getCategoryName())) {
			return fail(JsonResultCode.CODE_ARTICLE_CATEGORY_IS_EXIST);
		}
		return saas.articleCategory.insertArticleCategory(input)?success():fail();	
	}
	
	@PostMapping("/category/update")
	private JsonResult updateCategory(@RequestBody ArticleCategoryInPut input ) {
		if(StringUtils.isEmpty(input.getCategoryName()) || null == input.getCategoryId()) {
			return fail(JsonResultCode.CODE_ARTICLE_CATEGORY_UPDATE_FAILED);
		}
		return saas.articleCategory.updateArticleCategory(input)?success():fail();
	}
	
	@PostMapping("/delete")
	private JsonResult delete(@RequestBody ArticleInPut article) {
		if(null == article.getArticleId()) {
			return fail(JsonResultCode.CODE_ARTICLE_ARTICLEID_ISNULL);
		}
		return saas.article.deleteArticle(article.getArticleId())?success():fail();
	}
	
	@PostMapping("/add")
	private JsonResult insert(@RequestBody ArticleInPut article ) {
		if(StringUtils.isEmpty(article.getTitle())) {
			return fail(JsonResultCode.CODE_ARTICLE_TITLE_ISNULL);
		}
		String token = request.getHeader("V-Token");
		return saas.article.insertArticle(article,token)?success():fail();	
	}
	
	@PostMapping("/update")
	private JsonResult update(@RequestBody ArticleInPut article ) {
		if(null == article.getArticleId()) {
			return fail(JsonResultCode.CODE_ARTICLE_ARTICLEID_ISNULL);
		}
		return saas.article.updateArticle(article) ? success() : fail();
	}
	
	@PostMapping("/get")
	private JsonResult get(@RequestBody ArticleInPut article ) {
		if(null == article.getArticleId()) {
			return fail(JsonResultCode.CODE_ARTICLE_ARTICLEID_ISNULL);
		}
		return success(saas.article.get(article.getArticleId()));
	}
}
