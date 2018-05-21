package pl.sda.patient_registration_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.sda.patient_registration_app.type.VisitStatusType;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitDto implements Comparable<VisitDto> {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayOfVisit;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hourOfVisit;
    private DoctorDto doctor;
    private PatientDto patient;
    private VisitStatusType status;
    private Long id;


    @Override
    public int compareTo(VisitDto visitDto) {
        return this.hourOfVisit.compareTo(visitDto.hourOfVisit);
    }
}
