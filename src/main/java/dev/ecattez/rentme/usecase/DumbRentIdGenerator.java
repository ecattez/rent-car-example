package dev.ecattez.rentme.usecase;

import dev.ecattez.rentme.model.RentId;

public class DumbRentIdGenerator implements RentIdGenerator {

    private int counter = 0;

    @Override
    public RentId generate() {
        return RentId.of("RENTID_#" + (++counter));
    }
}
