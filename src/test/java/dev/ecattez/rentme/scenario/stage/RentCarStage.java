package dev.ecattez.rentme.scenario.stage;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import dev.ecattez.rentme.UseCase;
import dev.ecattez.rentme.model.*;
import dev.ecattez.rentme.scenario.context.ScenarioClock;
import dev.ecattez.rentme.scenario.context.ScenarioContext;
import dev.ecattez.rentme.spi.RentEventBus;
import dev.ecattez.rentme.spi.impl.FakeRentRepository;
import dev.ecattez.rentme.usecase.CarAlreadyRent;
import dev.ecattez.rentme.usecase.CarRented;
import dev.ecattez.rentme.model.Rent;
import dev.ecattez.rentme.usecase.RentCar;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@JGivenStage
public class RentCarStage extends Stage<RentCarStage> {

    @Autowired
    private FakeRentRepository rentRepository;
    @Autowired
    private RentEventBus rentEventBus;
    @Autowired
    private ScenarioClock clock;
    @Autowired
    private UseCase<RentCar> rentCarAPI;

    @ScenarioState
    private final ScenarioContext context = new ScenarioContext();

    public RentCarStage today_is(LocalDate today) {
        clock.fixDateTo(today);
        return self();
    }

    // ---- GIVEN

    public RentCarStage a_car_not_rented_yet() {
        context.carId = CarId.of("0123456789");

        rentRepository.clear();

        return self();
    }


    public RentCarStage a_car_is_rented_between_$_and_$(LocalDate rentedAt, LocalDate rentedUntil) {
        context.carId = CarId.of("0123456789");

        Rent rent = Rent.builder()
                .carId(context.carId)
                .by(CustomerId.of("anotherCustomer"))
                .at(rentedAt)
                .until(rentedUntil)
                .build();

        rentRepository.initWith(rent);

        return self();
    }

    // ---- WHEN

    public RentCarStage customer_rent_that_car() {
        context.customerId = CustomerId.of("ecattez");

        RentCar command = RentCar.builder()
                .carId(context.carId)
                .requestedBy(context.customerId)
                .build();

        context.occurredError = catchThrowable(() -> rentCarAPI.accept(command));

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

        ArgumentCaptor<Rent> rentCaptor = ArgumentCaptor.forClass(Rent.class);

        InOrder inOrder = inOrder(rentRepository, rentEventBus);
        inOrder.verify(rentRepository).save(rentCaptor.capture());
        inOrder.verify(rentEventBus).publish(expectedEvent);

        Rent expectedRent = Rent.builder()
                .carId(context.carId)
                .by(context.customerId)
                .at(rentedAt)
                .until(rentedUntil)
                .build();

        Rent actualRent = rentCaptor.getValue();
        assertThat(actualRent).isEqualTo(expectedRent);

        return self();
    }

    public RentCarStage car_renting_is_aborted() {
        assertThat(context.occurredError)
                .isInstanceOf(RentException.class)
                .isInstanceOf(CarAlreadyRent.class)
                .hasMessage("Car '0123456789' is already rent at this date");

        verify(rentRepository, never()).save(any());
        verifyNoInteractions(rentEventBus);

        return self();
    }

}
