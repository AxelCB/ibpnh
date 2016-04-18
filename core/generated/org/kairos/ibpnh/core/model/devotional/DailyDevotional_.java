package org.kairos.ibpnh.core.model.devotional;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.kairos.ibpnh.core.model.user.User;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-04-16T17:35:11")
@StaticMetamodel(DailyDevotional.class)
public class DailyDevotional_ { 

    public static volatile SingularAttribute<DailyDevotional, Date> date;
    public static volatile SingularAttribute<DailyDevotional, byte[]> image;
    public static volatile SingularAttribute<DailyDevotional, User> creator;
    public static volatile SingularAttribute<DailyDevotional, Boolean> deleted;
    public static volatile SingularAttribute<DailyDevotional, String> description;
    public static volatile SingularAttribute<DailyDevotional, Long> id;
    public static volatile SingularAttribute<DailyDevotional, String> shortDescription;
    public static volatile SingularAttribute<DailyDevotional, String> title;

}