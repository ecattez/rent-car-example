package dev.ecattez.rentme.scenario.config;

import dev.ecattez.rentme.model.RentId;
import dev.ecattez.rentme.scenario.context.ScenarioClock;
import dev.ecattez.rentme.spi.RentEventBus;
import dev.ecattez.rentme.spi.RentRepository;
import dev.ecattez.rentme.usecase.RentIdGenerator;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

import static dev.ecattez.rentme.fixtures.RentFixtures.RENT_ID;

@Configuration
public class RentTestModule {

    private static final Logger LOG = LoggerFactory.getLogger(RentTestModule.class);

    @MockBean
    RentEventBus rentEventBus;
    @SpyBean
    RentRepository rentRepository;

    @Bean
    RentIdGenerator rentIdGenerator() {
        return () -> RENT_ID;
    }

    @Bean
    Clock clock() {
        return new ScenarioClock();
    }

    @PostConstruct
    void init() {
        LOG.info("[RentTestModule] enabled");
    }

}
