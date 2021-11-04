package org.wangtianyu.userPlatform.Model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName platformuserinformation
 */
@TableName(value ="platformuserinformation")
@Data
@Accessors(chain = true)
public class PlatformUserInformation implements Serializable {
    /**
     * 
     */
    @TableId(value = "user_information_id",type=IdType.ASSIGN_UUID)
    private String userInformationId;

    /**
     * 
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 
     */
    @TableField(value = "user_sex")
    private String userSex;

    /**
     * 
     */
    @TableField(value = "user_description")
    private String userDescription;

    /**
     * 
     */
    @TableField(value = "user_icon_path_location")
    private String userIconPathLocation;

    /**
     * 
     */
    @TableField(value = "user_city")
    private String userCity;

    /**
     * 
     */
    @TableField(value = "user_primary_location_id")
    private String userPrimaryLocationId;


    @TableField(value = "user_telephone")
    private String userTelephone;
}