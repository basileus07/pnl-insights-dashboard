package com.machinecoding.Optionx.repository.Imp;

import com.machinecoding.Optionx.repository.HealthRepoCustom;
import com.machinecoding.Optionx.pojo.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HealthRepoCustomImp implements HealthRepoCustom {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void savePatient(Patient patient) {
        mongoTemplate.insert(patient);
    }

}
