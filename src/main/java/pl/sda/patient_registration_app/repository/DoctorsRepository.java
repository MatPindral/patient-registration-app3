package pl.sda.patient_registration_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.patient_registration_app.entity.Doctor;
import pl.sda.patient_registration_app.type.DoctorSpecializationType;

import java.util.List;

@Repository
public interface DoctorsRepository extends JpaRepository<Doctor, Long> {


    List<Doctor> findBySpecialization(DoctorSpecializationType doctorSpecializationType);

    Doctor findByLogin(String login);

    Doctor findFirstByLogin(String login);


}
