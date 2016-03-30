package org.kairos.ibpnh.core.model.alert;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.kairos.ibpnh.core.model.E_Priority;
import org.kairos.ibpnh.core.model.user.User;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-03-29T21:10:13")
@StaticMetamodel(Alert.class)
public class Alert_ { 

    public static volatile SingularAttribute<Alert, String> objectClassName;
    public static volatile SingularAttribute<Alert, Boolean> deleted;
    public static volatile SingularAttribute<Alert, String> description;
    public static volatile SingularAttribute<Alert, Long> id;
    public static volatile SingularAttribute<Alert, E_Priority> priority;
    public static volatile SingularAttribute<Alert, User> user;
    public static volatile SingularAttribute<Alert, Long> objectId;
    public static volatile SingularAttribute<Alert, Date> timestamp;

}