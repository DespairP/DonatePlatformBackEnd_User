package org.wangtianyu.userPlatform.Model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName platformuserwallet
 */
@TableName(value ="platformuserwallet")
@Data
@Accessors(chain = true)
public class PlatformUserWallet implements Serializable {
    /**
     * 
     */
    @TableId(value = "wallet_id")
    private String walletId;

    /**
     * 
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 
     */
    @TableField(value = "user_PID")
    private String userPid;

    /**
     * 
     */
    @TableField(value = "user_real_name")
    private String userRealName;

    /**
     * 
     */
    @TableField(value = "wallet_money")
    private BigDecimal walletMoney;

    /**
     * 
     */
    @TableField(value = "wallet_payment_method")
    private Object walletPaymentMethod;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}