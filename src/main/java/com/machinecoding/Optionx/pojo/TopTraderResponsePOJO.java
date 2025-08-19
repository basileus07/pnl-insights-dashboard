package com.machinecoding.Optionx.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TopTraderResponsePOJO {
    private String traderId;
    private BigDecimal averagePnlPercentage;
    private Integer totalTrades;
    private BigDecimal totalPnlAmount;
}
