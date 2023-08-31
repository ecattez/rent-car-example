package dev.ecattez.rentme.rent.model;

import dev.ecattez.rentme.rent.rule.rent_car.CarRented;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;

public class Car {

    private final CarId carId;
    private CustomerId rentedBy;
    private LocalDate rentedAt;
    private LocalDate rentedUntil;

    private Car(CarId carId) {
        this.carId = carId;
    }

    public boolean rented() {
        return Objects.nonNull(rentedBy);
    }

    public CarRented rent(CustomerId requestedBy, Clock clock) {
        this.rentedBy = requestedBy;
        this.rentedAt = LocalDate.now(clock);
        this.rentedUntil = rentedAt.plusDays(6);

        return CarRented.builder()
                .carId(carId)
                .rentedBy(rentedBy)
                .rentedAt(rentedAt)
                .rentedUntil(rentedUntil)
                .build();
    }

    public static Car withId(CarId carId) {
        return new Car(carId);
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", rentedBy=" + rentedBy +
                ", rentedAt=" + rentedAt +
                ", rentedUntil=" + rentedUntil +
                '}';
    }
}
