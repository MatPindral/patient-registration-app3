package pl.sda.patient_registration_app.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.patient_registration_app.dto.DoctorDto;
import pl.sda.patient_registration_app.dto.VisitDto;
import pl.sda.patient_registration_app.entity.DoctorTimetable;
import pl.sda.patient_registration_app.entity.Visit;
import pl.sda.patient_registration_app.repository.DoctorTimetablesRepository;
import pl.sda.patient_registration_app.repository.VisitsRepository;
import pl.sda.patient_registration_app.type.VisitStatusType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitsService {


    private final VisitsRepository visitsRepository;
    private final DoctorTimetablesRepository timetablesRepository;
    private final MappingService mappingService;

    @Autowired
    public VisitsService(VisitsRepository visitsRepository,
                         DoctorTimetablesRepository timetablesRepository,
                         MappingService mappingService) {
        this.visitsRepository = visitsRepository;
        this.timetablesRepository = timetablesRepository;
        this.mappingService = mappingService;
    }

    public void createAvailableVisitsFromDoctorTimetable(LocalDate date, List<DoctorDto> doctorsDto) {
        for (DoctorDto doctorDto : doctorsDto) {
            doctorDto.setVisits(createAvailableVisitsFromDoctorTimetable(date, doctorDto));
        }
    }

    private List<VisitDto> createAvailableVisitsFromDoctorTimetable(LocalDate date, DoctorDto doctorDto) {
        List<VisitDto> availableVisits = new ArrayList<>();

        DoctorTimetable timetable = getTimetableForDayOfWeek(date, doctorDto);

        if (timetable != null) {
            int from = timetable.getFromTime().getHour();
            int to = timetable.getToTime().getHour();
            List<Visit> alreadyReservedVisits = visitsRepository.findByDateAndDoctor_Id(date, doctorDto.getId());
            List<LocalTime> alreadyReservedVisitTimes = alreadyReservedVisits.stream()
                    .map(v -> v.getTime())
                    .collect(Collectors.toList());


            for (int i = from; i <= to; i++) {

                if (!alreadyReservedVisitTimes.contains(LocalTime.of(i, 0))) {
                    availableVisits.add(VisitDto.builder()
                            .hourOfVisit(LocalTime.of(i, 0))
                            .dayOfVisit(date)
                            .status(VisitStatusType.AVAILABLE)
                            .doctor(doctorDto)
                            .build()
                    );


                }
            }
        }

        return availableVisits;
    }

    private DoctorTimetable getTimetableForDayOfWeek(LocalDate date, DoctorDto doctorDto) {
        return timetablesRepository
                .findByDayOfWeekAndDoctor(date.getDayOfWeek(), mappingService.mapDoctorDtoToDoctor(doctorDto));
    }


}
