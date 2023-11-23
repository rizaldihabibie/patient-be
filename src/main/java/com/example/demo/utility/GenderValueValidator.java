package com.example.demo.utility;

import com.example.demo.enumerations.Gender;
import com.example.demo.interfaces.GenderValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class GenderValueValidator implements ConstraintValidator<GenderValue, Gender> {

    private Gender[] values;

    @Override
    public void initialize(GenderValue constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.values = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(Gender gender, ConstraintValidatorContext constraintValidatorContext) {
        return gender != null || Arrays.asList(values).contains(gender);
    }
}
