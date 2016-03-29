package org.ibpnh.core.dao.user;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ibpnh.core.dao.AbstractDao;
import org.ibpnh.core.model.user.PasswordRecover;
import org.ibpnh.core.model.user.PasswordRecover_;
import org.ibpnh.core.model.user.User;
import org.ibpnh.core.model.user.User_;
import org.ibpnh.core.vo.user.PasswordRecoverVo;

/**
 * Password Recover DAO Implementation.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class PasswordRecoverDaoImpl extends
		AbstractDao<PasswordRecover, PasswordRecoverVo> implements
		I_PasswordRecoverDao {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory
			.getLogger(PasswordRecoverDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_Dao#getVoClazz()
	 */
	@Override
	public Class<PasswordRecoverVo> getVoClazz() {
		return PasswordRecoverVo.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getClazz()
	 */
	@Override
	protected Class<PasswordRecover> getClazz() {
		return PasswordRecover.class;
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
	protected Predicate addFilters(Root<PasswordRecover> root,
			CriteriaBuilder builder, Predicate filters, PasswordRecoverVo vo) {

		if (vo.getUser() != null && vo.getUser().getId() != null) {
			Join<PasswordRecover, User> user = root.join(PasswordRecover_.user);
			filters = builder.and(filters,
					builder.equal(user.get(User_.id), vo.getUser().getId()));
		}

		return filters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_PasswordRecoverDao#getByHashes(javax.persistence
	 * .EntityManager, java.lang.String, java.lang.String)
	 */
	@Override
	public PasswordRecoverVo getByHashes(EntityManager em, String privateHash,
			String publicHash) {
		this.logger.debug("attempting get password recover object");

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<PasswordRecover> query = builder
				.createQuery(PasswordRecover.class);
		Root<PasswordRecover> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(
				root.get(PasswordRecover_.deleted).as(Boolean.class),
				Boolean.FALSE));

		// filters by the recovered flag
		filters = builder.and(filters, builder.equal(
				root.get(PasswordRecover_.recovered).as(Boolean.class),
				Boolean.FALSE));

		// filter byn the hashes
		filters = builder.and(filters, builder.equal(
				root.get(PasswordRecover_.privateHash).as(Boolean.class),
				privateHash));
		filters = builder.and(filters, builder.equal(
				root.get(PasswordRecover_.publicHash).as(Boolean.class),
				publicHash));

		query.where(filters);

		try {
			// fetch the password recover object
			PasswordRecover passwordRecover = em.createQuery(query)
					.getSingleResult();

			return this.map(passwordRecover);
		} catch (NoResultException e) {
			// there was no user with required name
			return null;
		}
	}

}