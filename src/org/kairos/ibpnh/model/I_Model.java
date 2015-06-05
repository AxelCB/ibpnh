package org.kairos.ibpnh.model;

/**
 * General Model Interface.
 *
 * @author AxelCollardBovy ,created on 24/02/2015.
 *
 */
public interface I_Model {

    /**
     * Entity ID getter.
     *
     * @return id
     */
    public String getId();

    /**
     * Entity ID setter.
     *
     * @param id to set
     */
    public void setId(String id);

    /**
     * Entity Deletion Flag getter.
     *
     * @return is deleted
     */
    public Boolean getDeleted();

    /**
     * Entity Deletion Flag setter.
     *
     * @param deleted flag to set
     */
    public void setDeleted(Boolean deleted);

}