package main;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import main.models.HttpClient;
import main.models.Steps;
import main.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ChangeUserDataTests extends BaseTest {
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
    @DisplayName("successLogin")
    public void UpdateUserWithAuth() {
        String token = Steps.Login(httpClient, email, password);

        var newName = "newName";
        User user = new User(email, newName, password);
        Response response = httpClient.callPatchWithLogin(user, "api/auth/user", token);
        response.then().assertThat().statusCode(200);
        String newNameResponse = response.body().jsonPath().get("user.name");
        assertEquals(newName,newNameResponse);
    }

    @Test
    @DisplayName("fakeLogin")
    public void UpdateUserWithoutAuth() {
        var newName = "newName";
        User user = new User(email, newName, password);
        Response response = httpClient.callPatch(user,"api/auth/user");
        response.then().assertThat().statusCode(401);
    }

    @After
    public void tearUp() {
        String token = Steps.Login(httpClient, email, password);
        httpClient.callDeleteWithAuth("api/auth/user", token);
    }
}
