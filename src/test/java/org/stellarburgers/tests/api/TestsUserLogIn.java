package org.stellarburgers.tests.api;

import org.stellarburgers.tests.support.ApiMethods;
import org.stellarburgers.tests.support.SupportForTestsBase;
import org.stellarburgers.tests.support.TestsBase;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestsUserLogIn extends TestsBase
{
    @Before
    public void setUp()
    {
        SupportForTestsBase supportForTestsBase = new SupportForTestsBase();

        setUserForLogIn(supportForTestsBase.createNewUserForLogIn("test1-data@yandex.ru", "password"));
        setUserForLogInWithInvalidEmail(supportForTestsBase.createNewUserForLogIn("invalid email", "password"));
        setUserForLogInWithInvalidPassword( supportForTestsBase.createNewUserForLogIn("test1-data@yandex.ru", "invalid password"));
        setUserForLogInWithInvalidEmailAndPassword(supportForTestsBase.createNewUserForLogIn("invalid email", "invalid password"));
    }
    @Test
    @DisplayName("Авторизация пользователя")
    public void logInUserShouldLogInUser()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 200;
        int actualStatusCode;
        boolean expectedResponse = true;
        boolean actualResponse;

        //Act
        ValidatableResponse response = methods.logInUser(getUserForLogIn(), "api/auth/login");
        actualResponse = response.extract().path("success");
        actualStatusCode = response.extract().statusCode();

        //Assert
        assertThat("Статус код должен быть  200", actualStatusCode, equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться true",actualResponse, equalTo( expectedResponse));
    }

    @Test
    @DisplayName("Авторизация пользователя с невалидными данными")
    public void logInUserWithInvalidEmailAndPasswordShouldBeFailed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 401;
        int actualStatusCode;
        String expectedMessage = "email or password are incorrect";
        String actualMessage;

        //Act
        ValidatableResponse response = methods.logInUser(getUserForLogInWithInvalidEmailAndPassword(), "api/auth/login");
        actualStatusCode = response.extract().statusCode();
        actualMessage = response.extract().path("message");

        //Assert
        assertThat("Статус код должен быть  401", actualStatusCode, equalTo(expectedStatusCode));
        assertThat("В заголовке message должно содержаться email or password are incorrect",actualMessage, equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Авторизация пользователя с невалидным логином")
    public void logInUserWithInvalidPasswordShouldBeFailed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 401;
        int actualStatusCode;
        String expectedMessage = "email or password are incorrect";
        String actualMessage;

        //Act
        ValidatableResponse response = methods.logInUser(getUserForLogInWithInvalidPassword(), "api/auth/login");
        actualStatusCode = response.extract().statusCode();
        actualMessage = response.extract().path("message");


        //Assert
        assertThat("Статус код должен быть  200", actualStatusCode, equalTo(expectedStatusCode));
        assertThat("В заголовке message должно содержаться email or password are incorrect",actualMessage, equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Авторизация пользователя с невалидным паролем")
    public void logInUserWithInvalidEmailShouldBeFailed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 401;
        int actualStatusCode;
        String expectedMessage = "email or password are incorrect";
        String actualMessage;

        //Act
        ValidatableResponse response = methods.logInUser(getUserForLogInWithInvalidEmail(), "api/auth/login");
        actualStatusCode = response.extract().statusCode();
        actualMessage = response.extract().path("message");

        //Assert
        assertThat("Статус код должен быть  401", actualStatusCode, equalTo(expectedStatusCode));
        assertThat("В заголовке message должно содержаться email or password are incorrect",actualMessage, equalTo(expectedMessage));
    }
}
