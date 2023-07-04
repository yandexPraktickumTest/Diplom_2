import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestsUserCreation
{
    @Test
    @DisplayName("Удаление созданного пользователя")
    public void deleteUserShouldDeleteUser()
    {
        //Arrange
        UserWithLoginAndEmailAndPassword user = new UserWithLoginAndEmailAndPassword("HopHip9`@mail.ru", "password", "name");
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 200;
        String token;
        boolean expectedResponse = true;

        //Act
        ValidatableResponse response = methods.createNewUser(user);
        token = response.extract().path("accessToken");
        UserToken obj = new UserToken(token);
        ValidatableResponse response1 = methods.deleteUser(obj);

        //Assert
        assertThat(response1.extract().path("message"), response1.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться true",response1.extract().path("success"), equalTo( expectedResponse));
    }

    @Test
    @DisplayName("Создание нового пользователя")
    public void createNewUserIsPossible()
    {
        //Arrange
        UserWithLoginAndEmailAndPassword user = new UserWithLoginAndEmailAndPassword("HopHip10@mail.ru", "password", "name");
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 200;
        boolean expectedResponse = true;

        //Act
        ValidatableResponse response = methods.createNewUser(user);


        //Assert
        assertThat("Статус код должен быть  200", response.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться true",response.extract().path("success"), equalTo( expectedResponse));
    }
    @After
    public void clearData()
    {
        UserWithEmailAndPassword user = new UserWithEmailAndPassword("HopHip4@mail.ru","password" );
        ApiMethods methods = new ApiMethods();
        String token;

        ValidatableResponse response = methods.logInUser(user );
        token = response.extract().path("accessToken");
        UserToken obj = new UserToken(token);

        methods.deleteUser(obj);
    }

    @Test
    @DisplayName("Попытка создать уже существующего пользователя")
    public void createAlreadyExistUserShouldBeFailed()
    {
        //Arrange
        UserWithLoginAndEmailAndPassword user = new UserWithLoginAndEmailAndPassword("test-data@yandex.ru", "password", "Username");
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 403;
        boolean expectedResponse = false;
        String expectedMessage = "User already exists";

        //Act
        ValidatableResponse response = methods.createNewUser(user);

        //Assert
        assertThat("Статус код должен быть  403", response.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false",response.extract().path("success"), equalTo( expectedResponse));
        assertThat("В заголовке message должно содержаться User already exists",response.extract().path("message"), equalTo(expectedMessage));

    }

    @Test
    @DisplayName("Попытка создать пользователя без пароля")
    public void createNewUserWithOutPasswordShouldBeFailed()
    {
        //Arrange
        UserWithLoginAndEmail user = new UserWithLoginAndEmail("Username","test-data@yandex.ru");
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 403;
        boolean expectedResponse = false;
        String expectedMessage = "Email, password and name are required fields";

        //Act
        ValidatableResponse response = methods.createNewUser(user);

        //Assert
        assertThat("Статус код должен быть  403", response.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false",response.extract().path("success"), equalTo( expectedResponse));
        assertThat("В заголовке message должно содержаться Email, password and name are required fields",response.extract().path("message"), equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Попытка создать нового пользователя без логина")
    public void createNewUserWithOutLoginShouldBeFailed()
    {
        //Arrange
        UserWithEmailAndPassword user = new UserWithEmailAndPassword("test-data@yandex.ru","password");
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 403;
        boolean expectedResponse = false;
        String expectedMessage = "Email, password and name are required fields";

        //Act
        ValidatableResponse response = methods.createNewUser(user);

        //Assert
        assertThat("Статус код должен быть  403", response.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false",response.extract().path("success"), equalTo( expectedResponse));
        assertThat("В заголовке message должно содержаться Email, password and name are required fields",response.extract().path("message"), equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Попытка создать нового пользователя без почты")
    public void createNewUserWithOutEmailShouldBeFailed()
    {
        //Arrange
        UserWithLoginAndPassword user = new UserWithLoginAndPassword("Username","password");
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 403;
        boolean expectedResponse = false;
        String expectedMessage = "Email, password and name are required fields";

        //Act
        ValidatableResponse response = methods.createNewUser(user);

        //Assert
        assertThat("Статус код должен быть  403", response.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false",response.extract().path("success"), equalTo( expectedResponse));
        assertThat("В заголовке message должно содержаться Email, password and name are required fields",response.extract().path("message"), equalTo(expectedMessage));
    }
}
