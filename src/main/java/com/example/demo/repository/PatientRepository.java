package com.example.demo.repository;

import com.example.demo.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    @Query(value = "SELECT * FROM patients p WHERE p.pid = :pid " +
            "UNION " +
            "SELECT * FROM patients p WHERE p.first_name like %:name% " +
            "UNION " +
            "SELECT * FROM patients p WHERE p.last_name like %:name% ",
            countQuery = "SELECT count(*) FROM patients",
            nativeQuery = true)
    Page<Patient> findByPidOrFirstNameOrLastName(
            @Param("pid") String PID,
            @Param("name") String name, Pageable pageable);

    Optional<Patient> findByPid(String pid);


}
