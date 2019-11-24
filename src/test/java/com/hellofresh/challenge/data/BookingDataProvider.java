package com.hellofresh.challenge.data;

import org.testng.annotations.DataProvider;

public class BookingDataProvider {

    @DataProvider(name = "RandomBookingDataProvider")
    public static Object[] getBookingDataProvider() {
        return new Object[]{
                BookingDataRegistry.getRandomBooking()
        };
    }
}
