import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestsChangeUserData
{
    @Test
    @DisplayName("Попытка изменения данных пользователя с авторизацией")
    public void changeUserDataWithLogInShouldBeSucceed()
    {
        //Arrange
        UserWithEmailAndPassword user = new UserWithEmailAndPassword("test-data@yandex.ru","password");
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 200;
        boolean expectedResponse = true;
        ValidatableResponse response = methods.logInUser(user);
        String token = response.extract().path("accessToken");
        UserToken user1 = new UserToken(token,"test12fg12-data@yandex.ru", "password1", "UserName1" );

        //Act
        ValidatableResponse response1 = methods.changeUserData(user1);

        //Assert
        assertThat("Статус код должен быть  200", response1.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться true",response1.extract().path("success"), equalTo( expectedResponse));
    }

    @Test
    @DisplayName("Попытка изменения данных пользователя без авторизации")
    public void changeUserDataWithOutLogInShouldBeFailed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 401;
        boolean expectedResponse = false;
        String expectedMessage = "You should be authorised";
        UserToken user = new UserToken("test12fg12-data@yandex.ru", "password1", "UserName1" );

        //Act
        ValidatableResponse response = methods.changeUserData(user);

        //Assert
        assertThat("Статус код должен быть  401", response.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false",response.extract().path("success"), equalTo( expectedResponse));
        assertThat("В заголовке message должно содержаться You should be authorised",response.extract().path("message"), equalTo(expectedMessage));
    }
}
