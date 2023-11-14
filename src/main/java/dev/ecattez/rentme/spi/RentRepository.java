package dev.ecattez.rentme.spi;

import dev.ecattez.rentme.model.CarId;
import dev.ecattez.rentme.model.Rent;

import java.time.LocalDate;

public interface RentRepository {

    void save(Rent rent);

    boolean isRentedAt(CarId carId, LocalDate rentedAt);

}
