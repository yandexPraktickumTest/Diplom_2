package TestsSupport;

import ClassModel.*;
import ClassSupport.UserToken;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;

public class SupportForTestsBase
{
    ApiMethods apiMethods = new ApiMethods();
    @Step("Create new User")
    public Object createNewUser(String email, String password, String name) {return new UserWithLoginAndEmailAndPassword(email, password, name);}

    @Step("Create new User for Log In")
    public Object createNewUserForLogIn(String email, String password) {return new UserWithEmailAndPassword(email, password);}

    @Step("Create new User with Token")
    public Object createNewUserWithToken(String token){return new UserToken(token);}

    @Step("Create new User with name and password")
    public Object createNewUserWithNameAndPassword(String name, String password) {return new UserWithLoginAndPassword(name, password);}

    @Step("Create new User with name an email")
    public Object createNewUserWithNameAndEmail(String name, String email) {return new UserWithLoginAndEmail(name, email);}

    @Step("Create new User with with null token")
    public Object createNewUserWithNullToken (String name, String email, String password, String token) {return new UserToken(token, email, password, name);}

    @Step("Create new Ingredients")
    public Object createNewIngredients(String email, String password)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("61c0c5a71d1f82001bdaaa6d");
        arrayList.add("61c0c5a71d1f82001bdaaa6c");
        ValidatableResponse response = apiMethods.logInUser(createNewUserForLogIn(email, password), "api/auth/login");
        String token = response.extract().path("accessToken");
        return new Ingredients(arrayList, token);
    }

    @Step("Create new Ingredients with invalid ingredients")
    public Object createNewIngredientsWithInvalidIngredients(String email, String password)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("61c0c5a71d1f82001bdaaa6d1");
        arrayList.add("61c0c5a71d1f82001bdaaa6c1");
        ValidatableResponse response = apiMethods.logInUser(createNewUserForLogIn(email, password), "api/auth/login");
        String token = response.extract().path("accessToken");
        return new Ingredients(arrayList, token);
    }

    @Step("Create new Ingredients with out ingredients")
    public Object createNewIngredientsWithOutIngredients(String email, String password)
    {
        ValidatableResponse response = apiMethods.logInUser(createNewUserForLogIn(email, password), "api/auth/login");
        String token = response.extract().path("accessToken");
        return new Ingredients(token);
    }

    @Step("Create new Ingredients with invalid ingredients")
    public Object createNewIngredientsWithOutToken()
    {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("61c0c5a71d1f82001bdaaa6d");
        arrayList.add("61c0c5a71d1f82001bdaaa6c");

        return new Ingredients(arrayList);
    }

    @Step("Get token by Registration")
    public Object getTokenByRegistration(String email, String password, String name)
    {
        ValidatableResponse response = apiMethods.registerNewUser(createNewUser(email, password, name), "api/auth/register");
        String token = response.extract().path("accessToken");
        return createNewUserWithToken(token);
    }
    @Step("Get token by Log In")
    public Object getTokenByLogIn(String email, String password)
    {
        ValidatableResponse response = apiMethods.logInUser(createNewUserForLogIn(email, password), "api/auth/login");
        String token = response.extract().path("accessToken");
        return createNewUserWithToken(token);
    }
}
