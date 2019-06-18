package com.vpu.mp.service.shop.decoration;

import static com.vpu.mp.db.shop.tables.PageClassification.PAGE_CLASSIFICATION;

import java.util.HashMap;
import java.util.Map;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;
import org.jooq.types.UInteger;

import com.vpu.mp.db.shop.tables.records.PageClassificationRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;

import lombok.Data;

/**
 * 
 * @author lixinguo
 *
 */
public class PageClassificationService extends BaseService {

	@Data
	public static class PageCategoryListQueryParam {
		public Integer id;
		public Integer del;
		public String name;
		public Integer page;
		public String keywords;
		public String act;
	};

	/**
	 * 装修页面列表
	 * 
	 * @param param
	 * @return
	 */
	public PageResult getPageList(PageCategoryListQueryParam param) {
		SelectWhereStep<Record> select = db().select().from(PAGE_CLASSIFICATION);
		select = this.buildOptions(select, param);
		select.orderBy(PAGE_CLASSIFICATION.CREATE_TIME.desc());
		return this.getPageResult(select, param.page);
	}

	/**
	 * 查询条件
	 * 
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectWhereStep<Record> buildOptions(SelectWhereStep<Record> select, PageCategoryListQueryParam param) {
		if (param == null) {
			return select;
		}

		if (!StringUtils.isBlank(param.keywords)) {
			select.where(PAGE_CLASSIFICATION.NAME.like(this.likeValue(param.keywords)));
		}

		return select;
	}

	/**
	 * 获取分类Map
	 * 
	 * @return
	 */
	public Map<String, String> getClassificationMap() {
		Map<String, String> result = new HashMap<String, String>(20);
		for (PageClassificationRecord r : getAll()) {
			result.put(r.getId().toString(), r.getName());
		}
		return result;
	}

	/**
	 * 得到所有页面分类
	 * 
	 * @return
	 */
	public Result<PageClassificationRecord> getAll() {
		return db().fetch(PAGE_CLASSIFICATION);
	}

	/**
	 * 得到id对应分类
	 * 
	 * @param id
	 * @return
	 */
	public PageClassificationRecord getRow(Integer id) {
		return db().fetchAny(PAGE_CLASSIFICATION, PAGE_CLASSIFICATION.ID.eq(UInteger.valueOf(id)));
	}

	public PageClassificationRecord getRowByName(String name) {
		return db().fetchAny(PAGE_CLASSIFICATION, PAGE_CLASSIFICATION.NAME.eq(name));
	}

	/**
	 * 得到名称相同，但ID不同的一条分类记录
	 * 
	 * @param notId
	 * @param name
	 * @return
	 */
	public PageClassificationRecord getNoIdRow(Integer notId, String name) {
		return db().fetchAny(PAGE_CLASSIFICATION,
				PAGE_CLASSIFICATION.NAME.eq(name).and(PAGE_CLASSIFICATION.ID.ne(UInteger.valueOf(notId))));
	}

	public int removeRow(Integer id) {
		return db().delete(PAGE_CLASSIFICATION).where(PAGE_CLASSIFICATION.ID.eq(UInteger.valueOf(id))).execute();
	}

	public int setName(Integer id, String name) {
		return db().update(PAGE_CLASSIFICATION)
				.set(PAGE_CLASSIFICATION.NAME, name)
				.where(PAGE_CLASSIFICATION.ID.eq(UInteger.valueOf(id)))
				.execute();
	}

	public int addRow(String name) {
		return db().insertInto(PAGE_CLASSIFICATION)
				.set(PAGE_CLASSIFICATION.NAME, name)
				.execute();
	}

}
