package org.ibpnh.core.model.user;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ibpnh.core.model.person.Person;
import org.ibpnh.core.model.user.User;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-03-29T00:05:59")
@StaticMetamodel(RegisteredUser.class)
public class RegisteredUser_ { 

    public static volatile SingularAttribute<RegisteredUser, Boolean> deleted;
    public static volatile SingularAttribute<RegisteredUser, Person> person;
    public static volatile SingularAttribute<RegisteredUser, String> cellphone;
    public static volatile SingularAttribute<RegisteredUser, Long> id;
    public static volatile SingularAttribute<RegisteredUser, User> user;

}