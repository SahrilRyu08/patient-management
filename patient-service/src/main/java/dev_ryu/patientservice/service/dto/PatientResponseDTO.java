package dev_ryu.patientservice.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PatientResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String address;
    private String dateOfBirth;

}
