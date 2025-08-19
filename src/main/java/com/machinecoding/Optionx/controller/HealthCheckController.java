package com.machinecoding.Optionx.controller;

import com.machinecoding.Optionx.pojo.Patient;
import com.machinecoding.Optionx.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @Autowired
    private HealthService healthService;

    @GetMapping("/check")
    public ResponseEntity<String> health(){
        return  ResponseEntity.ok("Application is running ✅");

    }

    @GetMapping("/list")
    public List<Patient> getAll(){
       List<Patient>patients =  healthService.getAllpatient();
        return patients;
    }

    @PostMapping("/add-patient")
    public void addPatient(@RequestBody Patient patient){
        healthService.saveEntry(patient);
    }

}
