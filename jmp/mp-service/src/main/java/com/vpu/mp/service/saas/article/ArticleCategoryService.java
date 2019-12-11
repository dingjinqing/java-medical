package com.vpu.mp.service.saas.article;

import static com.vpu.mp.db.main.tables.Article.ARTICLE;
import static com.vpu.mp.db.main.tables.ArticleCategory.ARTICLE_CATEGORY;

import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SelectSeekStep1;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.Page;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.saas.article.category.ArtCategoryListQuertParam;
import com.vpu.mp.service.pojo.saas.article.category.ArticleCategoryParam;
import com.vpu.mp.service.pojo.saas.article.category.ArticleCategoryVo;

/**
 * 文章分类业务逻辑
 * @author 新国、wangshuai
 *
 */
@Service

public class ArticleCategoryService extends MainBaseService {
	
	/**
	 * 分页查询文章分类
	 * 
	 */
	public PageResult<ArticleCategoryVo> getPageList(ArtCategoryListQuertParam param) {
		SelectWhereStep<Record2<Integer, String>> select = db().select(
				ARTICLE_CATEGORY.CATEGORY_ID,
				ARTICLE_CATEGORY.CATEGORY_NAME
				).from(ARTICLE_CATEGORY);
		select.where(ARTICLE_CATEGORY.DEL_STATE.equal(DelFlag.NORMAL.getCode()));
		select.orderBy(ARTICLE_CATEGORY.CATEGORY_ID.asc());	
		Page page = param.getPage();
		return getPageResult(select,page.getCurrentPage(),page.getPageRows(),ArticleCategoryVo.class);

	}
	
	
	/**
	 * 查询文章分类,不分类
	 * 
	 */
	public List<ArticleCategoryVo> getCategoryList() {
		SelectSeekStep1<Record2<Integer, String>, Integer> select = db().select(
				ARTICLE_CATEGORY.CATEGORY_ID,
				ARTICLE_CATEGORY.CATEGORY_NAME
				).from(ARTICLE_CATEGORY).where(ARTICLE_CATEGORY.DEL_STATE.equal(DelFlag.NORMAL.getCode())).orderBy(ARTICLE_CATEGORY.CATEGORY_ID.asc());
		Result<Record2<Integer, String>> fetch = select.fetch();
		List<ArticleCategoryVo> into =new ArrayList<ArticleCategoryVo>();
		if(fetch!=null) {
			 into = fetch.into(ArticleCategoryVo.class);
		}
		return into;
	}
	/**
	 * 文章类型新增
	 * @param arArticleCategory
	 * @return
	 */
	
	public boolean insertArticleCategory(ArticleCategoryParam arArticleCategory) {
		int num = db().insertInto(ARTICLE_CATEGORY,ARTICLE_CATEGORY.CATEGORY_NAME)
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
			num[0] = db.update(ARTICLE_CATEGORY)
			.set(ARTICLE_CATEGORY.DEL_STATE, DelFlag.DISABLE.getCode()).set(ARTICLE_CATEGORY.CATEGORY_NAME,"del_"+arArticleCategory.getCategoryId()+arArticleCategory.getCategoryName())
			.where(ARTICLE_CATEGORY.CATEGORY_ID.eq(arArticleCategory.getCategoryId())).execute();
			num[1] = db.update(ARTICLE).set(ARTICLE.CATEGORY_ID, defaultCategory).where(ARTICLE.CATEGORY_ID.eq(arArticleCategory.getCategoryId())).execute();
		});
		return  (num[0] > 0 && num[1] >=0 )? true : false;
	}

	public boolean updateArticleCategory(ArticleCategoryParam arArticleCategory) {
		int num = db().update(ARTICLE_CATEGORY)
			.set(ARTICLE_CATEGORY.CATEGORY_NAME, arArticleCategory.getCategoryName()).where(ARTICLE_CATEGORY.CATEGORY_ID.eq(arArticleCategory.getCategoryId())).execute();
		return  num > 0 ? true : false;
	}
	
	/**
	 * 判断该文章分类是否存在
	 * 
	 */
	public boolean isExist(String categoryName) {
		int num = db().fetchCount(ARTICLE_CATEGORY,ARTICLE_CATEGORY.CATEGORY_NAME.eq(categoryName)
			.and(ARTICLE_CATEGORY.DEL_STATE.equal(DelFlag.NORMAL.getCode())));
		return num > 0 ? true : false;
	}
}
