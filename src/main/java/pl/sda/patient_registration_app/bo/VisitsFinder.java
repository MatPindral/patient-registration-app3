package pl.sda.patient_registration_app.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.patient_registration_app.dto.VisitDto;
import pl.sda.patient_registration_app.entity.Visit;
import pl.sda.patient_registration_app.repository.PatientsRepository;
import pl.sda.patient_registration_app.repository.VisitsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitsFinder {

    private final VisitsRepository visitsRepository;
    private final MappingService mappingService;
    private final PatientsRepository patientsRepository;

    @Autowired
    public VisitsFinder(VisitsRepository visitsRepository, MappingService mappingService, PatientsRepository patientsRepository) {
        this.visitsRepository = visitsRepository;
        this.mappingService = mappingService;
        this.patientsRepository = patientsRepository;
    }


    public List<VisitDto> findVisitsByPatientId(Long id) {

        List<Visit> foundVisits = visitsRepository.findByPatient(patientsRepository.findOne(id));


        return foundVisits.stream()
                .map(v -> mappingService.mapVisitToVisitDto(v))
                .collect(Collectors.toList());

    }

}
