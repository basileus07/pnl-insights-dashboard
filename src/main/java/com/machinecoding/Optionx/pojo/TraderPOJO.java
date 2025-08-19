package com.machinecoding.Optionx.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection =  "traders")
public class TraderPOJO {
    private String traderId;
    private String name;
    private Date date;
}

