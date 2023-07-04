import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestsCreateOrder
{
    @Test
    @DisplayName("Создание заказа с валидными данными и с авторизацией")
    public void createOrderWithLogInAndWithCorrectIngredientsAndTokenShouldBeSucceed()
    {
        //Arrange
        UserWithEmailAndPassword user = new UserWithEmailAndPassword("test-data@yandex.ru","password");
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 200;
        boolean expectedResponse = true;
        ValidatableResponse response = methods.logInUser(user);
        String token = response.extract().path("accessToken");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("61c0c5a71d1f82001bdaaa6d");
        arrayList.add("61c0c5a71d1f82001bdaaa6c");
        Ingredients ingredients = new Ingredients(arrayList ,token);

        //Act
        ValidatableResponse response1 = methods.createOrder(ingredients);

        //Assert
        assertThat("Статус код должен быть  200", response1.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться true",response1.extract().path("success"), equalTo( expectedResponse));
    }

    @Test
    @DisplayName("Создание нового заказа с авторизацией, но без ингредиентов")
    public void createOrderWithLogInAndWithOutIngredientsAndWithTokenShouldBeFailed()
    {
        //Arrange
        UserWithEmailAndPassword user = new UserWithEmailAndPassword("test-data@yandex.ru","password");
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 400;
        boolean expectedResponse = false;
        String expectedMessage = "Ingredient ids must be provided";
        ValidatableResponse response = methods.logInUser(user);
        String token = response.extract().path("accessToken");
        Ingredients ingredients = new Ingredients(token);

        //Act
        ValidatableResponse response1 = methods.createOrder(ingredients);

        //Assert
        assertThat("Статус код должен быть  400", response1.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false",response1.extract().path("success"), equalTo( expectedResponse));
        assertThat("В заголовке message должно содержаться Ingredient ids must be provided",response1.extract().path("message"), equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Создание нового заказа с авторизацией, но с невалидными ингредиентами")
    public void createOrderWithLogInAndWithInCorrectIngredientsAndCorrectTokenShouldBeFailed()
    {
        //Arrange
        UserWithEmailAndPassword user = new UserWithEmailAndPassword("test-data@yandex.ru","password");
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 500;
        ValidatableResponse response = methods.logInUser(user);
        String token = response.extract().path("accessToken");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("61c0c5a71d1f82001bdaaa6d1");
        Ingredients ingredients = new Ingredients(arrayList ,token);

        //Act
        ValidatableResponse response1 = methods.createOrder(ingredients);

        //Assert
        assertThat("Статус код должен быть  500", response1.extract().statusCode() , equalTo(expectedStatusCode));
    }

    @Test
    @DisplayName("Создание нового заказа без авторизации")
    public void createOrderWithOutLogInShouldBeFailed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 401;
        boolean expectedResponse = false;

        ArrayList<String> arrayList = new ArrayList<>();
        Ingredients ingredients = new Ingredients(arrayList ,"");

        //Act
        ValidatableResponse response1 = methods.createOrder(ingredients);

        //Assert
        assertThat("Статус код должен быть  401", response1.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false",response1.extract().path("success"), equalTo( expectedResponse));
    }

    @Test
    @DisplayName("Создание нового заказа без авторизации с невалидными ингредиентами")
    public void createOrderWithOutLogInAndWithCorrectIngredientsShouldBeFailed()
    {
        //Arrange
        ApiMethods methods = new ApiMethods();
        int expectedStatusCode = 401;
        boolean expectedResponse = false;

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("61c0c5a71d1f82001bdaaa6d");
        arrayList.add("61c0c5a71d1f82001bdaaa6c");
        Ingredients ingredients = new Ingredients(arrayList ,"");

        //Act
        ValidatableResponse response1 = methods.createOrder(ingredients);

        //Assert
        assertThat("Статус код должен быть  401", response1.extract().statusCode() , equalTo(expectedStatusCode));
        assertThat("В заголовке success должно содержаться false",response1.extract().path("success"), equalTo( expectedResponse));
    }
}
