package ru.yandex.praktikum.diplom.steps;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseSteps {
    protected RequestSpecification prepareRestSpec() {
        RestAssured.baseURI = "https://stellarburgers.education-services.ru";
        return given()
                .header("Content-type", "application/json");
    }

    protected RequestSpecification prepareRestSpec(String token) {
        return prepareRestSpec()
                .header("Authorization", token);
    }
}
