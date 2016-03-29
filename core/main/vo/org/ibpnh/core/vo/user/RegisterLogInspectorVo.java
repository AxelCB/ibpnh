package org.ibpnh.core.vo.user;

import java.io.Serializable;
import java.util.Date;

import org.pojomatic.annotations.AutoProperty;
import org.ibpnh.core.vo.AbstractVo;

/**
 * Value object for the register log inspector entity.
 * 
 * @author Axel Collard Bovy
 * 
 */
@AutoProperty
public class RegisterLogInspectorVo extends AbstractVo implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -738681574285289519L;

	/**
	 * String Codigo Accion.
	 */
	private String codigoAccion;
	
	/**
	 * String coordinates.
	 */
	private String coordinates;
	
	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted;
	
	/**
	 * timestamp.
	 */
	private Date timestamp;

	/**
	 * String name user
	 */
	private String username;
	
	/**
	 * String rol user
	 */
	private String rol;

	
	public String getCodigoAccion() {
		return codigoAccion;
	}

	public void setCodigoAccion(String codigoAccion) {
		this.codigoAccion = codigoAccion;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

}
