package TestsSupport;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class MethodBase
{
    public final String URL = "https://stellarburgers.nomoreparties.site/";

    protected RequestSpecification apiBlank(Object obj)
    {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(URL)
                .setBody(obj)
                .build();
    }

}
