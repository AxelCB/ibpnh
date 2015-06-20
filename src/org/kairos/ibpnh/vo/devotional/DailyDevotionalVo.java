package org.kairos.ibpnh.vo.devotional;

import org.kairos.ibpnh.vo.AbstractVo;

import java.util.Date;

/**
 * @author AxelCollardBovy ,created on 24/02/2015.
 */
public class DailyDevotionalVo extends AbstractVo {

    /**
     * Devotional's title
     */
    private String title;

    /**
     * Text description
     */
    private String description;

    /**
     * Short Text description
     */
    private String shortDescription;

    /**
     * Image blobkey
     */
    private String imageBlobKey;

    /**
     * Image url ( for the view only)
     */
    private String imageUrl;

    /**
     * Devotional's date
     */
    private Date date;

    /**
     * Default Contstructor
     */
    public DailyDevotionalVo() {
        super();
    }

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
}
