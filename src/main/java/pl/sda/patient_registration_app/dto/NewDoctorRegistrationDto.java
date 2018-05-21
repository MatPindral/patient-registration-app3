package pl.sda.patient_registration_app.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.patient_registration_app.type.DoctorSpecializationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewDoctorRegistrationDto extends NewUserRegistrationDto {


    private DoctorSpecializationType specialization;


}
