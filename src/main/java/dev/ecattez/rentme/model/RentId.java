package dev.ecattez.rentme.model;

public record RentId(String value) {

    public static RentId of(String value) {
        return new RentId(value);
    }
}
