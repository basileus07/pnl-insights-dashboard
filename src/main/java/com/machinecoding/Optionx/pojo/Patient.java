package com.machinecoding.Optionx.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "patient_details")
public class Patient {

    @Id
    private  String id;
    private String name;
    private Integer age;
    private String gender;
    private Date date;
}
