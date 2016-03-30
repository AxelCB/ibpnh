package org.kairos.ibpnh.core.vo.devotional;

import org.kairos.ibpnh.core.vo.AbstractVo;

import java.io.Serializable;

/**
 * Value object for bible study
 *
 * @author Axel Collard Bovy
 *
 * Created on 28/03/2016.
 */
public class BibleStudyVo extends AbstractVo implements Serializable{

    /**
     * Bible Study's title
     */
    private String title;

    /**
     * Bible Study's body
     */
    private String body;

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
