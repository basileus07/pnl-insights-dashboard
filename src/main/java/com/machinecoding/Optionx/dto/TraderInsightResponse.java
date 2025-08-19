package com.machinecoding.Optionx.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class TraderInsightResponse {
    private Date tradingdate;
    private BigDecimal avgPnlPercentage;
    private BigDecimal maxPnlPercentage;
    private BigDecimal minPnlPercentage;

}
