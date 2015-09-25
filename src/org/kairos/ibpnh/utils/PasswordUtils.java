package org.kairos.ibpnh.utils;

import org.kairos.ibpnh.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.model.configuration.parameter.Parameter;
import org.kairos.ibpnh.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Password utils.
 * 
 * This currently uses BCrypt to store hashed passwords.
 * 
 * The cost (log rounds) is retrieved from a parameter (that MUST exist on the
 * database).
 * 
 * BCrypt hash help to improve security on brute force attacks (in the case that
 * the server is penetrated and the passwords hashes retrieved from the
 * database).
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 * 
 */
public class PasswordUtils implements I_PasswordUtils {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(PasswordUtils.class);

	/**
	 * Parameter DAO.
	 */
	@Autowired
	private I_ParameterDao parameterDao;

	/**
	 * Format check of a potential password. Retrieves the regExp from the
	 * parameter to check the password received
	 * 
	 * @return
	 */
	@Override
	public Boolean formatCheck(String password) {
		try {
			Parameter passwordRegexpParameter = null;
//                    this.getParameterDao()
//					.getByName(pm, ParameterVo.PASSWORD_REGEXP);
			String[] regExpList = passwordRegexpParameter.getValue().split(",");

			for (String regexp : regExpList) {
				if (!password.matches(regexp)) {
					return Boolean.TRUE;
				}
			}
			return Boolean.FALSE;
		} catch (Exception e) {
			this.logger.debug("error getting {} parameter", e,
					Parameter.PASSWORD_REGEXP);

			return Boolean.FALSE;
		} finally {
//			this.getPmh().closePersistenceManager(pm);
		}
	}

	/**
	 * Checks password of a user.
	 * 
	 * @param password
	 *            password to check
	 * @param user
	 *            user
	 * 
	 * @return true iif
	 */
	@Override
	public Boolean checkPassword(String password, User user,
			Long currentCost) {
		Boolean result = Boolean.FALSE;
		Boolean update = Boolean.FALSE;

		if (user.getHashCost() == null) {
			// old password (hashed with SHA-512), marks it to update
			result = HashUtils.hashPasswordSHA512(password).equals(
					user.getPassword());
			update = Boolean.TRUE;
		} else {
			// the password check doesn't need the cost (is stored in the hash
			// itself)
			result = HashUtils.checkPassword(password, user.getPassword());
			// if the costs are different the password needs to be updated
			update = !user.getHashCost().equals(currentCost);
		}

		if (result && update) {
			// only updates if is needed AND the check was successful
			user.setHashCost(currentCost);
			user.setPassword(HashUtils.hashPassword(password, currentCost));
			// note: this updates occurs only in the VO (i.e., in memory)
			// the actual update on the database depends on the use given
			// to the VO after this update is made (e.g., in Fx_Login, the VO
			// is persisted after a successful login, thus, this changes are
			// made on the database as well)
		}

		return result;
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

}
