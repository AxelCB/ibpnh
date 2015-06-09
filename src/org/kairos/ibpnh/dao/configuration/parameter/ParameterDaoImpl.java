package org.kairos.ibpnh.dao.configuration.parameter;

import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.dao.AbstractDao;
import org.kairos.ibpnh.dao.PersistenceManagerHolder;
import org.kairos.ibpnh.model.configuration.parameter.Parameter;
import org.kairos.ibpnh.services.caching.client.api.I_ParameterCacheManager;
import org.kairos.ibpnh.vo.configuration.parameter.ParameterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jdo.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Dao for the Parameter's entities.
 * 
 * @author AxelCollardBovy ,created on 08/03/2015.
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
	 * PersistenceManagerHolder. This will be used only in the init method.
	 * 
	 */
	@Autowired
	private PersistenceManagerHolder persistenceManagerHolder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universe.core.dao.AbstractDao#getClazz()
	 */
	@Override
	protected Class<Parameter> getClazz() {
		return Parameter.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universe.core.dao.AbstractDao#getVoClazz()
	 */
	@Override
	public Class<ParameterVo> getVoClazz() {
		return ParameterVo.class;
	}

	/**
	 * Bean init method.
	 */
	public void init() {
		this.loadGlobalParameters(this.getPersistenceManagerHolder()
				.getPersistenceManager());
	}

//	@Override
//	public void addDefaultOrder(Root<Parameter> root, CriteriaBuilder builder,
//			CriteriaQuery<Parameter> query) {
//		query.orderBy(builder.asc(root.get(Parameter_.name)));
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universe.core.dao.configuration.parameter.I_ParameterDao#
	 * loadGlobalParameters(javax.persistence.EntityManager)
	 */
	@Override
	public void loadGlobalParameters(JDOPersistenceManager pm) {
		this.logger.debug("loading global parameters");

		Query query = pm.newQuery(this.getClazz());
		query.setFilter("deleted == false && global == true");
		List<Parameter> parameters = (List<Parameter>) query.execute();

		List<ParameterVo> parametersVo = this.map(parameters);

		// removes all the current load parameters on the cache
//		Set<String> currentLoadedParameters = this.getParameterCacheManager()
//				.keySet();
//		for (String name : currentLoadedParameters) {
//			this.getParameterCacheManager().removeParameter(name);
//		}

		// puts the parameters currently stated as global onto the cache
		for (ParameterVo parameterVo : parametersVo) {
			this.getParameterCacheManager().putParameter(parameterVo.getName(),
					parameterVo);
		}

	}

	@Override
	public boolean checkNameUniqueness(JDOPersistenceManager pm, String name, String excludeId) {
		ParameterVo parameterVo = this.getByName(pm,name);
		if(parameterVo==null || parameterVo.getId().equals(excludeId)){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.dao.parameter.I_ParameterDao#getByName(javax.persistence
	 * .EntityManager, java.lang.String)
	 */
	@Override
	public ParameterVo getByName(JDOPersistenceManager pm, String name) {
		this.logger.debug("getting parameter by name");

		ParameterVo cached = this.getCachedParameter(name);
		if (cached != null) {
			this.logger.debug("global parameter fetched: " + name);

			return cached;
		} else {
			if (pm == null) {
				// we are not trying to get a parameter from the DB rather only
				// from the cache, return null
				this.logger.debug("returning null for parameter");

				return null;
			}

			Query query = pm.newQuery(this.getClazz());
			//TODO SHOULD COMPARE WITH LIKE OPERATOR BUT IS NOT POSSIBLE IN DATASTORE
			query.setFilter("deleted == false && name == nameParam");
			query.declareParameters("String nameParam");
			query.setUnique(Boolean.TRUE);
			try{
				Parameter parameter = (Parameter)query.execute(name);

				return this.map(parameter);
			}catch(Exception e){
				this.logger.debug("there was no parameter with required name");
				// there was no parameter with required name
				return null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.dao.parameter.I_ParameterDao#getByName(javax.persistence
	 * .EntityManager, java.lang.String)
	 */
	@Override
	public List<ParameterVo> getsByName(JDOPersistenceManager pm, String name) {
		this.logger.debug("getting parameter by name");

		// builds the query
/*

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
*/
		try {
			// fetch the parameter
//			List<Parameter> parameters = em.createQuery(query).getResultList();

//			return this.map(parameters);
		}catch (Exception e) {
            // there was no parameter with required name
            return null;
        }
//        catch (NoResultException e) {
//			// there was no parameter with required name
//			return null;
//		}
        return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.dao.configuration.parameter.I_ParameterDao#getByName
	 * (javax.persistence.EntityManager, java.util.List)
	 */
	@Override
	public List<ParameterVo> getByName(JDOPersistenceManager pm,
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
            /*
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
*/
			// fetch the parameter
//			List<Parameter> parameters = em.createQuery(query).getResultList();

//			parametersVo.addAll(this.map(parameters));
		}

		return parametersVo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.dao.AbstractDao#addFilters(javax.persistence.criteria
	 * .Root, javax.persistence.criteria.CriteriaBuilder,
	 * javax.persistence.criteria.Predicate, org.universe.core.vo.AbstractVo)
	 */
	/*@Override
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

		return filters;
	}*/

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
	 * @see org.universe.core.dao.parameter.I_ParameterDao#persistHistory(javax.
	 * persistence.EntityManager, org.universe.core.vo.parameter.ParameterVo,
	 * java.lang.String, org.universe.core.model.E_HistoricOperationType)
	 */
//	@Override
//	public void persistHistory(EntityManager em, ParameterVo parameterVo,
//			String username, E_HistoricOperationType operationType) {
//		this.logger.debug("persisting parameter history");
//
//		ParameterHistory parameterHistory = new ParameterHistory();
//		parameterHistory.setDeleted(Boolean.FALSE);
//		parameterHistory.setParameter(this.getEntityById(em,
//				parameterVo.getId()));
//		parameterHistory.setTimestamp(Calendar.getInstance().getTime());
//		parameterHistory.setUsername(username);
//		parameterHistory.setValue(parameterVo.getValue());
//		parameterHistory.setOperationType(operationType);
//
//		em.persist(parameterHistory);
//	}

	/**
	 * @return the persistenceManagerHolder
	 */
	public PersistenceManagerHolder getPersistenceManagerHolder() {
		return this.persistenceManagerHolder;
	}

	/**
	 * @param persistenceManagerHolder
	 *            the persistenceManagerHolder to set
	 */
	public void setPersistenceManagerHolder(PersistenceManagerHolder persistenceManagerHolder) {
		this.persistenceManagerHolder = persistenceManagerHolder;
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

}
