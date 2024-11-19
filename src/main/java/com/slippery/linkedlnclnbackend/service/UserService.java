package com.slippery.linkedlnclnbackend.service;

import com.slippery.linkedlnclnbackend.dto.UserDto;
import com.slippery.linkedlnclnbackend.models.User;

public interface UserService {
    UserDto registerUser(User userDetails);
    UserDto updateUser(User userDetails);
    UserDto login(User userDetails);
    UserDto logout();
}
