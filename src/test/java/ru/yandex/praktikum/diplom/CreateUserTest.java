package ru.yandex.praktikum.diplom;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Регистрация пользователя")
public class CreateUserTest extends BaseUserTest {

    @After
    public void tearDown() {
        // Тут убираем за собой
        userSteps.delete(email, password);
    }

    @DisplayName("Регистрация пользователя")
    @Description("Создание пользователя в системе")
    @Test
    public void createUser() {
        Response response = userSteps.register(email, password, name);

        System.out.println(response.body().prettyPrint());
        response
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("user.email", equalTo(email))
                .body("user.name", equalTo(name));
    }

    @DisplayName("Регистрация пользователя: логин занят")
    @Description("Создание пользователя в системе. Такой логин уже используется.")
    @Test
    public void createUserWithExistingLogin() {
        userSteps.register(email, password, name);

        Response response = userSteps.register(email, faker.internet().password(), name);

        System.out.println(response.body().prettyPrint());
        response
                .then()
                .statusCode(HttpServletResponse.SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @DisplayName("Регистрация пользователя без логина")
    @Description("Создание пользователя в системе. Логин не задан")
    @Test
    public void createUserNoEmail() {
        Response response = userSteps.register(null, password, name);

        System.out.println(response.body().prettyPrint());
        response
                .then()
                .statusCode(HttpServletResponse.SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @DisplayName("Регистрация пользователя без пароля")
    @Description("Создание пользователя в системе. Пароль не задан")
    @Test
    public void createUserNoPassword() {
        Response response = userSteps.register(email, null, name);

        System.out.println(response.body().prettyPrint());
        response
                .then()
                .statusCode(HttpServletResponse.SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @DisplayName("Регистрация пользователя без имени")
    @Description("Создание пользователя в системе. Имя не задано")
    @Test
    public void createUserNoName() {
        Response response = userSteps.register(email, password, null);

        System.out.println(response.body().prettyPrint());
        response
                .then()
                .statusCode(HttpServletResponse.SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
