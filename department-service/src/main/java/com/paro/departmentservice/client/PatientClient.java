package com.paro.departmentservice.client;

import com.paro.departmentservice.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//@FeignClient(name = "patient-service")
//@FeignClient(name = "patient-service", fallback = PatientClientFallback.class)
@FeignClient(name = "patient-service", fallbackFactory = PatientClientFallbackFactory.class)
public interface PatientClient {
    @GetMapping("/service/department/{departmentId}")
    List<Patient> findByDepartment(@PathVariable("departmentId") Long departmentId);

}
