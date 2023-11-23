package com.example.demo.dto;

import com.example.demo.entity.Patient;
import com.example.demo.enumerations.Gender;
import com.example.demo.interfaces.GenderValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

    private Long id;

    @NotEmpty
    private String pid;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private Date dateOfBirth;

    @NotNull
    @GenderValue(anyOf = {Gender.FEMALE, Gender.MALE})
    private Gender gender;

    @NotEmpty
    private String address;
    @NotEmpty
    private String suburb;
    @NotEmpty
    private String state;
    @NotEmpty
    private String postcode;

    public Patient toEntity() {
        return Patient.builder()
                .id(id)
                .pid(pid)
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(dateOfBirth)
                .gender(gender)
                .address(address)
                .suburb(suburb)
                .state(state)
                .postcode(postcode)
                .build();
    }
}
