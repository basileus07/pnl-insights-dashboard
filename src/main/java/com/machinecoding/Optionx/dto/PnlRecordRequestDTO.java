package com.machinecoding.Optionx.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter

public class PnlRecordRequestDTO {
    private String traderId;
    private Date traderDate;
    private BigDecimal pnlAmount;
    private BigDecimal utilizedMargin;
    private BigDecimal allocatedMargin;
}
