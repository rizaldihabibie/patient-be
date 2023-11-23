package com.example.demo.controller;

import com.example.demo.dto.PatientDto;
import com.example.demo.entity.Patient;
import com.example.demo.enumerations.Gender;
import com.example.demo.response.GlobalResponse;
import com.example.demo.response.PatientResponse;
import com.example.demo.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PatientControllerTest {

    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientService patientService;

    private PatientDto patient;
    private List<Patient> patients;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @BeforeEach
    public void setup() throws ParseException {

        MockitoAnnotations.openMocks(this);

        patient = new PatientDto();
        patients = new ArrayList<>();

        patient.setPid("PID1");
        patient.setAddress("this is address");
        patient.setGender(Gender.MALE);
        patient.setPostcode("12313453");
        patient.setFirstName("first name");
        patient.setLastName("last name");
        patient.setState("state");
        patient.setDateOfBirth(simpleDateFormat.parse("1997-01-23"));

        patients.add(patient.toEntity());
        Patient patient2 = new Patient();
        patient2.setAddress("this is address 2");
        patient2.setGender(Gender.FEMALE);
        patient2.setPostcode("345234");
        patient2.setFirstName("first name 2");
        patient2.setLastName("last name 2");
        patient2.setState("another state");
        patient2.setDateOfBirth(simpleDateFormat.parse("1995-10-11"));
        patients.add(patient2);

    }

    @Test
    void saveShouldReturnCreated() {

        doNothing().when(patientService).save(patient);
        ResponseEntity responseEntity = patientController.save(patient);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(patientService, times(1)).save(patient);

    }

    @Test
    void updateShouldReturnAccepted() {

        doNothing().when(patientService).update(patient);
        ResponseEntity responseEntity = patientController.update(patient);
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        verify(patientService, times(1)).update(patient);

    }


    @Test
    void deleteShouldReturnAccepted() {

        doNothing().when(patientService).delete(1L);
        ResponseEntity responseEntity = patientController.delete(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(patientService, times(1)).delete(1L);

    }

    @Test
    void findShouldReturnCorrectData() {

        PatientResponse patientResponse = PatientResponse.builder()
                .page(1)
                .data(patients)
                .totalPage(10)
                .build();
        when(patientService.find(anyString(), anyString(), any(Pageable.class)))
                .thenReturn(patientResponse);

        ResponseEntity<GlobalResponse> responseEntity = patientController.find(
                "PIDre",
                "name",
                1,
                10
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        GlobalResponse globalResponse = responseEntity.getBody();
        PatientResponse actual = (PatientResponse) globalResponse.getData();

        assertEquals(1, actual.getPage());
        assertEquals(10, actual.getTotalPage());
        assertEquals(patients.size(), actual.getData().size());

        verify(patientService, times(1)).find(anyString(), anyString(), any(Pageable.class));

    }
}