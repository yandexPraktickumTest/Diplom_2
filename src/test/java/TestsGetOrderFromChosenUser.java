import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestsGetOrderFromChosenUser
{
    @Test
    @DisplayName("Получение списка заказов выбранного пользователя с авторизацией")
    public void getPickedUserOrdersWithLogInShouldBeSucceed()
    {
        //Arrange
        UserWithEmailAndPassword user = new UserWithEmailAndPassword("test-data@yandex.ru","password");
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 200;
        boolean expectedResponse = true;
        ValidatableResponse response = methods.logInUser(user);
        String token = response.extract().path("accessToken");
        UserToken userToken = new UserToken(token);

        //Act
        ValidatableResponse response1 = methods.getPickedUserOrders(userToken);

        //Assert
        assertThat("Статус код должен быть  200", response1.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться true",response1.extract().path("success"), equalTo( expectedResponse));
        assertThat("Список заказов не должен быть пустым",response1,is(notNullValue()));
    }

    @Test
    @DisplayName("Получение списка заказов выбранного опльзователя без авторизации")
    public void getPickedUserOrdersWithOutLogInShouldBeFailed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 401;
        boolean expectedResponse = false;
        String expectedMessage = "You should be authorised";
        UserToken userToken = new UserToken();

        //Act
        ValidatableResponse response1 = methods.getPickedUserOrders(userToken);

        //Assert
        assertThat("Статус код должен быть  401", response1.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false;",response1.extract().path("success"), equalTo( expectedResponse));
        assertThat("В заголовке message должно содержаться IYou should be authorised",response1.extract().path("message"), equalTo(expectedMessage));
    }
}
