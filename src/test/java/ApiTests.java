import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Enclosed.class)
public class ApiTests //Авторизация через токен не работает, все тесты, где нужна авторизация будут падать
                     //Документация по API написана коряво!!!
{
    public static class UserCreation
    {
        @Test
        public void deleteUserShouldDeleteUser()
        {
            //Arrange
            UserWithLoginAndEmailAndPassword user = new UserWithLoginAndEmailAndPassword("HopHip3`@mail.ru", "password", "name");
            ApiMethods methods = new ApiMethods();
            int expectedStatusCode = 200;
            String token;

            //Act
            ValidatableResponse response = methods.createNewUser(user);
            token = response.extract().path("accessToken");
            UserToken obj = new UserToken(token);
            ValidatableResponse response1 = methods.deleteUser(obj);

            //Assert
            assertThat(response1.extract().path("message"), response1.extract().statusCode() , equalTo(expectedStatusCode));
        }

        @Test
        public void createNewUserIsPossible()
        {
            //Arrange
            UserWithLoginAndEmailAndPassword user = new UserWithLoginAndEmailAndPassword("HopHip4@mail.ru", "password", "name");
            ApiMethods methods = new ApiMethods();
            int expectedStatusCode = 200;
            boolean expectedResponse = true;
            String token;

            //Act
            ValidatableResponse response = methods.createNewUser(user);
            token = response.extract().path("accessToken");
            UserToken obj = new UserToken(token);

            //Assert
            assertThat("Статус код должен быть  200", response.extract().statusCode() , equalTo(expectedStatusCode));
            assertThat("В заголовке success должно содержаться true",response.extract().path("success"), equalTo( expectedResponse));

            methods.deleteUser(obj);
        }

        @Test
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

    public static class UserLogIn
    {
        @Test
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

    public static class ChangeUserData
    {
        @Test
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

    public static class CreateOrder
    {
        @Test
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
    public static class GerOrdersForChosenUser
    {
        @Test
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
}
