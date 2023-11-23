package com.example.demo.response;

import com.example.demo.entity.Patient;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PatientResponse {

    private int page;
    private List<Patient> data;
    private int totalPage;

}
