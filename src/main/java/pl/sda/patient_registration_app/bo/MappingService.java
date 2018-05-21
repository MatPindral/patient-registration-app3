package pl.sda.patient_registration_app.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.patient_registration_app.dto.*;
import pl.sda.patient_registration_app.entity.Doctor;
import pl.sda.patient_registration_app.entity.Patient;
import pl.sda.patient_registration_app.entity.Visit;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MappingService {


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MappingService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public PatientDto mapPatientToPatientDto(Patient patient) {
        if (patient != null) {
            return PatientDto.builder()
                    .id(patient.getId())
                    .firstName(patient.getFirstName())
                    .lastName(patient.getLastName())
                    .build();
        } else {
            return null;
        }
    }

    public DoctorDto mapDoctorToDoctorDto(Doctor doctor) {
        if (doctor != null) {
            return DoctorDto.builder()
                    .id(doctor.getId())
                    .firstName(doctor.getFirstName())
                    .lastName(doctor.getLastName())
                    .specialization(doctor.getSpecialization())
                    .email(doctor.getEmail())
                    .build();

        } else {
            return null;
        }
    }


    public VisitDto mapVisitToVisitDto(Visit visit) {
        if (visit != null) {
            return VisitDto.builder()
                    .id(visit.getId())
                    .doctor(mapDoctorToDoctorDto(visit.getDoctor()))
                    .patient(mapPatientToPatientDto(visit.getPatient()))
                    .dayOfVisit(visit.getDate())
                    .hourOfVisit(visit.getTime())
                    .build();
        } else {
            return null;
        }
    }

    public Patient mapNewUserRegistrationDtoToPatient(NewUserRegistrationDto newUserRegistrationDto) {

        if (newUserRegistrationDto != null) {
            Patient patient = new Patient();
            patient.setFirstName(newUserRegistrationDto.getFirstName());
            patient.setLastName(newUserRegistrationDto.getLastName());
            patient.setLogin(newUserRegistrationDto.getLogin());
            patient.setPassword(passwordEncoder.encode(newUserRegistrationDto.getPassword()));
            return patient;
        } else {
            return null;
        }


    }


    public Doctor mapDoctorDtoToDoctor(DoctorDto doctorDto) {
        if (doctorDto != null) {
            Doctor doctor = new Doctor();
            doctor.setId(doctorDto.getId());
            return doctor;
        } else {
            return null;
        }
    }

    public Doctor mapNewDoctorRegistrationDtoToDoctor(NewDoctorRegistrationDto newDoctorRegistrationDto) {
        if (newDoctorRegistrationDto != null) {
            Doctor doctor = new Doctor();
            doctor.setFirstName(newDoctorRegistrationDto.getFirstName());
            doctor.setLastName(newDoctorRegistrationDto.getLastName());
            doctor.setSpecialization(newDoctorRegistrationDto.getSpecialization());
            doctor.setLogin(newDoctorRegistrationDto.getLogin());
            doctor.setPassword(passwordEncoder.encode(newDoctorRegistrationDto.getPassword()));
            doctor.setEmail(newDoctorRegistrationDto.getEmail());
            return doctor;
        } else return null;
    }

    private List<VisitDto> mapVisitsToVisitsDto(Set<Visit> visits) {
        return visits.stream()
                .map(v -> mapVisitToVisitDto(v))
                .collect(Collectors.toList());
    }


}
