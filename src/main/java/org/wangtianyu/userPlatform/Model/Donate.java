package org.wangtianyu.userPlatform.Model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName donate
 */
@TableName("donate")
@Data
@Accessors(chain = true)
public class Donate implements Serializable {

    private String userId;

    private String donateTierId;

    private String projectId;


    private BigDecimal donatePrice;

    @JsonIgnore
    private Boolean donateIsIgnored;

}