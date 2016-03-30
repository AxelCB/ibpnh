package org.kairos.ibpnh.core.dao.configuration.parameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.SchemaUtils;
import org.kairos.ibpnh.core.dao.AbstractDao;
import org.kairos.ibpnh.core.dao.EntityManagerHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.model.E_HistoricOperationType;
import org.kairos.ibpnh.core.model.configuration.parameter.Parameter;
import org.kairos.ibpnh.core.model.configuration.parameter.ParameterHistory;
import org.kairos.ibpnh.core.model.configuration.parameter.Parameter_;
import org.kairos.ibpnh.core.services.caching.client.api.I_ParameterCacheManager;
import org.kairos.ibpnh.core.utils.I_DateUtils;
import org.kairos.ibpnh.core.utils.StringUtils;
import org.kairos.ibpnh.core.vo.configuration.parameter.ParameterVo;

/**
 * Dao for the Parameter's entities.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class ParameterDaoImpl extends AbstractDao<Parameter, ParameterVo>
		implements I_ParameterDao {

	/**
	 * Parameter Cache Manager.
	 */
	@Autowired
	private I_ParameterCacheManager parameterCacheManager;

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(ParameterDaoImpl.class);

	/**
	 * EntityManagerHolder. This will be used only in the init method.
	 * 
	 */
	@Autowired
	private EntityManagerHolder entityManagerHolder;
	
	/**
	 * Date Utils.
	 */
	@Autowired
	private I_DateUtils dateUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getClazz()
	 */
	@Override
	protected Class<Parameter> getClazz() {
		return Parameter.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getVoClazz()
	 */
	@Override
	public Class<ParameterVo> getVoClazz() {
		return ParameterVo.class;
	}

	/**
	 * Bean init method.
	 */
	public void init() {
		this.loadGlobalParameters(this.getEntityManagerHolder()
				.getEntityManager());
	}

	@Override
	public void addDefaultOrder(Root<Parameter> root, CriteriaBuilder builder,
			CriteriaQuery<Parameter> query) {
		query.orderBy(builder.asc(root.get(Parameter_.name)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_ParameterDao#
	 * loadGlobalParameters(javax.persistence.EntityManager)
	 */
	@Override
	public void loadGlobalParameters(EntityManager em) {
		this.logger.debug("loading global parameters");

		// builds the query
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Parameter> query = builder.createQuery(this.getClazz());
		Root<Parameter> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by global flag
		filters = builder.and(filters, builder.equal(root
				.get(Parameter_.global).as(Boolean.class), Boolean.TRUE));
		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(
				root.get(Parameter_.deleted).as(Boolean.class), Boolean.FALSE));

		query.where(filters);

		// fetch the parameters
		List<Parameter> parameters = em.createQuery(query).getResultList();

		List<ParameterVo> parametersVo = this.map(parameters);

		// removes all the current load parameters on the cache
		Set<String> currentLoadedParameters = this.getParameterCacheManager()
				.keySet();
		for (String name : currentLoadedParameters) {
			this.getParameterCacheManager().removeParameter(name);
		}

		// puts the parameters currently stated as global onto the cache
		for (ParameterVo parameterVo : parametersVo) {
			this.getParameterCacheManager().putParameter(parameterVo.getName(),
					parameterVo);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kairos.ibpnh.core.dao.parameter.I_ParameterDao#getByName(javax.persistence
	 * .EntityManager, java.lang.String)
	 */
	@Override
	public ParameterVo getByName(EntityManager em, String name) {
		this.logger.debug("getting parameter by name");

		ParameterVo cached = this.getCachedParameter(name);
		if (cached != null) {
			this.logger.debug("global parameter fetched: " + name);

			return cached;
		} else {
			if (em == null) {
				// we are not trying to get a parameter from the DB rather only
				// from the cache, return null
				this.logger.debug("returning null for parameter");

				return null;
			}

			// builds the query
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Parameter> query = builder.createQuery(this
					.getClazz());
			Root<Parameter> root = query.from(this.getClazz());

			Predicate filters = builder.conjunction();

			// filters by name
			filters = builder.and(
					filters,
					builder.like(
							builder.lower(root.get(Parameter_.name).as(
									String.class)), name.toLowerCase()));
			// filters by the deleted flag
			filters = builder.and(filters, builder.equal(
					root.get(Parameter_.deleted).as(Boolean.class),
					Boolean.FALSE));

			query.where(filters);

			try {
				// fetch the parameter
				Parameter parameter = em.createQuery(query).getSingleResult();

				return this.map(parameter);

			} catch (NoResultException e) {
				// there was no parameter with required name
				return null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kairos.ibpnh.core.dao.parameter.I_ParameterDao#getByName(javax.persistence
	 * .EntityManager, java.lang.String)
	 */
	@Override
	public List<ParameterVo> getsByName(EntityManager em, String name) {
		this.logger.debug("getting parameter by name");

		// builds the query
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Parameter> query = builder.createQuery(this.getClazz());
		Root<Parameter> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by name
		filters = builder.and(
				filters,
				builder.like(builder.lower(root.get(Parameter_.name).as(
						String.class)), "%" + name.toLowerCase() + "%"));
		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(
				root.get(Parameter_.deleted).as(Boolean.class), Boolean.FALSE));

		query.where(filters);

		try {
			// fetch the parameter
			List<Parameter> parameters = em.createQuery(query).getResultList();

			return this.map(parameters);
		} catch (NoResultException e) {
			// there was no parameter with required name
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_ParameterDao#getByName
	 * (javax.persistence.EntityManager, java.util.List)
	 */
	@Override
	public List<ParameterVo> getByName(EntityManager em,
			Collection<String> names) {
		this.logger.debug("getting parameter by name");

		// we select the names that are in the global parameters map
		Collection<String> globalNames = org.apache.commons.collections4.CollectionUtils
				.select(names,
						new org.apache.commons.collections4.Predicate<String>() {

							@Override
							public boolean evaluate(String name) {
								return ParameterDaoImpl.this
										.getCachedParameter(name) != null;
							}

						});
		// we create the result collection
		List<ParameterVo> parametersVo = new ArrayList<ParameterVo>();
		// add all of the selected parameters
		for (String name : globalNames) {
			parametersVo.add(this.getCachedParameter(name));
		}

		// get the collection of parameters without the global ones
		names.removeAll(globalNames);

		// if we need to get some parameters from then DB
		if (names.size() > 0) {

			// builds the query
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Parameter> query = builder.createQuery(this
					.getClazz());
			Root<Parameter> root = query.from(this.getClazz());

			Predicate filters = builder.conjunction();

			// filters by names
			filters = builder.and(filters,
					root.get(Parameter_.name).as(String.class).in(names));
			// filters by the deleted flag
			filters = builder.and(filters, builder.equal(
					root.get(Parameter_.deleted).as(Boolean.class),
					Boolean.FALSE));

			query.where(filters);

			// fetch the parameter
			List<Parameter> parameters = em.createQuery(query).getResultList();

			parametersVo.addAll(this.map(parameters));
		}

		return parametersVo;
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
	protected Predicate addFilters(Root<Parameter> root,
			CriteriaBuilder builder, Predicate filters, ParameterVo vo) {
		this.logger.debug("adding parameter filters");

		if (!StringUtils.isBlank(vo.getName())) {
			// filters by name
			filters = builder.and(
					filters,
					builder.like(
							builder.lower(root.get(Parameter_.name).as(
									String.class)),
							("%" + vo.getName() + "%").toLowerCase()));
		}

		if (!StringUtils.isBlank(vo.getTags())) {
			// filters by tags
			List<String> tags = Arrays.asList(vo.getTags().split(","));
			for (String tag : tags) {

				filters = builder.and(
						filters,
						builder.equal(
								builder.function(SchemaUtils.getSchema()
										+ ".includestag", Boolean.class,
										root.get(Parameter_.tags),
										builder.literal(tag)), Boolean.TRUE));
			}
		}

		if (vo.getType() != null) {
			// filters by type
			filters = builder.and(filters,
					builder.equal(root.get(Parameter_.type), vo.getType()));
		}

		if (!StringUtils.isBlank(vo.getValue())) {
			// filters by type
			filters = builder.and(
					filters,
					builder.like(
							builder.lower(root.get(Parameter_.value).as(
									String.class)),
							("%" + vo.getValue() + "%").toLowerCase()));
		}

		if (vo.getGlobal() != null) {
			filters = builder.and(filters,
					builder.equal(root.get(Parameter_.global), vo.getGlobal()));
		}

		if (vo.getViewed() != null) {
			filters = builder.and(filters,
					builder.equal(root.get(Parameter_.viewed), vo.getViewed()));
		}

		return filters;
	}

	/**
	 * Get's an cached parameter.
	 * 
	 * @param name
	 *            name of the parameter
	 * @return ParameterVo or null
	 */
	private ParameterVo getCachedParameter(String name) {
		return this.getParameterCacheManager().getParameter(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kairos.ibpnh.core.dao.parameter.I_ParameterDao#persistHistory(javax.
	 * persistence.EntityManager, org.kairos.ibpnh.core.vo.parameter.ParameterVo,
	 * java.lang.String, E_HistoricOperationType)
	 */
	@Override
	public void persistHistory(EntityManager em, ParameterVo parameterVo,
			String username, E_HistoricOperationType operationType) {
		this.logger.debug("persisting parameter history");

		ParameterHistory parameterHistory = new ParameterHistory();
		parameterHistory.setDeleted(Boolean.FALSE);
		parameterHistory.setParameter(this.getEntityById(em,
				parameterVo.getId()));
		parameterHistory.setTimestamp(Calendar.getInstance().getTime());
		parameterHistory.setUsername(username);
		parameterHistory.setValue(parameterVo.getValue());
		parameterHistory.setOperationType(operationType);

		em.persist(parameterHistory);
	}

	/**
	 * @return the entityManagerHolder
	 */
	public EntityManagerHolder getEntityManagerHolder() {
		return this.entityManagerHolder;
	}

	/**
	 * @param entityManagerHolder
	 *            the entityManagerHolder to set
	 */
	public void setEntityManagerHolder(EntityManagerHolder entityManagerHolder) {
		this.entityManagerHolder = entityManagerHolder;
	}

	/**
	 * @return the parameterCacheManager
	 */
	public I_ParameterCacheManager getParameterCacheManager() {
		return this.parameterCacheManager;
	}

	/**
	 * @param parameterCacheManager
	 *            the parameterCacheManager to set
	 */
	public void setParameterCacheManager(
			I_ParameterCacheManager parameterCacheManager) {
		this.parameterCacheManager = parameterCacheManager;
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
