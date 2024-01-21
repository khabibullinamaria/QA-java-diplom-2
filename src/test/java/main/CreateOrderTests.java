package main;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import main.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;

public class CreateOrderTests extends BaseTest {
    private String email;
    private String password;
    private HttpClient httpClient = new HttpClient();
    @Before
    public void setUp() {
        super.setUp();

        var name = UUID.randomUUID().toString();
        email = UUID.randomUUID() + "@yandex.ru";
        password = UUID.randomUUID().toString();
        User userObj = new User(email, name , password);
        httpClient.callPost(userObj, "api/auth/register");
    }
    @Test
    @DisplayName("Create Order Without Authorization")
    public void CreateOrderWithoutAuth() {

        String createOrder = "{\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\",\"61c0c5a71d1f82001bdaaa6f\"]}";
        Response response = httpClient.callPost(createOrder, "api/orders");
        response.then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Create Order With Ingredients")
    public void CreateOrderWithIngredients() {
        String token = Steps.Login(httpClient, email, password);

        String createOrder = "{\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\",\"61c0c5a71d1f82001bdaaa6f\"]}";
        Response response = httpClient.callPostWithLogin(createOrder, "api/orders", token);
        response.then().assertThat().statusCode(200);
    }
    @Test
    @DisplayName("Create Order Without Ingredients")
    public void CreateOrderWithoutIngredients() {
        String token = Steps.Login(httpClient, email, password);

        String createOrder = "{\"ingredients\": null}";
        Response response = httpClient.callPostWithLogin(createOrder, "api/orders", token);
        response.then().assertThat().statusCode(400);
    }
    @Test
    @DisplayName("Create Order With Fake Hash")
    public void CreateOrderWithFakeHash() {
        String token = Steps.Login(httpClient, email, password);

        String createOrder = "{\"ingredients\": [\"fakehash\",\"BarneyStinson\"]}";
        Response response = httpClient.callPostWithLogin(createOrder, "api/orders", token);
        response.then().assertThat().statusCode(500);
    }

    @After
    public void tearUp() {
        String token = Steps.Login(httpClient, email, password);
        httpClient.callDeleteWithAuth("api/auth/user", token);
    }
}
