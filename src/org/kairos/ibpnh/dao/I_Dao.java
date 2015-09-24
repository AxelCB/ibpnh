package org.kairos.ibpnh.dao;

import com.googlecode.objectify.Objectify;
import org.kairos.ibpnh.model.I_Model;

/**
 * General Interfaces for all Daos.
 *
 * @author AxelCollardBovy ,created on 22/09/2015.
 *
 *  * @param <E>
 *            the  class that this DAO returns
 */
public interface I_Dao<E extends I_Model> {

	public E persist(Objectify ofy,E entity);

}
