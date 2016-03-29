package org.ibpnh.core.vo.user;

import java.io.Serializable;

import org.pojomatic.annotations.AutoProperty;
import org.ibpnh.core.vo.AbstractVo;

/**
 * Value object for the roletype-function entity.
 * 
 * @author Axel Collard Bovy
 *
 */
@AutoProperty
public class RoleTypeFunctionVo extends AbstractVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5730277911141020191L;

	/**
	 * Id of the function object.
	 */
	private Long functionId;
	
	/**
	 * Function name.
	 */
	private String name;
	
	/**
	 * Function description.
	 */
	private String description;
	
	/**
	 * Enabled flag.
	 */
	private Boolean enabled;
	
	/**
	 * Disabled cause.
	 */
	private String disabledCause;
	
	/**
	 * The function URI.
	 */
	private String uri;

	/**
	 * @return the functionId
	 */
	public Long getFunctionId() {
		return this.functionId;
	}

	/**
	 * @param functionId the functionId to set
	 */
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

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
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return this.enabled;
	}

	/**
	 * @param enabled the enabled to set
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
	 * @param disabledCause the disabledCause to set
	 */
	public void setDisabledCause(String disabledCause) {
		this.disabledCause = disabledCause;
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
