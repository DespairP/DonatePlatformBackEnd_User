package org.wangtianyu.userPlatform.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;
import org.wangtianyu.userPlatform.Handler.JsonArrayHandler;

/**
 * 
 * @TableName donateprojectInfomation
 */
@Data
@Accessors(chain = true)
@TableName(value = "donateprojectinformation",autoResultMap = true)
public class DonateProjectInformation implements Serializable {

    @TableId("project_information_id")
    private String projectInformationId;

    @TableField("project_id")
    private String projectId;

    @TableField("project_update_date")
    private Date projectUpdateDate;

    @TableField("project_description")
    private String projectDescription;

    @TableField(value = "project_icons_path",typeHandler = JacksonTypeHandler.class)
    private List<String> projectIconsPath;

    @TableField(value = "project_label",typeHandler = JacksonTypeHandler.class)
    private List<String> projectLabel;

}