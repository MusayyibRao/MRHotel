package com.mr.mrhotel.controller;

import com.mr.mrhotel.dto.Response;
import com.mr.mrhotel.entity.Booking;
import com.mr.mrhotel.service.interf.BookingServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
//@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {


    @Autowired
    BookingServiceInter bookingServiceInter;

    @PostMapping("/book_room/{roomId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> saveBooking(@PathVariable Long roomId,
                                                @PathVariable Long userId,
                                                @RequestBody Booking bookingRequest) {
        Response response = bookingServiceInter.saveBooking(roomId, userId, bookingRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllBookings() {
        Response response = bookingServiceInter.getAllBookings();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/get_by_confirmation_code/{confirmationCode}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        Response response = bookingServiceInter.findBookingByConfirmationCode(confirmationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("cancel/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> cancelBooking(@PathVariable Long bookingId) {
        Response response = bookingServiceInter.cancelBooking(bookingId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


}
