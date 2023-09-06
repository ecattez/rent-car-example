package dev.ecattez.rentme.scenario.context;

import dev.ecattez.rentme.model.CarId;
import dev.ecattez.rentme.model.CustomerId;

import java.time.LocalDate;

public class ScenarioContext {
    public CarId carId;
    public CustomerId customerId;
    public Throwable occurredError;
}
