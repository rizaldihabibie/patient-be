package com.example.demo.entity;

import com.example.demo.enumerations.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "patients")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pid;

    private String firstName;
    private String lastName;

    @Column(name = "dob")
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String address;
    private String suburb;
    private String state;
    private String postcode;

}
