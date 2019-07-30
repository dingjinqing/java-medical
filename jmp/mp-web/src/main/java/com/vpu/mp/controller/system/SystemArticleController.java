package com.vpu.mp.controller.system;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.saas.article.ArticleListQueryParam;
import com.vpu.mp.service.pojo.saas.article.ArticleParam;
import com.vpu.mp.service.pojo.saas.article.ArticleVo;
import com.vpu.mp.service.pojo.saas.article.category.ArtCategoryListQuertParam;
import com.vpu.mp.service.pojo.saas.article.category.ArticleCategoryParam;
import com.vpu.mp.service.pojo.saas.article.category.ArticleCategoryVo;

/**
 * 文章与其分类控制器
 * @author wangshuai
 *
 */
@RestController
@RequestMapping("/api/system/article")
public class SystemArticleController extends SystemBaseController{
	@PostMapping("/list")
	public JsonResult get(@RequestBody ArticleListQueryParam param) {
		PageResult<ArticleVo> pageList = saas.article.getPageList(param);
		return success(pageList);
	}
	@PostMapping("/category/list")
	public JsonResult getCategory(@RequestBody ArtCategoryListQuertParam param) {
		PageResult<ArticleCategoryVo> pageList = saas.articleCategory.getPageList(param);
		return success(pageList);
	}
	@PostMapping("/category/delete")
	private JsonResult deleteCategory(@RequestBody ArticleCategoryParam input) {
		if(null == input.getCategoryId()) {
			return fail(JsonResultCode.CODE_ARTICLE_CATEGORY_CATEGORYID_ISNULL);
		}
		return saas.articleCategory.deleteArticleCategory(input)?success():fail();
	}
	
	@PostMapping("/category/add")
	private JsonResult insertCategory(@RequestBody @Valid ArticleCategoryParam input ) {
		if(saas.articleCategory.isExist(input.getCategoryName())) {
			return fail(JsonResultCode.CODE_ARTICLE_CATEGORY_IS_EXIST);
		}
		return saas.articleCategory.insertArticleCategory(input)?success():fail();	
	}
	
	@PostMapping("/category/update")
	private JsonResult updateCategory(@RequestBody @Valid ArticleCategoryParam input ) {
		if(null == input.getCategoryId()) {
			return fail(JsonResultCode.CODE_ARTICLE_CATEGORY_CATEGORYID_ISNULL);
		}
		return saas.articleCategory.updateArticleCategory(input)?success():fail();
	}
	
	@PostMapping("/delete")
	private JsonResult delete(@RequestBody ArticleParam article) {
		if(null == article.getArticleId()) {
			return fail(JsonResultCode.CODE_ARTICLE_ARTICLEID_ISNULL);
		}
		return saas.article.deleteArticle(article.getArticleId())?success():fail();
	}
	
	@PostMapping("/add")
	private JsonResult insert(@RequestBody @Valid ArticleParam article ) {
		article.setAuthor(sysAuth.user().getSystemUserId().toString());
		return saas.article.insertArticle(article)?success():fail();	
	}
	
	@PostMapping("/update")
	private JsonResult update(@RequestBody ArticleParam article ) {
		if(null == article.getArticleId()) {
			return fail(JsonResultCode.CODE_ARTICLE_ARTICLEID_ISNULL);
		}
		return saas.article.updateArticle(article) ? success() : fail();
	}
	
	@PostMapping("/get")
	private JsonResult get(@RequestBody ArticleParam article ) {
		if(null == article.getArticleId()) {
			return fail(JsonResultCode.CODE_ARTICLE_ARTICLEID_ISNULL);
		}
		return success(saas.article.get(article.getArticleId()));
	}
}
