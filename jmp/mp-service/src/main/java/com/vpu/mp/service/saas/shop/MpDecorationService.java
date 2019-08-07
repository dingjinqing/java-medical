package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.DecorationTemplate.DECORATION_TEMPLATE;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.DecorationTemplateRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.saas.decorate.DecorationTemplatePojo;
import com.vpu.mp.service.pojo.saas.shop.MpDecorationListQueryParam;

/**
 * 
 * @author lixinguo
 *
 */
@Service("SaasMpDecorationService")

public class MpDecorationService extends MainBaseService {

	public PageResult<DecorationTemplatePojo> getPageList(MpDecorationListQueryParam param) {
		SelectWhereStep<Record> select = db().select().from(DECORATION_TEMPLATE);
		select = this.buildOptions(select, param);
		select.orderBy(DECORATION_TEMPLATE.CREATE_TIME.desc());
		return this.getPageResult(select, param.page,DecorationTemplatePojo.class);
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
