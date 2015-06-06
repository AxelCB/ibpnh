package org.kairos.ibpnh.dao.configuration.parameter;

import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.dao.I_Dao;
import org.kairos.ibpnh.vo.configuration.parameter.ParameterVo;

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
	 * Finds a parameter by name
	 * 
	 * @param name name to find
	 * @param pm persistence manager
	 * 
	 * @return parameterVo or null
	 */
	public ParameterVo getByName(JDOPersistenceManager pm, String name);
	
	/**
	 * Finds the parameters that match with the name
	 * 
	 * @param name name to match
	 * @param pm persistence manager
	 * 
	 * @return list of parameterVo
	 */
	public List<ParameterVo> getsByName(JDOPersistenceManager pm, String name);
	
	/**
	 * Finds the parameters that match the names in the collection
	 * 
	 * @param names names to match
	 * @param pm persistence manager
	 * 
	 * @return list of parameterVo
	 */
	public List<ParameterVo> getByName(JDOPersistenceManager pm, Collection<String> names);
	
	/**
	 * Saves a history.
	 * 
	 * @param pm persistence manager
	 * @param parameterVo the vo that contains the data
	 * @param username username of the user that made the operation
	 * @param operationType type of the operation
	 */
//	public void persistHistory(JDOPersistenceManager pm, ParameterVo parameterVo, String username,
//                               E_HistoricOperationType operationType);
	
	/**
	 * Loads the global parameters.
	 * Overrides existing ones.
	 * 
	 * @param pm persistence manager
	 */
	public void loadGlobalParameters(JDOPersistenceManager pm);

	/**
	 * Returns wether a parameter name is unique (there's no parameter with that name) or not.
	 *
	 * @param pm
	 * @param name
	 * @param id
	 * @return
	 */
	public boolean checkNameUniqueness(JDOPersistenceManager pm, String name, String id);
}
