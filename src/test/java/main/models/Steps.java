package main.models;

import io.restassured.response.Response;
import io.qameta.allure.Step;

public class Steps {
    @Step ("Login")
    public static String Login(HttpClient httpClient, String email, String password) {
        Login login = new Login(email, password);
        Response loginResponse = httpClient.callPost(login, "api/auth/login");
        String token = loginResponse.body().jsonPath().get("accessToken");

        return token;
    }
}
