package org.kairos.ibpnh.core.model.devotional;

import org.kairos.ibpnh.core.model.I_Model;
import org.kairos.ibpnh.core.model.user.User;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Axel Collard Bovy ,created on 24/02/2015.
 */
@Entity
@AutoProperty(policy = DefaultPojomaticPolicy.TO_STRING)
public class DailyDevotional implements I_Model,Serializable {

    private static final long serialVersionUID = 7725130853686061184L;

    /**
     * Entity id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dailydevotional_seq")
    @SequenceGenerator( name = "dailydevotional_seq", sequenceName = "dailydevotional_seq", allocationSize = 1)
    @Property(policy = PojomaticPolicy.EQUALS)
    private Long id;

    /**
     * Deteled flag
     */
    private Boolean deleted;

    /**
     * Devotional title
     */
    private String title;

    /**
     * Short Text description
     */
    private String shortDescription;

    /**
     * Text description
     */
    private String description;

    /**
     * Image Blob
     */
    @Lob
    private byte[] image;

    /**
     * User who creates this Daily Devotional
     */
    @Property(policy = PojomaticPolicy.NONE)
    private User creator;

    /**
     * Devotional's date
     */
    @Temporal(TemporalType.DATE)
    private Date date;

    @Override
    public Long getId() { return id; }

    @Override
    public void setId(Long id) { this.id = id; }

    @Override
    public Boolean getDeleted() { return deleted; }

    @Override
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
