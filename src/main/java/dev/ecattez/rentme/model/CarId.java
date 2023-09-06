package dev.ecattez.rentme.model;

public record CarId(String value) {

    public static CarId of(String value) {
        return new CarId(value);
    }

}
