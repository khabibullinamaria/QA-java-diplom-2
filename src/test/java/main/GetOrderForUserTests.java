package main;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import main.models.HttpClient;
import main.models.Login;
import main.models.Steps;
import main.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;

public class GetOrderForUserTests extends BaseTest {
    private String email;
    private String password;
    private HttpClient httpClient = new HttpClient();

    @Before
    public void setUp() {
        super.setUp();

        var name = UUID.randomUUID().toString();
        email = UUID.randomUUID() + "@yandex.ru";
        password = UUID.randomUUID().toString();
        User userObj = new User(email, name, password);
        httpClient.callPost(userObj, "api/auth/register");
    }

    @Test
    @DisplayName("Get orders for user with authorization")
    public void GetOrdersForUserWithAuth() {
        String token = Steps.Login(httpClient, email, password);

        String createOrder = "{\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\",\"61c0c5a71d1f82001bdaaa6f\"]}";
        Response createResponse = httpClient.callPostWithLogin(createOrder, "api/orders", token);
        createResponse.then().assertThat().statusCode(200);

        Response response = httpClient.callGetWithAuth("api/orders",token);
        response.then().assertThat().statusCode(200);
        Boolean result = response.body().jsonPath().get("success");
        Assert.assertEquals(true, result);
    }
    @Test
    @DisplayName("Get orders for user without authorization")
    public void GetOrdersForUserWithoutAuth() {

        Response response = httpClient.callGet("api/orders");
        response.then().assertThat().statusCode(401);
    }
}
