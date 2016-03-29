package org.ibpnh.core.model.person;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;
import org.ibpnh.core.dao.I_EntityCreationListener;
import org.ibpnh.core.dao.I_EntityUpdateListener;
import org.ibpnh.core.model.I_Model;

/**
 * Template entity for Ant
 * 
 * @author Axel Collard Bovy
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@AutoProperty(policy = DefaultPojomaticPolicy.TO_STRING)
public class Person implements Serializable, I_Model, I_EntityCreationListener,
		I_EntityUpdateListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3288023199812278550L;

	/**
	 * Entity ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
	@SequenceGenerator( name = "person_seq", sequenceName = "person_seq", allocationSize = 1)
	@Property(policy = PojomaticPolicy.EQUALS)
	private Long id;

	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted;

	/**
	 * The name of the person.
	 */
	private String name;

	/**
	 * The surname of the person.
	 */
	private String surname;

	/**
	 * The type of the ID document.
	 */
	@ManyToOne(optional = true)
	private DocumentType documentType;

	/**
	 * The number of the ID document.
	 */
	private String documentNumber;

	/**
	 * The line phone number.
	 */
	private String phoneNumber;

	/**
	 * The cell phone number.
	 */
	private String cellphoneNumber;

	/**
	 * The email address.
	 */
	private String email;

	/**
	 * Fixes relationships.
	 * 
	 * @param em
	 *            the entity manager
	 */
	public void fixesRelationships(EntityManager em) {
		if (this.getDocumentType() != null
				&& this.getDocumentType().getId() == null) {
			em.detach(this.getDocumentType());
			this.setDocumentType(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_EntityCreationListener#beforeCreate(javax.persistence
	 * .EntityManager)
	 */
	@Override
	public void beforeCreate(EntityManager em) {
		this.fixesRelationships(em);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_EntityUpdateListener#beforeMap()
	 */
	@Override
	public void beforeMap() {
		this.setDocumentType(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_EntityUpdateListener#beforeUpdate(javax.persistence
	 * .EntityManager)
	 */
	@Override
	public void beforeUpdate(EntityManager em) {
		this.fixesRelationships(em);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_Model#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_Model#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_Model#getDeleted()
	 */
	@Override
	public Boolean getDeleted() {
		return this.deleted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_Model#setDeleted(java.lang.Boolean)
	 */
	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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
	 * @return the surname
	 */
	public String getSurname() {
		return this.surname;
	}

	/**
	 * @param surname
	 *            the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the documentType
	 */
	public DocumentType getDocumentType() {
		return this.documentType;
	}

	/**
	 * @param documentType
	 *            the documentType to set
	 */
	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	/**
	 * @return the documentNumber
	 */
	public String getDocumentNumber() {
		return this.documentNumber;
	}

	/**
	 * @param documentNumber
	 *            the documentNumber to set
	 */
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the cellphoneNumber
	 */
	public String getCellphoneNumber() {
		return this.cellphoneNumber;
	}

	/**
	 * @param cellphoneNumber
	 *            the cellphoneNumber to set
	 */
	public void setCellphoneNumber(String cellphoneNumber) {
		this.cellphoneNumber = cellphoneNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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

}