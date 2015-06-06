package org.kairos.ibpnh.vo.devotional;

import org.kairos.ibpnh.model.I_Model;
import org.kairos.ibpnh.vo.AbstractVo;

import javax.jdo.annotations.*;
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
     * Image Url
     */
    private String imageUrl;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
