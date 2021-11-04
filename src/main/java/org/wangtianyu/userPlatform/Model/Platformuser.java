package org.wangtianyu.userPlatform.Model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.wangtianyu.userPlatform.Model.Dto.PlatformuserDTO;

import java.sql.Timestamp;

@Data
@TableName("platformuser")
public class Platformuser {
    @TableId(value = "user_id",type = IdType.ASSIGN_UUID)
    private String userId;

    @TableField("user_number")
    private Long userNumber;

    @JsonIgnore
    @TableField("user_account")
    private String userAccount;

    @JsonIgnore
    @TableField("user_password")
    private String userPassword;

    @JsonIgnore
    @TableField("user_reg_type")
    private String userRegType;

    @JsonIgnore
    @TableField("user_create_date")
    private java.sql.Timestamp userCreateDate;

    @JsonIgnore
    @TableField("user_last_login_time")
    private java.sql.Timestamp userLastLoginTime;

    @TableField("user_nick_name")
    private String userNickName;

    @JsonIgnore
    @TableField("user_is_disposed")
    private boolean userIsDisposed;

    public static Platformuser createUserByDto(PlatformuserDTO dto){
        Platformuser user = new Platformuser();
        user.userAccount = dto.getUsername();
        user.userPassword = dto.getPassword();
        user.userIsDisposed = false;
        user.userCreateDate = new Timestamp(System.currentTimeMillis());
        user.userLastLoginTime = user.userCreateDate;
        return user;
    }
}
