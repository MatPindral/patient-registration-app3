package pl.sda.patient_registration_app.entity;

import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "timetables")
public class DoctorTimetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fromTime")
    private LocalTime fromTime;

    @Column(name = "toTime")
    private LocalTime toTime;

    @Column(name = "dayOfWeek")
    @Enumerated(value = EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @ManyToOne
    private Doctor doctor;

}
