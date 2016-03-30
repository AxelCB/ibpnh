package org.kairos.ibpnh.core.model.configuration.parameter;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kairos.ibpnh.core.model.E_HistoricOperationType;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;
import org.kairos.ibpnh.core.model.I_History;
import org.kairos.ibpnh.core.model.I_Model;

/**
 * System Global Parameter History
 * 
 * @author Axel Collard Bovy
 *
 */
@Entity
@AutoProperty(policy=DefaultPojomaticPolicy.TO_STRING)
public class ParameterHistory implements Serializable, I_Model, I_History {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8824868600221674965L;

	/**
	 * Entity ID.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="parameterhistory_seq")
	@SequenceGenerator(
			
			name="parameterhistory_seq",
			sequenceName="parameterhistory_seq",
			allocationSize=1)
	@Property(policy=PojomaticPolicy.EQUALS)
	private Long id;
	
	/**
	 * Historic value.
	 */
	@Lob
	private String value;
	
	/**
	 * Original parameter.
	 */
	@ManyToOne
	private Parameter parameter;
	
	/**
	 * Modification timestamp.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	/**
	 * The username of the user that made the change.
	 */
	private String username;
	
	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted;
	
	/**
	 * Operation Type.
	 */
	@Enumerated(EnumType.STRING)
	private E_HistoricOperationType operationType;

	/*
	 * (non-Javadoc)
	 * @see org.kairos.ibpnh.core.model.parameter.I_Model#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kairos.ibpnh.core.model.parameter.I_Model#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the parameter
	 */
	public Parameter getParameter() {
		return this.parameter;
	}

	/**
	 * @param parameter the parameter to set
	 */
	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	/**
	 * @return the timestamp
	 */
	@Override
	public Date getTimestamp() {
		return this.timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	@Override
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the username
	 */
	@Override
	public String getUsername() {
		return this.username;
	}

	/**
	 * @param username the username to set
	 */
	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kairos.ibpnh.core.model.parameter.I_Model#getDeleted()
	 */
	@Override
	public Boolean getDeleted() {
		return this.deleted;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kairos.ibpnh.core.model.parameter.I_Model#setDeleted(java.lang.Boolean)
	 */
	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the operationType
	 */
	@Override
	public E_HistoricOperationType getOperationType() {
		return this.operationType;
	}

	/**
	 * @param operationType the operationType to set
	 */
	@Override
	public void setOperationType(E_HistoricOperationType operationType) {
		this.operationType = operationType;
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
