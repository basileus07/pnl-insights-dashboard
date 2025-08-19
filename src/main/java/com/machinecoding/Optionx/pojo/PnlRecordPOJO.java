package com.machinecoding.Optionx.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter

@Document(collection = "pnl_records")
public class PnlRecordPOJO {
    @Id
    private String id;
    private Date traderDate;
    private BigDecimal pnlAmount;
    private BigDecimal allocatedMargin;
    private String traderId;
    private BigDecimal utilizedMargin;
}
