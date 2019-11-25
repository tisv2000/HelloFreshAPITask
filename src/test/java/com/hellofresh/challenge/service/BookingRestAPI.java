package com.hellofresh.challenge.service;

import com.hellofresh.challenge.models.Booking;
import com.hellofresh.challenge.models.BookingList;
import com.hellofresh.challenge.models.CreatedBooking;
import com.hellofresh.challenge.models.Response;
import lombok.extern.slf4j.Slf4j;

import static com.hellofresh.challenge.data.JsonMapper.*;
import static io.restassured.RestAssured.given;

@Slf4j
public class BookingRestAPI {

    private static String BASE_URL = System.getProperty("base.url");
    private static String BASE_ENDPOINT_WITH_BOOKING_ID = BASE_URL + "{id}";

    public static Response<Booking> getBookingById(int bookingId) {
        io.restassured.response.Response response =
                given()
                        .pathParam("id", bookingId)
                        .when()
                        .get(BASE_ENDPOINT_WITH_BOOKING_ID);
        log.info("GOTTEN BOOKING BY ID RESPONSE {}", response.asString());

        return parseResponse(response, response.statusCode(), Booking.class);
    }

    public static Response<BookingList> getBookingsByRoomId(int roomId) {
        io.restassured.response.Response response =
                given()
                        .queryParam("roomid", roomId)
                        .when()
                        .get(BASE_URL);
        log.info("GOTTEN BOOKING BY ROOM ID RESPONSE {}", response.asString());

        return parseResponse(response, response.statusCode(), BookingList.class);
    }

    public static Response<BookingList> getBookings() {
        io.restassured.response.Response response =
                given()
                        .when()
                        .get(BASE_URL);
        log.info("GOTTEN BOOKINGS RESPONSE {}", response.asString());
        log.info("GOTTEN STATUS {}", response.getStatusCode());

        return parseResponse(response, response.statusCode(), BookingList.class);
    }

    public static Response<CreatedBooking> createBooking(Booking bookingToCreate) {
        String jsonRequest = writeObjectToJson(bookingToCreate);
        log.info("CREATE BOOKING REQUEST {}", bookingToCreate);
        io.restassured.response.Response response =
                given()
                        .contentType("application/json")
                        .body(jsonRequest)
                        .when()
                        .post(BASE_URL);
        log.info("CREATING BOOKING RESPONSE {}", response.body().asString());
        log.info("CREATED BOOKING STATUS {}", response.getStatusCode());

        return parseResponse(response, response.statusCode(), CreatedBooking.class);
    }
}
