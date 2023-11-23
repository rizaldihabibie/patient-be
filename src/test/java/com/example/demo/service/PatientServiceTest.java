package com.example.demo.service;

import com.example.demo.dto.PatientDto;
import com.example.demo.entity.Patient;
import com.example.demo.enumerations.Gender;
import com.example.demo.repository.PatientRepository;
import com.example.demo.response.PatientResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.SerializationUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    private PatientDto patient;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    public void setup() throws ParseException {
        MockitoAnnotations.openMocks(this);

        patient = new PatientDto();
        patient.setPid("PID23");
        patient.setAddress("this is address");
        patient.setGender(Gender.MALE);
        patient.setPostcode("12313453");
        patient.setFirstName("first name");
        patient.setLastName("last name");
        patient.setState("state");
        patient.setDateOfBirth(simpleDateFormat.parse("1993-11-23"));
    }

    @Test
    public void saveShouldReturnTrue() {

        when(patientRepository.findByPid(patient.getPid()))
                .thenReturn(Optional.empty());
        patientService.save(patient);
        verify(patientRepository, times(1)).save(any(Patient.class));

    }

    @Test
    public void saveShouldThrowDataAlreadyExist() {

        when(patientRepository.findByPid(patient.getPid()))
                .thenReturn(Optional.of(patient.toEntity()));
        Throwable throwable = assertThrows(RuntimeException.class, () -> patientService.save(patient));
        assertEquals("Data already exist", throwable.getMessage());
        verify(patientRepository, times(0)).save(any(Patient.class));

    }

    @Test
    public void updateShouldReturnTrue() {

        patient.setId(1L);
        Patient existing = SerializationUtils.clone(patient.toEntity());
        existing.setId(1L);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(existing));
        patientService.update(patient);
        verify(patientRepository, times(1)).save(existing);

    }

    @Test
    public void updateShouldThrowRuntimeException() {

        Patient existing = SerializationUtils.clone(patient.toEntity());
        existing.setId(1L);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(existing));
        Throwable throwable = assertThrows(RuntimeException.class, () -> patientService.update(patient));
        assertEquals("Data not found", throwable.getMessage());
        verify(patientRepository, times(0)).save(existing);

    }

    @Test
    public void deleteShouldThrowRuntimeException() {

        Patient existing = SerializationUtils.clone(patient.toEntity());
        existing.setId(1L);
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        Throwable throwable = assertThrows(RuntimeException.class, () -> patientService.delete(1L));
        assertEquals("Data not found", throwable.getMessage());
        verify(patientRepository, times(0)).delete(existing);

    }

    @Test
    public void deleteShouldReturnTrue() {

        Patient existing = SerializationUtils.clone(patient.toEntity());
        existing.setId(1L);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(existing));
        patientService.delete(1L);
        verify(patientRepository, times(1)).delete(existing);

    }

    @Test
    public void findShouldReturnRightData() throws ParseException {

        List<Patient> patients = new ArrayList<>();
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

        Page<Patient> expected = mock(Page.class);
        Pageable pageable = PageRequest.of(1, 10);
        when(expected.getTotalPages()).thenReturn(1);
        when(expected.getContent()).thenReturn(patients);
        when(expected.getNumber()).thenReturn(1);

        when(patientRepository.findByPidOrFirstNameOrLastName("PID1", "some name", pageable))
                .thenReturn(expected);

        PatientResponse actual = patientService.find("PID1", "some name", pageable);
        assertEquals(expected.getContent().size(), actual.getData().size());
        assertEquals(expected.getTotalPages(), actual.getTotalPage());
        assertEquals(expected.getNumber(), actual.getPage());

    }
}
