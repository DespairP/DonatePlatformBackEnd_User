package org.wangtianyu.userPlatform.Model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("platformuser")
public class Platformuser {
    @TableId("user_id")
    private String userId;

    @TableField("user_number")
    private Long userNumber;

    @TableField("user_account")
    private String userAccount;

    @JsonIgnore
    @TableField("user_password")
    private String userPassword;

    @TableField("user_reg_type")
    private String userRegType;

    @TableField("user_create_date")
    private java.sql.Timestamp userCreateDate;

    @JsonIgnore
    @TableField("user_last_login_time")
    private java.sql.Timestamp userLastLoginTime;

    @TableField("user_nick_name")
    private String userNickName;

    @TableField("user_is_disposed")
    private boolean userIsDisposed;

    public static Platformuser createUserByDto(PlatformuserDTO dto){
        Platformuser user = new Platformuser();
        user.userAccount = dto.getUserAccount();
        user.userCreateDate = new Timestamp(System.currentTimeMillis());
        user.userLastLoginTime = user.userCreateDate;
        return user;
    }
}
