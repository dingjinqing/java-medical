package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.Tables.TAG;
import static com.vpu.mp.db.shop.Tables.USER_TAG;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.Tag;
import com.vpu.mp.db.shop.tables.UserTag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.tag.TagInfoVo;
import com.vpu.mp.service.pojo.shop.member.tag.TagPageListParam;
import com.vpu.mp.service.pojo.shop.member.tag.UpdateTagParam;
import com.vpu.mp.service.shop.member.dao.tag.TagDao;
import com.vpu.mp.service.pojo.shop.member.tag.CommonTagVo;
import com.vpu.mp.service.pojo.shop.member.tag.TagVo;

/**
 * 
 * @author 黄壮壮 2019-07-09 20:16
 */
@Service
public class TagService extends ShopBaseService {
	final static Byte DELETE_NO = 0;
	@Autowired private TagDao tagDao;

	public PageResult<TagInfoVo> getPageList(TagPageListParam param) {

		UserTag ut = USER_TAG.as("ut");
		Tag t = TAG.as("t");

		final Byte existTag = 0;

		/*
		 * Field<Integer> count =
		 * this.db().select(DSL.count()).from(ut).where(ut.TAG_ID.eq(t.TAG_ID)).groupBy(
		 * ut.TAG_ID) .asField("count");
		 */

		Field<Integer> count = this.db().select(DSL.count()).from(ut).where(ut.TAG_ID.eq(t.TAG_ID)).asField("count");
		SelectWhereStep<Record4<Integer, String, Timestamp, Integer>> select = (SelectWhereStep<Record4<Integer, String, Timestamp, Integer>>) this
				.db().select(t.TAG_ID, t.TAG_NAME, t.CREATE_TIME, count).from(t);
		/*
		 * 按照降序进行查询
		 */

		select = buildOptions(select, param, t);

		select.orderBy(t.CREATE_TIME.desc());
		PageResult<TagInfoVo> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(),
				TagInfoVo.class);
		for (TagInfoVo tag : pageResult.dataList) {
			if (tag.getCount() == null) {
				tag.setCount(0);
			}
		}
		return pageResult;
	}

	/**
	 * 构建选择条件
	 * 
	 * @param select
	 * @param param
	 * @return
	 */

	private SelectWhereStep<Record4<Integer, String, Timestamp, Integer>> buildOptions(
			SelectWhereStep<Record4<Integer, String, Timestamp, Integer>> select, TagPageListParam param, Tag t) {
		if (param == null) {
			return select;
		}
		if (!StringUtils.isEmpty(param.getTagName())) {
			// like value
			String likeValue = this.likeValue(param.getTagName());
			select.where(t.TAG_NAME.like(likeValue));
		}
		return select;
	}

	/**
	 * check if tagName is exits
	 * 
	 * @param tagName
	 */
	public boolean tagNameExists(String tagName) {
		return this.db().fetchCount(TAG, TAG.TAG_NAME.eq(tagName)) > 0;
	}

	/**
	 * 
	 * @param tagName
	 * @return int
	 */
	public int addTagName(String tagName) {
		return this.db().insertInto(TAG, TAG.TAG_NAME).values(tagName).execute();
	}

	/**
	 * 
	 * @param tagId
	 * @return
	 */
	public boolean tagIdExists(Integer tagId) {

		return this.db().fetchCount(TAG, TAG.TAG_ID.eq(tagId)) > 0;
	}

	/**
	 * delete tagName
	 * 
	 * @param tagId
	 */
	public void deleteTag(Integer tagId) {

		// 删除tag tag_id,删除user_tag
		Byte deleteFlag = 1;

		this.transaction(() -> {
			db().delete(TAG).where(TAG.TAG_ID.eq(tagId)).execute();
			db().delete(USER_TAG).where(USER_TAG.TAG_ID.eq(tagId)).execute();
		});

	}

	/**
	 * 更新标签名称
	 */
	public int updateTag(@Valid UpdateTagParam param) {
		int result = db().update(TAG).set(TAG.TAG_NAME, param.getTagName()).where(TAG.TAG_ID.eq(param.getTagId()))
				.execute();
		return result;
	}

	/**
	 * 查询标签列表
	 */
	public List<CommonTagVo> getCommonTagList() {

		List<CommonTagVo> list = db().selectFrom(TAG).orderBy(TAG.TAG_ID.asc()).fetch().into(CommonTagVo.class);
		logger().info("共查询到： " + list.size() + " 条数据");
		return list;
	}
	/**
	 * 获取标签基本信息
	 */
	public List<TagVo> getAllTag() {
		logger().info("获取所有标签");
		 return db().select(TAG.TAG_NAME,TAG.TAG_ID)
			.from(TAG)
			.fetch()
			.into(TagVo.class);
	}

	/**
	 * 根据标签名查询id
	 */
	public List<Integer> getId(String name){
		return tagDao.getId(name);
	}
}
