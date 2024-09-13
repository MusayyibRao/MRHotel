package com.mr.mrhotel.controller;

import com.mr.mrhotel.dto.Response;
import com.mr.mrhotel.service.interf.BookingServiceInter;
import com.mr.mrhotel.service.interf.RoomServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
//@CrossOrigin(origins = "http://localhost:3000")
public class RoomController {


    @Autowired
    private RoomServiceInter roomServiceInter;

    @Autowired
    private BookingServiceInter bookingServiceInter;


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addRoom(@RequestParam(value = "photo", required = false) MultipartFile photo
            , @RequestParam(value = "roomType", required = false) String roomType
            , @RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice
            , @RequestParam(value = "roomDescription", required = false) String roomDescription) {
        if (photo == null || photo.isEmpty() || roomType == null || roomType.isBlank()) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("please Enter all values");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response = roomServiceInter.addNewRoom(photo, roomType, roomPrice, roomDescription);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllRooms() {
        Response response = roomServiceInter.getAllRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }
    @GetMapping("/room_by_id/{roomId}")
    public ResponseEntity<Response> getRoomById(@PathVariable Long roomId) {
        Response response = roomServiceInter.getRoomById(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }


    @GetMapping("/roomType")
    public List<String> getAllTypesRoom() {
        return roomServiceInter.getAllRoomType();
    }

    @GetMapping("/allAvailableRooms")
    public ResponseEntity<Response> getAllAvailableRooms() {
        Response response = roomServiceInter.getAllAvailableRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/available_rooms_by_date_type")
    public ResponseEntity<Response> addRoom(@RequestParam(value = "checkInDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate
            , @RequestParam(value = "checkOutDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate
            , @RequestParam(value = "roomType", required = false) String roomType) {
        if (checkInDate == null || checkOutDate == null || roomType == null) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("please Enter all values");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response = roomServiceInter.getAvailableRoomByDateAndType(checkInDate, checkOutDate, roomType);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRoom(@PathVariable Long roomId,
                                               @RequestParam(value = "photo", required = false) MultipartFile photo
            , @RequestParam(value = "roomType", required = false) String roomType
            , @RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice
            , @RequestParam(value = "roomDescription", required = false) String roomDescription) {

        Response response = roomServiceInter.updateRoom(roomId, roomType, roomDescription, roomPrice, photo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @DeleteMapping("/delete/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteRoom(@PathVariable Long roomId) {
        Response response = roomServiceInter.deleteRoomType(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


}
