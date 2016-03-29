package org.ibpnh.core.dao.configuration.parameter;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.ibpnh.core.dao.I_Dao;
import org.ibpnh.core.model.E_HistoricOperationType;
import org.ibpnh.core.vo.configuration.parameter.ParameterVo;

/**
 * Interface for the Parameter DAO.
 * 
 * @author Axel Collard Bovy
 *
 */
public interface I_ParameterDao extends I_Dao<ParameterVo> {

	/**
	 * Finds a parameter by name
	 * 
	 * @param name name to find
	 * @param em entity manager
	 * 
	 * @return parameterVo or null
	 */
	public ParameterVo getByName(EntityManager em, String name);
	
	/**
	 * Finds the parameters that match with the name
	 * 
	 * @param name name to match
	 * @param em entity manager
	 * 
	 * @return list of parameterVo
	 */
	public List<ParameterVo> getsByName(EntityManager em, String name);
	
	/**
	 * Finds the parameters that match the names in the collection
	 * 
	 * @param names names to match
	 * @param em entity manager
	 * 
	 * @return list of parameterVo
	 */
	public List<ParameterVo> getByName(EntityManager em, Collection<String> names);
	
	/**
	 * Saves a history.
	 * 
	 * @param em entity manager
	 * @param parameterVo the vo that contains the data
	 * @param username username of the user that made the operation
	 * @param operationType type of the operation
	 */
	public void persistHistory(EntityManager em, ParameterVo parameterVo, String username,
		E_HistoricOperationType operationType);
	
	/**
	 * Loads the global parameters.
	 * Overrides existing ones.
	 * 
	 * @param em the entity manager
	 */
	public void loadGlobalParameters(EntityManager em);
	
}
