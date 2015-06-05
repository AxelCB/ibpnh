package org.kairos.ibpnh.vo.configuration.menuOrder;

import org.kairos.ibpnh.vo.AbstractVo;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Value Object for the Menu Order entity
 * 
 * @author AxelCollardBovy ,created on 08/03/2015.
 * 
 */
@AutoProperty
public class MenuOrderVo extends AbstractVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6200504647234794963L;

	/**
	 * Order name.
	 */
	private String name;

	/**
	 * Order description.
	 */
	private String description;

	/**
	 * MenuOrder-Function associations for this menu order.
	 */
	private List<MenuOrderFunctionVo> menuOrderFunctions;
	
	/**
	 * Returns the order of the function Id.
	 * 
	 * @param functionId function ID to search
	 * 
	 * @return order integer or null
	 */
	public Integer order(Long functionId) {
		for (MenuOrderFunctionVo menuOrderFunctionVo : this.getMenuOrderFunctions()) {
			if (menuOrderFunctionVo.getFunctionId().equals(functionId)) {
				return this.getMenuOrderFunctions().indexOf(menuOrderFunctionVo);
			}
		}
		
		return null;
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
	 * @return the menuOrderFunctions
	 */
	public List<MenuOrderFunctionVo> getMenuOrderFunctions() {
		return this.menuOrderFunctions;
	}

	/**
	 * @param menuOrderFunctions
	 *            the menuOrderFunctions to set
	 */
	public void setMenuOrderFunctions(
			List<MenuOrderFunctionVo> menuOrderFunctions) {
		this.menuOrderFunctions = menuOrderFunctions;
	}

}
