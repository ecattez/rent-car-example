package dev.ecattez.rentme.spi;

import dev.ecattez.rentme.model.RentEvent;

public interface RentEventBus {

    void publish(RentEvent event);

}
