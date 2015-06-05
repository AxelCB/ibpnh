package org.kairos.ibpnh.model.user;

import org.kairos.ibpnh.model.I_Model;

import javax.jdo.annotations.*;

/**
 * @author AxelCollardBovy ,created on 24/02/2015.
 */
@PersistenceCapable
public class User implements I_Model {

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
     * User's username.
     */
    @Persistent
    @Unique
    private String username;

    /**
     * User's hashed password.
     */
    @Persistent
    private String password;

    /**
     * Total login Attempts
     */
    @Persistent
    private Integer loginAttempts;

    /**
     * Enabled flag.
     */
    @Persistent
    private Boolean enabled;

    /**
     * First login flag.
     */
    @Persistent
    private Boolean firstLogin;

    /**
     * Disabled cause.
     */
    @Persistent
    private String disabledCause;

    /**
     * Hash cost for the BCrypt algorithm.
     */
    @Persistent
    public Long hashCost;

    /**
     * Enabling hash.
     */
    @Persistent
    private String enablingHash;

    /**
     * User's Role
     */
    @Persistent
    private Role role;

    @Override
    public String getId() { return id; }

    @Override
    public void setId(String id) { this.id = id; }

    @Override
    public Boolean getDeleted() { return deleted; }

    @Override
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(Integer loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getDisabledCause() {
        return disabledCause;
    }

    public void setDisabledCause(String disabledCause) {
        this.disabledCause = disabledCause;
    }

    public Long getHashCost() {
        return hashCost;
    }

    public void setHashCost(Long hashCost) {
        this.hashCost = hashCost;
    }

    public String getEnablingHash() {
        return enablingHash;
    }

    public void setEnablingHash(String enablingHash) {
        this.enablingHash = enablingHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
