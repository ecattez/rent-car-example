package dev.ecattez.rentme.scenario;

import com.tngtech.jgiven.integration.spring.EnableJGiven;
import com.tngtech.jgiven.integration.spring.junit5.SimpleSpringScenarioTest;
import dev.ecattez.rentme.scenario.stage.RentCarStage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@EnableJGiven
@SpringBootTest
class RentCarScenarioTest extends SimpleSpringScenarioTest<RentCarStage> {

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

    @ParameterizedTest
    @MethodSource("alreadyRentExamples")
    void car_already_rented_in_time_slots(LocalDate rentedAt, LocalDate rentedUntil, LocalDate today) {
        given().today_is(today)
                .and().a_car_is_rented_between_$_and_$(rentedAt, rentedUntil)
                .when().customer_rent_that_car()
                .then().car_renting_is_aborted();
    }

    public static Stream<Arguments> alreadyRentExamples() {
        return Stream.of(
                arguments(LocalDate.of(2023, 1, 4), LocalDate.of(2023, 1, 7), LocalDate.of(2023, 1, 5)),
                arguments(LocalDate.of(2023, 1, 5), LocalDate.of(2023, 1, 7), LocalDate.of(2023, 1, 5)),
                arguments(LocalDate.of(2023, 1, 4), LocalDate.of(2023, 2, 7), LocalDate.of(2023, 2, 7)),
                arguments(LocalDate.of(2023, 1, 7), LocalDate.of(2023, 2, 7), LocalDate.of(2023, 2, 7))
        );
    }

}
