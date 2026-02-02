package dev_ryu.patientservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExcpetionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExcpetionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> emailAlreadyExistException(EmailAlreadyExistException ex) {
        LOGGER.warn("Email address already exist {}",  ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("email", "Email address already exist ");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> patientNotfoundException(PatientNotFoundException ex) {
        LOGGER.warn("Patient not found {}",  ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("email", "Patient not found ");
        return ResponseEntity.badRequest().body(errors);
    }
}
