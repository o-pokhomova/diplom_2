package ru.yandex.praktikum.diplom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRegisterRequestDto {
    private String email;
    private String password;
    private String name;
}
