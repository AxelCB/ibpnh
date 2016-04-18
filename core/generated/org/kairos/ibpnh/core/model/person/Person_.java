package org.kairos.ibpnh.core.model.person;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.kairos.ibpnh.core.model.person.DocumentType;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-04-16T17:35:11")
@StaticMetamodel(Person.class)
public class Person_ { 

    public static volatile SingularAttribute<Person, String> cellphoneNumber;
    public static volatile SingularAttribute<Person, Boolean> deleted;
    public static volatile SingularAttribute<Person, String> phoneNumber;
    public static volatile SingularAttribute<Person, DocumentType> documentType;
    public static volatile SingularAttribute<Person, String> surname;
    public static volatile SingularAttribute<Person, String> documentNumber;
    public static volatile SingularAttribute<Person, String> name;
    public static volatile SingularAttribute<Person, Long> id;
    public static volatile SingularAttribute<Person, String> email;

}