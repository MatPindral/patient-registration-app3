package pl.sda.patient_registration_app.bo;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.patient_registration_app.exceptions.EmailExistsException;
import pl.sda.patient_registration_app.dto.NewUserRegistrationDto;
import pl.sda.patient_registration_app.entity.Patient;
import pl.sda.patient_registration_app.repository.PatientsRepository;
import pl.sda.patient_registration_app.type.RoleType;

@Service
public class NewPatientRegistrationService {

    private final MappingService mappingService;
    private final PatientsRepository patientsRepository;

    public NewPatientRegistrationService(MappingService mappingService, PatientsRepository patientsRepository) {
        this.mappingService = mappingService;
        this.patientsRepository = patientsRepository;
    }

    @Transactional
    public Patient saveNewPatientToDB(NewUserRegistrationDto newUserRegistrationDto) throws EmailExistsException {

        if (emailExist(newUserRegistrationDto.getEmail())) {
            throw new EmailExistsException(
                    "Konto z takim adresem email już istnieje "
                            + newUserRegistrationDto.getEmail());
        }


        Patient patient = mappingService.mapNewUserRegistrationDtoToPatient(newUserRegistrationDto);
        patient.setRole(RoleType.PATIENT);
        return patientsRepository.save(patient);

    }


    private boolean emailExist(String email) {
        Patient patient = patientsRepository.findFirstByEmail(email);
        if (patient != null) {
            return true;
        }
        return false;
    }

}
