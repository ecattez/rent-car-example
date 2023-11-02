package dev.ecattez.rentme.usecase;

import dev.ecattez.rentme.UseCase;
import dev.ecattez.rentme.model.Car;
import dev.ecattez.rentme.spi.CarRepository;
import dev.ecattez.rentme.spi.RentEventBus;

import java.time.Clock;

public class RentCarAPI implements UseCase<RentCar> {

    private final CarRepository carRepository;
    private final RentEventBus eventBus;
    private final Clock clock;

    public RentCarAPI(CarRepository carRepository, RentEventBus eventBus, Clock clock) {
        this.carRepository = carRepository;
        this.eventBus = eventBus;
        this.clock = clock;
    }

    @Override
    public void accept(RentCar command) {
        Car car = carRepository.forId(command.carId());
        CarRented carRented = car.rent(command.requestedBy(), clock);

        carRepository.save(car);
        eventBus.publish(carRented);
    }

}
