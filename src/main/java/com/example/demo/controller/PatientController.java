package com.example.demo.controller;

import com.example.demo.constants.StatusCode;
import com.example.demo.dto.PatientDto;
import com.example.demo.response.GlobalResponse;
import com.example.demo.response.PatientResponse;
import com.example.demo.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/patients")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<GlobalResponse> save(@Valid @RequestBody PatientDto patient) {
        patientService.save(patient);
        return new ResponseEntity<>(GlobalResponse.builder()
                .statusCode(StatusCode.SUCCESS.getCode())
                .statusCode(StatusCode.SUCCESS.getMessage()).build(),HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<GlobalResponse> update(@RequestBody PatientDto patient) {
        patientService.update(patient);
        return new ResponseEntity<>(GlobalResponse.builder()
                .statusCode(StatusCode.SUCCESS.getCode())
                .statusCode(StatusCode.SUCCESS.getMessage()).build(),HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<GlobalResponse> delete(@PathVariable(name = "id") Long id) {
        patientService.delete(id);
        return new ResponseEntity<>(GlobalResponse.builder()
                .statusCode(StatusCode.SUCCESS.getCode())
                .statusCode(StatusCode.SUCCESS.getMessage()).build(),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GlobalResponse> find(
            @RequestParam(name = "pid", defaultValue = "") String pid,
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        PatientResponse patientResponse = patientService.find(pid, name, pageable);
        return new ResponseEntity<>(GlobalResponse.builder()
                .statusCode(StatusCode.SUCCESS.getCode())
                .data(patientResponse)
                .statusCode(StatusCode.SUCCESS.getMessage()).build(),HttpStatus.OK);
    }

}
