package dev.ecattez.rentme.rent.model;

import dev.ecattez.rentme.rent.model.Car;
import dev.ecattez.rentme.rent.model.CarId;

public interface CarRepository {

    Car forId(CarId carId);

    void save(Car car);

}
