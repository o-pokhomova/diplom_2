package ru.yandex.praktikum.diplom.dto;

import lombok.Data;

@Data
public class AuthRegisterResponseDto {
    private Boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;
}
