package TestsApi;

import TestsSupport.ApiMethods;
import TestsSupport.SupportForTestsBase;
import TestsSupport.TestsBase;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestsCreateOrder extends TestsBase
{
    @Before
    public void setUp()
    {
        SupportForTestsBase supportForTestsBase = new SupportForTestsBase();

        setIngredients(supportForTestsBase.createNewIngredients("test1-data@yandex.ru", "password"));
        setIngredientsWithInvalidIngredients(supportForTestsBase.createNewIngredientsWithInvalidIngredients("test1-data@yandex.ru", "password"));
        setIngredientsWithOutIngredients(supportForTestsBase.createNewIngredientsWithOutIngredients("test1-data@yandex.ru", "password"));
        setIngredientsWithOutToken(supportForTestsBase.createNewIngredientsWithOutToken());
    }
    @Test
    @DisplayName("Создание заказа с валидными данными и с авторизацией")
    public void createOrderWithLogInAndWithCorrectIngredientsAndTokenShouldBeSucceed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 200;
        int actualStatusCode;
        boolean expectedResponse = true;
        boolean actualResponse;

        //Act
        ValidatableResponse response = methods.createOrder(getIngredients(), "api/orders");
        actualResponse = response.extract().path("success");
        actualStatusCode = response.extract().statusCode();

        //Assert
        assertThat("Статус код должен быть  200", actualStatusCode, equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться true",actualResponse, equalTo( expectedResponse));
    }

    @Test
    @DisplayName("Создание нового заказа с авторизацией, но без ингредиентов")
    public void createOrderWithLogInAndWithOutIngredientsAndWithTokenShouldBeFailed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 400;
        int actualStatusCode;
        boolean expectedResponse = false;
        boolean actualResponse;
        String expectedMessage = "Ingredient ids must be provided";
        String actualMessage;


        //Act
        ValidatableResponse response = methods.createOrder(getIngredientsWithOutIngredients(), "api/orders");
        actualResponse = response.extract().path("success");
        actualStatusCode = response.extract().statusCode();
        actualMessage = response.extract().path("message");

        //Assert
        assertThat("Статус код должен быть  400", actualStatusCode, equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false",actualResponse, equalTo( expectedResponse));
        assertThat("В заголовке message должно содержаться Ingredient ids must be provided",actualMessage, equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Создание нового заказа с авторизацией, но с невалидными ингредиентами")
    public void createOrderWithLogInAndWithInCorrectIngredientsAndCorrectTokenShouldBeFailed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 500;
        int actualStatusCode;

        //Act
        ValidatableResponse response = methods.createOrder(getIngredientsWithInvalidIngredients(), "api/orders");
        actualStatusCode = response.extract().statusCode();

        //Assert
        assertThat("Статус код должен быть  500", actualStatusCode, equalTo(expectedStatusCode));
    }

    @Test
    @DisplayName("Создание нового заказа без авторизации")
    public void createOrderWithOutLogInShouldBeFailed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 401;
        int actualStatusCode;
        boolean expectedResponse = false;
        boolean actualResponse;

        //Act
        ValidatableResponse response = methods.createOrder(getIngredientsWithOutToken(), "api/orders");
        actualResponse = response.extract().path("success");
        actualStatusCode = response.extract().statusCode();

        //Assert
        assertThat("Статус код должен быть  401", actualStatusCode, equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false",actualResponse, equalTo( expectedResponse));
    }

    @Test
    @DisplayName("Создание нового заказа без авторизации с невалидными ингредиентами")
    public void createOrderWithOutLogInAndWithCorrectIngredientsShouldBeFailed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 401;
        int actualStatusCode;
        boolean expectedResponse = false;
        boolean actualResponse;

        //Act
        ValidatableResponse response = methods.createOrder(getIngredientsWithOutToken(), "api/orders");
        actualResponse = response.extract().path("success");
        actualStatusCode = response.extract().statusCode();

        //Assert
        assertThat("Статус код должен быть  401", actualStatusCode, equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false",actualResponse, equalTo( expectedResponse));
    }
}
