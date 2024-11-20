package com.slippery.linkedlnclnbackend.service;

import com.slippery.linkedlnclnbackend.dto.UserDto;
import com.slippery.linkedlnclnbackend.models.User;


public interface UserService {
    UserDto registerUser(User userDetails);
    UserDto updateUser(User userDetails,Long id);
    UserDto login(User userDetails);
    UserDto logout();
    UserDto addFollowers(Long userId,Long personToFollowId);
    UserDto removeFollower(Long userId,Long personToUnFollowId);
    UserDto getProfile(Long userId);
    UserDto getAllUsers();
    UserDto findUserByUsername(String username);
}
