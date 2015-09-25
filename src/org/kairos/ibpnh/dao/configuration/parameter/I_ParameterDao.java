package org.kairos.ibpnh.dao.configuration.parameter;

import org.kairos.ibpnh.dao.I_Dao;
import org.kairos.ibpnh.model.configuration.parameter.Parameter;

import java.util.Collection;
import java.util.List;

/**
 * Interface for the Parameter DAO.
 * 
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
public interface I_ParameterDao extends I_Dao<Parameter> {

	/**
	 * Finds a parameter by name
	 * 
	 * @param name name to find
	 *
	 * @return parameter or null
	 */
	public Parameter getByName( String name);
	
	/**
	 * Finds the parameters that match with the name
	 * 
	 * @param name name to match
	 * 
	 * @return list of parameterVo
	 */
	public List<Parameter> getsByName(String name);
	
	/**
	 * Finds the parameters that match the names in the collection
	 * 
	 * @param names names to match
	 * 
	 * @return list of parameterVo
	 */
	public List<Parameter> getByName(Collection<String> names);
	
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
	public boolean checkNameUniqueness(String name, Long excludeId);
}
