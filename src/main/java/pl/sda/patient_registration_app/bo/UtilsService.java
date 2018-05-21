package pl.sda.patient_registration_app.bo;

import org.springframework.stereotype.Service;
import pl.sda.patient_registration_app.type.DoctorSpecializationType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilsService {

    public static final int OPENING_HOUR = 6;
    public static final int CLOSING_HOUR = 19;


    public List<LocalTime> getOpenHoursOfTheClinic() {
        List<LocalTime> hours = new ArrayList<>();
        for (int i = OPENING_HOUR; i <= CLOSING_HOUR; i++) {
            hours.add(LocalTime.of(i, 0));
        }

        return hours;
    }

    public List<String> convertDoctorSpecializationTypesToString() {

        List<DoctorSpecializationType> doctorSpecializationTypes = Arrays.asList(DoctorSpecializationType.values());
        List<String> docSpecNames = doctorSpecializationTypes.stream()
                .map(s -> s.getName())
                .collect(Collectors.toList());
        docSpecNames.sort(String::compareTo);
        return docSpecNames;
    }


}
