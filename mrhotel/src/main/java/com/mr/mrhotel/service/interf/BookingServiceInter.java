package com.mr.mrhotel.service.interf;

import com.mr.mrhotel.dto.Response;
import com.mr.mrhotel.entity.Booking;

public interface BookingServiceInter {

    Response saveBooking(long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response cancelBooking(Long bookingId);

}
