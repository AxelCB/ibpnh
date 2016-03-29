package org.ibpnh.core.model.person;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;
import org.ibpnh.core.model.I_Model;

/**
 * Person's Document Type
 *
 * @author Axel Collard Bovy
 *
 */
@Entity
@AutoProperty(policy=DefaultPojomaticPolicy.TO_STRING)
public class DocumentType implements Serializable, I_Model {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4663759718859814869L;

	/**
	 * Entity ID.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="documenttype_seq")
	@SequenceGenerator(
			
			name="documenttype_seq",
			sequenceName="documenttype_seq",
			allocationSize=1)
	@Property(policy=PojomaticPolicy.EQUALS)
	private Long id;
	
	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted;
	
	/**
	 * The acronym of the document type's description.
	 */
	private String acronym;
	
	/**
	 * The document type's description.
	 */
	private String description;

	/*
	 * (non-Javadoc)
	 * @see I_Model#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see I_Model#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * @see I_Model#getDeleted()
	 */
	@Override
	public Boolean getDeleted() {
		return this.deleted;
	}

	/*
	 * (non-Javadoc)
	 * @see I_Model#setDeleted(java.lang.Boolean)
	 */
	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	/**
	 * @return the acronym
	 */
	public String getAcronym() {
		return this.acronym;
	}

	/**
	 * @param acronym the acronym to set
	 */
	public void setAcronym(String acronym) {
		this.acronym = acronym;
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
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return Pojomatic.equals(this, obj);
	}

}
