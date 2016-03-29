package org.ibpnh.core.vo.person;

import org.ibpnh.core.vo.AbstractVo;
import org.ibpnh.core.web.I_MessageSolver;
import org.pojomatic.annotations.AutoProperty;

/**
 * Value Object for the Person Entity
 * 
 * @author Axel Collard Bovy
 * 
 */
@AutoProperty
public class PersonVo extends AbstractVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6575619010896202579L;

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
	private DocumentTypeVo documentType;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractVo#validate(org.ibpnh.core.web.
	 * I_MessageSolver)
	 */
	@Override
	public String validate(I_MessageSolver messageSolver) {
//		if (StringUtils.isNotBlank(this.getEmail())
//				&& !StringUtils.validMail(this.getEmail())) {
//			return messageSolver
//					.getMessage("fx.updatePersonalData.validation.mail");
//		}

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
	public DocumentTypeVo getDocumentType() {
		return this.documentType;
	}

	/**
	 * @param documentType
	 *            the documentType to set
	 */
	public void setDocumentType(DocumentTypeVo documentType) {
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

}
