package pl.sda.patient_registration_app.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.patient_registration_app.dto.DoctorDto;
import pl.sda.patient_registration_app.repository.DoctorsRepository;
import pl.sda.patient_registration_app.type.DoctorSpecializationType;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorsFinder {

    private final DoctorsRepository doctorsRepository;
    private final MappingService mappingService;

    @Autowired
    public DoctorsFinder(DoctorsRepository doctorsRepository, MappingService mappingService) {
        this.doctorsRepository = doctorsRepository;
        this.mappingService = mappingService;
    }

    public List<DoctorDto> showAllDoctors() {
        return doctorsRepository.findAll().stream()
                .map(v -> mappingService.mapDoctorToDoctorDto(v))
                .collect(Collectors.toList());
    }

    public List<DoctorDto> findBySpecialization(DoctorSpecializationType doctorSpecializationType) {
        return doctorsRepository.findBySpecialization(doctorSpecializationType).stream()
                .map(d -> mappingService.mapDoctorToDoctorDto(d))
                .collect(Collectors.toList());
    }

    public DoctorDto findByLogin(String login) {
        return mappingService.mapDoctorToDoctorDto(doctorsRepository.findFirstByLogin(login));
    }
}
