package ru.yandex.praktikum.diplom;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.praktikum.diplom.dto.OrderCreateResponseDto;
import ru.yandex.praktikum.diplom.steps.OrderSteps;

import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.Matchers.*;

@DisplayName("Создание заказа зарегистрированным пользователем")
public class OrderCreateTest extends BaseAuthorizedUserTest {
    private final OrderSteps orderSteps = new OrderSteps();

    @DisplayName("Успешное создание заказа")
    @Description("Создаём заказ с двумя ингредиентами")
    @Test
    public void create() {
        Response response = orderSteps.createAuthorized(
                getAccessToken(),
                Ingredient.FLUORESCENT_BUN,
                Ingredient.MEAT_OF_THE_IMMORTAL_MOLLUSKS_PROTOSTOMIA
        );
        System.out.println(response.body().prettyPrint());
        OrderCreateResponseDto responseDto = response.body().as(OrderCreateResponseDto.class);
        response
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .body("success", equalTo(true))
                .body("name", notNullValue())

                // Проверки order
                .body("order._id", notNullValue())
                .body("order.status", notNullValue())
                .body("order.name", equalTo(responseDto.getName()))
                .body("order.number", notNullValue())
                .body("order.number", lessThanOrEqualTo(Integer.MAX_VALUE))
                .body("order.price", notNullValue())
                .body("order.price", equalTo(2325))

                // Даты
                .body("order.createdAt", notNullValue())
                .body("order.updatedAt", notNullValue())

                // Владелец
                .body("order.owner.name", notNullValue())
                .body("order.owner.name", equalTo(name))
                .body("order.owner.email", notNullValue())
                .body("order.owner.email", equalTo(email))
                .body("order.owner.createdAt", notNullValue())
                .body("order.owner.updatedAt", notNullValue())


                // Количество ингредиентов
                .body("order.ingredients.size()", is(2))

                // Первый ингредиент (булка)
                .body("order.ingredients[0]._id", is(Ingredient.FLUORESCENT_BUN))
                .body("order.ingredients[0].name", is("Флюоресцентная булка R2-D3"))
                .body("order.ingredients[0].type", is("bun"))
                .body("order.ingredients[0].proteins", is(44))
                .body("order.ingredients[0].fat", is(26))
                .body("order.ingredients[0].carbohydrates", is(85))
                .body("order.ingredients[0].calories", is(643))
                .body("order.ingredients[0].price", is(988))
                .body("order.ingredients[0].image", containsString("bun-01.png"))
                .body("order.ingredients[0].image_mobile", containsString("bun-01-mobile.png"))
                .body("order.ingredients[0].image_large", containsString("bun-01-large.png"))

                // Второй ингредиент (мясо)
                .body("order.ingredients[1]._id", is(Ingredient.MEAT_OF_THE_IMMORTAL_MOLLUSKS_PROTOSTOMIA))
                .body("order.ingredients[1].name", is("Мясо бессмертных моллюсков Protostomia"))
                .body("order.ingredients[1].type", is("main"))
                .body("order.ingredients[1].proteins", is(433))
                .body("order.ingredients[1].fat", is(244))
                .body("order.ingredients[1].carbohydrates", is(33))
                .body("order.ingredients[1].calories", is(420))
                .body("order.ingredients[1].price", is(1337))
                .body("order.ingredients[1].image", containsString("meat-02.png"));
    }

    @DisplayName("Неудачное создание заказа: нет ингредиентов")
    @Description("Создаём заказ без ингредиентов")
    @Test
    public void createWithoutIngredients() {
        Response response = orderSteps.createAuthorized(getAccessToken());
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

