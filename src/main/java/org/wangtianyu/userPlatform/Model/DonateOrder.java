package org.wangtianyu.userPlatform.Model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import org.wangtianyu.userPlatform.Handler.EnumOrderStatusHandler;

/**
 * 
 * @TableName donateorders
 */
@TableName(value ="donateorders")
@Data
public class DonateOrder implements Serializable {
    /**
     * 
     */
    @TableId(value = "order_id")
    private String orderId;

    /**
     * 
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 
     */
    @TableField(value = "project_id")
    private String projectId;

    /**
     * 
     */
    @TableField(value = "project_donate_tier_id")
    private String projectDonateTierId;

    /**
     * 
     */
    @TableField(value = "order_location")
    private String orderLocation;

    /**
     * 
     */
    @TableField(value = "order_postal_code")
    private String orderPostalCode;

    /**
     * 
     */
    @TableField(value = "order_deliver_name")
    private String orderDeliverName;

    /**
     * 
     */
    @TableField(value = "order_status",typeHandler = EnumOrderStatusHandler.class)
    private OrderStatus orderStatus;

    /**
     * 
     */
    @TableField(value = "order_is_disposed")
    private Boolean orderIsDisposed;

    /**
     * 
     */
    @TableField(value = "order_description")
    private String orderDescription;

    @TableField(exist = false)
    private BigDecimal donatePrice;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public enum OrderStatus{
        CANCEL,CREATED,POSTING,COMPLETED
    }
}