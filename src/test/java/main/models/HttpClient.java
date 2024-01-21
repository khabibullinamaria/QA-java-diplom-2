package main.models;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class HttpClient {
    public Response callPost(Object json, String url) {
        Response response =
            given()
                .header("Content-type", "application/json")
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(url);
        return response;
    }

    public Response callPostWithLogin(Object json, String url, String token) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .header("Authorization", token)
                        .and()
                        .body(json)
                        .when()
                        .post(url);
        return response;
    }

    public Response callPatch(Object json, String url) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .patch(url);
        return response;
    }

    public Response callPatchWithLogin(Object json, String url, String token) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .header("Authorization", token)
                        .and()
                        .body(json)
                        .when()
                        .patch(url);
        return response;
    }

    public Response callGet(String url) {
        Response response =
            given()
                .get(url);
        return response;
    }
    public Response callGetWithAuth(String url, String token) {
        Response response =
                given()
                        .header("Authorization", token)
                        .get(url);
        return response;
    }

    public Response callDelete(String url) {
        Response response =
            given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete(url);

        return response;
    }

    public Response callDeleteWithAuth(String url, String token) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .header("Authorization", token)
                        .and()
                        .when()
                        .delete(url);

        return response;
    }
}
