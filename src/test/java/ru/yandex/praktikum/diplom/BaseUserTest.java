package ru.yandex.praktikum.diplom;

import com.github.javafaker.Faker;
import org.junit.Before;
import ru.yandex.praktikum.diplom.steps.UserSteps;

public abstract class BaseUserTest {
    protected final UserSteps userSteps = new UserSteps();
    protected final Faker faker = new Faker();
    protected final String email = faker.internet().emailAddress();
    protected final String password = faker.internet().password();
    protected final String name = faker.name().fullName();

    @Before
    public final void printUserDataBeforeTest() {
        System.out.println("Email is " + email);
        System.out.println("Password is " + password);
        System.out.println("Name is " + name);
    }
}
