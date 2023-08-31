package dev.ecattez.rentme.rent.model;

public record CarId(String value) {

    public static CarId of(String value) {
        return new CarId(value);
    }

}
