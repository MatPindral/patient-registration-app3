package pl.sda.patient_registration_app.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDto {

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Long doctorId;

}
