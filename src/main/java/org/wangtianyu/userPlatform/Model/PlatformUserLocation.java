package org.wangtianyu.userPlatform.Model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName platformuserlocation
 */
@TableName(value ="platformuserlocation")
@Data
public class PlatformUserLocation implements Serializable {
    /**
     * 
     */
    @TableField(value = "location_id")
    private String locationId;

    /**
     * 
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 
     */
    @TableField(value = "location_area_or_country")
    private String locationAreaOrCountry;

    /**
     * 
     */
    @TableField(value = "location_city")
    private String locationCity;

    /**
     * 
     */
    @TableField(value = "location_detail")
    private String locationDetail;

    /**
     * 
     */
    @TableField(value = "location_postal_code")
    private String locationPostalCode;

    /**
     * 
     */
    @TableField(value = "localtion_geometry")
    private Object localtionGeometry;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}