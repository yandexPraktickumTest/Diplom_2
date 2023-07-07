package TestsSupport;

import ClassModel.*;
import ClassSupport.UserToken;
import TestsSupport.MethodBase;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class ApiMethods extends MethodBase
{
    @Step("Send information to create new user")
    public ValidatableResponse registerNewUser(Object obj, String apiPath)
        {
            return given()
                    .spec(apiBlank(obj))
                    .when()
                    .post(apiPath)
                    .then();
        }

    @Step("Send information to delete user")
    public ValidatableResponse deleteUser(Object obj, String apiPath)
        {
            return given()
                    .spec(apiBlank(obj))
                    .when()
                    .delete(apiPath)
                    .then();
        }

    @Step("Send information to log in user")
    public ValidatableResponse logInUser(Object obj, String apiPath)
    {
        return given()
                .spec(apiBlank(obj))
                .when()
                .post(apiPath)
                .then();
    }

    @Step("Send information to change user data")
    public ValidatableResponse changeUserData(Object obj, String apiPath)
    {
        return given()
                .spec(apiBlank(obj))
                .when()
                .patch(apiPath)
                .then();
    }

    @Step("Send information to create order")
    public ValidatableResponse createOrder(Object obj, String apiPath)
    {
        return given()
                .spec(apiBlank(obj))
                .when()
                .post(apiPath)
                .then();
    }

    @Step("Send information to get chosen user orders")
    public ValidatableResponse getPickedUserOrders(Object obj, String apiPath)
    {
        return given()
                .spec(apiBlank(obj))
                .when()
                .get(apiPath)
                .then();
    }

}
