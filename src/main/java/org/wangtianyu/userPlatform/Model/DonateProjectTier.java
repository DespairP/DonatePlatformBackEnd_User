package org.wangtianyu.userPlatform.Model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName donateprojecttier
 */
@TableName("donateprojecttier")
@Data
public class DonateProjectTier implements Serializable {

    @TableField("project_id")
    private String projectId;

    @TableField("project_donate_tier_id")
    private String projectDonateTierId;

    @TableField("project_donate_tier_level")
    private Integer projectDonateTierLevel;

    @TableField("project_donate_tier_name")
    private String projectDonateTierName;

    @TableField("project_donate_tier_price")
    private BigDecimal projectDonateTierPrice;

    @TableField("project_donate_tier_description")
    private String projectDonateTierDescription;

    @TableField("project_donate_tier_icon")
    private String projectDonateTierIcon;

    @TableField("project_donate_tier_is_disposed")
    private Boolean projectDonateTierIsDisposed;


}