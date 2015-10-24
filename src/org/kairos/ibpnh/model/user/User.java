package org.kairos.ibpnh.model.user;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.kairos.ibpnh.model.I_Model;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import java.io.Serializable;

/**
 * @author AxelCollardBovy ,created on 24/02/2015.
 */
@Entity
@AutoProperty(policy = DefaultPojomaticPolicy.TO_STRING)
public class User implements I_Model,Serializable {

    private static final long serialVersionUID = -3245162189792344327L;

    @Id
    @Property(policy = PojomaticPolicy.EQUALS)
    private Long id;

    /**
     * Deteled flag
     */
    private Boolean deleted;

    /**
     * User's username.
     */
    @Index
    private String username;

    /**
     * User's hashed password.
     */
    private String password;

    /**
     * Total login Attempts
     */
    private Integer loginAttempts;

    /**
     * Enabled flag.
     */
    private Boolean enabled = Boolean.TRUE;

    /**
     * First login flag.
     */
    private Boolean firstLogin;

    /**
     * Disabled cause.
     */
    private String disabledCause;

    /**
     * Hash cost for the BCrypt algorithm.
     */
    public Long hashCost;

    /**
     * Enabling hash.
     */
    private String enablingHash;

    /**
     * User session's token.
     */
    private String token;

    /**
     * User's Role
     */
    @Index
    private E_RoleType roleType;

    @Override
    public Long getId() { return id; }

    @Override
    public void setId(Long id) { this.id = id; }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public E_RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(E_RoleType roleType) {
        this.roleType = roleType;
    }
}
