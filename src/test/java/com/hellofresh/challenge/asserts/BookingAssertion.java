package com.hellofresh.challenge.asserts;

import com.hellofresh.challenge.models.Booking;
import com.hellofresh.challenge.models.BookingList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BookingAssertion {

    public static void assertListContainsAllBookings(BookingList bookingList, String excludedFields, Booking... bookings) {
        Set<String> excludedFieldsSet = new HashSet<>(Arrays.asList(excludedFields.split(",")));
        StringBuilder errors = new StringBuilder();
        boolean result = true;
        for (Booking booking : bookings) {
            result &= doesListContainBooking(bookingList, booking, errors, excludedFieldsSet);
        }
        if (!result) throw new AssertionError(errors.toString());
    }

    private static boolean doesListContainBooking(BookingList bookingList, Booking booking, StringBuilder errors, Set<String> excludedFields) {
        for (Booking bookingInList : bookingList.getBookings()) {
            if (areBookingsEqual(bookingInList, booking, excludedFields)) return true;
        }
        if (errors.length() == 0) errors.append("BookingList doesn't contain the following bookings:\n");
        errors.append(booking.toString()).append("\n");
        return false;
    }


    public static void assertBookingsEqual(Booking actualBooking, Booking expectedBooking) {
        assertBookingsEqual(actualBooking, expectedBooking, "");
    }

    public static void assertBookingsEqual(Booking actualBooking, Booking expectedBooking, String excludedFields) {
        Set<String> excludedFieldsSet = new HashSet<>(Arrays.asList(excludedFields.split(",")));
        StringBuilder errors = new StringBuilder();
        if (!areBookingsEqual(actualBooking, expectedBooking, errors, excludedFieldsSet)) {
            throw new AssertionError(errors.toString());
        }
    }

    private static boolean areBookingsEqual(Booking actualBooking, Booking expectedBooking, Set<String> excludeFields) {
        return areBookingsEqual(actualBooking, expectedBooking, null, excludeFields);
    }

    private static boolean areBookingsEqual(Booking actualBooking, Booking expectedBooking, StringBuilder errors, Set<String> excludeFields) {
        boolean result = true;
        result &= isFieldEqual("bookingId", actualBooking.getBookingid(), expectedBooking.getBookingid(), errors, excludeFields);
        result &= isFieldEqual("firstName", actualBooking.getFirstname(), expectedBooking.getFirstname(), errors, excludeFields);
        result &= isFieldEqual("lastName", actualBooking.getLastname(), expectedBooking.getLastname(), errors, excludeFields);
        result &= isFieldEqual("roomId", actualBooking.getRoomid(), expectedBooking.getRoomid(), errors, excludeFields);
        result &= isFieldEqual("phone", actualBooking.getPhone(), expectedBooking.getPhone(), errors, excludeFields);
        result &= isFieldEqual("email", actualBooking.getEmail(), expectedBooking.getEmail(), errors, excludeFields);
        result &= isFieldEqual("isDepositPaid", actualBooking.isDepositpaid(), expectedBooking.isDepositpaid(), errors, excludeFields);
        result &= isFieldEqual("checkIn", actualBooking.getBookingdates().getCheckin(), expectedBooking.getBookingdates().getCheckin(), errors, excludeFields);
        result &= isFieldEqual("checkOut", actualBooking.getBookingdates().getCheckout(), expectedBooking.getBookingdates().getCheckout(), errors, excludeFields);
        return result;
    }

    private static boolean isFieldEqual(String fieldName, Object actual, Object expected, StringBuilder errors, Set<String> excludeFields) {
        if (excludeFields.contains(fieldName)) return true;
        if (actual == null && expected == null) return true;
        if (actual == null || expected == null) {
            addError(fieldName, actual, expected, errors);
            return false;
        }
        if (actual.equals(expected)) return true;
        addError(fieldName, actual, expected, errors);
        return false;
    }

    private static void addError(String fieldName, Object actual, Object expected, StringBuilder errors) {
        if (errors == null) return;
        if (errors.length() == 0) errors.append("Bookings are not equal\n");
        errors.append("Expected ")
                .append(fieldName)
                .append(": ")
                .append(expected)
                .append("\n")
                .append("  Actual ")
                .append(fieldName)
                .append(": ")
                .append(actual)
                .append("\n");
    }
}
