package org.kairos.ibpnh.core.model.user;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.kairos.ibpnh.core.model.user.E_RoleType;
import org.kairos.ibpnh.core.model.user.RoleType;
import org.kairos.ibpnh.core.model.user.RoleTypeFunction;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-03-29T21:10:13")
@StaticMetamodel(RoleType.class)
public class RoleType_ { 

    public static volatile SingularAttribute<RoleType, RoleType> passwordReseter;
    public static volatile SingularAttribute<RoleType, Boolean> deleted;
    public static volatile SingularAttribute<RoleType, E_RoleType> roleTypeEnum;
    public static volatile SingularAttribute<RoleType, String> name;
    public static volatile ListAttribute<RoleType, RoleTypeFunction> roleTypeFunctions;
    public static volatile SingularAttribute<RoleType, String> description;
    public static volatile SingularAttribute<RoleType, Long> id;

}