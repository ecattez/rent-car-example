package dev.ecattez.rentme.model;

public class CarNotFound extends RentException {

    public CarNotFound(CarId carId) {
        super("Car '%s' has not been found or does not exist".formatted(carId.value()));
    }
}
