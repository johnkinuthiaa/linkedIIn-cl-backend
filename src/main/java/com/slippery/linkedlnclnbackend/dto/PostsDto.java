package com.slippery.linkedlnclnbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.slippery.linkedlnclnbackend.models.Posts;
import com.slippery.linkedlnclnbackend.models.User;
import jakarta.persistence.Lob;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostsDto {
    private String message;
    private String error;
    private int statusCode;
    private Long id;
    private String content;
    private String postTo;
    private String image;
    private List<Posts> postsList;
    private Posts post;
    private User user;
    private Long userId;
}
