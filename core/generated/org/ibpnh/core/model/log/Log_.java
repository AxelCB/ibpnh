package org.ibpnh.core.model.log;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-03-29T00:05:59")
@StaticMetamodel(Log.class)
public class Log_ { 

    public static volatile SingularAttribute<Log, Boolean> deleted;
    public static volatile SingularAttribute<Log, String> logDate;
    public static volatile SingularAttribute<Log, String> logLevel;
    public static volatile SingularAttribute<Log, String> logMessage;
    public static volatile SingularAttribute<Log, Date> creationTimestamp;
    public static volatile SingularAttribute<Log, String> logStackTrace;
    public static volatile SingularAttribute<Log, Long> id;
    public static volatile SingularAttribute<Log, String> logLocation;

}