package pl.sda.patient_registration_app.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.patient_registration_app.entity.Doctor;
import pl.sda.patient_registration_app.entity.DoctorTimetable;
import pl.sda.patient_registration_app.repository.DoctorTimetablesRepository;
import pl.sda.patient_registration_app.repository.DoctorsRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Service
public class TimetablesService {

    private final DoctorTimetablesRepository timetablesRepository;
    private final DoctorsRepository doctorsRepository;

    @Autowired
    public TimetablesService(DoctorTimetablesRepository timetablesRepository, DoctorsRepository doctorsRepository) {
        this.timetablesRepository = timetablesRepository;
        this.doctorsRepository = doctorsRepository;
    }

    //Temporary method to set doctors' schedules
    public void addPredefinedTimetablesToDB() {
        List<Doctor> doctors = doctorsRepository.findAll();
        for (Doctor doctor : doctors) {
            timetablesRepository.save(DoctorTimetable.builder()
                    .fromTime(LocalTime.of(7, 0))
                    .toTime(LocalTime.of(13, 0))
                    .dayOfWeek(DayOfWeek.MONDAY)
                    .doctor(doctor)
                    .build());
            timetablesRepository.save(DoctorTimetable.builder()
                    .fromTime(LocalTime.of(9, 0))
                    .toTime(LocalTime.of(13, 0))
                    .dayOfWeek(DayOfWeek.TUESDAY)
                    .doctor(doctor)
                    .build());
            timetablesRepository.save(DoctorTimetable.builder()
                    .fromTime(LocalTime.of(10, 0))
                    .toTime(LocalTime.of(15, 0))
                    .dayOfWeek(DayOfWeek.WEDNESDAY)
                    .doctor(doctor)
                    .build());
            timetablesRepository.save(DoctorTimetable.builder()
                    .fromTime(LocalTime.of(6, 0))
                    .toTime(LocalTime.of(14, 0))
                    .dayOfWeek(DayOfWeek.THURSDAY)
                    .doctor(doctor)
                    .build());
            timetablesRepository.save(DoctorTimetable.builder()
                    .fromTime(LocalTime.of(10, 0))
                    .toTime(LocalTime.of(18, 0))
                    .dayOfWeek(DayOfWeek.FRIDAY)
                    .doctor(doctor)
                    .build());
        }
    }

}
