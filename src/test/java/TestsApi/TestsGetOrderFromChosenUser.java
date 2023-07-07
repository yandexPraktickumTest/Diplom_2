package TestsApi;

import TestsSupport.ApiMethods;
import TestsSupport.SupportForTestsBase;
import TestsSupport.TestsBase;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestsGetOrderFromChosenUser extends TestsBase
{
    @Before
    public void setUp()
    {
        SupportForTestsBase supportForTestsBase = new SupportForTestsBase();

        setUserWithTokenByLogIn(supportForTestsBase.getTokenByLogIn("test1-data@yandex.ru", "password"));
        setUserWithNullToken(supportForTestsBase.createNewUserWithNullToken("name", "email", "password",""));
    }
    @Test
    @DisplayName("Получение списка заказов выбранного пользователя с авторизацией")
    public void getPickedUserOrdersWithLogInShouldBeSucceed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 200;
        int actualStatusCode;
        boolean expectedResponse = true;
        boolean actualResponse;

        //Act
        ValidatableResponse response = methods.getPickedUserOrders(getUserWithTokenByLogIn(), "api/orders");
        actualResponse = response.extract().path("success");
        actualStatusCode = response.extract().statusCode();

        //Assert
        assertThat("Статус код должен быть  200", actualStatusCode , equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться true",actualResponse, equalTo( expectedResponse));
        assertThat("Список заказов не должен быть пустым",response,is(notNullValue()));
    }

    @Test
    @DisplayName("Получение списка заказов выбранного опльзователя без авторизации")
    public void getPickedUserOrdersWithOutLogInShouldBeFailed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 401;
        int actualStatusCode;
        boolean expectedResponse = false;
        boolean actualResponse;
        String expectedMessage = "You should be authorised";
        String actualMessage;

        //Act
        ValidatableResponse response = methods.getPickedUserOrders(getUserWithNullToken(), "api/orders");
        actualStatusCode = response.extract().statusCode();
        actualResponse = response.extract().path("success");
        actualMessage = response.extract().path("message");

        //Assert
        assertThat("Статус код должен быть  401", actualStatusCode, equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false;", actualResponse, equalTo( expectedResponse));
        assertThat("В заголовке message должно содержаться You should be authorised", actualMessage, equalTo(expectedMessage));
    }
}
