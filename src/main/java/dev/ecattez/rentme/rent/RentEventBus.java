package dev.ecattez.rentme.rent;

import dev.ecattez.rentme.rent.model.RentEvent;

public interface RentEventBus {

    void publish(RentEvent event);

}
