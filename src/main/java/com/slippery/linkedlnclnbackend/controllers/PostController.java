package com.slippery.linkedlnclnbackend.controllers;

import com.slippery.linkedlnclnbackend.dto.PostsDto;
import com.slippery.linkedlnclnbackend.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller

@RequestMapping("/api/v1/posts")

public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }
    @PostMapping("/create/post")
    public ResponseEntity<PostsDto> createNewPost(@RequestBody PostsDto post){
        return ResponseEntity.ok(service.createNewPost(post));
    }
    @DeleteMapping("/delete/post")
    public ResponseEntity<PostsDto> deletePost(@RequestParam Long userId, @RequestParam Long postId){
        return ResponseEntity.ok(service.deletePost(userId, postId));
    }
    @GetMapping("/get/all")
    public ResponseEntity<PostsDto> getAllPosts(){
        return ResponseEntity.ok(service.getAllPosts());
    }
    @GetMapping("/search/words")
    public ResponseEntity<PostsDto> findPostsByKeyWords(@RequestParam String words){
        return ResponseEntity.ok(service.findPostsByKeyWords(words));
    }
}
