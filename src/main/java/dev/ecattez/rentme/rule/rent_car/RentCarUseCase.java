package dev.ecattez.rentme.rule.rent_car;

import dev.ecattez.rentme.model.Car;
import dev.ecattez.rentme.spi.CarRepository;
import dev.ecattez.rentme.spi.RentEventBus;

import java.time.Clock;

public class RentCarUseCase {

    private final CarRepository carRepository;
    private final RentEventBus eventBus;
    private final Clock clock;

    public RentCarUseCase(CarRepository carRepository, RentEventBus eventBus, Clock clock) {
        this.carRepository = carRepository;
        this.eventBus = eventBus;
        this.clock = clock;
    }

    public void execute(RentCar command) {
        Car car = carRepository.forId(command.carId());
        CarRented carRented = car.rent(command.requestedBy(), clock);

        carRepository.save(car);
        eventBus.publish(carRented);
    }

}
