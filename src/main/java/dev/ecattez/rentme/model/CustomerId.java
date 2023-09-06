package dev.ecattez.rentme.model;

public record CustomerId(String value) {

    public static CustomerId of(String value) {
        return new CustomerId(value);
    }

}
