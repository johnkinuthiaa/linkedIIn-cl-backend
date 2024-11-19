package com.slippery.linkedlnclnbackend.service.implementation;

import com.slippery.linkedlnclnbackend.dto.UserDto;
import com.slippery.linkedlnclnbackend.models.User;
import com.slippery.linkedlnclnbackend.repository.UserRepository;
import com.slippery.linkedlnclnbackend.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;

    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository repository) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
    }

    @Override
    public UserDto registerUser(User userDetails) {
        UserDto response =new UserDto();
        User username=repository.findByUsername(userDetails.getUsername());
        if(username !=null){
            throw new RuntimeException("user already exists");
        }
        try{
            User user =new User();
            user.setEmail(userDetails.getEmail());
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            user.setPhoneNumber(userDetails.getPhoneNumber());
            user.setUsername(userDetails.getUsername());
            user.setPosts(null);
            repository.save(user);
            response.setMessage("user "+userDetails.getUsername()+" created successfully");
            response.setStatusCode(200);
            response.setUser(user);

        } catch (Exception e) {
            response.setMessage("error creating user");
            response.setError(e.getMessage());
            throw new RuntimeException(e);
        }
        return response;
    }

    @Override
    public UserDto updateUser(User userDetails,Long id) {
        Optional<User> existingUser =repository.findById(id);
        UserDto response =new UserDto();
        if(existingUser.isPresent()){
            User currUser =existingUser.get();
            currUser.setUsername(userDetails.getUsername());
            currUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            currUser.setEmail(userDetails.getEmail());
            currUser.setPhoneNumber(userDetails.getPhoneNumber());
            repository.save(currUser);
            response.setMessage("user updated successfully");
            response.setStatusCode(200);
            response.setUser(currUser);
        }else{
            response.setMessage("user was not updated successfully try again later");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public UserDto login(User user) {
        UserDto response =new UserDto();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()));
        if (authentication.isAuthenticated()) {
            response.setMessage("user logged in successfully");
            response.setStatusCode(200);
            response.setUser(user);
        }else{
            response.setMessage("not authenticated");
            response.setStatusCode(401);
        }
        return response;
    }

    @Override
    public UserDto logout() {
        return null;
    }
}
