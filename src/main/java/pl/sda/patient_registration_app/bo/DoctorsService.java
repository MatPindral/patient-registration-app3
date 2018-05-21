package pl.sda.patient_registration_app.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.patient_registration_app.dto.NewDoctorRegistrationDto;
import pl.sda.patient_registration_app.entity.Doctor;
import pl.sda.patient_registration_app.repository.DoctorsRepository;

@Service
public class DoctorsService {


    private final DoctorsRepository doctorsRepository;
    private final MappingService mappingService;

    @Autowired
    public DoctorsService(DoctorsRepository doctorsRepository, MappingService mappingService) {
        this.doctorsRepository = doctorsRepository;
        this.mappingService = mappingService;
    }


    @Transactional
    public void saveNewDoctorToDB(NewDoctorRegistrationDto newDoctorRegistrationDto) {
        Doctor doctor = mappingService.mapNewDoctorRegistrationDtoToDoctor(newDoctorRegistrationDto);
        doctorsRepository.save(doctor);

    }


}
