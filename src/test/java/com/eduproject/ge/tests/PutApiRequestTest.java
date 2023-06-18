package com.eduproject.ge.tests;

import com.eduproject.ge.utils.FileNameConstants;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class PutApiRequestTest {

    @Test
    public void putApiRequestTest() throws IOException {

        String postApiRequestBody = FileUtils.readFileToString(new File(FileNameConstants.POST_API_REQUEST_BODY), "UTF-8");
        String tokenApiRequestBody = FileUtils.readFileToString(new File(FileNameConstants.GET_TOKEN_BODY), "UTF-8");
        String putApiRequestBody = FileUtils.readFileToString(new File(FileNameConstants.PUT_API_REQUEST_BODY), "UTF-8");

        //post api call
        Response postResponse =
                RestAssured
                        .given()
                            .contentType(ContentType.JSON)
                            .baseUri("https://restful-booker.herokuapp.com/booking")
                            .body(postApiRequestBody)
                        .when()
                            .post()
                        .then()
                            .assertThat()
                            .statusCode(200)
                        .extract()
                            .response();

        JSONArray jsonArrayFirstName = JsonPath.read(postResponse.body().asString(), "$.booking..firstname");
        String firstName = (String) jsonArrayFirstName.get(0);
        Assert.assertEquals(firstName, "fname");

        int bookingId = JsonPath.read(postResponse.body().asString(), "$.bookingid");

        //get api call
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri("https://restful-booker.herokuapp.com/booking/")
                .when()
                .get("/{bookingID}", bookingId)
                .then()
                .assertThat()
                .statusCode(200);

        //token generation
        Response tokenResponse =
                RestAssured
                        .given()
                            .contentType(ContentType.JSON)
                            .baseUri("https://restful-booker.herokuapp.com/auth")
                            .body(tokenApiRequestBody)
                        .when()
                            .post()
                        .then()
                            .assertThat()
                            .statusCode(200)
                        .extract()
                            .response();

        String token = JsonPath.read(tokenResponse.body().asString(), "$.token");

        //put api call
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .baseUri("https://restful-booker.herokuapp.com/booking")
                    .body(putApiRequestBody)
                    .header("Cookie", "token="+token)
                .when()
                    .put("/{bookingID}", bookingId)
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("firstname", Matchers.equalTo("Specflow"));

    }
}
