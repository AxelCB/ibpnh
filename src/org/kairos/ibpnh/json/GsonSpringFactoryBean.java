package org.kairos.ibpnh.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.dao.PersistenceManagerHolder;
import org.kairos.ibpnh.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.utils.I_DateUtils;
import org.kairos.ibpnh.vo.configuration.parameter.ParameterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Factory Bean for Gson Serializer/Deserializer
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
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
//    @Autowired
//    private I_BigDecimalUtils bigDecimalUtils;

    /**
     * Date Utils.
     */
    @Autowired
    private I_DateUtils dateUtils;

    /**
     * Entity Manager Holder.
     */
    @Autowired
    private PersistenceManagerHolder persistenceManagerHolder;

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
//            gsb.registerTypeAdapterFactory(new ReadableDateFormatTypeAdapterFactory(
//                    this.getDateUtils()));

            // type adapters registration
//            gsb.registerTypeAdapter(BigDecimal.class,
//                    new BigDecimalTypeAdapter(this.bigDecimalUtils));
//            gsb.registerTypeAdapter(BigDecimalWithoutTypeAdapting.class,
//                    new BigDecimalWithoutTypeAdaptingTypeAdapter());

            // exclude password from being exposed to the client
//            gsb.addSerializationExclusionStrategy(new CustomExclusionStrategy(
//                    UserVo.class, false, "password"));

            // point of sale circular reference avoidance
//            gsb.addSerializationExclusionStrategy(new CustomExclusionStrategy(
//                    MobileDashboardVo.class, false, "timestampAsDate"));

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
        Objectify ofy = null;
        try {
            // try to get date time format parameter
            pm = this.getPersistenceManagerHolder().getPersistenceManager();
            Parameter parameter = this.getParameterDao().getByName(pm,
                    ParameterVo.JSON_DATE_TIME_EXCHANGE_FORMAT);
            if (parameter == null) {
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
            this.getPersistenceManagerHolder().closePersistenceManager(pm);
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

    public PersistenceManagerHolder getPersistenceManagerHolder() {
        return persistenceManagerHolder;
    }

    public void setPersistenceManagerHolder(PersistenceManagerHolder persistenceManagerHolder) {
        this.persistenceManagerHolder = persistenceManagerHolder;
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