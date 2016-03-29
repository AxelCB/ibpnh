package org.ibpnh.core.model.user;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.ibpnh.core.model.person.Person;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;
import org.ibpnh.core.dao.I_EntityCreationListener;
import org.ibpnh.core.model.I_Model;

/**
 * Entity that adds the persons detail to the user. (for those user that don't
 * already have an entity doing that, like PointOfSaleUser or InspectorUser)
 * 
 * @author Axel Collard Bovy
 * 
 */
@Entity
@AutoProperty(policy = DefaultPojomaticPolicy.TO_STRING)
public class UserDetails implements Serializable, I_Model,
		I_EntityCreationListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2818878609125227048L;

	/**
	 * Entity ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userdetails_seq")
	@SequenceGenerator( name = "userdetails_seq", sequenceName = "userdetails_seq", allocationSize = 1)
	@Property(policy = PojomaticPolicy.EQUALS)
	private Long id;

	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted;

	/**
	 * The personal data of the inspector.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private Person person;

	/**
	 * The user of the inspector.
	 */
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
		if (this.getPerson() != null && this.getPerson().getDeleted() == null) {
			this.getPerson().setDeleted(Boolean.FALSE);
		}
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
	 * @return the person
	 */
	public Person getPerson() {
		return this.person;
	}

	/**
	 * @param person
	 *            the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
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
