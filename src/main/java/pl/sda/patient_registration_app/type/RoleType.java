package pl.sda.patient_registration_app.type;

public enum RoleType {
    PATIENT("ROLE_PATIENT"),
    MANAGER("ROLE_MANAGER"),
    ADMIN("ROLE_ADMIN");
    private String name;

    RoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
