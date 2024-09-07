package com.mr.mrhotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class RoomDto {

    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private String photo;
    private String roomDescription;
    private List<BookingDto> bookings;


    public RoomDto(Long id, String roomType, BigDecimal roomPrice) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }

    public RoomDto(Long id, String roomType, BigDecimal roomPrice, byte[] photoBytes, String roomDescription, List<BookingDto> bookings) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes):null;
        this.roomDescription = roomDescription;
        this.bookings = bookings;
    }
}
