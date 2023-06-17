package com.eduproject.ge.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class PostApiRequestTest {
    @Test
    public void createBooking() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        JSONObject booking = new JSONObject();
        JSONObject bookingDate = new JSONObject();

        bookingDate.put("checkin", "2021-01-01");
        bookingDate.put("checkout", "2022-01-01");
        booking.put("firstname", "first project");
        booking.put("lastname", "tutorial");
        booking.put("totalprice", "1000");
        booking.put("depositpaid", true);
        booking.put("bookingdates", bookingDate);
        booking.put("additionalneeds", "something else");

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .baseUri("https://restful-booker.herokuapp.com/booking")
                    .body(booking.toString())
                .when()
                    .post()
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("booking.firstname", Matchers.equalTo("first project"))
                    .body("booking.lastname", Matchers.equalTo("tutorial"))
                    .body("booking.totalprice", Matchers.equalTo(1000))
                    .body("booking.depositpaid", Matchers.equalTo(true))
                    .body("booking.bookingdates.checkin", Matchers.equalTo("2021-01-01"))
                    .body("booking.bookingdates.checkout", Matchers.equalTo("2022-01-01"))
                    .body("booking.additionalneeds", Matchers.equalTo("something else"));
    }
}
