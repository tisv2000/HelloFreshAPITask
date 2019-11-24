package com.hellofresh.challenge.models;

import lombok.Data;

@Data
public class Booking {

    private BookingDates bookingdates;
    private int bookingid;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private int roomid;
    private boolean depositpaid;
}
