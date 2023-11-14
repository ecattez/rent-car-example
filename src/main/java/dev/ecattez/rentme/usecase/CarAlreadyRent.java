package dev.ecattez.rentme.usecase;

import dev.ecattez.rentme.model.CarId;
import dev.ecattez.rentme.model.RentException;

public class CarAlreadyRent extends RentException {

    public CarAlreadyRent(CarId carId) {
        super("Car '%s' is already rent at this date".formatted(carId.value()));
    }

}
