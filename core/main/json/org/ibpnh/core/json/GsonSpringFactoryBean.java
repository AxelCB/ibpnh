package org.ibpnh.core.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ibpnh.core.dao.EntityManagerHolder;
import org.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.ibpnh.core.utils.BigDecimalWithoutTypeAdapting;
import org.ibpnh.core.utils.I_BigDecimalUtils;
import org.ibpnh.core.utils.I_DateUtils;
import org.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.ibpnh.core.vo.user.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

/**
 * Factory Bean for Gson Serializer/Deserializer
 * 
 * @author Axel Collard Bovy
 * 
 */
public class GsonSpringFactoryBean implements FactoryBean<Gson> {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory
			.getLogger(GsonSpringFactoryBean.class);

	/**
	 * Singleton Instance.
	 */
	private Gson singleton = null;

	/**
	 * Big Decimal Utils.
	 */
	@Autowired
	private I_BigDecimalUtils bigDecimalUtils;

	/**
	 * Date Utils.
	 */
	@Autowired
	private I_DateUtils dateUtils;

	/**
	 * Entity Manager Holder.
	 */
	@Autowired
	private EntityManagerHolder entityManagerHolder;

	/**
	 * Parameter DAO.
	 */
	@Autowired
	private I_ParameterDao parameterDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public Gson getObject() throws Exception {
		if (this.singleton == null) {
			// we create the GsonBuilder
			GsonBuilder gsb = new GsonBuilder();

			// sets the date format
			this.setDateTimeFormat(gsb);

			// set the type adapter factory for date management
			gsb.registerTypeAdapterFactory(new ReadableDateFormatTypeAdapterFactory(
					this.getDateUtils()));

			// type adapters registration
			gsb.registerTypeAdapter(BigDecimal.class,
					new BigDecimalTypeAdapter(this.bigDecimalUtils));
			gsb.registerTypeAdapter(BigDecimalWithoutTypeAdapting.class,
					new BigDecimalWithoutTypeAdaptingTypeAdapter());

			// exclude password from being exposed to the client
			gsb.addSerializationExclusionStrategy(new CustomExclusionStrategy(
					UserVo.class, false, "password"));

			// point of sale circular reference avoidance
//			gsb.addSerializationExclusionStrategy(new CustomExclusionStrategy(
//					AccountVo.class, false, "inMovements", "outMovements"));

			// serializes complex map keys
			gsb.enableComplexMapKeySerialization();

			// returns the created builder
			this.singleton = gsb.create();
		}

		// returns the singleton
		return this.singleton;
	}

	/**
	 * Sets the Date Time Format for the GSON Builder.
	 * 
	 * @param gsb
	 *            GSON builder
	 */
	private void setDateTimeFormat(GsonBuilder gsb) {
		EntityManager em = null;
		try {
			// try to get date time format parameter
			em = this.getEntityManagerHolder().getEntityManager();
			ParameterVo parameterVo = this.getParameterDao().getByName(em,
					ParameterVo.JSON_DATE_TIME_EXCHANGE_FORMAT);
			if (parameterVo == null) {
				this.logger
						.debug("parameter {} not found, defaulting to dd/MM/yyyy HH:mm:ss.SSS",
								ParameterVo.JSON_DATE_TIME_EXCHANGE_FORMAT);
				gsb.setDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
			} else {
				this.logger.debug("parameter {} found, with value {}",
						ParameterVo.JSON_DATE_TIME_EXCHANGE_FORMAT,
						parameterVo.getValue());
				gsb.setDateFormat(parameterVo.getValue());
			}
		} catch (Exception e) {
			this.logger.debug("error getting "
					+ ParameterVo.JSON_DATE_TIME_EXCHANGE_FORMAT
					+ " parameter, defaulting to dd/MM/yyyy HH:mm:ss.SSS", e);
			gsb.setDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		} finally {
			this.getEntityManagerHolder().closeEntityManager(em);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return Gson.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	@Override
	public boolean isSingleton() {
		return true;
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
	 * @return the bigDecimalUtils
	 */
	public I_BigDecimalUtils getBigDecimalUtils() {
		return this.bigDecimalUtils;
	}

	/**
	 * @param bigDecimalUtils
	 *            the bigDecimalUtils to set
	 */
	public void setBigDecimalUtils(I_BigDecimalUtils bigDecimalUtils) {
		this.bigDecimalUtils = bigDecimalUtils;
	}

	/**
	 * @return the dateUtils
	 */
	public I_DateUtils getDateUtils() {
		return this.dateUtils;
	}

	/**
	 * @param dateUtils
	 *            the dateUtils to set
	 */
	public void setDateUtils(I_DateUtils dateUtils) {
		this.dateUtils = dateUtils;
	}

}
