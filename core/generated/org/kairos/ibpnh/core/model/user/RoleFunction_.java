package org.kairos.ibpnh.core.model.user;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.kairos.ibpnh.core.model.user.Function;
import org.kairos.ibpnh.core.model.user.Role;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-03-29T21:10:13")
@StaticMetamodel(RoleFunction.class)
public class RoleFunction_ { 

    public static volatile SingularAttribute<RoleFunction, Boolean> deleted;
    public static volatile SingularAttribute<RoleFunction, Role> role;
    public static volatile SingularAttribute<RoleFunction, Function> function;
    public static volatile SingularAttribute<RoleFunction, String> disabledCause;
    public static volatile SingularAttribute<RoleFunction, Long> id;
    public static volatile SingularAttribute<RoleFunction, Boolean> enabled;

}