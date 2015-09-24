package org.kairos.ibpnh.dao;

import org.kairos.ibpnh.model.I_Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Typed Abstract DAO with common functionality.
 *
 * @author AxelCollardBovy ,created on 22/09/2015.
 *
 */
public abstract class AbstractDao<E extends I_Model> implements I_Dao<E> {

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(AbstractDao.class);
}
