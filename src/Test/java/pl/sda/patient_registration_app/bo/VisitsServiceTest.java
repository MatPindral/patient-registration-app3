package pl.sda.patient_registration_app.bo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.sda.patient_registration_app.dto.DoctorDto;
import pl.sda.patient_registration_app.dto.VisitDto;
import pl.sda.patient_registration_app.entity.Doctor;
import pl.sda.patient_registration_app.entity.DoctorTimetable;
import pl.sda.patient_registration_app.entity.Patient;
import pl.sda.patient_registration_app.entity.Visit;
import pl.sda.patient_registration_app.repository.DoctorTimetablesRepository;
import pl.sda.patient_registration_app.repository.VisitsRepository;
import pl.sda.patient_registration_app.type.DoctorSpecializationType;
import pl.sda.patient_registration_app.type.VisitStatusType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class VisitsServiceTest {

    private List<DoctorDto> doctorDtos;
    private List<Visit> visits;
    private LocalDate date;
    private Doctor doctorAdam;
    private DoctorTimetable timetableMonday;
    private DoctorDto doctorDtoAdam;

    @Mock
    private VisitsRepository visitsRepository;
    @Mock
    private DoctorTimetablesRepository timetablesRepository;
    @Mock
    private MappingService mappingService;

    @InjectMocks
    private VisitsService visitsService;

    @Before
    public void setUp() throws Exception {

        doctorDtos = new ArrayList<>();
        visits = new ArrayList<>();
        date = LocalDate.of(2018, 5, 21);


        doctorAdam = new Doctor();
        doctorAdam.setLogin("AdamNowak");
        doctorAdam.setSpecialization(DoctorSpecializationType.CARDIOLOGIST);
        doctorAdam.setId((long) 1);
        doctorAdam.setEmail("adam@doctor.com");
        doctorAdam.setFirstName("Adam");
        doctorAdam.setLastName("Nowak");

        timetableMonday = DoctorTimetable.builder()
                .dayOfWeek(DayOfWeek.MONDAY)
                .fromTime(LocalTime.of(8, 0))
                .toTime(LocalTime.of(13, 0))
                .doctor(doctorAdam)
                .id((long) 1)
                .build();

        doctorDtoAdam = DoctorDto.builder()
                .specialization(DoctorSpecializationType.CARDIOLOGIST)
                .id((long) 1)
                .firstName("Adam")
                .lastName("Nowak")
                .email("adam@doctor.com")
                .build();


        doctorDtos.add(doctorDtoAdam);

    }

    @Test
    public void createAvailableVisitsFromDoctorTimetableShouldCreateAsManyAvailableVisitsInVisitListsAsExpectedFromTimetableIfThereAreNoReservedVisitsForGivenDateAndDoctorInDB() {


        Mockito.when(timetablesRepository.findByDayOfWeekAndDoctor(DayOfWeek.MONDAY, doctorAdam)).thenReturn(timetableMonday);
        Mockito.when(visitsRepository.findByDateAndDoctor_Id(date, doctorAdam.getId())).thenReturn(visits);
        Mockito.when(mappingService.mapDoctorDtoToDoctor(doctorDtoAdam)).thenReturn(doctorAdam);

        visitsService.createAvailableVisitsFromDoctorTimetable(date, doctorDtos);

        Assert.assertEquals(6, doctorDtos.get(0).getVisits().size());


    }

    @Test
    public void createAvailableVisitsFromDoctorTimetableShouldCreateLessVisitsInVisitListsThanExpectedFromTimetableIfThereAreAlreadyReservedVisitsInDBForGivenDateAndDoctor() {


        Visit visit = Visit.builder()
                .doctor(doctorAdam)
                .patient(new Patient())
                .time(LocalTime.of(8, 0))
                .date(date)
                .id((long) 1)
                .build();

        visits.add(visit);

        Mockito.when(timetablesRepository.findByDayOfWeekAndDoctor(DayOfWeek.MONDAY, doctorAdam)).thenReturn(timetableMonday);
        Mockito.when(visitsRepository.findByDateAndDoctor_Id(date, doctorAdam.getId())).thenReturn(visits);
        Mockito.when(mappingService.mapDoctorDtoToDoctor(doctorDtoAdam)).thenReturn(doctorAdam);

        visitsService.createAvailableVisitsFromDoctorTimetable(date, doctorDtos);

        Assert.assertEquals(5, doctorDtos.get(0).getVisits().size());


    }

    @Test
    public void createAvailableVisitsFromDoctorTimetableShouldCreateVisitsWithAvailableStatus() {


        Visit visit = Visit.builder()
                .doctor(doctorAdam)
                .patient(new Patient())
                .time(LocalTime.of(8, 0))
                .date(date)
                .id((long) 1)
                .build();

        visits.add(visit);

        Mockito.when(timetablesRepository.findByDayOfWeekAndDoctor(DayOfWeek.MONDAY, doctorAdam)).thenReturn(timetableMonday);
        Mockito.when(visitsRepository.findByDateAndDoctor_Id(date, doctorAdam.getId())).thenReturn(visits);
        Mockito.when(mappingService.mapDoctorDtoToDoctor(doctorDtoAdam)).thenReturn(doctorAdam);

        visitsService.createAvailableVisitsFromDoctorTimetable(date, doctorDtos);

        boolean areAllCreatedvisitsAvailable = true;
        for (VisitDto visitDto : doctorDtos.get(0).getVisits()) {

            if (!visitDto.getStatus().equals(VisitStatusType.AVAILABLE)) {

                areAllCreatedvisitsAvailable = false;

            }

        }

        Assert.assertTrue(areAllCreatedvisitsAvailable);

    }


}