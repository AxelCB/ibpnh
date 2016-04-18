package org.kairos.ibpnh.core.model.configuration.parameter;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.kairos.ibpnh.core.model.configuration.parameter.E_ParameterType;
import org.kairos.ibpnh.core.model.configuration.parameter.ParameterHistory;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-04-16T17:35:11")
@StaticMetamodel(Parameter.class)
public class Parameter_ { 

    public static volatile SingularAttribute<Parameter, Boolean> deleted;
    public static volatile SingularAttribute<Parameter, Boolean> viewed;
    public static volatile SingularAttribute<Parameter, String> name;
    public static volatile SingularAttribute<Parameter, String> description;
    public static volatile SingularAttribute<Parameter, Boolean> global;
    public static volatile SingularAttribute<Parameter, Boolean> fixed;
    public static volatile SingularAttribute<Parameter, Long> id;
    public static volatile ListAttribute<Parameter, ParameterHistory> history;
    public static volatile SingularAttribute<Parameter, E_ParameterType> type;
    public static volatile SingularAttribute<Parameter, String> value;
    public static volatile SingularAttribute<Parameter, String> tags;

}