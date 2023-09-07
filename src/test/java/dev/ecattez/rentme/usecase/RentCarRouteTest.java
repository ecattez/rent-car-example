package dev.ecattez.rentme.usecase;

import dev.ecattez.rentme.model.CarId;
import dev.ecattez.rentme.model.CustomerId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureWebTestClient
class RentCarRouteTest {

    @Autowired
    WebTestClient webTestClient;
    @MockBean
    RentCarAPI rentCarAPI;

    @Test
    void delegate_to_application_layer() {
        WebTestClient.ResponseSpec actualResponse = webTestClient.post()
                .uri("/rent")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        """
                                {
                                    "customerId": "ecattez",
                                    "carId": "12345"
                                }
                                """
                ))
                .exchange();

        actualResponse.expectStatus().isNoContent();

        verify(rentCarAPI).accept(RentCar.builder()
                .carId(CarId.of("12345"))
                .requestedBy(CustomerId.of("ecattez"))
                .build());
    }
}