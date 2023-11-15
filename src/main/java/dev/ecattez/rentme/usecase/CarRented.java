package dev.ecattez.rentme.usecase;

import dev.ecattez.rentme.model.CarId;
import dev.ecattez.rentme.model.CustomerId;
import dev.ecattez.rentme.model.RentEvent;
import dev.ecattez.rentme.model.RentId;

import java.time.LocalDate;

public record CarRented(RentId rentId, CarId carId, CustomerId rentedBy, LocalDate rentedAt, LocalDate rentedUntil) implements RentEvent {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private RentId rentId;
        private CarId carId;
        private CustomerId rentedBy;
        private LocalDate rentedAt;
        private LocalDate rentedUntil;

        public Builder rentId(RentId rentId) {
            this.rentId = rentId;
            return this;
        }

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
            return new CarRented(rentId, carId, rentedBy, rentedAt, rentedUntil);
        }
    }

}
