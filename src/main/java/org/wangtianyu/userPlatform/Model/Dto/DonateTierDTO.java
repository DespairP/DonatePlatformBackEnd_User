package org.wangtianyu.userPlatform.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonateTierDTO {
    private String projectTierId;

    private BigDecimal totalProgress;

    private Integer donatedUserNumber;
}
