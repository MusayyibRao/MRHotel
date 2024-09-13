package com.mr.mrhotel.utils;

import com.mr.mrhotel.dto.BookingDto;
import com.mr.mrhotel.dto.RoomDto;
import com.mr.mrhotel.dto.UserDto;
import com.mr.mrhotel.entity.Booking;
import com.mr.mrhotel.entity.Room;
import com.mr.mrhotel.entity.User;
import org.apache.commons.codec.binary.Base64;

import java.security.SecureRandom;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public static RoomDto roomEntityToRoomDto(Room room) throws SQLException {

        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setRoomType(room.getRoomType());
        roomDto.setRoomPrice(room.getRoomPrice());
        roomDto.setRoomDescription(room.getRoomDescription());
        String photoString = photoConvert(room.getPhoto());
        roomDto.setPhoto(photoString);
        return roomDto;
    }

    public static RoomDto roomEntityToRoomDtoPlusBooking(Room room) throws SQLException {

        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setRoomType(room.getRoomType());
        roomDto.setRoomPrice(room.getRoomPrice());
        String photoString= photoConvert(room.getPhoto());
        roomDto.setPhoto(photoString);

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
            userDto.setBookings(user.getBooking().stream().map(booking -> {
                try {
                    return mapBookingEntityBookingDtoPlusBookingRoom(booking, false);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList()));
        }
        return userDto;
    }

    public static BookingDto mapBookingEntityBookingDtoPlusBookingRoom(Booking booking, boolean mapUser) throws SQLException {

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
               String photoString = photoConvert(booking.getRoom().getPhoto());
                    roomDto.setPhoto(photoString);
                     bookingDto.setRoom(roomDto);
        }

        return bookingDto;
    }

    public static List<UserDto> userListEntityToUserListDto(List<User> userList) {
        return userList.stream().map(Utils::userEntityToUserDto).collect(Collectors.toList());
    }

    public static List<RoomDto> roomListEntityToRoomListDto(List<Room> roomList) throws SQLException {

        List<RoomDto> list = new ArrayList<>();
        for (Room room : roomList) {
            RoomDto roomDto = roomEntityToRoomDto(room);
            list.add(roomDto);
        }
        return list;
    }

    public static List<BookingDto> bookingListEntityToBookingListDto(List<Booking> bookingList) {

        return bookingList.stream().map(Utils::mapBookingentityToBookingDto).collect(Collectors.toList());
    }


    public static String photoConvert(Blob photo) throws SQLException {

        if(photo !=null) {
            byte[] photoByte = photo.getBytes(1,(int)photo.length());
            if(photoByte !=null && photoByte.length>0) {
                String base64Photo = Base64.encodeBase64String(photoByte);
              return base64Photo;
            }
        }
        return null;
    }


}
