package pl.sda.patient_registration_app.type;

import java.util.Arrays;

public enum DoctorSpecializationType {

    CARDIOLOGIST("kardiolog"),
    DERMATOLOGIST("dermatolog"),
    DENTIST("dentysta"),
    HEMATOLOGIST("hematolog");

    private String name;

    DoctorSpecializationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static DoctorSpecializationType findByName(String name) {
        DoctorSpecializationType[] values = DoctorSpecializationType.values();
        return Arrays.asList(values).stream()
                .filter(t -> t.getName().equals(name))
                .findFirst().get();
    }
}
