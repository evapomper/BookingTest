package com.eduproject.ge.tests;

import com.eduproject.ge.pojos.Booking;
import com.eduproject.ge.pojos.BookingDates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

public class PostApiRequestUsingPojos {

    @Test
    public void postApiRequest() throws JsonProcessingException {

        try {
            BookingDates bookingDates = new BookingDates("2022-01-01", "2023-01-01");
            Booking booking = new Booking("pojos name", "pojos lname", "something", true, 800, bookingDates);

            //serialization
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);
            System.out.println(requestBody);

            //de-serialization
            Booking bookingDetails = objectMapper.readValue(requestBody, Booking.class);
            System.out.println(bookingDetails.getFirstname());
            System.out.println(bookingDetails.getBookingDates().getCheckin());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
}
