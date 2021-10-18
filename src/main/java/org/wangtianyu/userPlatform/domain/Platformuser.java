package org.wangtianyu.userPlatform.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName platformuser
 */
@TableName(value ="platformuser")
public class Platformuser implements Serializable {
    /**
     * 
     */
    @TableId
    private String userId;

    /**
     * 
     */
    private Long userNumber;

    /**
     * 
     */
    private String userAccount;

    /**
     * 
     */
    private String userPassword;

    /**
     * 
     */
    private Object userRegType;

    /**
     * 
     */
    private Date userCreateDate;

    /**
     * 
     */
    private Date userLastLoginTime;

    /**
     * 
     */
    private String userNickName;

    /**
     * 
     */
    private Boolean userIsDisposed;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     */
    public Long getUserNumber() {
        return userNumber;
    }

    /**
     * 
     */
    public void setUserNumber(Long userNumber) {
        this.userNumber = userNumber;
    }

    /**
     * 
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * 
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * 
     */
    public Object getUserRegType() {
        return userRegType;
    }

    /**
     * 
     */
    public void setUserRegType(Object userRegType) {
        this.userRegType = userRegType;
    }

    /**
     * 
     */
    public Date getUserCreateDate() {
        return userCreateDate;
    }

    /**
     * 
     */
    public void setUserCreateDate(Date userCreateDate) {
        this.userCreateDate = userCreateDate;
    }

    /**
     * 
     */
    public Date getUserLastLoginTime() {
        return userLastLoginTime;
    }

    /**
     * 
     */
    public void setUserLastLoginTime(Date userLastLoginTime) {
        this.userLastLoginTime = userLastLoginTime;
    }

    /**
     * 
     */
    public String getUserNickName() {
        return userNickName;
    }

    /**
     * 
     */
    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    /**
     * 
     */
    public Boolean getUserIsDisposed() {
        return userIsDisposed;
    }

    /**
     * 
     */
    public void setUserIsDisposed(Boolean userIsDisposed) {
        this.userIsDisposed = userIsDisposed;
    }
}