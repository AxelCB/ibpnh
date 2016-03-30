package org.kairos.ibpnh.core.model.alert;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kairos.ibpnh.core.model.E_Priority;
import org.kairos.ibpnh.core.model.I_Model;
import org.kairos.ibpnh.core.model.user.User;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;
import org.kairos.ibpnh.core.dao.I_EntityCreationListener;

/**
 * FX Alert
 * 
 * @author Axel Collard Bovy
 * 
 */
@Entity
@AutoProperty(policy = DefaultPojomaticPolicy.TO_STRING)
public class Alert implements I_Model, Serializable, I_EntityCreationListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3116901662984319889L;

	/**
	 * Entity ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alert_seq")
	@SequenceGenerator(

	name = "alert_seq", sequenceName = "alert_seq", allocationSize = 1)
	@Property(policy = PojomaticPolicy.EQUALS)
	private Long id;

	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted;

	/**
	 * Alert priority
	 */
	@Enumerated(EnumType.STRING)
	private E_Priority priority;

	/**
	 * Date time of the alert.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	/**
	 * Description.
	 */
	private String description;

	/**
	 * Involved Object ID
	 */
	private Long objectId;

	/**
	 * Involved Object Class Name
	 */
	private String objectClassName;

	/**
	 * User that generated the alert
	 */
	@ManyToOne
	private User user;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_EntityCreationListener#beforeCreate(javax.persistence
	 * .EntityManager)
	 */
	@Override
	public void beforeCreate(EntityManager em) {
		if (this.getUser() != null && this.getUser().getId() == null) {
			em.detach(this.getUser());
			this.setUser(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return Pojomatic.equals(this, obj);
	}

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	@Override
	public void setId(Long id) {
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
	 * @param deleted
	 *            the deleted to set
	 */
	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the priority
	 */
	public E_Priority getPriority() {
		return this.priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(E_Priority priority) {
		this.priority = priority;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return this.timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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
	 * @return the objectId
	 */
	public Long getObjectId() {
		return this.objectId;
	}

	/**
	 * @param objectId
	 *            the objectId to set
	 */
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	/**
	 * @return the objectClassName
	 */
	public String getObjectClassName() {
		return this.objectClassName;
	}

	/**
	 * @param objectClassName
	 *            the objectClassName to set
	 */
	public void setObjectClassName(String objectClassName) {
		this.objectClassName = objectClassName;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
