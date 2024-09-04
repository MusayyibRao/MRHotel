package com.mr.mrhotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int statusCode;
    private String message;
    private String token;
    private String expirationTime;
    private String role;
    private String bookingConfirmationCode;
    private UserDto user;
    private RoomDto room;
    private BookingDto booking;
    private List<UserDto> userDtos;
    private List<RoomDto> roomDtos;
    private List<BookingDto> bookingDtos;
}
