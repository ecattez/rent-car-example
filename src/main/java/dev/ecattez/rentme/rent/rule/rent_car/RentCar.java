package dev.ecattez.rentme.rent.rule.rent_car;

import dev.ecattez.rentme.rent.model.CarId;
import dev.ecattez.rentme.rent.model.CustomerId;

public record RentCar(CarId carId, CustomerId requestedBy) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private CarId carId;
        private CustomerId customerId;

        public Builder carId(CarId carId) {
            this.carId = carId;
            return this;
        }

        public Builder requestedBy(CustomerId customerId) {
            this.customerId = customerId;
            return this;
        }

        public RentCar build() {
            return new RentCar(carId, customerId);
        }

    }

}
