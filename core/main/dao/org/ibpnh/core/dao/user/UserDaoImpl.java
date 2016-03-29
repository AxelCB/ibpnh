package org.ibpnh.core.dao.user;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ibpnh.core.dao.AbstractDao;
import org.ibpnh.core.model.user.E_RoleType;
import org.ibpnh.core.model.user.Role;
import org.ibpnh.core.model.user.RoleType;
import org.ibpnh.core.model.user.RoleType_;
import org.ibpnh.core.model.user.Role_;
import org.ibpnh.core.model.user.User;
import org.ibpnh.core.model.user.User_;
import org.ibpnh.core.utils.DozerUtils;
import org.ibpnh.core.vo.user.RoleTypeVo;
import org.ibpnh.core.vo.user.UserVo;

/**
 * Dao for the User Entities.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class UserDaoImpl extends AbstractDao<User, UserVo> implements I_UserDao {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getClazz()
	 */
	@Override
	protected Class<User> getClazz() {
		return User.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getVoClazz()
	 */
	@Override
	public Class<UserVo> getVoClazz() {
		return UserVo.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AbstractDao#addFilters(javax.persistence.criteria
	 * .Root, javax.persistence.criteria.CriteriaBuilder,
	 * javax.persistence.criteria.Predicate, AbstractVo)
	 */
	@Override
	protected Predicate addFilters(Root<User> root, CriteriaBuilder builder,
			Predicate filters, UserVo vo) {

		if (StringUtils.isNotBlank(vo.getUsername())) {
			filters = builder.and(filters, builder.like(
					builder.lower(root.get(User_.username).as(String.class)),
					("%" + vo.getUsername() + "%").toLowerCase()));
		}
		if (vo.getEnabled() != null) {
			filters = builder.and(
					filters,
					builder.equal(root.get(User_.enabled).as(Boolean.class),
							vo.getEnabled()));
		}

		return filters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_UserDao#getByUsername(javax.persistence.
	 * EntityManager, java.lang.String)
	 */
	@Override
	public UserVo getByUsername(EntityManager em, String username) {
		this.logger.debug("attempting login for username: {}", username);

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by name
		filters = builder.and(filters, builder.equal(root.get(User_.username)
				.as(String.class), username));
		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(root.get(User_.deleted)
				.as(Boolean.class), Boolean.FALSE));

		query.where(filters);

		try {
			// fetch the user
			User user = em.createQuery(query).getSingleResult();

			return this.map(user);
		} catch (NoResultException e) {
			// there was no user with required name
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_UserDao#findUsersByRoleTypeName(javax.
	 * persistence.EntityManager, java.lang.String)
	 */
	@Override
	public List<UserVo> findUsersByRoleTypeName(EntityManager em,
			RoleTypeVo roleTypeVo) {
		this.logger.debug("searching users with role type: {}", roleTypeVo);

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(root.get(User_.deleted)
				.as(Boolean.class), Boolean.FALSE));

		// filters by the role type ID
		Join<User, Role> joinUserRole = root.join(User_.role);
		Join<Role, RoleType> joinRoleRoleType = joinUserRole
				.join(Role_.roleType);
		filters = builder.and(filters, builder.equal(
				joinRoleRoleType.get(RoleType_.id).as(Long.class),
				roleTypeVo.getId()));

		query.where(filters);

		List<User> users = em.createQuery(query).getResultList();
		// transforms them to the VO entities
		List<UserVo> usersVo = DozerUtils.map(this.getMapper(), users,
				UserVo.class, this.getMapId());

		return usersVo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_UserDao#checkUsernameUniqueness(javax.
	 * persistence.EntityManager, java.lang.String, java.lang.Long)
	 */
	@Override
	public Boolean checkUsernameUniqueness(EntityManager em, String username,
			Long excludeId) {
		this.logger.debug("searching User by username: {}, id !=: {}",
				username, excludeId);

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by name
		filters = builder.and(
				filters,
				builder.like(builder.lower(root.get(User_.username).as(
						String.class)), username.toLowerCase()));

		if (excludeId != null) {
			// filters for ID different than the excluded ID
			filters = builder.and(filters, builder.notEqual(root.get(User_.id)
					.as(Long.class), excludeId));
		}

		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(root.get(User_.deleted)
				.as(Boolean.class), Boolean.FALSE));

		query.where(filters);

		// fetch the role types
		List<User> users = em.createQuery(query).getResultList();

		return users.size() == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_UserDao#countByRoleType(javax.persistence
	 * .EntityManager, java.lang.Boolean,
	 * E_RoleType[])
	 */
	@Override
	public Integer countByRoleType(EntityManager em, Boolean countDisabled,
			E_RoleType... roleTypes) {
		this.logger.debug("counting by role type, disabled's? {}, types: {}",
				countDisabled, roleTypes);

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<User> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();
		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(root.get(User_.deleted)
				.as(Boolean.class), Boolean.FALSE));
		// includes disabled?
		if (!countDisabled) {
			filters = builder.and(filters,
					builder.equal(root.get(User_.enabled), Boolean.TRUE));
		}

		if (roleTypes != null && roleTypes.length > 0) {
			Join<User, Role> role = root.join(User_.role);
			Join<Role, RoleType> roleType = role.join(Role_.roleType);
			filters = builder.and(filters, roleType.get(RoleType_.roleTypeEnum)
					.in(Arrays.asList(roleTypes)));
		}

		query.where(filters);

		query.select(builder.count(root));

		try {
			Long total = em.createQuery(query).getSingleResult();

			return total.intValue();
		} catch (NoResultException nre) {
			this.logger.debug("there was no result, we return 0");

			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_UserDao#getUserByEnablingHash(javax.persistence
	 * .EntityManager, java.lang.String)
	 */
	@Override
	public UserVo getUserByEnablingHash(EntityManager em, String enablingHash) {
		this.logger.debug("searching user with hash {}", enablingHash);

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(root.get(User_.deleted)
				.as(Boolean.class), Boolean.FALSE));

		// filters by the role type ID
		filters = builder.and(filters,
				builder.equal(root.get(User_.enablingHash), enablingHash));

		query.where(filters);

		try {
			User user = em.createQuery(query).getSingleResult();

			return this.map(user);
		} catch (NoResultException nre) {
			// no result exception, does nothing, just return null
			return null;
		}
	}
}
