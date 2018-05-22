package pl.sda.patient_registration_app.bo;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.sda.patient_registration_app.dto.*;
import pl.sda.patient_registration_app.entity.Doctor;
import pl.sda.patient_registration_app.entity.Patient;
import pl.sda.patient_registration_app.entity.User;
import pl.sda.patient_registration_app.entity.Visit;
import pl.sda.patient_registration_app.type.DoctorSpecializationType;
import pl.sda.patient_registration_app.type.RoleType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MappingServiceTest {

    private PasswordEncoder testPasswordEncoder = new PasswordEncoder();
    private MappingService mappingService = new MappingService(testPasswordEncoder);


    @Test
    public void mapPatientToPatientDtoShouldReturnNullIfPatientIsNull() {

        Patient patient = null;
        PatientDto patientDto = mappingService.mapPatientToPatientDto(patient);
        Assert.assertNull(patientDto);

    }

    @Test
    public void mapPatientToPatientDtoShouldMapAsExpected() {

        Patient patient = new Patient();
        patient.setId((long) 0);
        patient.setFirstName("Jon");
        patient.setLastName("Smith");

        PatientDto patientDto1 = PatientDto.builder()
                .id((long) 0)
                .lastName("Smith")
                .firstName("Jon")
                .build();

        PatientDto patientDto2 = mappingService.mapPatientToPatientDto(patient);

        Assert.assertEquals(patientDto2, patientDto1);

    }

    @Test
    public void mapDoctorToDoctorDtoShouldReturnNullIfDoctorIsNull() {

        Doctor doctor = null;
        DoctorDto doctorDto = mappingService.mapDoctorToDoctorDto(doctor);
        Assert.assertNull(doctorDto);
    }

    @Test
    public void mapDoctorToDoctorDtoShouldMapProperly() {

        Doctor doctor = new Doctor();
        doctor.setId((long) 1);
        doctor.setFirstName("Jan");
        doctor.setLastName("Kowalski");
        doctor.setEmail("Jan@Kowalski.com");
        doctor.setSpecialization(DoctorSpecializationType.DENTIST);

        DoctorDto doctorDto1 = DoctorDto.builder()
                .id((long) 1)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("Jan@Kowalski.com")
                .specialization(DoctorSpecializationType.DENTIST)
                .build();

        DoctorDto doctorDto2 = mappingService.mapDoctorToDoctorDto(doctor);
        Assert.assertTrue(doctorDto1.getId().equals(doctorDto2.getId())
                && doctorDto1.getFirstName().equals(doctorDto2.getFirstName())
                && doctorDto1.getLastName().equals(doctorDto2.getLastName())
                && doctorDto1.getSpecialization().equals(doctorDto2.getSpecialization())
                && doctorDto1.getEmail().equals(doctorDto2.getEmail()));
    }

    @Test
    public void mapVisitToVisitDtoShouldReturnNullIfVisitIsNull() {
        Visit visit = null;
        VisitDto visitDto = mappingService.mapVisitToVisitDto(visit);
        Assert.assertNull(visitDto);


    }

    @Test
    public void mapVisitToVisitDtoShouldMapAsExpected() {

        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        doctor.setId((long) 5);
        patient.setId((long) 15);
        VisitDto visitDto1 = VisitDto.builder()
                .hourOfVisit(LocalTime.of(13, 0))
                .dayOfVisit(LocalDate.of(2018, 06, 1))
                .doctor(mappingService.mapDoctorToDoctorDto(doctor))
                .patient(mappingService.mapPatientToPatientDto(patient))
                .id((long) 10)
                .build();

        Visit visit = Visit.builder()
                .time(LocalTime.of(13, 0))
                .date(LocalDate.of(2018, 06, 1))
                .doctor(doctor)
                .patient(patient)
                .id((long) 10)
                .build();
        VisitDto visitDto2 = mappingService.mapVisitToVisitDto(visit);

        Assert.assertTrue(
                visitDto1.getHourOfVisit().equals(visitDto2.getHourOfVisit())
                        && visitDto1.getDayOfVisit().equals(visitDto2.getDayOfVisit())
                        && visitDto1.getDoctor().getId().equals(visitDto2.getDoctor().getId())
                        && visitDto1.getPatient().getId().equals(visitDto2.getPatient().getId())
                        && visitDto1.getId().equals(visitDto2.getId())

        );

    }


    @Test
    public void mapNewUserRegistrationDtoToPatientShouldReturnNullIfNewUserRegistrationDtoIsNull() {

        NewUserRegistrationDto newUserRegistrationDto = null;
        Patient patient = mappingService.mapNewUserRegistrationDtoToPatient(newUserRegistrationDto);
        Assert.assertNull(patient);


    }


    @Test
    public void mapNewUserRegistrationDtoToPatientShouldMapAsExpected() {

        Patient patient1 = new Patient();
        patient1.setFirstName("Adam");
        patient1.setLastName("Nowak");
        patient1.setLogin("AdamNowak");
        patient1.setPassword(testPasswordEncoder.encode("password"));

        NewUserRegistrationDto newUserRegistrationDto1 = NewUserRegistrationDto.builder()
                .firstName("Adam")
                .lastName("Nowak")
                .login("AdamNowak")
                .password("password")
                .build();

        Patient patient2 = mappingService.mapNewUserRegistrationDtoToPatient(newUserRegistrationDto1);

        Assert.assertTrue(
                patient1.getFirstName().equals(patient2.getFirstName())
                        && patient1.getLastName().equals(patient2.getLastName())
                        && patient1.getLogin().equals(patient2.getLogin())
                        && patient1.getPassword().equals(patient2.getPassword())

        );


    }


    @Test
    public void mapDoctorDtoToDoctorShouldReturnNullIfDoctorDtoIsNull() {

        DoctorDto doctorDto = null;
        Doctor doctor = mappingService.mapDoctorDtoToDoctor(doctorDto);
        Assert.assertNull(doctor);

    }

    @Test
    public void mapDoctorDtoToDoctorShouldMapAsExpected() {
        Doctor doctor1 = new Doctor();
        doctor1.setId((long) 1);
        DoctorDto doctorDto = DoctorDto.builder()
                .id((long) 1)
                .build();
        Doctor doctor2 = mappingService.mapDoctorDtoToDoctor(doctorDto);
        Assert.assertTrue(doctor1.getId().equals(doctor2.getId()));
    }

    @Test
    public void mapNewDoctorRegistrationDtoToDoctorShouldReturnNullIfNewDoctorRegistrationDtoIsNull() {

        NewDoctorRegistrationDto newDoctorRegistrationDto = null;
        Doctor result = mappingService.mapNewDoctorRegistrationDtoToDoctor(newDoctorRegistrationDto);
        Assert.assertNull(result);


    }

    @Test
    public void mapNewDoctorRegistrationDtoToDoctorShouldMapAsExpected() {


        Doctor doctor = new Doctor();
        doctor.setFirstName("Jan");
        doctor.setLastName("Kowalski");
        doctor.setSpecialization(DoctorSpecializationType.DENTIST);
        doctor.setLogin("JanKowalski");
        doctor.setPassword("1234");
        doctor.setEmail("jan@kowalski.com");

        NewDoctorRegistrationDto newDoctorRegistrationDto = new NewDoctorRegistrationDto();
        newDoctorRegistrationDto.setFirstName("Jan");
        newDoctorRegistrationDto.setLastName("Kowalski");
        newDoctorRegistrationDto.setSpecialization(DoctorSpecializationType.DENTIST);
        newDoctorRegistrationDto.setLogin("JanKowalski");
        newDoctorRegistrationDto.setPassword("password");
        newDoctorRegistrationDto.setEmail("jan@kowalski.com");

        Doctor result = mappingService.mapNewDoctorRegistrationDtoToDoctor(newDoctorRegistrationDto);

        Assert.assertTrue(
                result.getSpecialization().equals(doctor.getSpecialization())
                        && result.getEmail().equals(doctor.getEmail())
                        && result.getLogin().equals(doctor.getLogin())
                        && result.getFirstName().equals(doctor.getFirstName())
                        && result.getLastName().equals(doctor.getLastName())
                        && result.getPassword().equals(doctor.getPassword())

        );


    }
    private class PasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder {

        @Override
        public String encode(CharSequence charSequence) {
            return "1234";
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
            return charSequence == s;
        }

    }

}

