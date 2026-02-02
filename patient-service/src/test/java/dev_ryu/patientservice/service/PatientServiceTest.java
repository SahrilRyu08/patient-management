package dev_ryu.patientservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PatientServiceTest {

    @Autowired
    private PatientService patientService;


    @BeforeEach
    void setUp() {

    }

    @Test
    void test_create_patient() {

    }

    @Test
    void test_get_all_patients() {

//        patientService.createPatient();

    }
}