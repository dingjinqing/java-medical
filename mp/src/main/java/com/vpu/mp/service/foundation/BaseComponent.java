package com.vpu.mp.service.foundation;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.DefaultDSLContext;

public class BaseComponent {

	protected DataManager dm = DataManager.instance();

	protected Integer shopId = 0;

	public BaseComponent() {
		Util.initComponents(this);
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public DefaultDSLContext db() {
		return this.shopId == 0 ? dm.db() : dm.db(shopId);
	}

	public DefaultDSLContext mainDb() {
		return dm.db();
	}

	public DefaultDSLContext shopDb(Integer shopId) {
		return dm.db(shopId);
	}

	public Result<Record> getPageList(Table<Record> table, int totalRows, Page page) {
		return db().selectFrom(table).limit((page.currentPage - 1) * page.pageRows, page.pageRows).fetch();
	}

	public Page getPage(Table<Record> table, int currentPage, int pageRows) {
		int totalRows = db().fetchCount(table);
		return Page.getPage(totalRows, currentPage, pageRows);
	}
	
	public String likeValue(String val)
    {
        return "%" + val.replaceAll("%", "%%") + "%";
    }

    public String prefixLikeValue(String val)
    {
    	return val.replaceAll("%", "%%") + "%";
    }


    public String suffixLikeValue(String val)
    {
    	return "%" + val.replaceAll("%", "%%");
    }
}
