package dev.ecattez.rentme.spi;

import dev.ecattez.rentme.model.Car;
import dev.ecattez.rentme.model.CarId;

public interface CarRepository {

    Car forId(CarId carId);

    void save(Car car);

}
