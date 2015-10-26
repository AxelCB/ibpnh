package org.kairos.ibpnh.dao.configuration.parameter;

import org.kairos.ibpnh.dao.I_Dao;
import org.kairos.ibpnh.model.configuration.parameter.Parameter;
import org.kairos.ibpnh.vo.configuration.parameter.ParameterVo;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.Collection;
import java.util.List;

/**
 * Interface for the Parameter DAO.
 * 
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
public interface I_ParameterDao extends I_Dao<ParameterVo> {

	/**
	 * Inits the global parameters on server start up
	 */
	public void init();

	/**
	 * Finds a parameter by name
	 * 
	 * @param name name to find
	 *
	 * @return parameter or null
	 */
	public ParameterVo getByName( String name);
	
	/**
	 * Finds the parameters that match with the name
	 * 
	 * @param name name to match
	 * 
	 * @return list of parameterVo
	 */
	public List<ParameterVo> getsByName(String name) throws NonUniqueResultException,NoResultException;
	
	/**
	 * Finds the parameters that match the names in the collection
	 * 
	 * @param names names to match
	 * 
	 * @return list of parameterVo
	 */
	public List<ParameterVo> getByName(Collection<String> names);
	
	/**
	 * Saves a history.
	 *
	 * @param parameter the  that contains the data
	 * @param username username of the user that made the operation
	 * @param operationType type of the operation
	 */
//	public void persistHistory(Objectify ofy, Parameter parameterVo, String username,
//                               E_HistoricOperationType operationType);
	
	/**
	 * Loads the global parameters.
	 * Overrides existing ones.
	 */
	public void loadGlobalParameters();

	/**
	 * Returns wether a parameter name is unique (there's no parameter with that name) or not.
	 *
	 * @param name
	 * @param excludeId
	 * @return
	 */
	public boolean checkNameUniqueness(String name, Long excludeId) throws NonUniqueResultException,NoResultException;
}
