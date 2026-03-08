package ru.yandex.praktikum.diplom;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Вход пользователя в систему")
public class LoginUserTest extends BaseAuthorizedUserTest {
    @DisplayName("Логин")
    @Description("Успешный вход в систему")
    @Test
    public void login() {
        userSteps.login(email, password)
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("user.email", equalTo(email))
                .body("user.name", equalTo(name));
    }

    @DisplayName("Логин: незарегистрированный логин")
    @Description("Неудачный вход в систему: такого пользователя не существует")
    @Test
    public void loginBadLogin() {
        userSteps.login("bad-login", password)
                .then()
                .statusCode(HttpServletResponse.SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @DisplayName("Логин: некооректный пароль")
    @Description("Неудачный вход в систему: пароль не подходит")
    @Test
    public void loginBadPassword() {
        userSteps.login(email, "bad-password")
                .then()
                .statusCode(HttpServletResponse.SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
