package org.wangtianyu.userPlatform.Model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName donateprojectpermissions
 */
@TableName(value ="donateprojectpermissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonateProjectPermission implements Serializable {
    /**
     * 
     */
    @TableField(value = "project_id")
    private String projectId;

    /**
     * 
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 
     */
    @TableField(value = "user_permission")
    private String userPermission;

}