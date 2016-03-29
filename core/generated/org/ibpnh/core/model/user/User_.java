package org.ibpnh.core.model.user;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ibpnh.core.model.user.Role;
import org.ibpnh.core.model.user.UserDetails;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-03-29T00:05:59")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, Role> role;
    public static volatile SingularAttribute<User, Integer> loginAttempts;
    public static volatile SingularAttribute<User, Boolean> deleted;
    public static volatile SingularAttribute<User, Boolean> firstLogin;
    public static volatile SingularAttribute<User, Long> hashCost;
    public static volatile SingularAttribute<User, String> disabledCause;
    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, UserDetails> userDetails;
    public static volatile SingularAttribute<User, Boolean> enabled;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, String> enablingHash;

}