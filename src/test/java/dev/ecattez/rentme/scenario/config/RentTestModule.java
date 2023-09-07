package dev.ecattez.rentme.scenario.config;

import dev.ecattez.rentme.scenario.context.ScenarioClock;
import dev.ecattez.rentme.spi.CarRepository;
import dev.ecattez.rentme.spi.RentEventBus;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class RentTestModule {

    private static final Logger LOG = LoggerFactory.getLogger(RentTestModule.class);

    @MockBean
    CarRepository carRepository;
    @MockBean
    RentEventBus rentEventBus;
    @Bean
    Clock clock() {
        return new ScenarioClock();
    }

    @PostConstruct
    void init() {
        LOG.info("[RentTestModule] enabled");
    }

}
