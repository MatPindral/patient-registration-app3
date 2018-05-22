package pl.sda.patient_registration_app.bo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.sda.patient_registration_app.dto.DoctorDayDto;
import pl.sda.patient_registration_app.dto.DoctorDto;
import pl.sda.patient_registration_app.dto.PatientDto;
import pl.sda.patient_registration_app.dto.VisitDto;
import pl.sda.patient_registration_app.entity.Doctor;
import pl.sda.patient_registration_app.entity.DoctorTimetable;
import pl.sda.patient_registration_app.entity.Patient;
import pl.sda.patient_registration_app.entity.Visit;
import pl.sda.patient_registration_app.repository.VisitsRepository;
import pl.sda.patient_registration_app.type.DoctorSpecializationType;
import pl.sda.patient_registration_app.type.VisitStatusType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class DoctorDayServiceTest {

    private List<DoctorDto> doctorDtos;
    private LocalDate date;
    private Doctor doctorAdam;
    private DoctorTimetable timetableMonday;
    private DoctorDto doctorDtoAdam;
    private DoctorSpecializationType doctorSpecializationType;
    private List<Visit> alreadyReservedVisits;
    private List<VisitDto> visitDtos;

    private PasswordEncoder testPasswordEncoder = new PasswordEncoder();
    private MappingService mappingService = new MappingService(testPasswordEncoder);
    private VisitsRepository visitsRepository = Mockito.mock(VisitsRepository.class);
    private DoctorsFinder doctorsFinder = Mockito.mock(DoctorsFinder.class);
    private VisitsService visitsService = Mockito.mock(VisitsService.class);
    private DoctorDayService doctorDayService = new DoctorDayService(visitsRepository, mappingService, doctorsFinder, visitsService);


    @Before
    public void setUp() throws Exception {

        visitDtos = new ArrayList<>();
        alreadyReservedVisits = new ArrayList<>();
        doctorSpecializationType = DoctorSpecializationType.CARDIOLOGIST;
        doctorDtos = new ArrayList<>();
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
                .fromTime(LocalTime.of(6, 0))
                .toTime(LocalTime.of(7, 0))
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

        VisitDto visitDto = VisitDto.builder()
                .doctor(doctorDtoAdam)
                .hourOfVisit(LocalTime.of(6, 0))
                .dayOfVisit(date)
                .build();

        visitDtos.add(visitDto);
        doctorDtos.add(doctorDtoAdam);


    }

    @Test
    public void createDoctorDayDtosForChosenDayAndSpecializationShouldReturnDoctorDayDtosWithVisitsListSizeCorrespondingToOpenHoursOfTheClinic() {

        doctorDtoAdam.setVisits(visitDtos);

        Visit visit = Visit.builder()
                .date(date)
                .doctor(doctorAdam)
                .time(LocalTime.of(7, 0))
                .patient(new Patient())
                .build();

        alreadyReservedVisits.add(visit);


        Mockito.when(doctorsFinder.findBySpecialization(DoctorSpecializationType.CARDIOLOGIST)).thenReturn(doctorDtos);
        Mockito.when(visitsRepository.findByDateAndDoctor_Id(date, doctorAdam.getId())).thenReturn(alreadyReservedVisits);
        Mockito.doNothing().when(visitsService).createAvailableVisitsFromDoctorTimetable(date, doctorDtos);

        //Assuming every visits takes one hour, the total number of visits (reserved, available and empty) should equal to:
        int expectedListSize = UtilsService.CLOSING_HOUR - UtilsService.OPENING_HOUR + 1;

        List<DoctorDayDto> doctorDayDtos = doctorDayService.createDoctorDayDtosForChosenDayAndSpecialization(date, doctorSpecializationType);
        Assert.assertEquals(expectedListSize, doctorDayDtos.get(0).getVisits().size());

    }

    @Test
    public void createDoctorDayDtosForChosenDayAndSpecializationShouldCreateOrderedVisitLists() {

        doctorDtoAdam.setVisits(visitDtos);

        Visit visit = Visit.builder()
                .date(date)
                .doctor(doctorAdam)
                .time(LocalTime.of(9, 0))
                .patient(new Patient())
                .build();

        alreadyReservedVisits.add(visit);


        Mockito.when(doctorsFinder.findBySpecialization(DoctorSpecializationType.CARDIOLOGIST)).thenReturn(doctorDtos);
        Mockito.when(visitsRepository.findByDateAndDoctor_Id(date, doctorAdam.getId())).thenReturn(alreadyReservedVisits);
        Mockito.doNothing().when(visitsService).createAvailableVisitsFromDoctorTimetable(date, doctorDtos);
        List<DoctorDayDto> doctorDayDtos = doctorDayService.createDoctorDayDtosForChosenDayAndSpecialization(date, doctorSpecializationType);
        boolean isVisitListSorted = true;

        for (int i = 1; i < doctorDayDtos.get(0).getVisits().size(); i++) {
            VisitDto visitDto1 = doctorDayDtos.get(0).getVisits().get(i);
            VisitDto visitDto2 = doctorDayDtos.get(0).getVisits().get(i - 1);

            if (visitDto1.compareTo(visitDto2) <= 0) {
                isVisitListSorted = false;
            }

        }

        Assert.assertTrue(isVisitListSorted);

    }

    @Test
    public void createDoctorDayDtosForChosenDayAndSpecializationShouldFillVisitListWithOnlyNonexistentVisitsIfThereAreNoReservedOrAvailableVisits() {

        visitDtos.clear();
        doctorDtoAdam.setVisits(visitDtos);

        Mockito.when(doctorsFinder.findBySpecialization(DoctorSpecializationType.CARDIOLOGIST)).thenReturn(doctorDtos);
        Mockito.when(visitsRepository.findByDateAndDoctor_Id(date, doctorAdam.getId())).thenReturn(alreadyReservedVisits);
        Mockito.doNothing().when(visitsService).createAvailableVisitsFromDoctorTimetable(date, doctorDtos);

        List<DoctorDayDto> doctorDayDtos = doctorDayService.createDoctorDayDtosForChosenDayAndSpecialization(date, doctorSpecializationType);

        boolean everyVisitIsNonexistent = true;

        for (VisitDto visitDto : doctorDayDtos.get(0).getVisits()) {

            if (visitDto.getStatus() != VisitStatusType.NONEXISTENT) {
                everyVisitIsNonexistent = false;
            }

        }
        Assert.assertTrue(everyVisitIsNonexistent);

    }

    @Test
    public void createDoctorDayDtosForChosenDayAndSpecializationShould() {

        doctorDtoAdam.setVisits(visitDtos);

        Visit visit = Visit.builder()
                .date(date)
                .doctor(doctorAdam)
                .time(LocalTime.of(7, 0))
                .patient(new Patient())
                .build();

        alreadyReservedVisits.add(visit);


        Mockito.when(doctorsFinder.findBySpecialization(DoctorSpecializationType.CARDIOLOGIST)).thenReturn(doctorDtos);
        Mockito.when(visitsRepository.findByDateAndDoctor_Id(date, doctorAdam.getId())).thenReturn(alreadyReservedVisits);
        Mockito.doNothing().when(visitsService).createAvailableVisitsFromDoctorTimetable(date, doctorDtos);

        List<DoctorDayDto> doctorDayDtos = doctorDayService.createDoctorDayDtosForChosenDayAndSpecialization(date, doctorSpecializationType);

        boolean areVisitStatusesAsExpected = true;

        if (doctorDayDtos.get(0).getVisits().get(0).getStatus() != VisitStatusType.AVAILABLE) {
            areVisitStatusesAsExpected = false;
        }
        if (doctorDayDtos.get(0).getVisits().get(1).getStatus() != VisitStatusType.UNAVAILABLE) {
            areVisitStatusesAsExpected = false;
        }

        for (int i = 2; i < doctorDayDtos.get(0).getVisits().size(); i++) {
            VisitDto visitDto = doctorDayDtos.get(0).getVisits().get(i);

            if (visitDto.getStatus() != VisitStatusType.NONEXISTENT) {
                areVisitStatusesAsExpected = false;
            }
        }

        Assert.assertTrue(areVisitStatusesAsExpected);
    }



    private class MappingService extends pl.sda.patient_registration_app.bo.MappingService {

        public MappingService(DoctorDayServiceTest.PasswordEncoder passwordEncoder) {
            super(passwordEncoder);
        }

        public VisitDto mapVisitToVisitDto(Visit visit) {
            if (visit != null) {
                return VisitDto.builder()
                        .id(visit.getId())
                        .doctor(mapDoctorToDoctorDto(visit.getDoctor()))
                        .patient(mapPatientToPatientDto(visit.getPatient()))
                        .dayOfVisit(visit.getDate())
                        .hourOfVisit(visit.getTime())
                        .build();
            } else {
                return null;
            }
        }

        public PatientDto mapPatientToPatientDto(Patient patient) {

            return new PatientDto();
        }

        public DoctorDto mapDoctorToDoctorDto(Doctor doctor) {

            return new DoctorDto();
        }

    }

    private class PasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder {

        @Override
        public String encode(CharSequence charSequence) {
            return "1234";
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
            return charSequence == s;
        }
    }
}

