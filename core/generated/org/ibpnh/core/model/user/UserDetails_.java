package org.ibpnh.core.model.user;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ibpnh.core.model.person.Person;
import org.ibpnh.core.model.user.User;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-03-29T00:05:59")
@StaticMetamodel(UserDetails.class)
public class UserDetails_ { 

    public static volatile SingularAttribute<UserDetails, Boolean> deleted;
    public static volatile SingularAttribute<UserDetails, Person> person;
    public static volatile SingularAttribute<UserDetails, Long> id;
    public static volatile SingularAttribute<UserDetails, User> user;

}