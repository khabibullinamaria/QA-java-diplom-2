package main;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import main.models.HttpClient;
import main.models.User;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserTests extends BaseTest {
    private String name;
    private String email;
    private String password;
    private HttpClient httpClient = new HttpClient();
    @Before
    public void setUp() {
        name = UUID.randomUUID().toString();
        email = UUID.randomUUID() + "@yandex.ru";
        password = UUID.randomUUID().toString();
        super.setUp();
    }

    @Test
    @DisplayName("createUser")
    public void createUser() {
        User userObj = new User(email, name , password);
        Response response = httpClient.callPost(userObj, "api/auth/register");
        response.then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("errorOnSameDataUser")
    public void errorOnSameDataUser() {
        User userObj = new User(email, name , password);
        Response response = httpClient.callPost(userObj, "api/auth/register");
        Response secondResponse = httpClient.callPost(userObj, "api/auth/register");
        secondResponse.then().assertThat().body("message", equalTo("User already exists"))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("errorOnIfNotAllFields")
    public void errorOnIfNotAllFields() {
        User nullEmailUser = new User(null, name , password);
        User nullNameUser = new User(email, null , password);
        User nullPassUser = new User(email, name , null);

        Response nullEmailResponse = httpClient.callPost(nullEmailUser, "api/auth/register");
        nullEmailResponse.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);

        Response nullNameResponse = httpClient.callPost(nullNameUser, "api/auth/register");
        nullNameResponse.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);

        Response nullPassResponse = httpClient.callPost(nullPassUser, "api/auth/register");
        nullPassResponse.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }

}
