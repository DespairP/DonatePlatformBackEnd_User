package org.wangtianyu.userPlatform.Model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;
import org.wangtianyu.userPlatform.Model.Dto.DonateDTO;


@TableName("donateproject")
@Data
@Accessors(chain = true)
public class DonateProject implements Serializable {

    @TableId
    private String projectId;

    @TableField("user_id")
    private String userId;

    @TableField("project_name")
    private String projectName;

    @TableField("project_milestone")
    private BigDecimal projectMilestone;

    @TableField("project_icon_path")
    private String projectIconPath;

    @TableField("project_create_date")
    private Date projectCreateDate;

    @TableField("project_publish_public_date")
    private Date projectPublishPublicDate;

    @TableField("project_duration_days")
    private Integer projectDurationDays;

    @TableField("project_status")
    private EnumProjectStatus projectStatus;

    @TableField(exist=false)
    private Platformuser owner;

    @TableField(exist=false)
    private DonateDTO projectDonateStatics;

    public enum EnumProjectStatus{
        NOTAPPROVED,ONREVIEW,APPROVED,ONPROGRESS,COMPLETED,DELAYEDPROGRESS,CANCELLED,FAILED,OTHER;

        public int getMySqlIndex(){
            return this.ordinal() + 1;
        }
    }

}