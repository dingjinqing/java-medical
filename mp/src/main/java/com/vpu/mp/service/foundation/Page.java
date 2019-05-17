package com.vpu.mp.service.foundation;
/**
 * 
 * @author 新国
 *
 */
public class Page {
	public Integer totalRows;
	public Integer currentPage;
	public Integer firstPage;
	public Integer prePage;
	public Integer nextPage;
	public Integer lastPage;
	public Integer pageRows;
	public Integer pageCount;
	public String pageInfo;
	

	public static Page getPage(Integer totalRows, Integer currentPage, Integer pageRows) {
		currentPage = currentPage == null ? 1 : currentPage;
		Integer pageCount = (Integer)(int)Math.ceil(totalRows / pageRows);
		Integer lastPage = pageCount > 0 ? pageCount : 1;
		currentPage = currentPage > lastPage ? lastPage : (currentPage <= 0 ? 1 : currentPage);
		Integer firstPage = 1;
		Integer prePage = currentPage > 1 ? currentPage - 1 : 1;
		Integer nextPage = currentPage >= lastPage ? lastPage : currentPage + 1;
		return new Page(totalRows, currentPage, firstPage, prePage, nextPage, lastPage, pageRows,pageCount);
	}

	public Page(Integer totalRows, Integer currentPage, Integer firstPage, Integer prePage, Integer nextPage,
			Integer lastPage, Integer pageRows, Integer pageCount) {
		super();
		this.totalRows = totalRows;
		this.currentPage = currentPage;
		this.firstPage = firstPage;
		this.prePage = prePage;
		this.nextPage = nextPage;
		this.lastPage = lastPage;
		this.pageRows = pageRows;
		this.pageCount = pageCount;
		this.pageInfo = "当前页面"+currentPage+"/"+lastPage+"，总记录"+totalRows+"条";
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(Integer firstPage) {
		this.firstPage = firstPage;
	}

	public Integer getPrePage() {
		return prePage;
	}

	public void setPrePage(Integer prePage) {
		this.prePage = prePage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	public Integer getLastPage() {
		return lastPage;
	}

	public void setLastPage(Integer lastPage) {
		this.lastPage = lastPage;
	}

	public Integer getPageRows() {
		return pageRows;
	}

	public void setPageRows(Integer pageRows) {
		this.pageRows = pageRows;
	}

	public String getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(String pageInfo) {
		this.pageInfo = pageInfo;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

}
