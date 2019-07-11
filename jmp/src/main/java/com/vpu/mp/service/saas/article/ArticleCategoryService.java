package com.vpu.mp.service.saas.article;

import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;

import com.vpu.mp.db.main.tables.Article;
import com.vpu.mp.db.main.tables.ArticleCategory;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.DelFlag;
import com.vpu.mp.service.foundation.Page;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.saas.article.category.ArtCategoryListQuertParam;
import com.vpu.mp.service.pojo.saas.article.category.ArticleCategoryParam;
import com.vpu.mp.service.pojo.saas.article.category.ArticleCategoryVo;

/**
 * 文章分类业务逻辑
 * @author 新国、wangshuai
 *
 */
public class ArticleCategoryService extends BaseService {
	
	/**
	 * 分页查询文章分类
	 * 
	 */
	public PageResult<ArticleCategoryVo> getPageList(ArtCategoryListQuertParam param) {
		SelectWhereStep<Record2<Integer, String>> select = db().select(
				ArticleCategory.ARTICLE_CATEGORY.CATEGORY_ID,
				ArticleCategory.ARTICLE_CATEGORY.CATEGORY_NAME
				).from(ArticleCategory.ARTICLE_CATEGORY);
		select.where(ArticleCategory.ARTICLE_CATEGORY.DEL_STATE.equal(DelFlag.NORMAL.getCode()));
		select.orderBy(ArticleCategory.ARTICLE_CATEGORY.CATEGORY_ID.asc());	
		Page page = param.getPage();
		return getPageResult(select,page.getCurrentPage(),page.getPageRows(),ArticleCategoryVo.class);

	}
	/**
	 * 文章类型新增
	 * @param arArticleCategory
	 * @return
	 */
	
	public boolean insertArticleCategory(ArticleCategoryParam arArticleCategory) {
		int num = db().insertInto(ArticleCategory.ARTICLE_CATEGORY,ArticleCategory.ARTICLE_CATEGORY.CATEGORY_NAME)
			.values(arArticleCategory.getCategoryName()).execute();
		return num > 0 ? true : false;
	}
	
	/**
	 * 删除文章分类
	 * @param arArticleCategory
	 * @return
	 */
	public boolean deleteArticleCategory(ArticleCategoryParam arArticleCategory) {
		Integer defaultCategory = 1;
		int[] num = {0,0};
		//删除文章分类需置等于该分类的文章的分类为1（数据库默认）
		db().transaction(configuration -> {
			DSLContext db = DSL.using(configuration);
			num[0] = db.update(ArticleCategory.ARTICLE_CATEGORY)
			.set(ArticleCategory.ARTICLE_CATEGORY.DEL_STATE, DelFlag.DISABLE.getCode()).where(ArticleCategory.ARTICLE_CATEGORY.CATEGORY_ID.eq(arArticleCategory.getCategoryId())).execute();
			num[1] = db.update(Article.ARTICLE).set(Article.ARTICLE.CATEGORY_ID, defaultCategory).where(Article.ARTICLE.CATEGORY_ID.eq(arArticleCategory.getCategoryId())).execute();
		});
		return  (num[0] > 0 && num[1] >=0 )? true : false;
	}

	public boolean updateArticleCategory(ArticleCategoryParam arArticleCategory) {
		int num = db().update(ArticleCategory.ARTICLE_CATEGORY)
			.set(ArticleCategory.ARTICLE_CATEGORY.CATEGORY_NAME, arArticleCategory.getCategoryName()).where(ArticleCategory.ARTICLE_CATEGORY.CATEGORY_ID.eq(arArticleCategory.getCategoryId())).execute();
		return  num > 0 ? true : false;
	}
	
	/**
	 * 判断该文章分类是否存在
	 * 
	 */
	public boolean isExist(String categoryName) {
		int num = db().fetchCount(ArticleCategory.ARTICLE_CATEGORY,ArticleCategory.ARTICLE_CATEGORY.CATEGORY_NAME.eq(categoryName)
			.and(ArticleCategory.ARTICLE_CATEGORY.DEL_STATE.equal(DelFlag.NORMAL.getCode())));
		return num > 0 ? true : false;
	}
}
