package dev.ecattez.rentme.usecase;

import dev.ecattez.rentme.model.RentId;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DumbRentIdGeneratorTest {

    RentIdGenerator rentIdGenerator = new DumbRentIdGenerator();

    @ParameterizedTest
    @MethodSource("numberOfGenerations")
    void generate_rent_id_number_X(int numberOfGenerations, String expectedValue) {
        RentId actualRentId = null;
        for (int i=1; i <= numberOfGenerations; i++) {
            actualRentId = rentIdGenerator.generate();
        }

        RentId expectedRentId = RentId.of(expectedValue);
        assertThat(actualRentId).isEqualTo(expectedRentId);
    }

    static Stream<Arguments> numberOfGenerations() {
        return Stream.of(
                arguments(1, "RENTID_#1"),
                arguments(3, "RENTID_#3"),
                arguments(12, "RENTID_#12"));
    }
}