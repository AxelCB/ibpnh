package org.kairos.ibpnh.core.model.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.kairos.ibpnh.core.model.I_Model;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

/**
 * Password recover entity for the user.
 * 
 * @author Axel Collard Bovy
 * 
 */
@Entity
@AutoProperty(policy = DefaultPojomaticPolicy.TO_STRING)
public class PasswordRecover implements Serializable, I_Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8296696523822338857L;

	/**
	 * Entity ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passwordrecover_seq")
	@SequenceGenerator( name = "passwordrecover_seq", sequenceName = "passwordrecover_seq", allocationSize = 1)
	@Property(policy = PojomaticPolicy.EQUALS)
	private Long id;

	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted = false;

	/**
	 * User that this recover is for.
	 */
	@ManyToOne
	private User user;

	/**
	 * Private hash generated for the view.
	 */
	private String privateHash;

	/**
	 * Public hash generated for the user email.
	 */
	private String publicHash;

	/**
	 * Recovered flag.
	 */
	private Boolean recovered;

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

	/**
	 * @return the privateHash
	 */
	public String getPrivateHash() {
		return this.privateHash;
	}

	/**
	 * @param privateHash
	 *            the privateHash to set
	 */
	public void setPrivateHash(String privateHash) {
		this.privateHash = privateHash;
	}

	/**
	 * @return the publicHash
	 */
	public String getPublicHash() {
		return this.publicHash;
	}

	/**
	 * @param publicHash
	 *            the publicHash to set
	 */
	public void setPublicHash(String publicHash) {
		this.publicHash = publicHash;
	}

	/**
	 * @return the recovered
	 */
	public Boolean getRecovered() {
		return this.recovered;
	}

	/**
	 * @param recovered
	 *            the recovered to set
	 */
	public void setRecovered(Boolean recovered) {
		this.recovered = recovered;
	}

}
