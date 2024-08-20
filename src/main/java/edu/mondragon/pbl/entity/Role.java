package edu.mondragon.pbl.entity;

public enum Role {
    USER("User"),
    ADMIN("Admin"),
    BAN("Ban");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
