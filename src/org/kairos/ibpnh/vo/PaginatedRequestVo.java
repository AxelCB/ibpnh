package org.kairos.ibpnh.vo;

/**
 * Value Object for a request that is used with pagination.
 * 
 * @author fgonzalez
 */
public class PaginatedRequestVo {
	
	/**
	 * The page to load
	 */
	private Long page;

	/**
	 * The previous loaded page
	 */
	private Long previousPage;

	/**
	 * The query cursor
	 */
	private String cursor;
	
	/**
	 * Flag that indicates if an aditional query is used to get the total number of items.
	 */
	private Boolean fetchTotal;

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Long getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(Long previousPage) {
		this.previousPage = previousPage;
	}

	public String getCursor() {
		return cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	public Boolean getFetchTotal() {
		return fetchTotal;
	}

	public void setFetchTotal(Boolean fetchTotal) {
		this.fetchTotal = fetchTotal;
	}
}
