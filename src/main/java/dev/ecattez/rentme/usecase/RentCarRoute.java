package dev.ecattez.rentme.usecase;

import dev.ecattez.rentme.UseCase;
import dev.ecattez.rentme.model.CarId;
import dev.ecattez.rentme.model.CustomerId;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class RentCarRoute implements HandlerFunction<ServerResponse> {

    private final UseCase<RentCar> rentCarAPI;

    public RentCarRoute(RentCarAPI rentCarAPI) {
        this.rentCarAPI = rentCarAPI;
    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return request.bodyToMono(RentCarBody.class)
                .map(body -> RentCar.builder()
                        .carId(CarId.of(body.carId))
                        .requestedBy(CustomerId.of(body.customerId))
                        .build())
                .doOnEach(command -> rentCarAPI.accept(command.get()))
                .flatMap(command -> ServerResponse.noContent().build());
    }

    record RentCarBody(String carId, String customerId) {}
}
