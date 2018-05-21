package pl.sda.patient_registration_app.dto;

import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorTimetableDto {

    private Long id;
    private LocalTime from;
    private LocalTime to;
    private DayOfWeek dayOfWeek;
    private DoctorDto doctor;

}
