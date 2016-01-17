package org.kairos.ibpnh.vo.user;


import org.kairos.ibpnh.model.user.E_RoleType;
import org.kairos.ibpnh.vo.AbstractVo;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;

import java.io.Serializable;

/**
 * @author AxelCollardBovy ,created on 23/10/2015.
 */
@AutoProperty(policy = DefaultPojomaticPolicy.TO_STRING)
public class UserVo extends AbstractVo implements Serializable {

    /**
     * User's username.
     */
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
    private E_RoleType roleType;

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
