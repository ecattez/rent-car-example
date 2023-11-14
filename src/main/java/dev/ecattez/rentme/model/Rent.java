package dev.ecattez.rentme.model;

import java.time.LocalDate;

public record Rent(CarId carId, CustomerId by, LocalDate at, LocalDate until) {

    public boolean contains(LocalDate today) {
        return (at.isBefore(today) || at.isEqual(today)) && (until.isAfter(today) || until.isEqual(today));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private CarId carId;
        private CustomerId by;
        private LocalDate at;
        private LocalDate until;

        public Builder carId(CarId carId) {
            this.carId = carId;
            return this;
        }

        public Builder by(CustomerId by) {
            this.by = by;
            return this;
        }

        public Builder at(LocalDate at) {
            this.at = at;
            return this;
        }

        public Builder until(LocalDate until) {
            this.until = until;
            return this;
        }

        public Rent build() {
            return new Rent(carId, by, at, until);
        }

    }

}
