package com.eduproject.ge.tests;

import com.eduproject.ge.pojos.Booking;
import com.eduproject.ge.pojos.BookingDates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class PostApiRequestUsingPojos {

    @Test
    public void postApiRequest() throws JsonProcessingException {

        try {
            BookingDates bookingdates = new BookingDates("2022-01-01", "2023-01-01");
            Booking booking = new Booking("pojos name", "pojos lname", "something", true, 800, bookingdates);

            //serialization
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);
            System.out.println(requestBody);

            //de-serialization
            Booking bookingDetails = objectMapper.readValue(requestBody, Booking.class);
            System.out.println(bookingDetails.getFirstname());
            System.out.println(bookingDetails.getBookingdates().getCheckin());

            Response response =
                    RestAssured
                            .given()
                                .contentType(ContentType.JSON)
                                .baseUri("https://restful-booker.herokuapp.com/booking")
                                .body(requestBody)
                            .when()
                                .post()
                            .then()
                                .assertThat()
                                .statusCode(200)
                            .extract()
                                .response();

                    int bookingId = response.path("bookingid");

                    RestAssured
                            .given()
                                .contentType(ContentType.JSON)
                                .baseUri("https://restful-booker.herokuapp.com/booking")
                            .when()
                                .get("/{bookingID}", bookingId)
                            .then()
                                .assertThat()
                                .statusCode(200);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
}
