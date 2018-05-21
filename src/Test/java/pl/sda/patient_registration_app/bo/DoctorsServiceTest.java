package pl.sda.patient_registration_app.bo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.sda.patient_registration_app.dto.NewDoctorRegistrationDto;
import pl.sda.patient_registration_app.entity.Doctor;
import pl.sda.patient_registration_app.repository.DoctorsRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class DoctorsServiceTest {

    @Mock
    private DoctorsRepository doctorsRepository;

    @Mock
    private MappingService mappingService;

    @InjectMocks
    private DoctorsService doctorsService;

    @Test
    public void saveNewDoctorToDBShouldCallSaveMethodOnDoctorsRepositoryOnce() {
        NewDoctorRegistrationDto newDoctorRegistrationDto = new NewDoctorRegistrationDto();
        Doctor doctor = new Doctor();
        Mockito.when(mappingService.mapNewDoctorRegistrationDtoToDoctor(any(NewDoctorRegistrationDto.class))).thenReturn(doctor);
        Mockito.when(doctorsRepository.save(any(Doctor.class))).thenReturn(doctor);
        doctorsService.saveNewDoctorToDB(newDoctorRegistrationDto);
        verify(doctorsRepository).save(doctor);

    }


}