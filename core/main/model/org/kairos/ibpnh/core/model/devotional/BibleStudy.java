package org.kairos.ibpnh.core.model.devotional;

import org.kairos.ibpnh.core.model.I_Model;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by acollardbovy on 06/11/2015.
 */
@Entity
@AutoProperty(policy = DefaultPojomaticPolicy.TO_STRING)
public class BibleStudy implements Serializable,I_Model{

    /**
     * Entity id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "biblestudy_seq")
    @SequenceGenerator( name = "biblestudy_seq", sequenceName = "biblestudy_seq", allocationSize = 1)
    @Property(policy = PojomaticPolicy.EQUALS)
    private Long id;

    /**
     * Logic deletion flag
     */
    private Boolean deleted;

    /**
     * Bible Study's title
     */
    private String title;

    /**
     * Bible Study's body
     */
    private String body;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
