package org.kairos.ibpnh.core.model.configuration.parameter;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.kairos.ibpnh.core.model.E_HistoricOperationType;
import org.kairos.ibpnh.core.model.configuration.parameter.Parameter;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-04-16T17:35:11")
@StaticMetamodel(ParameterHistory.class)
public class ParameterHistory_ { 

    public static volatile SingularAttribute<ParameterHistory, Boolean> deleted;
    public static volatile SingularAttribute<ParameterHistory, Parameter> parameter;
    public static volatile SingularAttribute<ParameterHistory, E_HistoricOperationType> operationType;
    public static volatile SingularAttribute<ParameterHistory, Long> id;
    public static volatile SingularAttribute<ParameterHistory, String> value;
    public static volatile SingularAttribute<ParameterHistory, Date> timestamp;
    public static volatile SingularAttribute<ParameterHistory, String> username;

}