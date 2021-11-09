package org.wangtianyu.userPlatform.Model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName donateprojectupdate
 */
@TableName(value ="donateprojectupdate")
@Data
@Accessors(chain = true)
public class DonateProjectUpdate implements Serializable {

    @TableId("project_update_id")
    private String projectUpdateId;

    /***/
    @TableField(value = "project_id")
    private String projectId;

    /**
     * 
     */
    @TableField(value = "project_update_time")
    private Date projectUpdateTime;

    /**
     * 
     */
    @TableField(value = "project_update_user")
    private String projectUpdateUser;

    /**
     * 
     */
    @TableField(value = "project_update_description")
    private String projectUpdateDescription;

    /**
     * 
     */
    @TableField(value = "project_update_tier")
    private Integer projectUpdateTier;

    /**
     * 
     */
    @TableField(value = "project_update_is_disposed")
    private Boolean projectUpdateIsDisposed;


    @TableField(exist = false)
    private Boolean isProjectUpdateVisible;

    public void setProjectUpdateInvisible(){
        this.isProjectUpdateVisible = false;
        this.setProjectUpdateDescription("捐助即可查看内容");
    }
}