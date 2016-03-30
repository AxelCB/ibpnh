package org.kairos.ibpnh.core.fx.user;

import org.apache.commons.lang3.StringUtils;
import org.kairos.ibpnh.core.fx.AbstractFxImpl;
import org.kairos.ibpnh.core.fx.FxValidationResponse;
import org.kairos.ibpnh.core.fx.I_Fx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.core.dao.person.I_PersonDao;
import org.kairos.ibpnh.core.dao.user.I_UserDao;
import org.kairos.ibpnh.core.dao.user.I_UserDetailsDao;
import org.kairos.ibpnh.core.dao.user.roleType.I_RoleTypeDao;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.model.E_Priority;
import org.kairos.ibpnh.core.model.user.E_RoleType;
import org.kairos.ibpnh.core.vo.alert.AlertVo;
import org.kairos.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.kairos.ibpnh.core.vo.person.PersonVo;
import org.kairos.ibpnh.core.vo.user.RoleVo;
import org.kairos.ibpnh.core.vo.user.UserDetailsVo;
import org.kairos.ibpnh.core.vo.user.UserVo;

/**
 * FX for Creating a USer
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_CreateUser extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_CreateUser.class);

	/**
	 * User DAO.
	 */
	@Autowired
	private I_UserDao dao;

	/**
	 * The role type DAO.
	 */
	@Autowired
	private I_RoleTypeDao roleTypeDao;

	/**
	 * Parameter DAO.
	 */
	@Autowired
	private I_ParameterDao parameterDao;

	/**
	 * Person DAO.
	 */
	@Autowired
	private I_PersonDao personDao;

	/**
	 * User Details DAO.
	 */
	@Autowired
	private I_UserDetailsDao userDetailsDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		this.logger.debug("executing Fx_CreateUser.validate()");

		if (StringUtils.isBlank(this.getVo().getUsername())) {
			return FxValidationResponse.error(this.getRealMessageSolver()
					.getRequiredParameterMessage("fx.createUser.field.username"));
		}

		if (this.getVo().getRole() == null
				|| this.getVo().getRole().getRoleType() == null
				|| this.getVo().getRole().getRoleType().getRoleTypeEnum() == null) {
			return FxValidationResponse.error(this.getRealMessageSolver()
					.getRequiredParameterMessage("fx.createUser.field.roleType"));
		}

		if (!this.getDao().checkUsernameUniqueness(this.getEm(),
				this.getVo().getUsername(), null)) {

			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage("fx.createUser.validation.nonUniqueUserName",
							null);

			return FxValidationResponse.error(jsonResponseMessage);
		}

		if (!this.getVo().getRole().getRoleType().getRoleTypeEnum()
				.getCanBeCreatedByAdmin()) {
			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage(
							"fx.createUser.validation.nonAvailableRoleType",
							null);

			return FxValidationResponse.error(jsonResponseMessage);
		}

		return FxValidationResponse.ok();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AbstractFxImpl#_completeAlert(org.kairos.ibpnh.core.
	 * vo.alert.AlertVo)
	 */
	@Override
	protected void _completeAlert(AlertVo alertVo) {
		alertVo.setPriority(E_Priority.HIGH);

		alertVo.setDescription(this.getRealMessageSolver().getMessage(
				"fx.createUser.alert.created",
				new String[] {
						this.getVo().getUsername(),
						this.getVo().getRole().getRoleType().getRoleTypeEnum()
								.toString() }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_CreateUser._execute()");

		try {
			this.getEm().getTransaction().begin();

			Long hashCost = this.getParameterDao()
					.getByName(this.getEm(), ParameterVo.HASH_COST)
					.getValue(Long.class);

			// get the enum to create
			E_RoleType roleType = this.getVo().getRole().getRoleType()
					.getRoleTypeEnum();

			// complete the user VO
			this.getVo().setEnabled(Boolean.TRUE);
			this.getVo().resetPassword(hashCost);
			this.getVo().setRole(new RoleVo());
			this.getVo()
					.getRole()
					.setRoleType(
							this.getRoleTypeDao().getByRoleTypeEnum(
									this.getEm(), roleType));
			this.getVo().getRole().copyOrUpdateFromRoleType();

			// persist it
			this.setVo(this.getDao().persist(this.getEm(), this.getVo()));

			// creates an empty person VO and persists it
			PersonVo personVo = new PersonVo();
			personVo = this.getPersonDao().persist(this.getEm(), personVo);

			// creates the relationship between the user and the details
			UserDetailsVo userDetails = new UserDetailsVo();
			userDetails.setPerson(personVo);
			userDetails.setUserId(this.getVo().getId());
			this.getUserDetailsDao().persist(this.getEm(), userDetails);

			this.getEm().getTransaction().commit();

			return JsonResponse.ok(
					this.getGson().toJson(this.getVo()),
					this.getRealMessageSolver().getMessage("fx.createUser.ok",
							new String[] { this.getVo().getUsername() }));

		} catch (Exception e) {
			this.logger.error("error executing Fx_CreateUser._execute()", e);
			try {
				this.getEm().getTransaction().rollback();
			} catch (Exception e1) {
				this.logger.error("error rollbacking transaction", e);
			}

			return this.unexpectedErrorResponse();
		}
	}

	/**
	 * Casted VO.
	 */
	@Override
	public UserVo getVo() {
		return (UserVo) super.getVo();
	}

	/**
	 * @return the dao
	 */
	public I_UserDao getDao() {
		return this.dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(I_UserDao dao) {
		this.dao = dao;
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
	 * @return the userDetailsDao
	 */
	public I_UserDetailsDao getUserDetailsDao() {
		return this.userDetailsDao;
	}

	/**
	 * @param userDetailsDao
	 *            the userDetailsDao to set
	 */
	public void setUserDetailsDao(I_UserDetailsDao userDetailsDao) {
		this.userDetailsDao = userDetailsDao;
	}

}
