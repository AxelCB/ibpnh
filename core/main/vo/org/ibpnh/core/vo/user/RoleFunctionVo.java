package org.ibpnh.core.vo.user;

import java.io.Serializable;

import org.pojomatic.annotations.AutoProperty;
import org.ibpnh.core.vo.AbstractVo;

/**
 * Value object for the role-function entity.
 * 
 * @author Axel Collard Bovy
 * 
 */
@AutoProperty
public class RoleFunctionVo extends AbstractVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -250667366149917296L;

	/**
	 * Id of the relation object.
	 */
	private Long roleFunctionId;

	/**
	 * Enabled flag.
	 */
	private Boolean enabled;

	/**
	 * Cause of the disqualification of the user for this function.
	 */
	private String disabledCause;

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
	 * Function URI
	 */
	private String uri;

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return this.enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the disabledCause
	 */
	public String getDisabledCause() {
		return this.disabledCause;
	}

	/**
	 * @param disabledCause
	 *            the disabledCause to set
	 */
	public void setDisabledCause(String disabledCause) {
		this.disabledCause = disabledCause;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
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
	 * @param description
	 *            the description to set
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
	 * @param menuName
	 *            the menuName to set
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
	 * @param submenuName
	 *            the submenuName to set
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
	 * @param actionName
	 *            the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * @return the roleFunctionId
	 */
	public Long getRoleFunctionId() {
		return this.roleFunctionId;
	}

	/**
	 * @param roleFunctionId
	 *            the roleFunctionId to set
	 */
	public void setRoleFunctionId(Long roleFunctionId) {
		this.roleFunctionId = roleFunctionId;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return this.uri;
	}

	/**
	 * @param uri
	 *            the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
}
