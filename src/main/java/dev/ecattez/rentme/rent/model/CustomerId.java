package dev.ecattez.rentme.rent.model;

public record CustomerId(String value) {

    public static CustomerId of(String value) {
        return new CustomerId(value);
    }

}
