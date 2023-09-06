package dev.ecattez.rentme.model;

public class RentException extends RuntimeException {

    public RentException(String reason) {
        super(reason);
    }
}
