package dev.ecattez.rentme.rent;

import dev.ecattez.rentme.rent.model.CarRepository;
import dev.ecattez.rentme.rent.rule.rent_car.RentCarUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class RentModule {

    @Bean
    RentCarUseCase rentCarUseCase(CarRepository carRepository, RentEventBus rentEventBus, Clock clock) {
        return new RentCarUseCase(carRepository, rentEventBus, clock);
    }

}
