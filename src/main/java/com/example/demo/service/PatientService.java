package com.example.demo.service;

import com.example.demo.dto.PatientDto;
import com.example.demo.entity.Patient;
import com.example.demo.interfaces.IPatient;
import com.example.demo.repository.PatientRepository;
import com.example.demo.response.PatientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PatientService implements IPatient {

    private final PatientRepository patientRepository;


    @Override
    public void save(PatientDto patientDto) {

        Patient patient = patientDto.toEntity();
        Optional<Patient> existing = patientRepository.findByPid(patient.getPid());
        if(existing.isPresent()) {
            throw new RuntimeException("Data already exist");
        } else {
            patientRepository.save(patient);
        }

    }

    @Override
    public void update(PatientDto patientDto) {

        Patient patient = patientDto.toEntity();
        Patient existing = patientRepository.findById(patient.getId()).orElseThrow( () -> new RuntimeException("Data not found"));
        existing.setPostcode(patient.getPostcode());
        existing.setState(patient.getState());
        existing.setLastName(patient.getLastName());
        existing.setDateOfBirth(patient.getDateOfBirth());
        existing.setFirstName(patient.getFirstName());
        existing.setAddress(patient.getAddress());
        existing.setSuburb(patient.getSuburb());
        existing.setGender(patient.getGender());
        existing.setPid(patient.getPid());

        patientRepository.save(existing);

    }

    @Override
    public void delete(Long id) {

        Patient existing = patientRepository.findById(id).orElseThrow( () -> new RuntimeException("Data not found"));
        patientRepository.delete(existing);

    }

    @Override
    public PatientResponse find(String PID,
                                String name,
                                Pageable pageable) {

        Page<Patient> result = patientRepository.findByPidOrFirstNameOrLastName(
                PID,
                name,
                pageable
        );

        return PatientResponse.builder()
                .data(result.getContent())
                .totalPage(result.getTotalPages())
                .page(result.getNumber())
                .build();
    }
}
