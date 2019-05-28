package com.vpu.mp.service.saas.article;

import static com.vpu.mp.db.main.tables.Article.ARTICLE;
import static com.vpu.mp.db.main.tables.ArticleCategory.ARTICLE_CATEGORY;
import static com.vpu.mp.db.main.tables.ArticleRecord.ARTICLE_RECORD;
import static com.vpu.mp.db.main.tables.MpAuthShop.MP_AUTH_SHOP;
import static com.vpu.mp.db.main.tables.Shop.SHOP;

import java.util.List;
import java.util.Map;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.jooq.types.UInteger;

import com.vpu.mp.db.main.tables.Article;
import com.vpu.mp.db.main.tables.records.ArticleCategoryRecord;
import com.vpu.mp.db.main.tables.records.ArticleRecord;
import com.vpu.mp.db.main.tables.records.ArticleRecordRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.saas.shop.ShopAccountService.ShopAccountListQueryParam;

/**
 * 
 * @author 新国
 *
 */
public class ArticleService extends BaseService {

	final public static class ArticleListQueryParam {
		public Integer categoryId;
		public Byte status;
		public String keywords;
		public String sortName;
		public Integer page;

		public Integer getPage() {
			return page;
		}

		public void setPage(Integer page) {
			this.page = page;
		}

		public Integer getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(Integer categoryId) {
			this.categoryId = categoryId;
		}

		public Byte getStatus() {
			return status;
		}

		public void setStatus(Byte status) {
			this.status = status;
		}

		public String getKeywords() {
			return keywords;
		}

		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}

		public String getSortName() {
			return sortName;
		}

		public void setSortName(String sortName) {
			this.sortName = sortName;
		}

	}

	public PageResult getPageList(ArticleListQueryParam param) {
		SelectWhereStep<Record> select = db().select(
				ARTICLE.asterisk(),
				ARTICLE_CATEGORY.CATEGORY_NAME,
				ARTICLE_CATEGORY.USE_FOOTER_NAV).from(ARTICLE)
				.leftJoin(ARTICLE_CATEGORY).on(ARTICLE.CATEGORY_ID.eq(ARTICLE_CATEGORY.CATEGORY_ID));
		select = this.buildOptions(select, param);
		return this.getPageResult(select, param.page);
	}

	public SelectWhereStep<Record> buildOptions(SelectWhereStep<Record> select, ArticleListQueryParam param) {
		if (param == null) {
			select.orderBy(ARTICLE.ARTICLE_ID.desc());
			return select;
		}

		if (param.categoryId != null && param.categoryId != 0) {
			select.where(ARTICLE.CATEGORY_ID.eq(param.categoryId));
		}

		if (param.status != null) {
			select.where(ARTICLE.STATUS.eq(param.status));
		}
		if (!StringUtils.isEmpty(param.keywords)) {
			select.where(ARTICLE.TITLE.like(param.keywords).or(ARTICLE.DESC.like(param.keywords)));
		}
		if (!StringUtils.isEmpty(param.sortName)) {
			select.orderBy(DSL.field(DSL.name(param.sortName)).desc());
		} else {
			select.orderBy(ARTICLE.ARTICLE_ID.desc());
		}

		return select;
	}

	/**
	 * 店+小程序首页，所需新闻列表
	 * 
	 * @return
	 */
	public Result<ArticleRecord> getFirstList() {
		Byte publishStatus = 1;
		Integer noticeCategoryId = 5;
		return db().selectFrom(ARTICLE)
				.where(ARTICLE.CATEGORY_ID.eq(noticeCategoryId))
				.and(ARTICLE.STATUS.eq(publishStatus))
				.orderBy(ARTICLE.IS_RECOMMEND.desc(), ARTICLE.CREATE_TIME.desc())
				.limit(9)
				.fetch();
	}

	/**
	 * 店+小程序首页，新闻列表分页查询
	 * 
	 * @param page
	 * @return
	 */
	public PageResult getPageList(Integer page) {
		Byte publishStatus = 1;
		Integer noticeCategoryId = 5;
		Integer pageRows = 10;
		SelectWhereStep<Record> select = db().select().from(ARTICLE);
		select.where(ARTICLE.CATEGORY_ID.eq(noticeCategoryId))
				.and(ARTICLE.STATUS.eq(publishStatus))
				.orderBy(ARTICLE.CREATE_TIME.desc());
		return this.getPageResult(select, page, pageRows);
	}

	public Result<ArticleRecord> getArticleByCat(Integer categoryId, Integer limitNumber) {
		Byte publishStatus = 1;
		return db().selectFrom(ARTICLE)
				.where(ARTICLE.CATEGORY_ID.eq(categoryId))
				.and(ARTICLE.STATUS.eq(publishStatus))
				.orderBy(ARTICLE.CREATE_TIME.desc())
				.limit(limitNumber)
				.fetch();
	}

	public List<Map<String, Object>> getFooterArticleList(Integer maxCategoryNum, Integer maxArticleNumPerCategory) {
		Byte useFooterNav = 1;
		Byte showFooter = 1;
		List<Map<String, Object>> result = db().select().from(ARTICLE_CATEGORY)
				.where(ARTICLE_CATEGORY.USE_FOOTER_NAV.eq(useFooterNav))
				.limit(maxCategoryNum)
				.fetch().intoMaps();
		for (Map<String, Object> record : result) {
			List<Map<String, Object>> articles = db().select().from(ARTICLE)
					.where(ARTICLE.CATEGORY_ID.eq(Util.getInteger(record.get("category_id"))))
					.and(ARTICLE.SHOW_FOOTER.eq(showFooter))
					.limit(maxArticleNumPerCategory)
					.fetch().intoMaps();
			record.put("articles", articles);
		}
		return result;
	}

	public ArticleRecordRecord lastArticle(Integer sysId) {
		return db().selectFrom(ARTICLE_RECORD)
				.where(ARTICLE_RECORD.SYS_ID.eq(sysId))
				.orderBy(ARTICLE_RECORD.ARTICLE_ID.desc())
				.fetchOne();
	}

	public List<UInteger> getArticleIdRows(Integer sysId) {
		return db().selectFrom(ARTICLE_RECORD)
				.where(ARTICLE_RECORD.SYS_ID.eq(sysId))
				.fetch(ARTICLE_RECORD.ARTICLE_ID);
	}

	/**
	 * 查找账号未读的最新一篇消息
	 * @param sysId
	 * @return
	 */
	public Record noReadArticle(Integer sysId) {
		ArticleRecordRecord record = lastArticle(sysId);
		Integer articleId = record == null ? 0 : record.getArticleId().intValue();

		Byte publishStatus = 1;
		Integer noticeCategoryId = 1;
		return db().select().from(ARTICLE)
				.where(ARTICLE.CATEGORY_ID.eq(noticeCategoryId))
				.and(ARTICLE.STATUS.eq(publishStatus))
				.and(ARTICLE.ARTICLE_ID.gt(articleId))
				.orderBy(ARTICLE.CREATE_TIME.desc())
				.fetchOne();
	}

}
