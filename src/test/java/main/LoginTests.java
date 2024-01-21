package main;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import main.models.HttpClient;
import main.models.Login;
import main.models.Steps;
import main.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;

public class LoginTests extends BaseTest {
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
        Response response = httpClient.callPost(userObj, "api/auth/register");
        response.then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("successLogin")
    public void successLogin() {
        Login login = new Login(email, password);
        Response response = httpClient.callPost(login, "api/auth/login");
        response.then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("fakeLogin")
    public void fakeLogin() {
        var fakeEmail = UUID.randomUUID().toString();
        Login login = new Login(fakeEmail, password);
        Response response = httpClient.callPost(login, "api/auth/login");
        response.then().assertThat().body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(401);
    }

    @After
    public void tearUp() {
        String token = Steps.Login(httpClient, email, password);
        httpClient.callDeleteWithAuth("api/auth/user", token);
    }
}
