package com.vpu.mp.service.shop.member;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record4;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.member.TagInfoVo;
import com.vpu.mp.service.pojo.shop.member.TagPageListParam;
import static org.jooq.impl.DSL.*;

import java.sql.Timestamp;

import static com.vpu.mp.db.shop.Tables.TAG;
import static com.vpu.mp.db.shop.Tables.USER_TAG;
import com.vpu.mp.db.shop.tables.UserTag;
import com.vpu.mp.db.shop.tables.Tag;

/**
 * 
 * @author 黄壮壮 2019-07-09 20:16
 */
public class TagService extends BaseService {

	public PageResult<TagInfoVo> getPageList(TagPageListParam param) {

		UserTag ut = USER_TAG.as("ut");
		Tag t = TAG.as("t");

		Field<Integer> count = this.db().select(DSL.count()).from(ut).where(ut.TAG_ID.eq(t.TAG_ID)).groupBy(ut.USER_ID)
				.asField("count");
		SelectWhereStep<Record4<Integer,String, Timestamp, Integer>> select = this.db().select(t.TAG_ID,t.TAG_NAME, t.CREATE_TIME, count)
				.from(t);
		/*
		 * 按照降序进行查询
		 */
		select.orderBy(t.CREATE_TIME.desc());
		PageResult<TagInfoVo> pageResult = this.getPageResult(select, param.getPage().getCurrentPage(),
				param.getPage().getPageRows(), TagInfoVo.class);
		for (TagInfoVo tag : pageResult.dataList) {
			if (tag.getCount() == null) {
				tag.setCount(0);
			}
		}
		return pageResult;
	}

}
