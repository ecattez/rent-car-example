package dev.ecattez.rentme.rule.rent_car;

import dev.ecattez.rentme.model.CarId;
import dev.ecattez.rentme.model.CustomerId;
import dev.ecattez.rentme.model.RentEvent;

import java.time.LocalDate;

public record CarRented(CarId carId, CustomerId rentedBy, LocalDate rentedAt, LocalDate rentedUntil) implements RentEvent {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private CarId carId;
        private CustomerId rentedBy;
        private LocalDate rentedAt;
        private LocalDate rentedUntil;

        public Builder carId(CarId carId) {
            this.carId = carId;
            return this;
        }

        public Builder rentedBy(CustomerId rentedBy) {
            this.rentedBy = rentedBy;
            return this;
        }

        public Builder rentedAt(LocalDate rentedAt) {
            this.rentedAt = rentedAt;
            return this;
        }

        public Builder rentedUntil(LocalDate rentedUntil) {
            this.rentedUntil = rentedUntil;
            return this;
        }

        public CarRented build() {
            return new CarRented(carId, rentedBy, rentedAt, rentedUntil);
        }
    }

}
