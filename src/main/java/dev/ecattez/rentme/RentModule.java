package dev.ecattez.rentme;

import dev.ecattez.rentme.spi.RentEventBus;
import dev.ecattez.rentme.spi.RentRepository;
import dev.ecattez.rentme.spi.impl.FakeRentRepository;
import dev.ecattez.rentme.spi.impl.SpringRentEventBus;
import dev.ecattez.rentme.usecase.DumbRentIdGenerator;
import dev.ecattez.rentme.usecase.RentCarAPI;
import dev.ecattez.rentme.usecase.RentCarRoute;
import dev.ecattez.rentme.usecase.RentIdGenerator;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.time.Clock;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@AutoConfiguration
public class RentModule {

    private static final Logger LOG = LoggerFactory.getLogger(RentModule.class);

    @Bean
    @ConditionalOnMissingBean(RentIdGenerator.class)
    RentIdGenerator rentIdGenerator() {
        return new DumbRentIdGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(RentRepository.class)
    RentRepository carRepository() {
        return new FakeRentRepository();
    }

    @Bean
    @ConditionalOnMissingBean(RentEventBus.class)
    RentEventBus rentEventBus(ApplicationEventPublisher applicationEventPublisher) {
        return new SpringRentEventBus(applicationEventPublisher);
    }

    @Bean
    @ConditionalOnMissingBean(Clock.class)
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    RentCarAPI rentCarAPI(RentIdGenerator rentIdGenerator, RentRepository rentRepository, RentEventBus rentEventBus, Clock clock) {
        return new RentCarAPI(rentIdGenerator, rentRepository, rentEventBus, clock);
    }

    @Configuration
    static class RoutingModule {

        @Bean
        RouterFunction<ServerResponse> rentCarRoute(RentCarAPI rentCarAPI) {
            return route(POST("/rent"), new RentCarRoute(rentCarAPI));
        }

    }

    @PostConstruct
    void init() {
        LOG.info("[RentModule] enabled");
    }

}
