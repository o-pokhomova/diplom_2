package ru.yandex.praktikum.diplom.steps;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.yandex.praktikum.diplom.Endpoints;
import ru.yandex.praktikum.diplom.dto.OrderCreateRequestDto;

import java.util.Arrays;

public class OrderSteps extends BaseSteps {
    public Response create(String... ingredients) {
        return create(prepareRestSpec(), ingredients);
    }

    public Response createAuthorized(String token, String... ingredients) {
        return create(prepareRestSpec(token), ingredients);
    }

    private Response create(RequestSpecification prepareRestSpec, String[] ingredients) {
        return prepareRestSpec
                .body(new OrderCreateRequestDto(Arrays.asList(ingredients)))
                .post(Endpoints.ORDERS);
    }
}
