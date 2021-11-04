package org.wangtianyu.userPlatform.Model.CaculateModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**为统计专门使用的Model*/
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class CalculateTierModel {
    /**捐助名称*/
    private String tierName;

    /**捐助人数*/
    private Integer donateCount;

    /**捐助的金额总数*/
    private BigDecimal donateSum;
}
