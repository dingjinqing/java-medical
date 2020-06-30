package com.vpu.mp.service.saas.shop;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.db.main.tables.records.DecorationTemplateRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.saas.decorate.DecorationTemplatePojo;
import com.vpu.mp.service.pojo.saas.shop.MpDecorationListQueryParam;
import jodd.util.StringUtil;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vpu.mp.db.main.tables.DecorationTemplate.DECORATION_TEMPLATE;

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

    public List<DecorationTemplatePojo> getAll() {
        List<DecorationTemplatePojo> list = db().selectFrom(DECORATION_TEMPLATE).where(DECORATION_TEMPLATE.PAGE_ENABLED.eq((byte)1)).fetchInto(DecorationTemplatePojo.class);
        list.forEach(t->{
            if(StringUtil.isNotEmpty(t.getPageImg())){
                t.setPageImg(saas.sysImage.imageUrl(t.getPageImg()));
            }
        });
        return list;
    }
	
	/**
	 * 得到系统模板
	 * 
	 * @param templateId
	 * @return
	 */
	public DecorationTemplateRecord getRow(Integer templateId) {
		return db().fetchAny(DECORATION_TEMPLATE,
				DECORATION_TEMPLATE.PAGE_ID.eq((templateId)));
	}
}
