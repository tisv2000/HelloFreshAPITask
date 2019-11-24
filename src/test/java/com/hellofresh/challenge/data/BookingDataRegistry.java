package com.hellofresh.challenge.data;

import com.hellofresh.challenge.models.Booking;
import com.hellofresh.challenge.models.BookingDates;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Random;

import static com.hellofresh.challenge.data.RandomUtils.*;

@Slf4j
public class BookingDataRegistry {

    public static Booking getRandomBooking() {
        Booking booking = new Booking();
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("2019-11-20");
        bookingDates.setCheckout("2019-11-21");
        booking.setBookingdates(bookingDates);
        booking.setBookingid(100);
        booking.setDepositpaid(true);
        booking.setEmail(generateRandomEmail());
        booking.setFirstname(generateRandomStringLettersOnly(5));
        booking.setLastname(generateRandomStringLettersOnly(5));
        booking.setPhone(generateRandomStringDigitsOnly(15));
        booking.setRoomid(new Random().nextInt(10000) + 1);

        return booking;
    }

    public static Booking getRandomBookingWithSpecifiedId(int roomId) {
        Booking booking = new Booking();
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin(generateCheckinDate());
        bookingDates.setCheckout(generateCheckoutDate());
        booking.setBookingdates(bookingDates);
        booking.setBookingid(1);
        booking.setDepositpaid(true);
        booking.setEmail(generateRandomEmail());
        booking.setFirstname(generateRandomStringLettersOnly(5));
        booking.setLastname(generateRandomStringLettersOnly(5));
        booking.setPhone(generateRandomStringDigitsOnly(15));
        booking.setRoomid(roomId);

        return booking;
    }

    private static String generateCheckinDate() {
        String now = String.valueOf(LocalDate.now());
        log.debug("Checkin date for booking request {}", now);
        return now;
    }

    private static String generateCheckoutDate() {
        String now = String.valueOf(LocalDate.now().plusDays(1));
        log.debug("Checkout date for booking request {}", now);
        return now;
    }
}
