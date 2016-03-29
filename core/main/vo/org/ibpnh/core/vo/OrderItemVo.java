package org.ibpnh.core.vo;

/**
 * An item of an order list.
 * 
 * @author Axel Collard Bovy
 *
 */
public class OrderItemVo {
	
	/**
	 * The property to order
	 */
	private String property;
	
	/**
	 * The direction of the order (asc-desc)
	 */
	private Boolean asc;
	
	/**
	 * Default constructor
	 */
	public OrderItemVo() {
	}
	
	/**
	 * Constructor with fields.
	 * 
	 * @param property
	 * @param asc
	 */
	public OrderItemVo(String property, Boolean asc) {
		super();
		this.property = property;
		this.asc = asc;
	}

	/**
	 * @return the property
	 */
	public String getProperty() {
		return this.property;
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * @return the asc
	 */
	public Boolean getAsc() {
		return this.asc;
	}

	/**
	 * @param asc the asc to set
	 */
	public void setAsc(Boolean asc) {
		this.asc = asc;
	}
	
}
