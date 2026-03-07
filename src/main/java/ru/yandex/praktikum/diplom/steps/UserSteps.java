package ru.yandex.praktikum.diplom.steps;

import io.restassured.response.Response;
import ru.yandex.praktikum.diplom.Endpoints;
import ru.yandex.praktikum.diplom.dto.AuthLoginRequestDto;
import ru.yandex.praktikum.diplom.dto.AuthRegisterRequestDto;
import ru.yandex.praktikum.diplom.dto.AuthRegisterResponseDto;

import javax.servlet.http.HttpServletResponse;

public class UserSteps extends BaseSteps {
    public Response register(String email, String password, String name) {
        AuthRegisterRequestDto requestBody = new AuthRegisterRequestDto(
                email,
                password,
                name
        );
        return prepareRestSpec()
                .and().body(requestBody)
                .when()
                .post(Endpoints.AUTH_REGISTER);
    }

    public Response login(String email, String password) {
        AuthLoginRequestDto requestBody = new AuthLoginRequestDto(
                email,
                password
        );
        return prepareRestSpec()
                .and().body(requestBody)
                .when()
                .post(Endpoints.AUTH_LOGIN);
    }

    public Response delete(String token) {
        return prepareRestSpec(token).when().delete(Endpoints.AUTH_USER);
    }

    public void delete(String email, String password) {
        Response response = login(email, password);
        if (response.statusCode() == HttpServletResponse.SC_OK) {
            String accessToken = response
                    .body().as(AuthRegisterResponseDto.class)
                    .getAccessToken();
            delete(accessToken)
                    .then()
                    .statusCode(HttpServletResponse.SC_ACCEPTED);
        } else if (response.statusCode() == HttpServletResponse.SC_UNAUTHORIZED) {
            System.out.println("Пользователя не существует");
        } else {
            throw new IllegalStateException("Неожиданный статус-код " + response.statusCode() + " при получении токена.");
        }
    }
}
