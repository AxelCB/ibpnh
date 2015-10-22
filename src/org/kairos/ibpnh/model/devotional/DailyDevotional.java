package org.kairos.ibpnh.model.devotional;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.kairos.ibpnh.model.I_Model;
import org.kairos.ibpnh.model.user.User;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import java.io.Serializable;
import java.util.Date;

/**
 * @author AxelCollardBovy ,created on 24/02/2015.
 */
@Entity
@AutoProperty(policy = DefaultPojomaticPolicy.TO_STRING)
public class DailyDevotional implements I_Model,Serializable {

    private static final long serialVersionUID = 7725130853686061184L;

    /**
     * Entity id
     */
    @Id
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
     * Image Blob Key
     */
    private String imageBlobKey;

    /**
     * Image Url
     */
    private String imageUrl;

    /**
     * User who creates this Daily Devotional
     */
    @Property(policy = PojomaticPolicy.NONE)
    private Ref<User> creator;

    /**
     * Devotional's date
     */
    @Index
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

    public String getImageBlobKey() {
        return imageBlobKey;
    }

    public void setImageBlobKey(String imageBlobKey) {
        this.imageBlobKey = imageBlobKey;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getCreator() {
        return creator.get();
    }

    public void setCreator(User creator) {
        this.creator = Ref.create(creator);
    }
}
