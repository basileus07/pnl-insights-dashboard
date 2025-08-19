package com.machinecoding.Optionx.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopTraderResponseDTO {
    private String traderId;
    private BigDecimal averagePnlPercentage;
    private Integer totalTrades;
    private BigDecimal totalPnlAmount;
}
