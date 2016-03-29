package org.ibpnh.core.model.user;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ibpnh.core.model.user.RoleFunction;
import org.ibpnh.core.model.user.RoleType;
import org.ibpnh.core.model.user.User;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-03-29T00:05:59")
@StaticMetamodel(Role.class)
public class Role_ { 

    public static volatile ListAttribute<Role, RoleFunction> roleFunctions;
    public static volatile SingularAttribute<Role, Boolean> deleted;
    public static volatile SingularAttribute<Role, Long> id;
    public static volatile SingularAttribute<Role, RoleType> roleType;
    public static volatile SingularAttribute<Role, User> user;

}