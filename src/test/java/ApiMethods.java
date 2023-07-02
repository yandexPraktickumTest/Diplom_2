import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ApiMethods
{
    @Step("Send information to create new user")
    public ValidatableResponse createNewUser(Object obj) // login + password + email
        {
            return given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(obj)
                    .when()
                    .post("https://stellarburgers.nomoreparties.site/api/auth/register")
                    .then();
        }

    @Step("Send information to delete user")
    public ValidatableResponse deleteUser(Object obj)
        {
            return given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(obj)
                    .when()
                    .delete("https://stellarburgers.nomoreparties.site/api/auth/user")
                    .then();
        }

    @Step("Send information to log in user")
    public ValidatableResponse logInUser(Object obj)
    {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(obj)
                .when()
                .post("https://stellarburgers.nomoreparties.site/api/auth/login")
                .then();
    }

    @Step("Send information to change user data")
    public ValidatableResponse changeUserData(Object obj)
    {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(obj)
                .when()
                .patch("https://stellarburgers.nomoreparties.site/api/auth/user")
                .then();
    }


    @Step("Send information to create order")
    public ValidatableResponse createOrder(Object obj)
    {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(obj)
                .when()
                .post("https://stellarburgers.nomoreparties.site/api/orders")
                .then();
    }

    @Step("Send information to get chosen user orders")
    public ValidatableResponse getPickedUserOrders(Object obj)
    {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(obj)
                .when()
                .get("https://stellarburgers.nomoreparties.site/api/orders")
                .then();
    }
}
