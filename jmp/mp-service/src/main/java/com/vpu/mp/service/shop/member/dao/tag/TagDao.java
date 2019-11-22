package com.vpu.mp.service.shop.member.dao.tag;

import static com.vpu.mp.db.shop.Tables.TAG;

import java.util.List;

import org.jooq.Result;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.TagRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;

/**
 * @author 黄壮壮
 * @Date: 2019年11月22日
 * @Description:
 */

@Service
public class TagDao extends ShopBaseService {
	public Result<TagRecord> getTag(String name) {
		return db().selectFrom(TAG)
					.where(TAG.TAG_NAME.eq(name))
					.fetch();
	}
	
	public List<Integer> getId(String name) {
		return getTag(name).getValues(TAG.TAG_ID,Integer.class);
	}
}
