package pl.sda.patient_registration_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.patient_registration_app.entity.Patient;

@Repository
public interface PatientsRepository extends JpaRepository<Patient,Long>{


    Patient findFirstByEmail(String email);
    Patient findFirstById(Long id);

}
