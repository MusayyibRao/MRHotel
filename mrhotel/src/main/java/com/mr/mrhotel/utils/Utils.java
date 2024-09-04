package com.mr.mrhotel.utils;

import com.mr.mrhotel.dto.BookingDto;
import com.mr.mrhotel.dto.RoomDto;
import com.mr.mrhotel.dto.UserDto;
import com.mr.mrhotel.entity.Booking;
import com.mr.mrhotel.entity.Room;
import com.mr.mrhotel.entity.User;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static final String ALPHANUMERIC_STRINGS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRendom = new SecureRandom();

    public static String generateRandomAlphaNumeric(int len) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int rendomIndex = secureRendom.nextInt(ALPHANUMERIC_STRINGS.length());
            char randomChar = ALPHANUMERIC_STRINGS.charAt(rendomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }


    public static UserDto userEntityToUserDto(User user) {

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNo(user.getPhoneNo());
        userDto.setRole(user.getRole());
        return userDto;

    }

    public static RoomDto roomEntityToRoomDto(Room room) {

        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setRoomType(room.getRoomType());
        roomDto.setRoomPrice(room.getRoomPrice());
        roomDto.setRoomDescription(room.getRoomDescription());
        roomDto.setPhotoUrl(room.getPhotoUrl());
        return roomDto;
    }

    public static RoomDto roomEntityToRoomDtoPlusBooking(Room room) {

        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setRoomType(room.getRoomType());
        roomDto.setRoomPrice(room.getRoomPrice());
        roomDto.setPhotoUrl(room.getPhotoUrl());
//        roomDto.setRoomDescription(room.getRoomDescription());

        if (room.getBookings() != null) {
            roomDto.setBookings(room.getBookings().stream().map(Utils::mapBookingentityToBookingDto).collect(Collectors.toList()));
        }

        return roomDto;
    }

    public static BookingDto mapBookingentityToBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();

        bookingDto.setId(booking.getId());
        bookingDto.setCheckInDate(booking.getCheckInDate());
        bookingDto.setCheckOutDate(booking.getCheckOutDate());
        bookingDto.setNumOfAdults(booking.getNumOfAdults());
        bookingDto.setNumOfChild(booking.getNumOfChild());
        bookingDto.setNumOfGuest(booking.getNumOfGuest());
        bookingDto.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        return bookingDto;
    }


    public static UserDto userEntityToUserDtoPlusUserBookingAndRoom(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNo(user.getPhoneNo());
        userDto.setRole(user.getRole());
        if (!user.getBooking().isEmpty()) {
            userDto.setBookings(user.getBooking().stream().map(booking -> mapBookingEntityBookingDtoPlusBookingRoom(booking, false)).collect(Collectors.toList()));
        }
        return userDto;
    }

    public static BookingDto mapBookingEntityBookingDtoPlusBookingRoom(Booking booking, boolean mapUser) {

        BookingDto bookingDto = new BookingDto();

        bookingDto.setId(booking.getId());
        bookingDto.setCheckInDate(booking.getCheckInDate());
        bookingDto.setCheckOutDate(booking.getCheckOutDate());
        bookingDto.setNumOfAdults(booking.getNumOfAdults());
        bookingDto.setNumOfChild(booking.getNumOfChild());
        bookingDto.setNumOfGuest(booking.getNumOfGuest());
        bookingDto.setBookingConfirmationCode(booking.getBookingConfirmationCode());

        if (mapUser) {
            bookingDto.setUser(Utils.userEntityToUserDto(booking.getUser()));
        }
        if (booking.getRoom() != null) {
            RoomDto roomDto = new RoomDto();

            roomDto.setId(booking.getRoom().getId());
            roomDto.setRoomType(booking.getRoom().getRoomType());
            roomDto.setRoomPrice(booking.getRoom().getRoomPrice());
            roomDto.setRoomDescription(booking.getRoom().getRoomDescription());
            roomDto.setPhotoUrl(booking.getRoom().getPhotoUrl());
            bookingDto.setRoom(roomDto);
        }

        return bookingDto;
    }

    public static List<UserDto> userListEntityToUserListDto(List<User> userList) {
        return userList.stream().map(Utils::userEntityToUserDto).collect(Collectors.toList());
    }

    public static List<RoomDto> roomListEntityToRoomListDto(List<Room> roomList) {

        return roomList.stream().map(Utils::roomEntityToRoomDto).collect(Collectors.toList());
    }

    public static List<BookingDto> bookingListEntityToBookingListDto(List<Booking> bookingList) {

        return bookingList.stream().map(Utils::mapBookingentityToBookingDto).collect(Collectors.toList());
    }


}
