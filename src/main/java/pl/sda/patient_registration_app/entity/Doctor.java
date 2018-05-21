package pl.sda.patient_registration_app.entity;

import lombok.*;
import pl.sda.patient_registration_app.type.DoctorSpecializationType;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctors")
public class Doctor extends User{



    @OneToMany(mappedBy = "doctor")
    private List<Visit> visits;

    @Column(name = "specialization")
    @Enumerated(value = EnumType.STRING)
    private DoctorSpecializationType specialization;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "doctor")
    private Set<DoctorTimetable> timetables;

}
