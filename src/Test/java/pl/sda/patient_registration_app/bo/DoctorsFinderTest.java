package pl.sda.patient_registration_app.bo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.sda.patient_registration_app.dto.DoctorDto;
import pl.sda.patient_registration_app.entity.Doctor;
import pl.sda.patient_registration_app.repository.DoctorsRepository;
import pl.sda.patient_registration_app.type.DoctorSpecializationType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class DoctorsFinderTest {


    private DoctorsRepository doctorsRepository = Mockito.mock(DoctorsRepository.class);


    private MappingService mappingService = Mockito.mock(MappingService.class);


    private DoctorsFinder doctorsFinder = new DoctorsFinder(doctorsRepository, mappingService);



    @Test
    public void showAllDoctorsShouldReturnAListOfAllDoctorsFromDoctorsRepositoryMappedToDoctorDto() {

        List<Doctor> doctors = new ArrayList<>();
        Doctor doctor1 = new Doctor();
        Doctor doctor2 = new Doctor();
        Doctor doctor3 = new Doctor();
        doctors.add(doctor1);
        doctors.add(doctor2);
        doctors.add(doctor3);

        Mockito.when(doctorsRepository.findAll()).thenReturn(doctors);
        Mockito.when(mappingService.mapDoctorToDoctorDto(any(Doctor.class))).thenReturn(new DoctorDto());

        List<DoctorDto> resultList = doctorsFinder.showAllDoctors();

        Assert.assertEquals(resultList.size(), doctors.size());
    }

    @Test
    public void findBySpecializationShouldReturnAListOfAllDoctorOfGivenSpecializationFromDoctorsRepositoryMappedToDoctorDto() {

        List<Doctor> doctors = new ArrayList<>();
        Doctor doctor1 = new Doctor();
        doctor1.setSpecialization(DoctorSpecializationType.DENTIST);
        Doctor doctor2 = new Doctor();
        doctor2.setSpecialization(DoctorSpecializationType.DENTIST);
        doctors.add(doctor1);
        doctors.add(doctor2);

        List<DoctorDto> doctorDtos = new ArrayList<>();
        DoctorDto doctorDto1 = DoctorDto.builder().specialization(DoctorSpecializationType.DENTIST).build();
        DoctorDto doctorDto2 = DoctorDto.builder().specialization(DoctorSpecializationType.DENTIST).build();
        doctorDtos.add(doctorDto1);
        doctorDtos.add(doctorDto2);

        Mockito.when(doctorsRepository.findBySpecialization(DoctorSpecializationType.DENTIST)).thenReturn(doctors);
        Mockito.when(mappingService.mapDoctorToDoctorDto(any(Doctor.class))).thenReturn(DoctorDto.builder().specialization(DoctorSpecializationType.DENTIST).build());

        List<DoctorDto> resultList = doctorsFinder.findBySpecialization(DoctorSpecializationType.DENTIST);

        Assert.assertEquals(doctorDtos.size(), resultList.size());
        Assert.assertTrue(doctorDtos.get(0).getSpecialization().equals(resultList.get(0).getSpecialization()));

    }

    @Test
    public void findByLoginShouldReturnADoctorDtoMappedFromADoctorWithGivenLoginRetrievedFromDoctorsRepository() {


        Doctor doctorAdam = new Doctor();
        doctorAdam.setLogin("AdamNowak");
        doctorAdam.setSpecialization(DoctorSpecializationType.CARDIOLOGIST);
        doctorAdam.setId((long) 1);
        doctorAdam.setEmail("adam@doctor.com");
        doctorAdam.setFirstName("Adam");
        doctorAdam.setLastName("Nowak");

        DoctorDto doctorDtoAdam = DoctorDto.builder()
                .specialization(DoctorSpecializationType.CARDIOLOGIST)
                .id((long) 1)
                .firstName("Adam")
                .lastName("Nowak")
                .email("adam@doctor.com")
                .build();

        Mockito.when(doctorsRepository.findFirstByLogin("Adam")).thenReturn(doctorAdam);
        Mockito.when(mappingService.mapDoctorToDoctorDto(doctorAdam)).thenReturn(doctorDtoAdam);

        String login = "Adam";
        DoctorDto result = doctorsFinder.findByLogin(login);

        Assert.assertTrue(doctorDtoAdam.getId().equals(result.getId())
                && doctorDtoAdam.getFirstName().equals(result.getFirstName())
                && doctorDtoAdam.getLastName().equals(result.getLastName())
                && doctorDtoAdam.getSpecialization().equals(result.getSpecialization())
                && doctorDtoAdam.getEmail().equals(result.getEmail()));


    }

}