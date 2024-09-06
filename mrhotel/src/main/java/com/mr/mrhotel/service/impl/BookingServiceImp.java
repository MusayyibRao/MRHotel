package com.mr.mrhotel.service.impl;

import com.mr.mrhotel.dto.BookingDto;
import com.mr.mrhotel.dto.Response;
import com.mr.mrhotel.entity.Booking;
import com.mr.mrhotel.entity.Room;
import com.mr.mrhotel.entity.User;
import com.mr.mrhotel.exception.MyException;
import com.mr.mrhotel.repository.BookingRepository;
import com.mr.mrhotel.repository.RoomRepository;
import com.mr.mrhotel.repository.UserRepository;
import com.mr.mrhotel.service.interf.BookingServiceInter;
import com.mr.mrhotel.service.interf.RoomServiceInter;
import com.mr.mrhotel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImp implements BookingServiceInter {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    RoomServiceInter roomServiceInter;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Response saveBooking(long roomId, Long userId, Booking bookingRequest) {

        Response response = new Response();

        try {
            if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
                throw new IllegalArgumentException("check in date Must come after check in date");
            }
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new MyException("Room Not Found"));
            User user = userRepository.findById(userId).orElseThrow(() -> new MyException("User Not Found"));

            List<Booking> existingBooking = room.getBookings();
            if (!roomIsAvailable(bookingRequest, existingBooking)) {
                throw new MyException("Room not Available for Selected Date");
            }
            bookingRequest.setRoom(room);
            bookingRequest.setUser(user);
            String bookingConfirmationCode = Utils.generateRandomAlphaNumeric(10);
            bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
            bookingRepository.save(bookingRequest);
            response.setStatusCode(200);
            response.setMessage("Successfully");
            response.setBookingConfirmationCode(bookingConfirmationCode);
        } catch (MyException e) {
            response.setStatusCode(405);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred" + e.getMessage());
        }

        return response;
    }


    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        Response response = new Response();

        try {
            Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(() -> new MyException("Booking Not Found"));
            BookingDto bookingDto = Utils.mapBookingEntityBookingDtoPlusBookingRoom(booking,true);
            response.setStatusCode(200);
            response.setMessage("Successfully");
            response.setBooking(bookingDto);
        } catch (MyException e) {
            response.setStatusCode(405);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();

        try {
            List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDto> bookingDtoList = Utils.bookingListEntityToBookingListDto(bookingList);
            response.setStatusCode(200);
            response.setMessage("Successfully");
            response.setBookingList(bookingDtoList);
        } catch (MyException e) {
            response.setStatusCode(405);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred" + e.getMessage());
        }

        return response;
    }


    @Override
    public Response cancelBooking(Long bookingId) {
        Response response = new Response();

        try {
            bookingRepository.findById(bookingId).orElseThrow(() -> new MyException("Booking does not exists"));
            bookingRepository.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("Successfully");
        } catch (MyException e) {
            response.setStatusCode(405);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred" + e.getMessage());
        }

        return response;
    }

    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBooking) {
        return existingBooking.stream()
                .noneMatch(existingBookings ->
                        bookingRequest.getCheckInDate().equals(existingBookings.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBookings.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBookings.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBookings.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBookings.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBookings.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBookings.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBookings.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBookings.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBookings.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBookings.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }
}
