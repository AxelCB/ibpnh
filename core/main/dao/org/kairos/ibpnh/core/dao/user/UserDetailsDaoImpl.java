package org.kairos.ibpnh.core.dao.user;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.kairos.ibpnh.core.dao.AbstractDao;
import org.kairos.ibpnh.core.model.user.User;
import org.kairos.ibpnh.core.model.user.UserDetails;
import org.kairos.ibpnh.core.model.user.UserDetails_;
import org.kairos.ibpnh.core.model.user.User_;
import org.kairos.ibpnh.core.vo.user.UserDetailsVo;
import org.kairos.ibpnh.core.vo.user.UserVo;

/**
 * Impmlementation of the user details DAO.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class UserDetailsDaoImpl extends AbstractDao<UserDetails, UserDetailsVo>
		implements I_UserDetailsDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_Dao#getVoClazz()
	 */
	@Override
	public Class<UserDetailsVo> getVoClazz() {
		return UserDetailsVo.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getClazz()
	 */
	@Override
	protected Class<UserDetails> getClazz() {
		return UserDetails.class;
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
	protected Predicate addFilters(Root<UserDetails> root,
			CriteriaBuilder builder, Predicate filters, UserDetailsVo vo) {
		// add filters
		return filters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_UserDetailsDao#findByUser(javax.persistence
	 * .EntityManager, UserVo)
	 */
	@Override
	public UserDetailsVo findByUser(EntityManager em, UserVo userVo) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<UserDetails> query = builder
				.createQuery(UserDetails.class);
		Root<UserDetails> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by the deleted flag
		filters = builder.and(filters,
				builder.equal(root.get(UserDetails_.deleted), Boolean.FALSE));

		// filters by the user
		Join<UserDetails, User> user = root.join(UserDetails_.user);
		filters = builder.and(filters,
				builder.equal(user.get(User_.id), userVo.getId()));

		query.where(filters);

		try {
			return this.map(em.createQuery(query).getSingleResult());
		} catch (NoResultException e) {
			// no result was found, return null
			return null;
		}
	}

}
