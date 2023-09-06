package dev.ecattez.rentme.model;

public class CarAlreadyRent extends RentException {

    public CarAlreadyRent(CarId carId) {
        super("Car '%s' is already rent at this date".formatted(carId.value()));
    }

}
