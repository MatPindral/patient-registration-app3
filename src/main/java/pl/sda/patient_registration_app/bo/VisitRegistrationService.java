package pl.sda.patient_registration_app.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.patient_registration_app.dto.RegisterDto;
import pl.sda.patient_registration_app.entity.Visit;
import pl.sda.patient_registration_app.repository.DoctorsRepository;
import pl.sda.patient_registration_app.repository.PatientsRepository;
import pl.sda.patient_registration_app.repository.VisitsRepository;

@Service
public class VisitRegistrationService {


    private final DoctorsRepository doctorsRepository;
    private final PatientsRepository patientsRepository;
    private final VisitsRepository visitsRepository;

    @Autowired
    public VisitRegistrationService(DoctorsRepository doctorsRepository, PatientsRepository patientsRepository, VisitsRepository visitsRepository) {
        this.doctorsRepository = doctorsRepository;
        this.patientsRepository = patientsRepository;
        this.visitsRepository = visitsRepository;
    }

    @Transactional
    public void registerPatientToAVisit(RegisterDto registerDto, Long patientId) {

        Visit newVisit = createNewVisitFromRegisterDtoAndPatientId(registerDto, patientId);

        visitsRepository.save(newVisit);

    }


    public Visit createNewVisitFromRegisterDtoAndPatientId(RegisterDto registerDto, Long patientId) {
        return Visit.builder()
                .date(registerDto.getDate())
                .time(registerDto.getTime())
                .doctor(doctorsRepository.findOne(registerDto.getDoctorId()))
                .patient(patientsRepository.findOne(patientId))
                .build();
    }


}
