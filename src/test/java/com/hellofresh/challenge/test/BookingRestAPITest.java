package com.hellofresh.challenge.test;

import com.hellofresh.challenge.data.BookingDataProvider;
import com.hellofresh.challenge.data.BookingDataRegistry;
import com.hellofresh.challenge.models.Booking;
import com.hellofresh.challenge.models.BookingList;
import com.hellofresh.challenge.models.CreatedBooking;
import com.hellofresh.challenge.models.Response;
import core.config.ConfigReader;
import core.logging.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.hellofresh.challenge.asserts.BookingAssertion.assertBookingsEqual;
import static com.hellofresh.challenge.asserts.BookingAssertion.assertListContainsAllBookings;
import static com.hellofresh.challenge.service.BookingRestAPI.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Listeners(TestListener.class)
public class BookingRestAPITest {

    private static final int STATUS_CREATED = 201;
    private static final int STATUS_OK = 200;

    static {
        ConfigReader.readConfig();
    }

    @Test(dataProviderClass = BookingDataProvider.class, dataProvider = "RandomBookingDataProvider")
    public void testCreateBooking(Booking bookingToCreate) {
        Response<CreatedBooking> response = createBooking(bookingToCreate);
        assertEquals(response.getStatusCode(), STATUS_CREATED);
        Booking createdBooking = response.getResponseBodyEntity().getBooking();
        assertBookingsEqual(createdBooking, bookingToCreate, "bookingId,phone,email");
    }

    @Test
    public void testGetBookingById() {
        Booking createdBooking = createBooking(BookingDataRegistry.getRandomBooking()).getResponseBodyEntity().getBooking();
        Response<Booking> response = getBookingById(createdBooking.getBookingid());
        assertEquals(response.getStatusCode(), STATUS_OK);
        assertBookingsEqual(response.getResponseBodyEntity(), createdBooking);
    }

    @Test
    public void testGetListOfBookings() {
        Booking createdBooking1 = createBooking(BookingDataRegistry.getRandomBooking()).getResponseBodyEntity().getBooking();
        Booking createdBooking2 = createBooking(BookingDataRegistry.getRandomBooking()).getResponseBodyEntity().getBooking();
        Response<BookingList> response = getBookings();
        assertEquals(response.getStatusCode(), STATUS_OK);
        assertTrue(response.getResponseBodyEntity().getBookings().size() >= 2);
        assertListContainsAllBookings(response.getResponseBodyEntity(), "bookingId,phone", createdBooking1, createdBooking2);
    }

    @Test
    public void testGetBookingsByRoomId() {
        Booking createdBooking1 = createBooking(BookingDataRegistry.getRandomBooking()).getResponseBodyEntity().getBooking();
        int createdRoomId = createdBooking1.getRoomid();
        Booking createdBooking2 = createBooking(BookingDataRegistry.getRandomBookingWithSpecifiedId(createdRoomId)).getResponseBodyEntity().getBooking();
        Response<BookingList> response = getBookingsByRoomId(createdRoomId);
        assertEquals(response.getStatusCode(), STATUS_OK);
        BookingList responseBodyEntity = response.getResponseBodyEntity();
        assertTrue(responseBodyEntity.getBookings().size() >= 2);
        assertListContainsAllBookings(responseBodyEntity, "", createdBooking1, createdBooking2);
    }
}
