package pl.sda.patient_registration_app.dto;

import lombok.*;
import pl.sda.patient_registration_app.type.DoctorSpecializationType;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {


    private String firstName;
    private String lastName;
    private DoctorSpecializationType specialization;
    private Long id;
    private String email;
    private List<VisitDto> visits;
    private List<DoctorTimetableDto> timetables;


}
