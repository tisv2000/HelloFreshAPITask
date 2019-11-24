package com.hellofresh.challenge.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookingList {

    private List<Booking> bookings = new ArrayList<>();
}
