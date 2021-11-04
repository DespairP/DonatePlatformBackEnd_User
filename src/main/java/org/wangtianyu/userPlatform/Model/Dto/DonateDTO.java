package org.wangtianyu.userPlatform.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DonateDTO {
    private String projectId;

    private BigDecimal totalProgress;

    private Integer donatedUserNumber;
}
