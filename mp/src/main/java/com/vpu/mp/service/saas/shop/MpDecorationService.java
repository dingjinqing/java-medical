package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.DecorationTemplate.DECORATION_TEMPLATE;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;

import com.vpu.mp.db.main.tables.records.DecorationTemplateRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;

/**
 * 
 * @author lixinguo
 *
 */
public class MpDecorationService extends BaseService {

	public static class MpDecorationListQueryParam {
		public Integer page;
		public String pageName;
		public Byte pageEnabled;
	}

	public PageResult getPageList(MpDecorationListQueryParam param) {
		SelectWhereStep<Record> select = db().select().from(DECORATION_TEMPLATE);
		select = this.buildOptions(select, param);
		select.orderBy(DECORATION_TEMPLATE.CREATE_TIME.desc());
		return this.getPageResult(select, param.page);
	}

	public SelectWhereStep<Record> buildOptions(SelectWhereStep<Record> select, MpDecorationListQueryParam param) {
		if (param == null) {
			return select;
		}
		if (!StringUtils.isBlank(param.pageName)) {
			select.where(DECORATION_TEMPLATE.PAGE_NAME.like(this.likeValue(param.pageName)));
		}

		if (param.pageEnabled != null) {
			select.where(DECORATION_TEMPLATE.PAGE_ENABLED.eq(param.pageEnabled));
		}

		return select;
	}

	public Result<DecorationTemplateRecord> getAll() {
		return db().fetch(DECORATION_TEMPLATE, DECORATION_TEMPLATE.PAGE_ENABLED.eq((byte) 1));
	}
}
