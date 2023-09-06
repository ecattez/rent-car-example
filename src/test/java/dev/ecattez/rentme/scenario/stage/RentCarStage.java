package dev.ecattez.rentme.scenario.stage;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import dev.ecattez.rentme.model.CarId;
import dev.ecattez.rentme.model.Car;
import dev.ecattez.rentme.model.CarAlreadyRent;
import dev.ecattez.rentme.model.CustomerId;
import dev.ecattez.rentme.model.Rent;
import dev.ecattez.rentme.model.RentException;
import dev.ecattez.rentme.rule.rent_car.CarRented;
import dev.ecattez.rentme.rule.rent_car.RentCar;
import dev.ecattez.rentme.rule.rent_car.RentCarUseCase;
import dev.ecattez.rentme.scenario.context.ScenarioClock;
import dev.ecattez.rentme.scenario.context.ScenarioContext;
import dev.ecattez.rentme.spi.CarRepository;
import dev.ecattez.rentme.spi.RentEventBus;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@JGivenStage
public class RentCarStage extends Stage<RentCarStage> {

    @Autowired
    private ScenarioClock clock;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private RentEventBus rentEventBus;
    @Autowired
    private RentCarUseCase rentCarUseCase;

    @ScenarioState
    private final ScenarioContext context = new ScenarioContext();

    public RentCarStage today_is(LocalDate today) {
        clock.fixDateTo(today);
        return self();
    }

    // ---- GIVEN

    public RentCarStage a_car_not_rented_yet() {
        context.carId = CarId.of("0123456789");

        Mockito.when(carRepository.forId(context.carId))
                .thenReturn(Car.from(context.carId));

        return self();
    }


    public RentCarStage a_car_is_rented__until(LocalDate rentedUntil) {
        context.carId = CarId.of("0123456789");

        Rent rent = Rent.from(
                CustomerId.of("anotherCustomer"),
                LocalDate.MIN,
                rentedUntil);

        Mockito.when(carRepository.forId(context.carId))
                .thenReturn(Car.from(context.carId, rent));

        return self();
    }

    // ---- WHEN

    public RentCarStage customer_rent_that_car() {
        context.customerId = CustomerId.of("ecattez");

        RentCar command = RentCar.builder()
                .carId(context.carId)
                .requestedBy(context.customerId)
                .build();

        context.occurredError = catchThrowable(() -> rentCarUseCase.execute(command));

        return self();
    }

    // ---- THEN

    public RentCarStage car_is_rented_from_$_to_$(LocalDate rentedAt, LocalDate rentedUntil) {
        assertThat(context.occurredError).isNull();

        CarRented expectedEvent = CarRented.builder()
                .carId(context.carId)
                .rentedBy(context.customerId)
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

    public RentCarStage car_renting_is_aborted() {
        assertThat(context.occurredError)
                .isInstanceOf(RentException.class)
                .isInstanceOf(CarAlreadyRent.class)
                .hasMessage("Car '0123456789' is already rent at this date");

        verify(carRepository, never()).save(any());
        verifyNoInteractions(rentEventBus);

        return self();
    }

}
