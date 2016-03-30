package org.kairos.ibpnh.core.vo.devotional;

import org.kairos.ibpnh.core.vo.AbstractVo;
import org.kairos.ibpnh.core.vo.user.UserVo;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import java.io.Serializable;
import java.util.Date;

/**
 * Value object for bible study
 *
 * @author Axel Collard Bovy
 *
 * Created on 28/03/2016.
 */
public class DailyDevotionalVo extends AbstractVo implements Serializable {

    private static final long serialVersionUID = 7725130853686061184L;

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
    private byte[] image;

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

    public UserVo getCreator() {
        return creator;
    }

    public void setCreator(UserVo creator) {
        this.creator = creator;
    }
}
