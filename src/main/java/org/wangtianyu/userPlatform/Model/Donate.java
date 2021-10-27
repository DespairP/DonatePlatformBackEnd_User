package org.wangtianyu.userPlatform.Model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName donate
 */
@TableName("donate")
@Data
public class Donate implements Serializable {
    /**
     * 
     */
    private String userId;

    /**
     * 
     */
    private String projectId;

    /**
     * 
     */
    private BigDecimal donatePrice;

    /**
     * 
     */
    private Boolean donateIsIgnored;

}