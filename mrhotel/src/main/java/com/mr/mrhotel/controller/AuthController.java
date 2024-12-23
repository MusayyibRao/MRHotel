package com.mr.mrhotel.controller;

import com.mr.mrhotel.dto.LoginRequest;
import com.mr.mrhotel.dto.Response;
import com.mr.mrhotel.entity.User;
import com.mr.mrhotel.service.interf.UserServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserServiceInter userServiceInter;


    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user) {
        Response response = userServiceInter.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response = userServiceInter.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }
}

