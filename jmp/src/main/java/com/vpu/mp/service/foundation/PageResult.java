package com.vpu.mp.service.foundation;

import java.util.List;
import java.util.Map;
/**
 * 
 * @author 新国
 *
 */
public class PageResult {
	public Page page;
	public List<Map<String, Object>> dataList;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}

    @Override
    public String toString() {
        return "PageResult{" +
                "page=" + page +
                ", dataList=" + dataList +
                '}';
    }
}
