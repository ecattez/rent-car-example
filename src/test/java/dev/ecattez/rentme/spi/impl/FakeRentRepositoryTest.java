package dev.ecattez.rentme.spi.impl;

import dev.ecattez.rentme.model.CarId;
import dev.ecattez.rentme.model.Rent;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class FakeRentRepositoryTest {

    private final static CarId CAR_ID = CarId.of("1234");

    FakeRentRepository rentRepository = new FakeRentRepository();

    @Test
    void save_rent() {
        // given
        Rent rent = Rent.builder()
                .carId(CAR_ID)
                .at(LocalDate.of(2023, 11, 15))
                .until(LocalDate.of(2023, 11, 17))
                .build();

        // when
        rentRepository.save(rent);

        // then
        boolean actualRentedAt = rentRepository.isRentedAt(CAR_ID, LocalDate.of(2023, 11, 16));
        assertThat(actualRentedAt).isTrue();
    }


    @Nested
    class ForTestPurpose {
        @Test
        void clear_repository() {
            // given
            Rent rent = Rent.builder()
                    .carId(CAR_ID)
                    .at(LocalDate.of(2023, 11, 15))
                    .until(LocalDate.of(2023, 11, 17))
                    .build();

            rentRepository.save(rent);

            // when
            rentRepository.clear();

            boolean actualRentedAt = rentRepository.isRentedAt(CAR_ID, LocalDate.of(2023, 11, 16));
            assertThat(actualRentedAt).isFalse();
        }
    }

}