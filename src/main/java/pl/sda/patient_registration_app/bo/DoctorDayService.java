package pl.sda.patient_registration_app.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.patient_registration_app.dto.DoctorDayDto;
import pl.sda.patient_registration_app.dto.DoctorDto;
import pl.sda.patient_registration_app.dto.VisitDto;
import pl.sda.patient_registration_app.entity.Visit;
import pl.sda.patient_registration_app.repository.VisitsRepository;
import pl.sda.patient_registration_app.type.DoctorSpecializationType;
import pl.sda.patient_registration_app.type.VisitStatusType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorDayService {

    private final VisitsRepository visitsRepository;
    private final MappingService mappingService;
    private final DoctorsFinder doctorsFinder;
    private final VisitsService visitsService;


    @Autowired
    public DoctorDayService(VisitsRepository visitsRepository, MappingService mappingService, DoctorsFinder doctorsFinder,
                            VisitsService visitsService) {
        this.visitsRepository = visitsRepository;
        this.mappingService = mappingService;
        this.doctorsFinder = doctorsFinder;
        this.visitsService = visitsService;
    }


    public List<DoctorDayDto> createDoctorDayDtosForChosenDayAndSpecialization(LocalDate date, DoctorSpecializationType doctorSpecializationType) {

        List<DoctorDayDto> doctorDayDtos = new ArrayList<>();
        List<DoctorDto> doctorDtos = doctorsFinder.findBySpecialization(doctorSpecializationType);

        visitsService.createAvailableVisitsFromDoctorTimetable(date, doctorDtos);

        for (DoctorDto doctorDto : doctorDtos) {
            DoctorDayDto doctorDayDto = DoctorDayDto.builder()
                    .doctorDto(doctorDto)
                    .date(date)
                    .visits(doctorDto.getVisits())
                    .build();
            fillDoctorDayDtoVisitsListWithVisits(doctorDayDto);

            doctorDayDto.setVisits(sortByHour(doctorDayDto.getVisits()));

            doctorDayDtos.add(doctorDayDto);
        }

        return doctorDayDtos;

    }

    private void fillDoctorDayDtoVisitsListWithVisits(DoctorDayDto doctorDayDto) {
        List<VisitDto> resultList = doctorDayDto.getVisits();
        List<Visit> existingVisitsForGivenDayAndDoctor = visitsRepository.findByDateAndDoctor_Id(doctorDayDto.getDate(), doctorDayDto.getDoctorDto().getId());
        resultList.addAll(
                existingVisitsForGivenDayAndDoctor.stream()
                        .map(v -> mappingService.mapVisitToVisitDto(v))
                        .collect(Collectors.toList())
        );

        for (VisitDto visitDto : resultList) {
            addStatusToVisit(visitDto);
        }

        fillUpListWithNonexistentVisits(resultList);

    }

    private List<VisitDto> sortByHour(List<VisitDto> visitsDto) {
        return visitsDto.stream()
                .sorted(VisitDto::compareTo)
                .collect(Collectors.toList());
    }

    private void fillUpListWithNonexistentVisits(List<VisitDto> visitsDto) {
        LocalTime temptime;
        boolean isContaining;

        for (int i = UtilsService.OPENING_HOUR; i <= UtilsService.CLOSING_HOUR; i++) {
            temptime = LocalTime.of(i, 0);
            isContaining = false;
            for (VisitDto visitDto : visitsDto) {
                if (visitDto.getHourOfVisit().equals(temptime)) {
                    isContaining = true;
                    break;
                }
            }
            if (!isContaining) {
                visitsDto.add(VisitDto.builder()
                        .status(VisitStatusType.NONEXISTENT)
                        .hourOfVisit(LocalTime.of(i, 0))
                        .build());
            }

        }
    }

    private void addStatusToVisit(VisitDto visitDto) {
        if (visitDto.getPatient() != null) {
            visitDto.setStatus(VisitStatusType.UNAVAILABLE);
        } else {
            visitDto.setStatus(VisitStatusType.AVAILABLE);
        }
    }
}
