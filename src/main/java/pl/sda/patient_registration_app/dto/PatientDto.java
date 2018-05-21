package pl.sda.patient_registration_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDto {

    private String firstName;
    private String lastName;
    private Long id;
    private List<VisitDto> plannedVisits;
    private String login;
    private String password;
}
