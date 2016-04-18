package org.kairos.ibpnh.core.model.user;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.kairos.ibpnh.core.model.user.User;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-04-16T17:35:11")
@StaticMetamodel(PasswordRecover.class)
public class PasswordRecover_ { 

    public static volatile SingularAttribute<PasswordRecover, Boolean> recovered;
    public static volatile SingularAttribute<PasswordRecover, Boolean> deleted;
    public static volatile SingularAttribute<PasswordRecover, String> publicHash;
    public static volatile SingularAttribute<PasswordRecover, Long> id;
    public static volatile SingularAttribute<PasswordRecover, User> user;
    public static volatile SingularAttribute<PasswordRecover, String> privateHash;

}