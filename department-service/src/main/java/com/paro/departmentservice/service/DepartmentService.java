package com.paro.departmentservice.service;

import com.paro.departmentservice.client.PatientClient;
import com.paro.departmentservice.controller.DepartmentController;
import com.paro.departmentservice.model.Department;
import com.paro.departmentservice.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);
    private PatientClient patientClient;
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService (DepartmentRepository departmentRepository, PatientClient patientClient) {
        this.departmentRepository=departmentRepository;
        this.patientClient=patientClient;
    }

    public List<Department> getAll() {
        List<Department> departmentsFound=departmentRepository.findAll();
        LOGGER.info("Departments found");
        return departmentsFound;
    }


    public Department getById(Long departmentId){
        Department departmentFound=departmentRepository.findById(departmentId).orElse(null);
        LOGGER.info("Department found with id={}: ", departmentId);
        return departmentFound;
    }

    public Department add(Department department){
        Department departmentSaved=departmentRepository.save(department);
        LOGGER.info("Department added with id={}", department.getId());
        return departmentSaved;
    }

    public List<Department> getByHospitalId(Long hospitalId){
        List<Department> departmentsFound=departmentRepository.findByHospitalId(hospitalId);
        LOGGER.info("Departments found for the hospital with an id={}", hospitalId);
        return departmentsFound;
    }

    public List<Department> getByHospitalWithPatients(Long hospitalId){
        List<Department> departmentList=departmentRepository.findByHospitalId(hospitalId);
        departmentList.forEach(department -> department.setPatientList(patientClient.findByDepartment(department.getId())));
        LOGGER.info("Departments found for the hospital with an id={}", hospitalId);
        return departmentList;
    }

}
