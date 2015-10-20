package org.kairos.ibpnh.dao.configuration.parameter;

import org.kairos.ibpnh.dao.AbstractDao;
import org.kairos.ibpnh.model.configuration.parameter.E_ParameterType;
import org.kairos.ibpnh.model.configuration.parameter.Parameter;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Dao for the Parameter entities.
 *
 * Created on 9/25/15 by
 *
 * @author AxelCollardBovy.
 */
public class ParameterDaoObjectifyImpl extends AbstractDao<Parameter> implements I_ParameterDao{

	@Override
	public Class<Parameter> getClazz() {
		return Parameter.class;
	}

	@Override
	public Parameter getByName(String name) throws NonUniqueResultException,NoResultException {
		int amountOfParameters = ofy().load().type(this.getClazz()).filter("name=",name).count();
		if(amountOfParameters>1){
			//NON UNIQUE RESULT
			throw new NonUniqueResultException("There should only be one parameter for a given name");
		}else{
			if(amountOfParameters<1){
				//No result
				throw new NoResultException("No result found for the given parameter name");
			}else{
				return ofy().load().type(this.getClazz()).filter("name=",name).first().now();
			}
		}
	}

	@Override
	public List<Parameter> getsByName(String name) {
		return null;
	}

	@Override
	public List<Parameter> getByName(Collection<String> names) {
		return null;
	}

	@Override
	public void loadGlobalParameters() {
		//TODO: LOAD INTO CACHE
		ofy().load().type(this.getClazz()).filter("global=", Boolean.TRUE).list();
	}

	public void init(){
		List<Parameter> globalParameters = new ArrayList<Parameter>();
		List<Parameter> parametersToBePersisted = new ArrayList<Parameter>();
		Parameter parameter = new Parameter(Parameter.DATE_FORMAT,"dd/MM/yyyy","Formato de Fecha", E_ParameterType.STRING,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(Parameter.DATETIME_FORMAT,"dd/MM/yyyy HH:mm:ss.SSS","Formato de Fecha hora", E_ParameterType.STRING,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(Parameter.DATETIME_FORMAT_WITHOUT_MILLISECONDS,"dd/MM/yyyy HH:mm:ss","Formato de Fecha hora sin milisegundos", E_ParameterType.STRING,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(Parameter.DATETIME_FORMAT_WITHOUT_SECONDS,"dd/MM/yyyy HH:mm","Formato de Fecha hora sin segundos", E_ParameterType.STRING,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(Parameter.DATETIME_FORMAT_WITHOUT_SECONDS_AND_YEAR,"dd/MM HH:mm","Formato de Fecha hora sin segundos ni años", E_ParameterType.STRING,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(Parameter.HOUR_FORMAT,"HH:mm","Formato de Hora", E_ParameterType.STRING,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(Parameter.ITEMS_PER_PAGE,"10","Cantidad de Items por Página", E_ParameterType.LONG,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(Parameter.JSON_DATE_TIME_EXCHANGE_FORMAT,"dd/MM/yyyy HH:mm:ss.SSS","Formato de Fecha Hora para Intercambio por JSON", E_ParameterType.LONG,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(Parameter.NATIVE_SQL_DATE_FORMAT,"yyyy-MM-dd","Formato de Fecha Nativo de SQL", E_ParameterType.LONG,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(Parameter.NATIVE_SQL_DATE_TIME_FORMAT,"yyyy-MM-dd HH:mm:ss.SSS","Formato de Fecha Hora Nativo de SQL", E_ParameterType.LONG,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);

		for (Parameter auxParameter : globalParameters){
			Parameter persistedParameter = this.getByName(auxParameter.getName());
			if(persistedParameter!=null){
				persistedParameter.setDescription(auxParameter.getDescription());
				persistedParameter.setValue(auxParameter.getValue());
				persistedParameter.setType(auxParameter.getType());
				parametersToBePersisted.add(persistedParameter);
			}else{
				parametersToBePersisted.add(auxParameter);
			}
		}
		ofy().save().entities(parametersToBePersisted).now();
		this.loadGlobalParameters();

	}

	@Override
	public boolean checkNameUniqueness(String name, Long excludeId) throws NonUniqueResultException,NoResultException {
		Parameter searchedParameter = this.getByName(name);
		return (searchedParameter!=null && !searchedParameter.getId().equals(excludeId));
	}
}