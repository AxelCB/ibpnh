package org.kairos.ibpnh.dao.configuration.parameter;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;
import com.googlecode.objectify.cmd.Query;
import org.kairos.ibpnh.dao.AbstractDao;
import org.kairos.ibpnh.model.configuration.parameter.E_ParameterType;
import org.kairos.ibpnh.model.configuration.parameter.Parameter;
import org.kairos.ibpnh.services.caching.client.api.I_ParameterCacheManager;
import org.kairos.ibpnh.services.caching.client.api.I_UserCacheManager;
import org.kairos.ibpnh.vo.configuration.parameter.ParameterVo;
import org.springframework.beans.factory.annotation.Autowired;

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
public class ParameterDaoObjectifyImpl extends AbstractDao<Parameter,ParameterVo> implements I_ParameterDao{

	/**
	 * User Cache Manager.
	 */
	@Autowired
	private I_ParameterCacheManager parameterCacheManager;

	@Override
	public Class<Parameter> getClazz() {
		return Parameter.class;
	}

	@Override
	public Class<ParameterVo> getVoClazz() {
		return ParameterVo.class;
	}

	@Override
	public ParameterVo getByName(String name) throws NonUniqueResultException,NoResultException {
		if(this.getParameterCacheManager().getParameter(name)!=null){
			return this.getParameterCacheManager().getParameter(name);
		}
		int amountOfParameters = ofy().load().type(this.getClazz()).filter("name=",name).filter("deleted=",Boolean.FALSE).count();
		if(amountOfParameters>1){
			//NON UNIQUE RESULT
			throw new NonUniqueResultException("There should only be one parameter for a given name");
		}else{
			if(amountOfParameters<1){
				//No result
				throw new NoResultException("No result found for the given parameter name");
			}else{
				return this.map(ofy().load().type(this.getClazz()).filter("name=", name).first().now());
			}
		}
	}

	@Override
	public List<ParameterVo> getsByName(String name) {
		return null;
	}

	@Override
	public List<ParameterVo> getByName(Collection<String> names) {
		return this.map(ofy().load().type(this.getClazz()).filter("name in", names).filter("deleted=", Boolean.FALSE).list());
	}

	@Override
	public void loadGlobalParameters() {
		List<Parameter> globalParameters = ofy().load().type(this.getClazz()).filter("global=", Boolean.TRUE).filter("deleted=",Boolean.FALSE).list();
		for (ParameterVo parameterVo : this.map(globalParameters)){
			this.getParameterCacheManager().putParameter(parameterVo.getName(),parameterVo);
		}
	}

	public void init(){
		List<Parameter> globalParameters = new ArrayList<Parameter>();
		final List<Parameter> parametersToBePersisted = new ArrayList<Parameter>();
		Parameter parameter = new Parameter(ParameterVo.DATE_FORMAT,"dd/MM/yyyy","Formato de Fecha", E_ParameterType.STRING,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(ParameterVo.DATETIME_FORMAT,"dd/MM/yyyy HH:mm:ss.SSS","Formato de Fecha hora", E_ParameterType.STRING,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(ParameterVo.DATETIME_FORMAT_WITHOUT_MILLISECONDS,"dd/MM/yyyy HH:mm:ss","Formato de Fecha hora sin milisegundos", E_ParameterType.STRING,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(ParameterVo.DATETIME_FORMAT_WITHOUT_SECONDS,"dd/MM/yyyy HH:mm","Formato de Fecha hora sin segundos", E_ParameterType.STRING,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(ParameterVo.DATETIME_FORMAT_WITHOUT_SECONDS_AND_YEAR,"dd/MM HH:mm","Formato de Fecha hora sin segundos ni anios", E_ParameterType.STRING,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(ParameterVo.HOUR_FORMAT,"HH:mm","Formato de Hora", E_ParameterType.STRING,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(ParameterVo.ITEMS_PER_PAGE,"10","Cantidad de Items por Pagina", E_ParameterType.LONG,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(ParameterVo.JSON_DATE_TIME_EXCHANGE_FORMAT,"dd/MM/yyyy HH:mm:ss.SSS","Formato de Fecha Hora para Intercambio por JSON", E_ParameterType.STRING,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(ParameterVo.NATIVE_SQL_DATE_FORMAT,"yyyy-MM-dd","Formato de Fecha Nativo de SQL", E_ParameterType.STRING,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(ParameterVo.NATIVE_SQL_DATE_TIME_FORMAT,"yyyy-MM-dd HH:mm:ss.SSS","Formato de Fecha Hora Nativo de SQL", E_ParameterType.STRING,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);
		parameter = new Parameter(ParameterVo.LOCALE_LANGUAGE_TAG,"es","Locale", E_ParameterType.STRING,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
		globalParameters.add(parameter);

		for (Parameter auxParameter : globalParameters){
			final String parameterName = auxParameter.getName();
			Parameter persistedParameter = ObjectifyService.run(new Work<Parameter>(){
				public Parameter run(){
					int amountOfParameters = ofy().load().type(Parameter.class).filter("name=",parameterName).filter("deleted=",Boolean.FALSE).count();
					if(amountOfParameters>1){
						//NON UNIQUE RESULT
						throw new NonUniqueResultException("There should only be one parameter for a given name");
					}else{
						if(amountOfParameters<1){
							//No result
							return null;
						}else{
							return ofy().load().type(Parameter.class).filter("name=",parameterName).first().now();
						}
					}
				}
			});
			if(persistedParameter!=null){
				persistedParameter.setDescription(auxParameter.getDescription());
				persistedParameter.setValue(auxParameter.getValue());
				persistedParameter.setType(auxParameter.getType());
				parametersToBePersisted.add(persistedParameter);
			}else{
				parametersToBePersisted.add(auxParameter);
			}
		}
		for (ParameterVo parameterVo : this.map(globalParameters)){
			this.getParameterCacheManager().putParameter(parameterVo.getName(),parameterVo);
		}
		globalParameters = ObjectifyService.run(new Work<List<Parameter>>(){
			public List<Parameter> run(){
				ofy().save().entities(parametersToBePersisted).now();
				List<Parameter> globalParameters = ofy().load().type(Parameter.class).filter("global=", Boolean.TRUE).filter("deleted=",Boolean.FALSE).list();
				return globalParameters;
			}
		});
		for (ParameterVo parameterVo : this.map(globalParameters)){
			this.getParameterCacheManager().putParameter(parameterVo.getName(),parameterVo);
		}
	}

	@Override
	public boolean checkNameUniqueness(String name, Long excludeId) throws NonUniqueResultException,NoResultException {
		ParameterVo searchedParameter = this.getByName(name);
		return (searchedParameter!=null && !searchedParameter.getId().equals(excludeId));
	}

	public I_ParameterCacheManager getParameterCacheManager() {
		return parameterCacheManager;
	}

	public void setParameterCacheManager(I_ParameterCacheManager parameterCacheManager) {
		this.parameterCacheManager = parameterCacheManager;
	}
}