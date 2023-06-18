package com.eduproject.ge.tests;

import com.eduproject.ge.utils.BaseTest;
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

public class PostApiRequestUsingFileTest extends BaseTest {
    @Test
    public void postApiRequestTest() throws IOException {

        String postApiRequestBody = FileUtils.readFileToString(new File(FileNameConstants.POST_API_REQUEST_BODY), "UTF-8");

        Response response =
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

        JSONArray jsonArrayFirstName = JsonPath.read(response.body().asString(), "$.booking..firstname");
        String firstName = (String) jsonArrayFirstName.get(0);
        Assert.assertEquals(firstName, "fname");

        JSONArray jsonArrayLastName = JsonPath.read(response.body().asString(), "$.booking..lastname");
        String lastName = (String) jsonArrayLastName.get(0);
        Assert.assertEquals(lastName, "lname");

        JSONArray jsonArrayCheckin = JsonPath.read(response.body().asString(), "$.booking.bookingdates..checkin");
        String checkin = (String) jsonArrayCheckin.get(0);
        Assert.assertEquals(checkin, "2018-01-01");

        int bookingId = JsonPath.read(response.body().asString(), "$.bookingid");

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .baseUri("https://restful-booker.herokuapp.com/booking/")
                .when()
                    .get("/{bookingID}", bookingId)
                .then()
                    .assertThat()
                    .statusCode(200);
                   // .body("firstname", Matchers.equalTo("first project"))
                   // .body("lastname", Matchers.equalTo("tutorial"));

    }
}
