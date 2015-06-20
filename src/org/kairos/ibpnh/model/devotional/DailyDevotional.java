package org.kairos.ibpnh.model.devotional;

import org.kairos.ibpnh.model.I_Model;

import javax.jdo.annotations.*;
import java.util.Date;

/**
 * @author AxelCollardBovy ,created on 24/02/2015.
 */
@PersistenceCapable
public class DailyDevotional implements I_Model {

    /**
     * Entity id
     */
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;

    /**
     * Deteled flag
     */
    @Persistent
    private Boolean deleted;

    /**
     * Devotional title
     */
    @Persistent
    private String title;

    /**
     * Short Text description
     */
    @Persistent
    private String shortDescription;

    /**
     * Text description
     */
    @Persistent
    private String description;

    /**
     * Image Blob Key
     */
    @Persistent
    private String imageBlobKey;

    /**
     * Devotional's date
     */
    @Persistent
    @Unique
    private Date date;

    @Override
    public String getId() { return id; }

    @Override
    public void setId(String id) { this.id = id; }

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

    public String getImageBlobKey() {
        return imageBlobKey;
    }

    public void setImageBlobKey(String imageBlobKey) {
        this.imageBlobKey = imageBlobKey;
    }
}
