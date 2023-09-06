package dev.ecattez.rentme.model;

import dev.ecattez.rentme.rule.rent_car.CarRented;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;

public class Car {

    private final CarId carId;
    private Rent rent;

    private Car(CarId carId) {
        this.carId = carId;
    }

    private Car(CarId carId, Rent rent) {
        this.carId = carId;
        this.rent = rent;
    }

    public CarId id() {
        return carId;
    }

    public boolean rented() {
        return Objects.nonNull(rent);
    }

    public CarRented rent(CustomerId requestedBy, Clock clock) {
        LocalDate today = LocalDate.now(clock);

        if (Objects.nonNull(rent) && rent.endsAfterOr(today)) {
            throw new CarAlreadyRent(carId);
        }

        LocalDate rentedUntil = today.plusDays(6);
        rent = Rent.from(requestedBy, today, rentedUntil);

        return CarRented.builder()
                .carId(carId)
                .rentedBy(rent.by())
                .rentedAt(rent.at())
                .rentedUntil(rent.until())
                .build();
    }

    public static Car from(CarId carId) {
        return new Car(carId);
    }

    public static Car from(CarId carId, Rent rent) {
        return new Car(carId, rent);
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", rent=" + rent +
                '}';
    }
}
