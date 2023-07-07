package TestsApi;

import TestsSupport.ApiMethods;
import TestsSupport.SupportForTestsBase;
import TestsSupport.TestsBase;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestsUserRegistration extends TestsBase
{
    @Before
    public void setUp()
    {
        SupportForTestsBase supportForTestsBase = new SupportForTestsBase();

        setUserForLogIn(supportForTestsBase.createNewUserForLogIn("test1-data@yandex.ru", "password"));
        setUserForRegistration(supportForTestsBase.createNewUser("HopHip01@mail.ru","password", "name"));
        setAlreadyExistingUserForRegistration(supportForTestsBase.createNewUserForLogIn("test1-data@yandex.ru","password" ));
        setUserWithNameAndPassword(supportForTestsBase.createNewUserWithNameAndPassword("name", "password"));
        setUserWithNameAndEmail(supportForTestsBase.createNewUserWithNameAndEmail("name", "test1-data@yandex.ru"));
        setUserWIthTokenByRegistration( supportForTestsBase.getTokenByRegistration("HopHip02@mail.ru", "password", "name"));
    }
    @Test
    @DisplayName("Удаление созданного пользователя")
    public void deleteUserShouldDeleteUser()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 200;
        int actualStatusCode;
        boolean expectedResponse = true;
        boolean actualResponse;

        //Act
        ValidatableResponse response = methods.deleteUser(getUserWIthTokenByRegistration(), "api/auth/user");
        actualResponse = response.extract().path("success");
        actualStatusCode = response.extract().statusCode();

        //Assert
        assertThat(response.extract().path("message"), actualStatusCode, equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться true", actualResponse, equalTo( expectedResponse));
    }

    @Test
    @DisplayName("Создание нового пользователя")
    public void registerNewUserIsPossible()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 200;
        int actualStatusCode;
        boolean expectedResponse = true;
        boolean actualResponse;

        //Act
        ValidatableResponse response = methods.registerNewUser(getUserForRegistration(), "api/auth/register");
        actualResponse = response.extract().path("success");
        actualStatusCode = response.extract().statusCode();

        //Assert
        assertThat("Статус код должен быть  200", actualStatusCode, equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться true", actualResponse, equalTo( expectedResponse));
    }
    @After
    public void clearData()
    {
        ApiMethods methods = new ApiMethods();

        methods.deleteUser(getUserWIthTokenByRegistration(), "api/auth/user");
    }

    @Test
    @DisplayName("Попытка создать уже существующего пользователя")
    public void registerAlreadyExistUserShouldBeFailed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 403;
        int actualStatusCode;
        boolean expectedResponse = false;
        boolean actualResponse;
        String expectedMessage = "User already exists";
        String actualMessage;

        //Act
        ValidatableResponse response = methods.registerNewUser(getAlreadyExistingUserForRegistration(), "api/auth/register");
        actualResponse = response.extract().path("success");
        actualStatusCode = response.extract().statusCode();
        actualMessage = response.extract().path("message");

        //Assert
        assertThat("Статус код должен быть  403", actualStatusCode, equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false", actualResponse, equalTo( expectedResponse));
        assertThat("В заголовке message должно содержаться User already exists", actualMessage, equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Попытка создать пользователя без пароля")
    public void registerNewUserWithOutPasswordShouldBeFailed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 403;
        int actualStatusCode;
        boolean expectedResponse = false;
        boolean actualResponse;
        String expectedMessage = "Email, password and name are required fields";
        String actualMessage;

        //Act
        ValidatableResponse response = methods.registerNewUser(getUserWithNameAndEmail(), "api/auth/register");
        actualResponse = response.extract().path("success");
        actualStatusCode = response.extract().statusCode();
        actualMessage = response.extract().path("message");

        //Assert
        assertThat("Статус код должен быть  403", actualStatusCode, equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false", actualResponse, equalTo( expectedResponse));
        assertThat("В заголовке message должно содержаться Email, password and name are required fields", actualMessage, equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Попытка создать нового пользователя без логина")
    public void registerNewUserWithOutNameShouldBeFailed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 403;
        int actualStatusCode;
        boolean expectedResponse = false;
        boolean actualResponse;
        String expectedMessage = "Email, password and name are required fields";
        String actualMessage;

        //Act
        ValidatableResponse response = methods.registerNewUser(getUserForLogIn(), "api/auth/register");
        actualResponse = response.extract().path("success");
        actualStatusCode = response.extract().statusCode();
        actualMessage = response.extract().path("message");

        //Assert
        assertThat("Статус код должен быть  403", actualStatusCode, equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false", actualResponse, equalTo( expectedResponse));
        assertThat("В заголовке message должно содержаться Email, password and name are required fields", actualMessage, equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Попытка создать нового пользователя без почты")
    public void registerNewUserWithOutEmailShouldBeFailed()
    {
        //Arrange        ;
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 403;
        int actualStatusCode;
        boolean expectedResponse = false;
        boolean actualResponse;
        String expectedMessage = "Email, password and name are required fields";
        String actualMessage;

        //Act
        ValidatableResponse response = methods.registerNewUser(getUserWithNameAndPassword(), "api/auth/register");
        actualResponse = response.extract().path("success");
        actualStatusCode = response.extract().statusCode();
        actualMessage = response.extract().path("message");

        //Assert
        assertThat("Статус код должен быть  403", actualStatusCode, equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false", actualResponse, equalTo( expectedResponse));
        assertThat("В заголовке message должно содержаться Email, password and name are required fields", actualMessage, equalTo(expectedMessage));
    }
}
