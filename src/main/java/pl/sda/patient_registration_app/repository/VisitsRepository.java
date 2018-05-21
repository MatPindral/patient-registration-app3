package pl.sda.patient_registration_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.patient_registration_app.dto.DoctorDto;
import pl.sda.patient_registration_app.entity.Patient;
import pl.sda.patient_registration_app.entity.Visit;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitsRepository extends JpaRepository<Visit, Long> {

    List<Visit> findByDate(LocalDate date);

    List<Visit> findByDateAndDoctor_Id(LocalDate date, Long doctor_Id);

    List<Visit> findByDoctor(DoctorDto doctorDto);

    List<Visit> findByPatient(Patient patient);


}
