package com.slippery.linkedlnclnbackend.controllers;

import com.slippery.linkedlnclnbackend.dto.UserDto;
import com.slippery.linkedlnclnbackend.models.User;
import com.slippery.linkedlnclnbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/auth")
public class UserController {
    /*
    UserDto logout();
        UserDto getAllUsers();
    UserDto findUserByUsername();
     */
    private final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody User userDetails){
        return ResponseEntity.ok(userService.registerUser(userDetails));
    }
    @PutMapping("/update/user")
    public ResponseEntity<UserDto> updateUser(@RequestBody User userDetails,Long id){
        return ResponseEntity.ok(userService.updateUser(userDetails,id));
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody User userDetails){
        return ResponseEntity.ok(userService.login(userDetails));
    }
    @PostMapping("/add/followers")
    public ResponseEntity<UserDto> addFollowers(@RequestParam Long userId, @RequestParam Long personToFollowId){
        return ResponseEntity.ok(userService.addFollowers(userId,personToFollowId));
    }
    @GetMapping("/all")
    public ResponseEntity<UserDto> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/get-profile")
    public ResponseEntity<UserDto> getProfile (@RequestParam Long userId){
        return ResponseEntity.ok(userService.getProfile(userId));
    }
    @PostMapping("/unfollow")
    public ResponseEntity<UserDto> removeFollower(@RequestParam Long userId, @RequestParam Long personToUnFollowId){
        return ResponseEntity.ok(userService.removeFollower(userId,personToUnFollowId));
    }

}
