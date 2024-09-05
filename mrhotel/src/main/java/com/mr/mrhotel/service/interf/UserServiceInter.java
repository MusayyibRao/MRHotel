package com.mr.mrhotel.service.interf;

import com.mr.mrhotel.dto.LoginRequest;
import com.mr.mrhotel.dto.Response;
import com.mr.mrhotel.entity.User;

public interface UserServiceInter {

    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUsersBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);

}
