package dev.ecattez.rentme.rent.features.stage;

import dev.ecattez.rentme.rent.model.CarId;
import dev.ecattez.rentme.rent.model.CustomerId;

import java.time.LocalDate;

public class StageContext {

    private CarId carId;
    private CustomerId customerId;
    private LocalDate today;

    public CarId carId(CarId carId) {
        this.carId = carId;
        return carId;
    }

    public CarId carId() {
        return carId;
    }

    public CustomerId customerId(CustomerId customerId) {
        this.customerId = customerId;
        return customerId;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public LocalDate today(LocalDate today) {
        this.today = today;
        return today;
    }

    public LocalDate today() {
        return today;
    }
}
