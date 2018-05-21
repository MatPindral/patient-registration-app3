package pl.sda.patient_registration_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.sda.patient_registration_app.entity.Doctor;
import pl.sda.patient_registration_app.entity.DoctorTimetable;

import java.time.DayOfWeek;

@Repository
public interface DoctorTimetablesRepository extends JpaRepository<DoctorTimetable, Long> {

    @Query("SELECT t FROM DoctorTimetable t WHERE t.dayOfWeek = :dayOfWeek AND t.doctor = :doctor")
    DoctorTimetable findByDayOfWeekAndDoctor(@Param("dayOfWeek") DayOfWeek dayOfWeek, @Param("doctor") Doctor doctor);

}
