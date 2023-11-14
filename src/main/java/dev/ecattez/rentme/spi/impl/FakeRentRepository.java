package dev.ecattez.rentme.spi.impl;

import dev.ecattez.rentme.model.CarId;
import dev.ecattez.rentme.model.Rent;
import dev.ecattez.rentme.spi.RentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class FakeRentRepository implements RentRepository {

    private static final Function<CarId, List<Rent>> INIT_TO_EMPTY_LIST = (key) -> new ArrayList<>();
    private final Map<CarId, List<Rent>> rentsPerCarId = new HashMap<>();

    @Override
    public void save(Rent rent) {
        this.rentsPerCarId.computeIfAbsent(rent.carId(), INIT_TO_EMPTY_LIST)
                .add(rent);
    }

    @Override
    public boolean isRentedAt(CarId carId, LocalDate rentedAt) {
        return rentsPerCarId.getOrDefault(carId, List.of()).stream()
                .anyMatch(rent -> rent.contains(rentedAt));
    }

    public void initWith(Rent rent) {
        clear();
        this.rentsPerCarId.put(rent.carId(), List.of(rent));
    }

    public void clear() {
        rentsPerCarId.clear();
    }
}
