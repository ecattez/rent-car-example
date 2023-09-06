package dev.ecattez.rentme.spi.impl;

import dev.ecattez.rentme.model.RentEvent;
import dev.ecattez.rentme.spi.RentEventBus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {SpringRentEventBus.class, SpringRentEventBusTest.EventSink.class})
class SpringRentEventBusTest {

    @Autowired
    RentEventBus rentEventBus;
    @Autowired
    EventSink eventSink;

    @Test
    void is_created() {
        assertThat(rentEventBus).isInstanceOf(SpringRentEventBus.class);
    }

    @Test
    void publish_event() {
        FakeRentEvent event = new FakeRentEvent();

        rentEventBus.publish(event);

        assertThat(eventSink.occurredEvents).containsExactly(event);
    }

    record FakeRentEvent() implements RentEvent {}

    static final class EventSink {

        private final List<RentEvent> occurredEvents = new ArrayList<>();

        @EventListener
        void handle(RentEvent rentEvent) {
            occurredEvents.add(rentEvent);
        }

    }
}