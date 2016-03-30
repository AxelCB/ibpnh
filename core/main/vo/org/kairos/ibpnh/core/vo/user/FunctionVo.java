package org.kairos.ibpnh.core.vo.user;

import org.pojomatic.annotations.AutoProperty;
import org.kairos.ibpnh.core.vo.AbstractVo;

/**
 * VO for the Function entity
 * 
 * @author Axel Collard Bovy
 *
 */
@AutoProperty
public class FunctionVo extends AbstractVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3227955954640754755L;

	/**
	 * Function name.
	 */
	private String name;
	
	/**
	 * Function description.
	 */
	private String description;
	
	/**
	 * Function menu name.
	 */
	private String menuName;
	
	/**
	 * Function subMenu name.
	 */
	private String submenuName;
	
	/**
	 * Function action name.
	 */
	private String actionName;
	
	/**
	 * Function access URI.
	 */
	private String uri;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the menuName
	 */
	public String getMenuName() {
		return this.menuName;
	}

	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * @return the submenuName
	 */
	public String getSubmenuName() {
		return this.submenuName;
	}

	/**
	 * @param submenuName the submenuName to set
	 */
	public void setSubmenuName(String submenuName) {
		this.submenuName = submenuName;
	}

	/**
	 * @return the actionName
	 */
	public String getActionName() {
		return this.actionName;
	}

	/**
	 * @param actionName the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return this.uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}	
}
