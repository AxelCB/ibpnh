package org.kairos.ibpnh.vo;

import org.kairos.ibpnh.model.I_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Value Object for a page of paginated items.
 * 
 * @author fgonzalez
 *
 * @param <E> the listed entity
 */
public class PaginatedListVo<E extends I_Model>{

	/**
	 * The items list.
	 */
	private List<E> items;
	
	/**
	 * The current page.
	 */
	private Long page;

	/**
	 * The previous page.
	 */
	private Long previousPage;

	/**
	 * The queries cursor.
	 */
	private String cursor;
	
	/**
	 * The total items.
	 */
	private Long totalItems;
	
	/**
	 * The items per page.
	 */
	private Long itemsPerPage;

	
	public PaginatedListVo(){
		this.setItems(new ArrayList<E>());
	}
	
	/**
	 * @return the items
	 */
	public List<E> getItems() {
		return this.items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<E> items) {
		this.items = items;
	}

	/**
	 * @return the totalItems
	 */
	public Long getTotalItems() {
		return this.totalItems;
	}

	/**
	 * @param totalItems the totalItems to set
	 */
	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	/**
	 * @return the itemsPerPage
	 */
	public Long getItemsPerPage() {
		return this.itemsPerPage;
	}

	/**
	 * @param itemsPerPage the itemsPerPage to set
	 */
	public void setItemsPerPage(Long itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
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
}
