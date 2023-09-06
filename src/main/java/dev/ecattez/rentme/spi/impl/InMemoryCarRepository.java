package dev.ecattez.rentme.spi.impl;

import dev.ecattez.rentme.model.Car;
import dev.ecattez.rentme.model.CarId;
import dev.ecattez.rentme.model.CarNotFound;
import dev.ecattez.rentme.spi.CarRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCarRepository implements CarRepository {

    private final Map<CarId, Car> carPerId = new HashMap<>();

    @Override
    public Car forId(CarId carId) {
        return Optional.ofNullable(carPerId.get(carId))
                .orElseThrow(() -> new CarNotFound(carId));
    }

    @Override
    public void save(Car car) {
        carPerId.put(car.id(), car);
    }
}
