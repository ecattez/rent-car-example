package dev.ecattez.rentme.model;

import java.time.LocalDate;

public record Rent(CustomerId by, LocalDate at, LocalDate until) {

    public static Rent from(CustomerId by, LocalDate at, LocalDate until) {
        return new Rent(by, at, until);
    }

    public boolean endsAfterOr(LocalDate today) {
        return until.isAfter(today) || until.isEqual(today);
    }
}
