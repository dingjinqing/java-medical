package com.vpu.mp.service.saas.article;

import static com.vpu.mp.db.main.tables.Article.ARTICLE;
import static com.vpu.mp.db.main.tables.ArticleCategory.ARTICLE_CATEGORY;
import static com.vpu.mp.db.main.tables.ArticleRecord.ARTICLE_RECORD;

import java.sql.Timestamp;
import java.util.List;

import org.jooq.Record;
import org.jooq.Record13;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;

import com.vpu.mp.db.main.tables.records.ArticleRecord;
import com.vpu.mp.db.main.tables.records.ArticleRecordRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.FieldsUtil;
import com.vpu.mp.service.foundation.JedisManager;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.saas.article.ArticleInPut;
import com.vpu.mp.service.pojo.saas.article.ArticleListQueryParam;
import com.vpu.mp.service.pojo.saas.article.ArticleOutPut;
import com.vpu.mp.service.saas.region.CityService;

/**
 * 
 * @author 新国
 *
 */
public class ArticleService extends BaseService {
	
	public CityService city;
	protected JedisManager jedis = JedisManager.instance();
	/**
	 * 多条件查询文章
	 * @param ArticleListQueryParam
	 * @return PageResult<ArticleOutPut>
	 */
	public PageResult<ArticleOutPut> getPageList(ArticleListQueryParam param) {
		SelectWhereStep<? extends Record> select = db().select(
				ARTICLE.ARTICLE_ID,ARTICLE.TITLE,ARTICLE.DESC,ARTICLE.KEYWORD,ARTICLE.AUTHOR,ARTICLE.IS_RECOMMEND,ARTICLE.STATUS,ARTICLE.IS_TOP,ARTICLE.HEAD_PIC,ARTICLE.UPDATE_TIME,
				ARTICLE_CATEGORY.CATEGORY_NAME).from(ARTICLE)
				.leftJoin(ARTICLE_CATEGORY).on(ARTICLE.CATEGORY_ID.eq(ARTICLE_CATEGORY.CATEGORY_ID));
		select = this.buildOptions(select, param);
		if(null != param.getPage().getPageRows()) {
			return getPageResult(select,param.getPage().getCurrentPage(),param.getPage().getPageRows(),ArticleOutPut.class);
		}else {
			return getPageResult(select,param.getPage().getPageRows(),ArticleOutPut.class);
		}
	}

	public SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends  Record> select, ArticleListQueryParam param) {
		if (param == null) {
			select.orderBy(ARTICLE.ARTICLE_ID.desc());
			return select;
		}
		if (param.getCategoryId() != null && param.getCategoryId() != 0) {
			select.where(ARTICLE.CATEGORY_ID.eq(param.getCategoryId()));
		}
		if (param.getStatus() != null) {
			select.where(ARTICLE.STATUS.eq(param.getStatus()));
		}
		if (!StringUtils.isEmpty(param.getKeywords())) {
			select.where(ARTICLE.TITLE.like(likeValue(param.getKeywords())).or(ARTICLE.DESC.like(likeValue(param.getKeywords()))));
		}
		if (!StringUtils.isEmpty(param.getSortName())) {
			String[] sortNames = param.getSortName().split(";");
			for (String sortName : sortNames) {
				String temp = sortName.substring(0, sortName.indexOf(","));
				if(sortName.endsWith("DESC") || sortName.endsWith("desc")) {
					select.orderBy(ARTICLE.field(temp).desc());
				}else {
					select.orderBy(ARTICLE.field(temp).asc());
				}
			}
		} else {
			select.orderBy(ARTICLE.ARTICLE_ID.desc());
		}
		return select;
	}
	
	public ArticleRecordRecord lastArticle(Integer sysId) {
		return db().selectFrom(ARTICLE_RECORD)
				.where(ARTICLE_RECORD.SYS_ID.eq(sysId))
				.orderBy(ARTICLE_RECORD.ARTICLE_ID.desc())
				.fetchAny();
	}

	public List<Integer> getArticleIdRows(Integer sysId) {
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
				.fetchAny();
	}
	
	public boolean insertArticle(ArticleInPut arArticle) {
		//防止传id
		arArticle.setArticleId(null);
		ArticleRecord record = db().newRecord(ARTICLE);
		FieldsUtil.assignNotNull(arArticle, record);
		//更新发布状态
		if(null != arArticle.getStatus()){
			if(1 == arArticle.getStatus()) {
				record.set(ARTICLE.PUB_TIME, new Timestamp(System.currentTimeMillis()));
			}else if(arArticle.getStatus() == 0){
				record.set(ARTICLE.PUB_TIME, null);
			}
			record.set(ARTICLE.STATUS, arArticle.getStatus());
		}
		int num = record.store();
		return num > 0 ? true : false;
	}

	public boolean deleteArticle(Integer articleId) {
		int num = db().delete(ARTICLE).where(ARTICLE.ARTICLE_ID.eq(articleId)).execute();
		return  num > 0 ? true : false;
	}

	public boolean updateArticle(ArticleInPut arArticle) {
		ArticleRecord record = db().fetchOne(ARTICLE,ARTICLE.ARTICLE_ID.eq(arArticle.getArticleId()));
		FieldsUtil.assignNotNull(arArticle, record);
		//更新发布状态
		if(null != arArticle.getStatus()){
			if(1 == arArticle.getStatus() && record.getStatus() == 0) {
				record.set(ARTICLE.PUB_TIME, new Timestamp(System.currentTimeMillis()));
			}else if(arArticle.getStatus() == 0){
				record.set(ARTICLE.PUB_TIME, null);
			}
			record.set(ARTICLE.STATUS, arArticle.getStatus());
		}
		int num = record.store();
		return  num > 0 ? true : false;
	}
	
	public ArticleOutPut get(Integer articleId) {
		Record13<Integer, Integer, String, String, String, String, String, Byte, Byte, String, String, Timestamp, Integer> article = db().select(ARTICLE.ARTICLE_ID,ARTICLE.CATEGORY_ID,ARTICLE.TITLE,ARTICLE.AUTHOR,ARTICLE.KEYWORD,ARTICLE.DESC,ARTICLE.CONTENT,ARTICLE.IS_RECOMMEND,ARTICLE.STATUS,ARTICLE.HEAD_PIC,ARTICLE_CATEGORY.CATEGORY_NAME,ARTICLE.UPDATE_TIME,ARTICLE.PV)
				.from(ARTICLE)
				.leftJoin(ARTICLE_CATEGORY).on(ARTICLE.CATEGORY_ID.eq(ARTICLE_CATEGORY.CATEGORY_ID))
				.where(ARTICLE.ARTICLE_ID.eq(articleId)).fetchOne();
		Integer integer = article.get(ARTICLE.PV);
		//更新访问量
		db().update(ARTICLE).set(ARTICLE.PV, integer ==null ? 0 : integer + 1);
		return	article==null?null:article.into(ArticleOutPut.class);
	}

}
