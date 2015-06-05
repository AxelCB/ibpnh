package org.kairos.ibpnh.vo.configuration.menuOrder;

import org.kairos.ibpnh.vo.AbstractVo;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;

/**
 * Value Object for Menu Order Function relationship.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 */
@AutoProperty
public class MenuOrderFunctionVo extends AbstractVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4888581845323315360L;
	
	/**
	 * Id of the function object.
	 */
	private Long functionId;

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
	
	
}
