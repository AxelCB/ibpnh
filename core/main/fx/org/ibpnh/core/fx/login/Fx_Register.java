package org.ibpnh.core.fx.login;

import java.text.ParseException;
import java.util.Date;

import org.ibpnh.core.fx.AbstractFxImpl;
import org.ibpnh.core.fx.FxValidationResponse;
import org.ibpnh.core.fx.I_Fx;
import org.ibpnh.core.fx.I_FxFactory;
import org.ibpnh.core.services.mail.I_MailJobScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.ibpnh.core.dao.person.I_PersonDao;
import org.ibpnh.core.dao.user.I_UserDao;
import org.ibpnh.core.dao.user.roleType.I_RoleTypeDao;
import org.ibpnh.core.fx.configuration.mail.parameters.Fx_Mail_Activation;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.model.user.E_RoleType;
import org.ibpnh.core.utils.ErrorCodes;
import org.ibpnh.core.utils.HashUtils;
import org.ibpnh.core.utils.I_DateUtils;
import org.ibpnh.core.utils.I_PasswordUtils;
import org.ibpnh.core.utils.StringUtils;
import org.ibpnh.core.vo.AbstractVo;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.vo.configuration.mail.MailVo;
import org.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.ibpnh.core.vo.person.DocumentTypeVo;
import org.ibpnh.core.vo.person.PersonVo;
import org.ibpnh.core.vo.user.RoleVo;
import org.ibpnh.core.vo.user.UserVo;
import org.ibpnh.core.web.I_MessageSolver;

/**
 * FX for User Registration.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_Register extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_Register.class);

	/**
	 * Parameter DAO.
	 */
	@Autowired
	private I_ParameterDao parameterDao;

	/**
	 * User DAO.
	 */
	@Autowired
	private I_UserDao userDao;

	/**
	 * Role Type DAO.
	 */
	@Autowired
	private I_RoleTypeDao roleTypeDao;

	/**
	 * Person DAO.
	 */
	@Autowired
	private I_PersonDao personDao;

	/**
	 * Mail Job Scheduler.
	 */
	@Autowired
	private I_MailJobScheduler mailJobScheduler;

	/**
	 * FX Factory.
	 */
	@Autowired
	private I_FxFactory fxFactory;

	/**
	 * Password utils
	 */
	@Autowired
	private I_PasswordUtils passwordUtils;
	
	/**
	 * Date Utils.
	 */
	@Autowired
	private I_DateUtils dateUtils;
	
	/**
	 * The remote IP.
	 */
	private String remoteIp;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		try {
			Boolean enabled = this.getParameterDao()
					.getByName(this.getEm(), ParameterVo.USER_REGISTRATION)
					.getValue(Boolean.class);

			if (!enabled) {
				this.logger.error("user registration disabled");

				String jsonResponseMessage = this.getRealMessageSolver()
						.getMessage("fx.register.validation.disabled");

				return FxValidationResponse.error(jsonResponseMessage);
			}
		} catch (ParseException e) {
			this.logger.error("failed to get value of parameter {}",
					ParameterVo.USER_REGISTRATION);

			return FxValidationResponse
					.error(this
							.unexpectedErrorResponse(
									ErrorCodes.ERROR_PARAMETER_PARSING)
							.getMessages().toArray(new String[] {}));
		}

		String result = this.getVo().validate(this.getWebContextHolder());
		
		if (this.passwordUtils.formatCheck(this.getVo().getPassword())) {
			
			ParameterVo errorMessageParameter = this.getParameterDao().
					getByName(this.getEm(), "fx.changePassword.validation.formatError");
			
			this.logger.error("password does not match against stored password regExps");

			return FxValidationResponse.error(errorMessageParameter.getValue());
		}

		if (!this.getPersonDao().checkEmailUniqueness(this.getEm(),
				this.getVo().getEmail(), null)) {
			return FxValidationResponse.error(this.getWebContextHolder()
					.getMessage("fx.nonUniqueEmail"));
		}

		if (result != null) {
			return FxValidationResponse.error(result);
		}

		try {
			Long minCharacters = this
					.getParameterDao()
					.getByName(this.getEm(),
							ParameterVo.PASSWORD_MIN_CHARACTERS)
					.getValue(Long.class);

			if (this.getVo().getPassword().length() < minCharacters) {
				this.logger.error("password does not have the min characters");

				String jsonResponseMessage = this
						.getRealMessageSolver()
						.getMessage(
								"fx.changePassword.validation.minCharactersError",
								new Long[] { minCharacters });

				return FxValidationResponse.error(jsonResponseMessage);
			}
		} catch (ParseException e) {
			this.logger.error("failed to value of parameter {}",
					ParameterVo.PASSWORD_MIN_CHARACTERS);

			return FxValidationResponse
					.error(this
							.unexpectedErrorResponse(
									ErrorCodes.ERROR_PARAMETER_PARSING)
							.getMessages().toArray(new String[] {}));
		}

		if (!this.getUserDao().checkUsernameUniqueness(this.getEm(),
				this.getVo().getUsername(), null)) {
			return FxValidationResponse.error(this.getWebContextHolder()
					.getMessage("fx.register.validation.nonUniqueUsername"));
		}
		return FxValidationResponse.ok();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AbstractFxImpl#_completeAlert(org.ibpnh.core.
	 * vo.alert.AlertVo)
	 */
	@Override
	protected void _completeAlert(AlertVo alertVo) {
		alertVo.setPriority(E_Priority.MEDIUM);

		alertVo.setDescription(this.getWebContextHolder().getMessage(
				"fx.register.alert.registered",
				new String[] { this.getVo().getUsername() }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("entering Fx_Register._execute()");

		JsonResponse jsonResponse = null;

		try {
			this.getEm().getTransaction().begin();

			Long hashCost = this.getParameterDao()
					.getByName(this.getEm(), ParameterVo.HASH_COST)
					.getValue(Long.class);

			// we create the new user
			// creates the user
			UserVo userVo = new UserVo();
			// it is not enabled until email account is validated
			userVo.setEnabled(Boolean.FALSE);
			userVo.setDisabledCause(this.getWebContextHolder().getMessage(
					"fx.register.disabled"));
			userVo.setFirstLogin(Boolean.FALSE);
			userVo.setPassword(HashUtils.hashPassword(this.getVo()
					.getPassword(), hashCost));
			userVo.setHashCost(hashCost);
			userVo.setUsername(this.getVo().getUsername());
			userVo.setLoginAttempts(0);
			userVo.setRole(new RoleVo());
			userVo.getRole().setRoleType(
					this.getRoleTypeDao().getByRoleTypeEnum(this.getEm(),
							E_RoleType.USER));
			userVo.getRole().copyOrUpdateFromRoleType();
			// we hash the current date as string concatenated with a random
			// sequence of 10 characters, and use that hash to enable the user
			userVo.setEnablingHash(HashUtils.hashPasswordSHA512(this.getDateUtils().formateDateTime(new Date())
					+ HashUtils.generateRandomPassword(10, Boolean.FALSE, Boolean.FALSE)));

			userVo = this.getUserDao().persist(this.getEm(), userVo);

			// create the person with the personal data
			PersonVo personVo = new PersonVo();
			personVo.setSurname(this.getVo().getSurname());
			personVo.setName(this.getVo().getName());
			personVo.setDocumentType(this.getVo().getDocumentType());
			personVo.setDocumentNumber(this.getVo().getDocumentNumber());
			personVo.setPhoneNumber(this.getVo().getPhoneNumber());
			personVo.setCellphoneNumber(this.getVo().getCellphoneNumber());
			personVo.setEmail(this.getVo().getEmail());

			personVo = this.getPersonDao().persist(this.getEm(), personVo);

			// send email
			Fx_Mail_Activation fx = this.getFxFactory().getNewFxInstance(
					Fx_Mail_Activation.class);
			fx.setActivationHash(userVo.getEnablingHash());
			fx.setUsername(userVo.getUsername());
			fx.setName(personVo.getName());
			fx.setSurname(personVo.getSurname());
			fx.setEmail(personVo.getEmail());
			this.getMailJobScheduler().triggerMail(
					MailVo.ACTIVATION_EMAIL_NAME, fx);

			this.getEm().getTransaction().commit();

			jsonResponse = JsonResponse.ok("", this.getWebContextHolder()
					.getMessage("fx.register.registered.description"));
		} catch (ParseException pe) {
			this.logger.error("error parsing parameter", pe);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse(
					ErrorCodes.ERROR_PARAMETER_PARSING);
		} catch (Exception e) {
			this.logger.error("unexpected error", e);

			jsonResponse = this.getWebContextHolder().unexpectedErrorResponse();
		} finally {
			if (this.getEm().getTransaction().isActive()) {
				try {
					this.getEm().getTransaction().rollback();
				} catch (Exception e2) {
					this.logger.error("error rollbacking transaction", e2);
					// does nothing
				}
			}
		}

		return jsonResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#getVo()
	 */
	@Override
	public RegistrationVo getVo() {
		return (RegistrationVo) super.getVo();
	}

	/**
	 * @return the parameterDao
	 */
	public I_ParameterDao getParameterDao() {
		return this.parameterDao;
	}

	/**
	 * @param parameterDao
	 *            the parameterDao to set
	 */
	public void setParameterDao(I_ParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}

	/**
	 * Helper VO Class.
	 * 
	 * @author Axel Collard Bovy
	 * 
	 */
	public class RegistrationVo extends AbstractVo {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5401874920346629819L;

		/**
		 * The username of the new user.
		 */
		private String username;

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

		/**
		 * Password.
		 */
		private String password;

		/**
		 * Captcha challenge.
		 */
		private String challenge;

		/**
		 * Captcha response.
		 */
		private String response;

		/*
		 * (non-Javadoc)
		 * 
		 * @see AbstractVo#validate(org.ibpnh.core.web.
		 * I_MessageSolver)
		 */
		@Override
		public String validate(I_MessageSolver messageSolver) {
			if (StringUtils.isBlank(this.getUsername())) {
				return messageSolver
						.getRequiredParameterMessage("fx.register.field.username");
			} else if (StringUtils.isBlank(this.getSurname())) {
				return messageSolver
						.getRequiredParameterMessage("fx.register.field.surname");
			} else if (StringUtils.isBlank(this.getName())) {
				return messageSolver
						.getRequiredParameterMessage("fx.register.field.name");
			} else if (this.getDocumentType() == null) {
				return messageSolver
						.getRequiredParameterMessage("fx.register.field.documentType");
			} else if (StringUtils.isBlank(this.getDocumentNumber())) {
				return messageSolver
						.getRequiredParameterMessage("fx.register.field.documentNumber");
			} else if (StringUtils.isBlank(this.getPassword())) {
				return messageSolver
						.getRequiredParameterMessage("fx.register.field.password");
			} else if (StringUtils.isBlank(this.getEmail())) {
				return messageSolver
						.getRequiredParameterMessage("fx.register.field.email");
			} else if (!StringUtils.validMail(this.getEmail())) {
				return messageSolver
						.getMessage("fx.register.validation.emailNotValid");
			}

			return null;
		}

		/**
		 * @return the username
		 */
		public String getUsername() {
			return this.username;
		}

		/**
		 * @param username
		 *            the username to set
		 */
		public void setUsername(String username) {
			this.username = username;
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

		/**
		 * @return the password
		 */
		public String getPassword() {
			return this.password;
		}

		/**
		 * @param password
		 *            the password to set
		 */
		public void setPassword(String password) {
			this.password = password;
		}

		/**
		 * @return the challenge
		 */
		public String getChallenge() {
			return this.challenge;
		}

		/**
		 * @param challenge
		 *            the challenge to set
		 */
		public void setChallenge(String challenge) {
			this.challenge = challenge;
		}

		/**
		 * @return the response
		 */
		public String getResponse() {
			return this.response;
		}

		/**
		 * @param response
		 *            the response to set
		 */
		public void setResponse(String response) {
			this.response = response;
		}

	}

	/**
	 * @return the userDao
	 */
	public I_UserDao getUserDao() {
		return this.userDao;
	}

	/**
	 * @param userDao
	 *            the userDao to set
	 */
	public void setUserDao(I_UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @return the roleTypeDao
	 */
	public I_RoleTypeDao getRoleTypeDao() {
		return this.roleTypeDao;
	}

	/**
	 * @param roleTypeDao
	 *            the roleTypeDao to set
	 */
	public void setRoleTypeDao(I_RoleTypeDao roleTypeDao) {
		this.roleTypeDao = roleTypeDao;
	}

	/**
	 * @return the personDao
	 */
	public I_PersonDao getPersonDao() {
		return this.personDao;
	}

	/**
	 * @param personDao
	 *            the personDao to set
	 */
	public void setPersonDao(I_PersonDao personDao) {
		this.personDao = personDao;
	}

	/**
	 * @return the remoteIp
	 */
	public String getRemoteIp() {
		return this.remoteIp;
	}

	/**
	 * @param remoteIp
	 *            the remoteIp to set
	 */
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	/**
	 * @return the mailJobScheduler
	 */
	public I_MailJobScheduler getMailJobScheduler() {
		return this.mailJobScheduler;
	}

	/**
	 * @param mailJobScheduler
	 *            the mailJobScheduler to set
	 */
	public void setMailJobScheduler(I_MailJobScheduler mailJobScheduler) {
		this.mailJobScheduler = mailJobScheduler;
	}

	/**
	 * @return the fxFactory
	 */
	public I_FxFactory getFxFactory() {
		return this.fxFactory;
	}

	/**
	 * @param fxFactory
	 *            the fxFactory to set
	 */
	public void setFxFactory(I_FxFactory fxFactory) {
		this.fxFactory = fxFactory;
	}

	/**
	 * @return the passwordUtils
	 */
	public I_PasswordUtils getPasswordUtils() {
		return this.passwordUtils;
	}

	/**
	 * @param passwordUtils 
	 * 			the passwordUtils to set
	 */
	public void setPasswordUtils(I_PasswordUtils passwordUtils) {
		this.passwordUtils = passwordUtils;
	}

	/**
	 * @return the dateUtils
	 */
	public I_DateUtils getDateUtils() {
		return this.dateUtils;
	}

	/**
	 * @param dateUtils the dateUtils to set
	 */
	public void setDateUtils(I_DateUtils dateUtils) {
		this.dateUtils = dateUtils;
	}

}
