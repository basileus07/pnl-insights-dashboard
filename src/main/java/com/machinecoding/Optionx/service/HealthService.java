package com.machinecoding.Optionx.service;

import com.machinecoding.Optionx.repository.HealthRepository;
import com.machinecoding.Optionx.repository.Imp.HealthRepoCustomImp;
import com.machinecoding.Optionx.pojo.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HealthService {
    @Autowired
    private HealthRepository healthRepository;

    @Autowired
    private HealthRepoCustomImp healthRepoCustomImp;
    public void saveEntry(Patient patient){
        patient.setDate(new Date());
        healthRepository.save(patient);
//        healthRepoCustomImp.savePatient(patient);
    }

    public List<Patient> getAllpatient(){
        return healthRepository.findAll();
    }
}
