package pl.sda.patient_registration_app.bo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import pl.sda.patient_registration_app.dto.RegisterDto;
import pl.sda.patient_registration_app.entity.Doctor;
import pl.sda.patient_registration_app.entity.Patient;
import pl.sda.patient_registration_app.entity.Visit;
import pl.sda.patient_registration_app.repository.DoctorsRepository;
import pl.sda.patient_registration_app.repository.PatientsRepository;
import pl.sda.patient_registration_app.repository.VisitsRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class VisitRegistrationServiceTest {

    @Mock
    private DoctorsRepository doctorsRepository;
    @Mock
    private PatientsRepository patientsRepository;
    @Mock
    private VisitsRepository visitsRepository;

    @InjectMocks
    private VisitRegistrationService visitRegistrationService;

    @Test
    public void registerPatientToAVisitShouldCallSaveMethodOnVisitsRepository() {

        RegisterDto registerDto = new RegisterDto();
        registerDto.setDoctorId((long) 15);
        Doctor doctor = new Doctor();
        doctor.setId((long) 15);
        Patient patient = new Patient();
        patient.setId((long) 10);

        Mockito.when(doctorsRepository.findOne(registerDto.getDoctorId())).thenReturn(doctor);
        Mockito.when(patientsRepository.findOne(patient.getId())).thenReturn(patient);
        Mockito.when(visitsRepository.save(any(Visit.class))).thenReturn(any(Visit.class));

        visitRegistrationService.registerPatientToAVisit(registerDto, patient.getId());

        verify(visitsRepository).save(any(Visit.class));


    }

    @Test
    public void createNewVisitFromRegisterDtoAndPatientIdShouldCreateVisitsWithProperDateAndDoctorAndPatientInAccordanceWithInput() {

        Doctor doctor = new Doctor();
        doctor.setId((long) 15);

        Patient patient = new Patient();
        patient.setId((long) 10);

        Visit visit = Visit.builder()
                .date(LocalDate.of(2018, 10, 1))
                .time(LocalTime.of(12, 0))
                .patient(patient)
                .doctor(doctor)
                .build();

        RegisterDto registerDto = RegisterDto.builder()
                .date(LocalDate.of(2018, 10, 1))
                .time(LocalTime.of(12, 0))
                .doctorId((long) 15)
                .build();

        Long patientId = Long.valueOf(10);


        Mockito.when(doctorsRepository.findOne((long) 15)).thenReturn(doctor);
        Mockito.when(patientsRepository.findOne((long) 10)).thenReturn(patient);


        Visit result = visitRegistrationService.createNewVisitFromRegisterDtoAndPatientId(registerDto,patientId);

        Assert.assertTrue(result.getDate().equals(visit.getDate())
                &&result.getTime().equals(visit.getTime())
                &&result.getPatient().equals(visit.getPatient())
                &&result.getDoctor().equals(visit.getDoctor())

        );



    }




}