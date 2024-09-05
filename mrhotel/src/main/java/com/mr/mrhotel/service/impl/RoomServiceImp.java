package com.mr.mrhotel.service.impl;

import com.mr.mrhotel.dto.Response;
import com.mr.mrhotel.dto.RoomDto;
import com.mr.mrhotel.entity.Room;
import com.mr.mrhotel.exception.MyException;
import com.mr.mrhotel.repository.BookingRepository;
import com.mr.mrhotel.repository.RoomRepository;
import com.mr.mrhotel.service.interf.RoomServiceInter;
import com.mr.mrhotel.utils.Helper;
import com.mr.mrhotel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomServiceImp implements RoomServiceInter {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    Helper helper;

    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {

        Response response = new Response();
        try {
            Room room = new Room();
            boolean  f= helper.uploadFile(photo);
            if(f) {
                String UPLOAD_DIR = helper.UPLOAD_DIR+photo.getOriginalFilename();
                room.setPhotoUrl(UPLOAD_DIR);
            }
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(description);
            Room saveRoom = roomRepository.save(room);
            RoomDto roomDto = Utils.roomEntityToRoomDto(saveRoom);
            response.setStatusCode(200);
            response.setMessage("successfully");
            response.setRoom(roomDto);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred" + e.getMessage());
        }

        return response;
    }

    @Override
    public List<String> getAllRoomType() {
        return roomRepository.findDistinctRoomType();

    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();
        try {

            List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDto> roomDtoList = Utils.roomListEntityToRoomListDto(roomList);
            response.setStatusCode(200);
            response.setMessage("successfully");
            response.setRoomList(roomDtoList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred" + e.getMessage());
        }
        return response;
    }


    @Override
    public Response deleteRoomType(Long roomId) {
        Response response = new Response();
        try {
            roomRepository.findById(roomId).orElseThrow(() -> new MyException("Room Not Found"));
            roomRepository.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("Data Delete Successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateRoom(Long roomId, String roomType, String description, BigDecimal roomPrice, MultipartFile photo) {
        Response response = new Response();
        try {
            String imageUrl = null;
            if (photo != null && !photo.isEmpty()) {
                boolean  f= helper.uploadFile(photo);
                if(f) {
                    String UPLOAD_DIR =helper.UPLOAD_DIR+photo.getOriginalFilename();
                    imageUrl=UPLOAD_DIR;
                }
            }
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new MyException("Room Not Found"));
            if (roomType != null) room.setRoomType(roomType);
            if (roomPrice != null) room.setRoomPrice(roomPrice);
            if (description != null) room.setRoomDescription(description);
            if (photo != null) room.setPhotoUrl(imageUrl);
            Room updateroom = roomRepository.save(room);
            RoomDto roomDto = Utils.roomEntityToRoomDto(updateroom);
            response.setStatusCode(200);
            response.setMessage("successfully");
            response.setRoom(roomDto);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();
        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new MyException("Room Not Found"));
            RoomDto roomDto = Utils.roomEntityToRoomDto(room);
            response.setStatusCode(200);
            response.setMessage("successfully");
            response.setRoom(roomDto);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableRoomByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();
        try {
            List<Room> availableRoom = roomRepository.findAvailableRoomByDateAndType(checkInDate, checkOutDate, roomType);
            List<RoomDto> roomDtoList = Utils.roomListEntityToRoomListDto(availableRoom);
            response.setStatusCode(200);
            response.setMessage("successfully");
            response.setRoomList(roomDtoList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        Response response = new Response();
        try {
            List<Room> roomList = roomRepository.getAllAvailableRoom();
            List<RoomDto> roomDtoList = Utils.roomListEntityToRoomListDto(roomList);
            response.setStatusCode(200);
            response.setMessage("successfully");
            response.setRoomList(roomDtoList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred" + e.getMessage());
        }
        return response;
    }
}
