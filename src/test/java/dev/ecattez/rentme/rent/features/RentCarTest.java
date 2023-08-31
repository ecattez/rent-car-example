package dev.ecattez.rentme.rent.features;

import com.tngtech.jgiven.integration.spring.junit5.SimpleSpringScenarioTest;
import dev.ecattez.rentme.rent.features.stage.RentCarStage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
@Import(RentTestModule.class)
class RentCarTest extends SimpleSpringScenarioTest<RentCarStage> {

    @ParameterizedTest
    @MethodSource("rentExamples")
    void rent_a_car(LocalDate today, LocalDate rentedUntil) {
        given().today_is(today)
                .and().a_car_not_rented_yet()
                .when().customer_rent_that_car()
                .then().car_is_rented_from_$_to_$(today, rentedUntil);
    }

    public static Stream<Arguments> rentExamples() {
        return Stream.of(
                arguments(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 7)),
                arguments(LocalDate.of(2023, 1, 31), LocalDate.of(2023, 2, 6))
        );
    }
}
