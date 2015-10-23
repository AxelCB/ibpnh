package org.kairos.ibpnh.vo.devotional;


import org.kairos.ibpnh.vo.AbstractVo;
import org.kairos.ibpnh.vo.user.UserVo;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import java.io.Serializable;
import java.util.Date;

/**
 * @author AxelCollardBovy ,created on 23/10/2015.
 */
@AutoProperty(policy = DefaultPojomaticPolicy.TO_STRING)
public class DailyDevotionalVo extends AbstractVo implements Serializable {

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
    private UserVo creator;

    /**
     * Devotional's date
     */
    private Date date;

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

    public UserVo getCreator() {
        return creator;
    }

    public void setCreator(UserVo creator) {
        this.creator = creator;
    }
}
