import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestsUserLogIn
{
    @Test
    @DisplayName("Авторизация пользователя")
    public void logInUserShouldLogInUser()
    {
        //Arrange
        UserWithEmailAndPassword user = new UserWithEmailAndPassword("test-data@yandex.ru","password");
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 200;
        boolean expectedResponse = true;

        //Act
        ValidatableResponse response = methods.logInUser(user);

        //Assert
        assertThat("Статус код должен быть  200", response.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться true",response.extract().path("success"), equalTo( expectedResponse));
    }

    @Test
    @DisplayName("Авторизация пользователя с невалидными данными")
    public void logInUserWithInvalidLoginAndPasswordShouldBeFailed()
    {
        //Arrange
        UserWithEmailAndPassword user = new UserWithEmailAndPassword("incorrectEmail","incorrectPassword");
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 401;
        String expectedMessage = "email or password are incorrect";

        //Act
        ValidatableResponse response = methods.logInUser(user);

        //Assert
        assertThat("Статус код должен быть  200", response.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке message должно содержаться email or password are incorrect",response.extract().path("message"), equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Авторизация пользователя с невалидным логином")
    public void logInUserWithInvalidLoginShouldBeFailed()
    {
        //Arrange
        UserWithEmailAndPassword user = new UserWithEmailAndPassword("test-data@yandex.ru","incorrectPassword");
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 401;
        String expectedMessage = "email or password are incorrect";

        //Act
        ValidatableResponse response = methods.logInUser(user);

        //Assert
        assertThat("Статус код должен быть  200", response.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке message должно содержаться email or password are incorrect",response.extract().path("message"), equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Авторизация пользователя с невалидным паролем")
    public void logInUserWithInvalidPasswordShouldBeFailed()
    {
        //Arrange
        UserWithEmailAndPassword user = new UserWithEmailAndPassword("incorrectEmail","password");
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 401;
        String expectedMessage = "email or password are incorrect";

        //Act
        ValidatableResponse response = methods.logInUser(user);

        //Assert
        assertThat("Статус код должен быть  200", response.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке message должно содержаться email or password are incorrect",response.extract().path("message"), equalTo(expectedMessage));
    }
}
