package org.ibpnh.core.model.user;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ibpnh.core.model.user.Function;
import org.ibpnh.core.model.user.RoleType;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-03-29T00:05:59")
@StaticMetamodel(RoleTypeFunction.class)
public class RoleTypeFunction_ { 

    public static volatile SingularAttribute<RoleTypeFunction, Boolean> deleted;
    public static volatile SingularAttribute<RoleTypeFunction, Function> function;
    public static volatile SingularAttribute<RoleTypeFunction, String> disabledCause;
    public static volatile SingularAttribute<RoleTypeFunction, Long> id;
    public static volatile SingularAttribute<RoleTypeFunction, RoleType> roleType;
    public static volatile SingularAttribute<RoleTypeFunction, Boolean> enabled;

}