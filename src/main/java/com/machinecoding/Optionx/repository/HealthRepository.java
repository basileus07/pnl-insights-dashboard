package com.machinecoding.Optionx.repository;

import com.machinecoding.Optionx.pojo.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HealthRepository extends MongoRepository<Patient, String> {

}
