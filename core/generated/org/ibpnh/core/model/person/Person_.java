package org.ibpnh.core.model.person;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ibpnh.core.model.person.DocumentType;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-03-29T00:05:59")
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