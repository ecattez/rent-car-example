package dev.ecattez.rentme.rent.features;

import com.tngtech.jgiven.integration.spring.EnableJGiven;
import dev.ecattez.rentme.rent.RentEventBus;
import dev.ecattez.rentme.rent.features.stage.StageClock;
import dev.ecattez.rentme.rent.features.stage.StageContext;
import dev.ecattez.rentme.rent.model.CarRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.ZoneId;

import static org.mockito.Mockito.mock;

@EnableJGiven
@TestConfiguration
public class RentTestModule {

    @Bean
    StageContext context() {
        return new StageContext();
    }

    @Bean
    Clock clock(StageContext context) {
        return new StageClock(context, ZoneId.of("UTC"));
    }

    @Bean
    CarRepository carRepository() {
        return mock(CarRepository.class);
    }

    @Bean
    RentEventBus rentEventBus() {
        return mock(RentEventBus.class);
    }

}
