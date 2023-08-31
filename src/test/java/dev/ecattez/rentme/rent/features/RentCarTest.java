package dev.ecattez.rentme.rent.features;

import com.tngtech.jgiven.integration.spring.junit5.SimpleSpringScenarioTest;
import dev.ecattez.rentme.rent.features.stage.RentCarStage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

@SpringBootTest
@Import(RentTestModule.class)
class RentCarTest extends SimpleSpringScenarioTest<RentCarStage> {

    @Test
    void rent_a_car() {
        given().today_is(LocalDate.of(2023, 1, 1))
                .and().a_car_not_rented_yet()
                .when().customer_rent_that_car()
                .then().car_is_rented_from_$_to_$(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 7));
    }
}
