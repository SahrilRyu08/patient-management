package dev_ryu.patientservice.service;

import dev_ryu.patientservice.exception.EmailAlreadyExistException;
import dev_ryu.patientservice.exception.PatientNotFoundException;
import dev_ryu.patientservice.mapper.PatientMapper;
import dev_ryu.patientservice.model.Patient;
import dev_ryu.patientservice.repository.PatientRepository;
import dev_ryu.patientservice.service.dto.PatientRequestDTO;
import dev_ryu.patientservice.service.dto.PatientResponseDTO;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatient() {
        List<Patient> patients = patientRepository.findAll();
        LOGGER.info("Found {} patients", patients.size());
        for (Patient patient : patients) {
            LOGGER.info("list patient, {}", patient.toString());
        }
        return patients
                .stream()
                .map(PatientMapper::toPatientResponseDTO).toList();
    }

    @Transactional
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistException("A patient with this email already exist : " + patientRequestDTO.getEmail());
        }
        Patient patient = patientRepository.save(PatientMapper.toPatient(patientRequestDTO));
        LOGGER.info("Created patient, {}", patient.toString());
        return PatientMapper.toPatientResponseDTO(patient);
    }

    @Transactional
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id :" + id));
        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistException("A patient with this email already exist : " + patientRequestDTO.getEmail());
        }
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        Patient updatedPatient = patientRepository.save(patient);
        LOGGER.info("Updated patient, {}", patient);
        return PatientMapper.toPatientResponseDTO(updatedPatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
