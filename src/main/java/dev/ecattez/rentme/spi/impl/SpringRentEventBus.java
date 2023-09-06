package dev.ecattez.rentme.spi.impl;

import dev.ecattez.rentme.model.RentEvent;
import dev.ecattez.rentme.spi.RentEventBus;
import org.springframework.context.ApplicationEventPublisher;

public class SpringRentEventBus implements RentEventBus {

    private final ApplicationEventPublisher eventPublisher;

    public SpringRentEventBus(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publish(RentEvent event) {
        eventPublisher.publishEvent(event);
    }
}
