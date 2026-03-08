package ru.yandex.praktikum.diplom.dto;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private Long number;
    private Integer price;
    private List<Ingredient> ingredients;
}
