package pl.sda.patient_registration_app.type;

public enum VisitStatusType {

    NONEXISTENT("Brak"),
    AVAILABLE("Wolna"),
    UNAVAILABLE("Zajeta");

    private String name;

    VisitStatusType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
