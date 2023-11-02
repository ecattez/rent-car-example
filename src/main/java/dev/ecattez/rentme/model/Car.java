package dev.ecattez.rentme.model;

import dev.ecattez.rentme.usecase.CarRented;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Car {

    private final CarId carId;
    private final Collection<Rent> rents;

    private Car(CarId carId, Collection<Rent> rents) {
        this.carId = carId;
        this.rents = new ArrayList<>(rents);
    }

    private Car(CarId carId) {
        this(carId, List.of());
    }

    public CarId id() {
        return carId;
    }

    public Collection<Rent> rents() {
        return List.copyOf(rents);
    }

    private boolean alreadyRentedAt(LocalDate today) {
        return rents.stream().anyMatch(rent -> rent.contains(today));
    }

    public CarRented rent(CustomerId requestedBy, Clock clock) {
        LocalDate today = LocalDate.now(clock);

        if (alreadyRentedAt(today)) {
            throw new CarAlreadyRent(carId);
        }

        LocalDate rentedUntil = today.plusDays(6);
        Rent rent = Rent.from(requestedBy, today, rentedUntil);
        rents.add(rent);

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

    public static Car from(CarId carId, Collection<Rent> rents) {
        return new Car(carId, rents);
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", rents=" + rents +
                '}';
    }
}
