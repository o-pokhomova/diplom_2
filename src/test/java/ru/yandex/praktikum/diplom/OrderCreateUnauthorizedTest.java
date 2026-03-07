package ru.yandex.praktikum.diplom;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.praktikum.diplom.steps.OrderSteps;

import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.Matchers.*;

@DisplayName("Создание заказа не зарегистрированным пользователем")
public class OrderCreateUnauthorizedTest {
    private final OrderSteps orderSteps = new OrderSteps();

    @DisplayName("Успешное создание заказа")
    @Description("Создаём заказ с двумя ингредиентами")
    @Test
    public void createUnauthorized() {
        Response response = orderSteps.create(
                Ingredient.FLUORESCENT_BUN,
                Ingredient.MEAT_OF_THE_IMMORTAL_MOLLUSKS_PROTOSTOMIA
        );
        System.out.println(response.body().prettyPrint());
        response
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order.number", notNullValue())
                .body("order.number", lessThanOrEqualTo(Integer.MAX_VALUE));
    }

    @DisplayName("Неудачное создание заказа: нет ингредиентов")
    @Description("Создаём заказ без ингредиентов")
    @Test
    public void createWithoutIngredients() {
        Response response = orderSteps.create();
        System.out.println(response.body().prettyPrint());
        response
                .then()
                .statusCode(HttpServletResponse.SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @DisplayName("Неудачное создание заказа: некорректный индентификатор ингредиента")
    @Description("Создаём заказ с некорректным идентификатором ингредиента")
    @Test
    public void createWithWrongIngredient() {
        Response response = orderSteps.create(
                Ingredient.FLUORESCENT_BUN,
                "Some wrong hash"
        );
        System.out.println(response.body().prettyPrint());
        response
                .then()
                .statusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}

