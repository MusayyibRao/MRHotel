package com.mr.mrhotel.controller;

import com.mr.mrhotel.dto.Response;
import com.mr.mrhotel.service.interf.UserServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
//@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    UserServiceInter userServiceInter;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers() {
        System.out.println("hello Users");
        Response response = userServiceInter.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable("id") String userId) {
        Response response = userServiceInter.getUserById(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> userDeleteById(@PathVariable("id") String userId) {
        Response response = userServiceInter.deleteUser(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/getProfile")
    public ResponseEntity<Response> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Response response = userServiceInter.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/getUserBooking/{id}")
    public ResponseEntity<Response> getUserBookingHistory(@PathVariable("id") String userId) {
        Response response = userServiceInter.getUsersBookingHistory(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }
}
