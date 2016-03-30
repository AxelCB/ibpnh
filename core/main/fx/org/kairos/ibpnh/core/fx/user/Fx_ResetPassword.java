package org.kairos.ibpnh.core.fx.user;

import org.kairos.ibpnh.core.fx.AbstractFxImpl;
import org.kairos.ibpnh.core.fx.FxValidationResponse;
import org.kairos.ibpnh.core.fx.I_Fx;
import org.kairos.ibpnh.core.fx.user.roleType.Fx_CreateRoleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.core.dao.user.I_UserDao;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.model.E_Priority;
import org.kairos.ibpnh.core.utils.HashUtils;
import org.kairos.ibpnh.core.vo.alert.AlertVo;
import org.kairos.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.kairos.ibpnh.core.vo.user.UserVo;

/**
 * FX for Creating a RoleType
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_ResetPassword extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_CreateRoleType.class);

	/**
	 * User DAO.
	 */
	@Autowired
	private I_UserDao dao;

	/**
	 * Parameter DAO.
	 */
	@Autowired
	private I_ParameterDao parameterDao;

	/**
	 * Flag indicating if the password would be generated randomly.
	 */
	private Boolean random = Boolean.FALSE;

	/**
	 * The random password length.
	 */
	private Long randomPasswordLength = 6L;

	/**
	 * If the random password must be only numeric.
	 */
	private Boolean randomPasswordNumeric = Boolean.FALSE;

	/**
	 * If the random password must be all in lower case.
	 */
	private Boolean randomPasswordAllLowercase = Boolean.FALSE;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		this.logger.debug("executing Fx_ResetPassword.validate()");

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
		alertVo.setPriority(E_Priority.MEDIUM);

		alertVo.setDescription(this.getRealMessageSolver().getMessage(
				"fx.resetPassword.alert.description",
				new String[] { this.getVo().getUsername() }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_ResetPassword._execute()");

		try {
			this.beginTransaction();

			Long hashCost = this.getParameterDao()
					.getByName(this.getEm(), ParameterVo.HASH_COST)
					.getValue(Long.class);

			// we persist the entity
			this.getDao().setDeepMapping(Boolean.FALSE);
			this.getVo().resetPassword(hashCost);

			String newPassword = null;
			if (this.getRandom()) {
				newPassword = HashUtils.generateRandomPassword(this
						.getRandomPasswordLength().intValue(), this
						.getRandomPasswordNumeric(), this
						.getRandomPasswordAllLowercase());

				this.getVo().setPassword(
						HashUtils.hashPassword(newPassword, hashCost));
				this.getVo().setHashCost(hashCost);
			}

			UserVo userVo = this.getDao().persist(this.getEm(), this.getVo());

			this.getEm().flush();
			this.commitTransaction();

			if (newPassword != null) {
				// we do this in order to the new password to be
				// accessible for the FX caller
				userVo.setPassword(newPassword);
			}
			this.setVo(userVo);

			return JsonResponse.ok(
					this.getGson().toJson(userVo),
					this.getRealMessageSolver().getMessage(
							"fx.resetPassword.ok",
							new String[] { this.getVo().getUsername() }));

		} catch (Exception e) {
			this.logger.error("error executing Fx_ResetPassword._execute()", e);
			
			this.rollbackTransaction();

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
	 * @return the random
	 */
	public Boolean getRandom() {
		return this.random;
	}

	/**
	 * @param random
	 *            the random to set
	 */
	public void setRandom(Boolean random) {
		this.random = random;
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
	 * @return the randomPasswordLength
	 */
	public Long getRandomPasswordLength() {
		return this.randomPasswordLength;
	}

	/**
	 * @param randomPasswordLength
	 *            the randomPasswordLength to set
	 */
	public void setRandomPasswordLength(Long randomPasswordLength) {
		this.randomPasswordLength = randomPasswordLength;
	}

	/**
	 * @return the randomPasswordNumeric
	 */
	public Boolean getRandomPasswordNumeric() {
		return this.randomPasswordNumeric;
	}

	/**
	 * @param randomPasswordNumeric
	 *            the randomPasswordNumeric to set
	 */
	public void setRandomPasswordNumeric(Boolean randomPasswordNumeric) {
		this.randomPasswordNumeric = randomPasswordNumeric;
	}

	/**
	 * @return the randomPasswordAllLowercase
	 */
	public Boolean getRandomPasswordAllLowercase() {
		return this.randomPasswordAllLowercase;
	}

	/**
	 * @param randomPasswordAllLowercase
	 *            the randomPasswordAllLowercase to set
	 */
	public void setRandomPasswordAllLowercase(Boolean randomPasswordAllLowercase) {
		this.randomPasswordAllLowercase = randomPasswordAllLowercase;
	}

}
