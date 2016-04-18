package org.kairos.ibpnh.core.model.configuration.mail;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.kairos.ibpnh.core.model.configuration.mail.E_Repetition;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-04-16T17:35:11")
@StaticMetamodel(Mail.class)
public class Mail_ { 

    public static volatile SingularAttribute<Mail, Boolean> cron;
    public static volatile SingularAttribute<Mail, String> footer;
    public static volatile SingularAttribute<Mail, String> subject;
    public static volatile SingularAttribute<Mail, Boolean> bodyJavascript;
    public static volatile SingularAttribute<Mail, String> toList;
    public static volatile SingularAttribute<Mail, String> body;
    public static volatile SingularAttribute<Mail, E_Repetition> repetition;
    public static volatile SingularAttribute<Mail, Date> sendTimestamp;
    public static volatile SingularAttribute<Mail, Boolean> enabled;
    public static volatile SingularAttribute<Mail, String> mailFxClass;
    public static volatile SingularAttribute<Mail, Boolean> deleted;
    public static volatile SingularAttribute<Mail, String> ccoList;
    public static volatile SingularAttribute<Mail, String> sender;
    public static volatile SingularAttribute<Mail, String> name;
    public static volatile SingularAttribute<Mail, String> ccList;
    public static volatile SingularAttribute<Mail, Long> id;

}