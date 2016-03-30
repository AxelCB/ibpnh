package org.kairos.ibpnh.core.dao.user;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kairos.ibpnh.core.dao.AbstractDao;
import org.kairos.ibpnh.core.model.user.RegisteredUser;
import org.kairos.ibpnh.core.model.user.RegisteredUser_;
import org.kairos.ibpnh.core.model.person.Person;
import org.kairos.ibpnh.core.model.person.Person_;
import org.kairos.ibpnh.core.model.user.User;
import org.kairos.ibpnh.core.model.user.User_;
import org.kairos.ibpnh.core.vo.user.RegisteredUserVo;
import org.kairos.ibpnh.core.vo.user.UserVo;

/**
 * Registered User DAO Implementation.
 * 
 * @author fgonzalez
 * 
 */
public class RegisteredUserDaoImpl extends
		AbstractDao<RegisteredUser, RegisteredUserVo> implements
		I_RegisteredUserDao {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory
			.getLogger(RegisteredUserDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getClazz()
	 */
	@Override
	protected Class<RegisteredUser> getClazz() {
		return RegisteredUser.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getVoClazz()
	 */
	@Override
	public Class<RegisteredUserVo> getVoClazz() {
		return RegisteredUserVo.class;
	}

	@Override
	protected Predicate addFilters(Root<RegisteredUser> root,
			CriteriaBuilder builder, Predicate filters, RegisteredUserVo vo) {

		if (vo.getCellphone() != null) {
			filters = builder.and(filters, builder.equal(
					root.get(RegisteredUser_.cellphone), vo.getCellphone()));
		}

		if (vo.getPerson() != null && vo.getPerson().getId() != null) {
			Join<RegisteredUser, Person> regUserPerson = root
					.join(RegisteredUser_.person);
			filters = builder.and(filters, builder.equal(
					regUserPerson.get(Person_.id), vo.getPerson().getId()));
		}

		if (vo.getUserId() != null) {
			Join<RegisteredUser, User> regUser = root
					.join(RegisteredUser_.user);
			filters = builder.and(filters,
					builder.equal(regUser.get(User_.id), vo.getUserId()));
		}

		if (StringUtils.isNotBlank(vo.getUsername())) {
			Join<RegisteredUser, User> regUser = root
					.join(RegisteredUser_.user);
			filters = builder.and(filters, builder.like(
					builder.lower(regUser.get(User_.username)),
					("%" + vo.getUsername() + "%").toLowerCase()));
		}

		return filters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kairos.ibpnh.core.dao.cellphone.I_RegisteredUserDao#findByUser(javax.
	 * persistence.EntityManager, UserVo)
	 */
	@Override
	public RegisteredUserVo findByUser(EntityManager em, UserVo userVo) {
		this.logger.debug("getting registerd user by user {}",
				userVo.getUsername());

		// builds the query
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<RegisteredUser> query = builder.createQuery(this
				.getClazz());
		Root<RegisteredUser> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(
				root.get(RegisteredUser_.deleted).as(Boolean.class),
				Boolean.FALSE));

		Join<RegisteredUser, User> registeredUserUser = root
				.join(RegisteredUser_.user);

		filters = builder
				.and(filters,
						builder.equal(registeredUserUser.get(User_.id),
								userVo.getId()));

		query.where(filters);

		try {
			// fetch the parameter
			RegisteredUser cellphone = em.createQuery(query).getSingleResult();

			return this.map(cellphone);

		} catch (NoResultException e) {
			// there was no cell phone with required number
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kairos.ibpnh.core.dao.cellphone.I_RegisteredUserDao#findByEmail(javax
	 * .persistence.EntityManager, java.lang.String)
	 */
	@Override
	public RegisteredUserVo findByEmail(EntityManager em, String email) {
		this.logger.debug("getting registerd user by email {}", email);

		// builds the query
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<RegisteredUser> query = builder.createQuery(this
				.getClazz());
		Root<RegisteredUser> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(
				root.get(RegisteredUser_.deleted).as(Boolean.class),
				Boolean.FALSE));

		// filters by email
		Join<RegisteredUser, Person> person = root.join(RegisteredUser_.person);
		filters = builder.and(filters,
				builder.equal(person.get(Person_.email), email));

		query.where(filters);

		try {
			// fetch the parameter
			RegisteredUser cellphone = em.createQuery(query).getSingleResult();

			return this.map(cellphone);

		} catch (NoResultException e) {
			// there was no cell phone with required number
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kairos.ibpnh.core.dao.cellphone.I_RegisteredUserDao#findByUsername(javax
	 * .persistence.EntityManager, java.lang.String)
	 */
	@Override
	public RegisteredUserVo findByUsername(EntityManager em, String username) {
		this.logger.debug("getting registerd user by username {}", username);

		// builds the query
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<RegisteredUser> query = builder.createQuery(this
				.getClazz());
		Root<RegisteredUser> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(
				root.get(RegisteredUser_.deleted).as(Boolean.class),
				Boolean.FALSE));

		Join<RegisteredUser, User> registeredUserUser = root
				.join(RegisteredUser_.user);

		filters = builder
				.and(filters, builder.equal(
						registeredUserUser.get(User_.username), username));

		query.where(filters);

		try {
			// fetch the registered user
			RegisteredUser registeredUser = em.createQuery(query)
					.getSingleResult();

			return this.map(registeredUser);

		} catch (NoResultException e) {
			// there was no registeredUser with required username
			return null;
		}
	}

}
