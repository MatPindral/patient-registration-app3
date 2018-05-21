package pl.sda.patient_registration_app.bo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.sda.patient_registration_app.dto.VisitDto;
import pl.sda.patient_registration_app.entity.Patient;
import pl.sda.patient_registration_app.entity.Visit;
import pl.sda.patient_registration_app.repository.PatientsRepository;
import pl.sda.patient_registration_app.repository.VisitsRepository;

import static org.mockito.Mockito.verify;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class VisitsFinderTest {

    @Mock
    private VisitsRepository visitsRepository;
    @Mock
    private MappingService mappingService;
    @Mock
    private PatientsRepository patientsRepository;

    @InjectMocks
    private VisitsFinder visitsFinder;

    @Test
    public void findVisitsByPatientId() {

        List<Visit> visits = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            visits.add(new Visit());
        }

        Patient patient = new Patient();
        patient.setId((long) 1);
        patient.setVisits(visits);

        Mockito.when(patientsRepository.findOne(patient.getId())).thenReturn(patient);
        Mockito.when(visitsRepository.findByPatient(patient)).thenReturn(patient.getVisits());
        Mockito.when(mappingService.mapVisitToVisitDto(any(Visit.class))).thenReturn(any(VisitDto.class));

        List<VisitDto> resultList = visitsFinder.findVisitsByPatientId((long) 1);

        Assert.assertEquals(resultList.size(), visits.size());
        verify(patientsRepository).findOne(patient.getId());
        verify(visitsRepository).findByPatient(patient);

    }


}