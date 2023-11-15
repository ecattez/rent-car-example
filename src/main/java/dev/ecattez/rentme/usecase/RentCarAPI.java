package dev.ecattez.rentme.usecase;

import dev.ecattez.rentme.UseCase;
import dev.ecattez.rentme.model.CarId;
import dev.ecattez.rentme.model.CustomerId;
import dev.ecattez.rentme.model.Rent;
import dev.ecattez.rentme.spi.RentEventBus;
import dev.ecattez.rentme.spi.RentRepository;

import java.time.Clock;
import java.time.LocalDate;

public class RentCarAPI implements UseCase<RentCar> {

    private final RentIdGenerator rentIdGenerator;
    private final RentRepository rentRepository;
    private final RentEventBus eventBus;
    private final Clock clock;

    public RentCarAPI(RentIdGenerator rentIdGenerator, RentRepository rentRepository, RentEventBus eventBus, Clock clock) {
        this.rentIdGenerator = rentIdGenerator;
        this.rentRepository = rentRepository;
        this.eventBus = eventBus;
        this.clock = clock;
    }

    @Override
    public void accept(RentCar command) {
        CarId carId = command.carId();
        LocalDate today = LocalDate.now(clock);

        if (rentRepository.isRentedAt(carId, today)) {
            throw new CarAlreadyRent(carId);
        }

        CustomerId requestedBy = command.requestedBy();
        Rent rent = rentCar(today, carId, requestedBy);

        eventBus.publish(CarRented.builder()
                .rentId(rent.id())
                .carId(carId)
                .rentedBy(rent.by())
                .rentedAt(rent.at())
                .rentedUntil(rent.until())
                .build());
    }

    private Rent rentCar(LocalDate today, CarId carId, CustomerId requestedBy) {
        LocalDate rentedUntil = today.plusDays(6);

        Rent rent = Rent.builder()
                .id(rentIdGenerator.generate())
                .carId(carId)
                .by(requestedBy)
                .at(today)
                .until(rentedUntil)
                .build();

        rentRepository.save(rent);
        return rent;
    }

}
