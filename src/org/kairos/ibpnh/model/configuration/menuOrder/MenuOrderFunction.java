package org.kairos.ibpnh.model.configuration.menuOrder;

import com.google.appengine.datanucleus.annotations.Unowned;
import org.kairos.ibpnh.model.I_Model;
import org.kairos.ibpnh.model.user.Function;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import javax.jdo.annotations.*;
import java.io.Serializable;

/**
 * Defines a menu order function item.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 */
@PersistenceCapable
@AutoProperty(policy= DefaultPojomaticPolicy.TO_STRING)
public class MenuOrderFunction implements Serializable, I_Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 434652441202738219L;

	/**
	 * Entity ID.
	 */
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
	
	/**
	 * Logic deletion flag.
	 */
    @Persistent
	private Boolean deleted;
	
	/**
	 * Menu Order side.
	 */
    @Persistent
	@Property(policy= PojomaticPolicy.NONE)
	@Unowned
	private MenuOrder menuOrder;
	
	/**
	 * Function side.
	 */
    @Persistent
	@Property(policy= PojomaticPolicy.HASHCODE_EQUALS)
	@Unowned
	private Function function;

	/**
	 * @return the id
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the deleted
	 */
	@Override
	public Boolean getDeleted() {
		return this.deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the menuOrder
	 */
	public MenuOrder getMenuOrder() {
		return this.menuOrder;
	}

	/**
	 * @param menuOrder the menuOrder to set
	 */
	public void setMenuOrder(MenuOrder menuOrder) {
		this.menuOrder = menuOrder;
	}

	/**
	 * @return the function
	 */
	public Function getFunction() {
		return this.function;
	}

	/**
	 * @param function the function to set
	 */
	public void setFunction(Function function) {
		this.function = function;
	}
	
	
}
