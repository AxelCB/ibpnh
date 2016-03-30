package org.kairos.ibpnh.core.vo;

/**
 * Helper VO Class.
 * 
 * @author Axel Collard Bovy
 *
 */
public class DetailItemVo {

	/**
	 * The key.
	 */
	private String key;
	
	/**
	 * The value.
	 */
	private Object value;
	
	/**
	 * @param key
	 * @param value
	 */
	public DetailItemVo(String key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
}