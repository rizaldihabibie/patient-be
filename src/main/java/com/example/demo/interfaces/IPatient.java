package com.example.demo.interfaces;

import com.example.demo.dto.PatientDto;
import com.example.demo.entity.Patient;
import com.example.demo.response.PatientResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPatient {

    void save(PatientDto patient);
    void update(PatientDto patient);
    void delete(Long id);
    PatientResponse find(String PID, String name, Pageable pageable);

}
