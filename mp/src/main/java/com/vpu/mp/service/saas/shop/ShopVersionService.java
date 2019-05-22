package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.ShopVersion.SHOP_VERSION;

import java.util.HashMap;
import java.util.Map;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;

import com.vpu.mp.db.main.tables.records.ShopVersionRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;

/**
 * 
 * @author 新国
 *
 */
public class ShopVersionService extends BaseService {

	final public class VersionListQueryParam {
		public Integer page;
		public String versionName;
	};

	public PageResult getPageList(VersionListQueryParam param) {
		SelectWhereStep<Record> select = db().select().from(SHOP_VERSION);
		if (!StringUtils.isEmpty(param.versionName)) {
			select.where(SHOP_VERSION.VERSION_NAME.like(this.likeValue(param.versionName)));
		}
		select.orderBy(SHOP_VERSION.ID.desc());
		return this.getPageResult(select, param.page);
	}

	public Result<ShopVersionRecord> getAllVersion() {
		return db().selectFrom(SHOP_VERSION)
				.where(SHOP_VERSION.FLAG.eq((byte) 0))
				.orderBy(SHOP_VERSION.LEVEL.desc())
				.fetch();
	}

	public Map<String, String> getVersionMap() {
		Map<String, String> result = new HashMap<String, String>(10);
		for (ShopVersionRecord record : getAllVersion()) {
			result.put(record.getLevel(), record.getVersionName());
		}
		return result;
	}
}
