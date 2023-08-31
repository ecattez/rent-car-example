package dev.ecattez.rentme.rent.features.stage;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import dev.ecattez.rentme.rent.model.Car;
import dev.ecattez.rentme.rent.model.CarId;
import dev.ecattez.rentme.rent.rule.rent_car.CarRented;
import dev.ecattez.rentme.rent.model.CarRepository;
import dev.ecattez.rentme.rent.model.CustomerId;
import dev.ecattez.rentme.rent.rule.rent_car.RentCar;
import dev.ecattez.rentme.rent.rule.rent_car.RentCarUseCase;
import dev.ecattez.rentme.rent.RentEventBus;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;

@JGivenStage
public class RentCarStage extends Stage<RentCarStage> {

    @Autowired
    private StageContext context;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private RentEventBus rentEventBus;
    @Autowired
    private RentCarUseCase rentCarUseCase;

    public RentCarStage today_is(LocalDate today) {
        context.today(today);
        return self();
    }

    public RentCarStage a_car_not_rented_yet() {
        context.carId(CarId.of("0123456789"));

        Mockito.when(carRepository.forId(context.carId()))
                .thenReturn(Car.withId(context.carId()));

        return self();
    }

    public RentCarStage customer_rent_that_car() {
        context.customerId(CustomerId.of("ecattez"));

        RentCar command = RentCar.builder()
                .carId(context.carId())
                .requestedBy(context.customerId())
                .build();

        rentCarUseCase.execute(command);

        return self();
    }

    public RentCarStage car_is_rented_from_$_to_$(LocalDate rentedAt, LocalDate rentedUntil) {
        CarRented expectedEvent = CarRented.builder()
                .carId(context.carId())
                .rentedBy(context.customerId())
                .rentedAt(rentedAt)
                .rentedUntil(rentedUntil)
                .build();

        ArgumentCaptor<Car> carCaptor = ArgumentCaptor.forClass(Car.class);

        InOrder inOrder = inOrder(carRepository, rentEventBus);
        inOrder.verify(carRepository).save(carCaptor.capture());
        inOrder.verify(rentEventBus).publish(expectedEvent);

        Car actualCar = carCaptor.getValue();
        assertThat(actualCar.rented()).isTrue();

        return self();
    }

}
