package org.kairos.ibpnh.core.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Value Object that encapsulates the description of a certain order
 * to add to a query.
 * 
 * @author Axel Collard Bovy
 *
 */
public class OrderVo {
	
	/**
	 * The order items
	 */
	private List<OrderItemVo> items;
	
	/**
	 * Default constructor
	 */
	public OrderVo() {
		this.items = new ArrayList<>();
	}

	/**
	 * Creates a new order with items.
	 * 
	 * @param orders items of the order
	 */
	public OrderVo(OrderItemVo... orders) {
		this.items = Arrays.asList(orders);
	}
	
	/**
	 * @return the items
	 */
	public List<OrderItemVo> getItems() {
		return this.items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<OrderItemVo> items) {
		this.items = items;
	}
	
}
