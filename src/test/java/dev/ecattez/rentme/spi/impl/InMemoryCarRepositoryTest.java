package dev.ecattez.rentme.spi.impl;

import dev.ecattez.rentme.model.Car;
import dev.ecattez.rentme.model.CarId;
import dev.ecattez.rentme.model.CarNotFound;
import dev.ecattez.rentme.spi.CarRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class InMemoryCarRepositoryTest {

    private final static CarId CAR_ID = CarId.of("1234");

    CarRepository carRepository = new InMemoryCarRepository();

    @Test
    void car_not_found() {
        assertThatExceptionOfType(CarNotFound.class)
                .isThrownBy(() -> carRepository.forId(CAR_ID))
                .withMessage("Car '1234' has not been found or does not exist");
    }

    @Test
    void save_car() {
        Car carToSave = Car.from(CAR_ID);

        carRepository.save(carToSave);

        Car actualCar = carRepository.forId(CAR_ID);
        assertThat(actualCar.id()).isEqualTo(actualCar.id());
    }
}