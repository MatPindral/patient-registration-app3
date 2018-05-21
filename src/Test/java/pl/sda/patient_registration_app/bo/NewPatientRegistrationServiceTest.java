package pl.sda.patient_registration_app.bo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.sda.patient_registration_app.dto.NewDoctorRegistrationDto;
import pl.sda.patient_registration_app.dto.NewUserRegistrationDto;
import pl.sda.patient_registration_app.entity.Patient;
import pl.sda.patient_registration_app.exceptions.EmailExistsException;
import pl.sda.patient_registration_app.repository.PatientsRepository;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NewPatientRegistrationServiceTest {

    @Mock
    private MappingService mappingService;
    @Mock
    private PatientsRepository patientsRepository;

    @InjectMocks
    private NewPatientRegistrationService newPatientRegistrationService;

    @Test
    public void saveNewPatientToDBShouldThrowEmailExistsExceptionIfGivenEmailAlreadyExistsInTheDB() {

        Patient patient1 = Patient.builder().email("jan@kowalski.com").build();
        NewUserRegistrationDto newUserRegistrationDto = NewUserRegistrationDto.builder().email("jan@kowalski.com").build();
        Mockito.when(patientsRepository.findFirstByEmail(patient1.getEmail())).thenReturn(patient1);

        try {
            newPatientRegistrationService.saveNewPatientToDB(newUserRegistrationDto);
            fail("Email exists exception should be thrown.");
        } catch (Throwable exception) {

            assertTrue(exception.getClass().equals(EmailExistsException.class));
        }


    }

    @Test
    public void saveNewPatientToDBShouldCallSaveMethodOnPatientsRepository() throws EmailExistsException {

        NewDoctorRegistrationDto newDoctorRegistrationDto = new NewDoctorRegistrationDto();
        Patient patient = new Patient();

        Mockito.when(patientsRepository.findFirstByEmail(patient.getEmail())).thenReturn(null);
        Mockito.when(mappingService.mapNewUserRegistrationDtoToPatient(any(NewUserRegistrationDto.class))).thenReturn(patient);
        Mockito.when(patientsRepository.save(any(Patient.class))).thenReturn(patient);

        newPatientRegistrationService.saveNewPatientToDB(newDoctorRegistrationDto);

        verify(patientsRepository).save(patient);

    }


}