package com.slippery.linkedlnclnbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.slippery.linkedlnclnbackend.models.Posts;
import com.slippery.linkedlnclnbackend.models.User;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    private String message;
    private String error;
    private int statusCode;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private final String role ="user";
    private List<User> users;
    private User user;
    private List<Posts> allPosts;
    private Posts post;
    private String jwtToken;
}
