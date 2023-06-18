package com.eduproject.ge.tests;

import com.eduproject.ge.pojos.Booking;
import com.eduproject.ge.pojos.BookingDates;
import com.eduproject.ge.utils.FileNameConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class PostApiRequestUsingPojos {

    @Test
    public void postApiRequest() throws JsonProcessingException {

        try {
            String jsonSchema = FileUtils.readFileToString(new File(FileNameConstants.JSON_SCHEMA), "UTF-8");

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
                                .statusCode(200)
                                .body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
