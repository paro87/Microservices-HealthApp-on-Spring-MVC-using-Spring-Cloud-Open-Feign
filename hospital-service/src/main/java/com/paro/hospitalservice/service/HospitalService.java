package com.paro.hospitalservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.paro.hospitalservice.client.DepartmentClient;
import com.paro.hospitalservice.client.PatientClient;
import com.paro.hospitalservice.controller.HospitalController;
import com.paro.hospitalservice.model.Hospital;
import com.paro.hospitalservice.repository.HospitalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HospitalService {
    private static final Logger LOGGER= LoggerFactory.getLogger(HospitalService.class);

    private HospitalRepository hospitalRepository;
    private DepartmentClient departmentClient;
    private PatientClient patientClient;
    @Autowired
    public void HospitalService (HospitalRepository hospitalRepository, DepartmentClient departmentClient, PatientClient patientClient){
        this.hospitalRepository=hospitalRepository;
        this.departmentClient=departmentClient;
        this.patientClient=patientClient;
    }

    public List<Hospital> getAll(){
        LOGGER.info("Hospitals found");
        return hospitalRepository.findAll();
    }

    public Hospital getById( Long hospitalId){
        Hospital hospitalFound=hospitalRepository.findById(hospitalId).orElse(null);
        LOGGER.info("Hospital found with id={}", hospitalId);
        return hospitalFound;
    }

    public Hospital add( Hospital hospital){
        Hospital hospitalSaved = hospitalRepository.save(hospital);
        LOGGER.info("Hospital added with id={}", hospital.getId());
        return hospitalSaved;
    }

    public Hospital getHospitalWithDepartments(Long hospitalId){
        Hospital hospital=hospitalRepository.findById(hospitalId).orElse(null);
        hospital.setDepartmentList(departmentClient.findByHospital(hospital.getId()));
        LOGGER.info("Departments found with hospital id={}", hospitalId);
        return hospital;
    }

    public Hospital getHospitalWithDepartmentsAndPatients(Long hospitalId){
        Hospital hospital=hospitalRepository.findById(hospitalId).orElse(null);
        hospital.setDepartmentList(departmentClient.findByHospitalWithPatients(hospital.getId()));

        LOGGER.info("Departments and patients found with hospital id={}", hospitalId);
        return hospital;
    }

    public Hospital getHospitalWithPatients(Long hospitalId){
        Hospital hospital=hospitalRepository.findById(hospitalId).orElse(null);
        hospital.setPatientList(patientClient.findByHospital(hospitalId));
        LOGGER.info("Patients found with hospital id={}", hospitalId);
        return hospital;
    }

}
