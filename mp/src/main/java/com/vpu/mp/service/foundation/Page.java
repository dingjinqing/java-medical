package com.vpu.mp.service.foundation;

public class Page {
	public int totalRows;
	public int currentPage;
	public int firstPage;
	public int prePage;
	public int nextPage;
	public int lastPage;
	public int pageRows;
	public String pageInfo;

	public static Page getPage(int totalRows, int currentPage, int pageRows) {
		int lastPage = (int) Math.ceil(totalRows / pageRows);
		lastPage = lastPage > 0 ? lastPage : 1;
		currentPage = currentPage > lastPage ? lastPage : (currentPage <= 0 ? 1 : currentPage);
		int firstPage = 1;
		int prePage = currentPage > 1 ? currentPage - 1 : 1;
		int nextPage = currentPage >= lastPage ? lastPage : currentPage + 1;
		return new Page(totalRows, currentPage, firstPage, prePage, nextPage, lastPage, pageRows);
	}

	public Page(int totalRows, int currentPage, int firstPage, int prePage, int nextPage, int lastPage, int pageRows) {
		super();
		this.totalRows = totalRows;
		this.currentPage = currentPage;
		this.firstPage = firstPage;
		this.prePage = prePage;
		this.nextPage = nextPage;
		this.lastPage = lastPage;
		this.pageRows = pageRows;
		this.pageInfo = "当前页面"+currentPage+"/"+lastPage+"，总记录"+totalRows+"条";
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getPageRows() {
		return pageRows;
	}

	public void setPageRows(int pageRows) {
		this.pageRows = pageRows;
	}

	public String getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(String pageInfo) {
		this.pageInfo = pageInfo;
	}

}
