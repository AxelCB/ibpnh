package org.ibpnh.core.dao.user.roleType;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ibpnh.core.dao.AbstractDao;
import org.ibpnh.core.model.user.E_RoleType;
import org.ibpnh.core.model.user.RoleType;
import org.ibpnh.core.model.user.RoleType_;
import org.ibpnh.core.vo.user.RoleTypeVo;

/**
 * Implementation of the RoleType DAO Interface.
 * 
 * @author Axel Collard Bovy
 *
 */
public class RoleTypeDaoImpl extends AbstractDao<RoleType, RoleTypeVo> implements I_RoleTypeDao {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(RoleTypeDaoImpl.class);
	
	/*
	 * (non-Javadoc)
	 * @see AbstractDao#getClazz()
	 */
	@Override
	protected Class<RoleType> getClazz() {
		return RoleType.class;
	}

	/*
	 * (non-Javadoc)
	 * @see AbstractDao#getVoClazz()
	 */
	@Override
	public Class<RoleTypeVo> getVoClazz() {
		return RoleTypeVo.class;
	}

	/*
	 * (non-Javadoc)
	 * @see AbstractDao#addFilters(javax.persistence.criteria.Root, javax.persistence.criteria.CriteriaBuilder, javax.persistence.criteria.Predicate, AbstractVo)
	 */
	@Override
	protected Predicate addFilters(Root<RoleType> root,
			CriteriaBuilder builder, Predicate filters, RoleTypeVo vo) {
		this.logger.debug("adding role type filters");
		
		if (vo.getId() != null) {
			//filters by name
			filters = builder.and(filters,
				builder.equal(
					root.get(RoleType_.id).as(Long.class),
					vo.getId()));
		}
		
		if (StringUtils.isNotBlank(vo.getName())) {
			//filters by name
			filters = builder.and(filters,
				builder.like(
					builder.lower(root.get(RoleType_.name).as(String.class)),
					("%" + vo.getName() + "%").toLowerCase()));
		}
		
		if (StringUtils.isNotBlank(vo.getDescription())) {
			//filters by name
			filters = builder.and(filters,
				builder.like(
					builder.lower(root.get(RoleType_.description).as(String.class)),
					("%" + vo.getDescription() + "%").toLowerCase()));
		}
		
		return filters;
	}
	
	/*
	 * (non-Javadoc)
	 * @see I_RoleTypeDao#getByRoleTypeEnum(javax.persistence.EntityManager, E_RoleType)
	 */
	@Override
	public RoleTypeVo getByRoleTypeEnum(EntityManager em, E_RoleType roleTypeEnum) {
		this.logger.debug("searching roletype by roleTypeEnum: {}", roleTypeEnum);
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<RoleType> query = builder.createQuery(RoleType.class);
		Root<RoleType> root = query.from(this.getClazz());
		
		Predicate filters = builder.conjunction();
		
		//filters by name
		filters = builder.and(filters,
			builder.equal(
				root.get(RoleType_.roleTypeEnum),
				roleTypeEnum));
		//filters by the deleted flag
		filters = builder.and(filters,
			builder.equal(
				root.get(RoleType_.deleted).as(Boolean.class),
				Boolean.FALSE));
		
		query.where(filters);
		
		try {
			//fetch the user
			RoleType roleType = em.createQuery(query).getSingleResult();
			
			return this.map(roleType);
		} catch (NoResultException e) {
			//there was no role type with required name 
			return null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see I_RoleTypeDao#checkNameUniqueness(javax.persistence.EntityManager, java.lang.String, java.lang.Long)
	 */
	@Override
	public Boolean checkNameUniqueness(EntityManager em, String name,
			Long excludeId) {
		this.logger.debug("searching roletype by name: {}", name);
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<RoleType> query = builder.createQuery(RoleType.class);
		Root<RoleType> root = query.from(this.getClazz());
		
		Predicate filters = builder.conjunction();
		
		//filters by name
		filters = builder.and(filters,
			builder.like(
				builder.lower(root.get(RoleType_.name).as(String.class)),
				name.toLowerCase()));
		
		if (excludeId != null) {
			//filters for ID different than the excluded ID
			filters = builder.and(filters,
				builder.notEqual(
					root.get(RoleType_.id).as(Long.class),
					excludeId));
		}
			
		//filters by the deleted flag
		filters = builder.and(filters,
			builder.equal(
				root.get(RoleType_.deleted).as(Boolean.class),
				Boolean.FALSE));
		
		query.where(filters);
		
		//fetch the role types
		List<RoleType> roleTypes = em.createQuery(query).getResultList();
			
		return roleTypes.size() == 0;	
	}
	
	/*
	 * (non-Javadoc)
	 * @see AbstractDao#addDefaultOrder(javax.persistence.criteria.Root, javax.persistence.criteria.CriteriaBuilder, javax.persistence.criteria.CriteriaQuery)
	 */
	@Override
	public void addDefaultOrder(Root<RoleType> root,
			CriteriaBuilder builder, CriteriaQuery<RoleType> query) {
		query.orderBy(builder.asc(root.get(RoleType_.name)));
	}
}
