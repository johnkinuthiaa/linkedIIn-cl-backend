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
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final JwtService jwtService;

    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository repository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.jwtService = jwtService;
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
            user.setFollowers(0L);
            user.setFollowing(0L);
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
        User current =repository.findByUsername(user.getUsername());
        UserDto response =new UserDto();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()));
        if (authentication.isAuthenticated()) {
            response.setMessage("user logged in successfully");
            response.setJwtToken(jwtService.generateJwtToken(user.getUsername()));
            response.setStatusCode(200);
            user.setId(current.getId());
            user.setFollowing(current.getFollowing());
            user.setFollowers(current.getFollowers());
            user.setPosts(current.getPosts());
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

    @Override
    public UserDto addFollowers(Long userId,Long personToFollowId) {
        UserDto response =new UserDto();
        Optional<User> currUser =repository.findById(userId);
        Optional<User> userToBeFollowed =repository.findById(personToFollowId);
        if(currUser.isPresent() && userToBeFollowed.isPresent() && !userId.equals(personToFollowId)){
            User theFollower =currUser.get();
            User theFollowed =userToBeFollowed.get();

            var hisFollowing =theFollower.getFollowing();

            theFollower.setFollowing(hisFollowing+1);
            var hisFollowers =theFollowed.getFollowers();

            theFollowed.setFollowers(hisFollowers+1);
            repository.save(theFollowed);
            repository.save(theFollower);
            response.setMessage(theFollower.getUsername()+" just followed "+theFollowed.getUsername());
            response.setStatusCode(200);
        } else if (userId.equals(personToFollowId)) {
            response.setMessage("Oh my!!Are you trying to follow yourself");
            response.setStatusCode(200);

        } else{
            response.setMessage("error following new people");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public UserDto removeFollower(Long userId, Long personToUnFollowId) {
        UserDto response =new UserDto();
        Optional<User> unFollower =repository.findById(userId);
        Optional<User> personToUnfollow =repository.findById(personToUnFollowId);
        if(unFollower.isPresent()
                &&personToUnfollow.isPresent()
                &&!userId.equals(personToUnFollowId)
        ){
            User currentUnFollower =unFollower.get();
            User personToBeUnFollowed =personToUnfollow.get();
            var hisFollowing =currentUnFollower.getFollowing();
            currentUnFollower.setFollowing(hisFollowing-1);
            var personToBeUnFollowedFollowers =personToBeUnFollowed.getFollowers();
            personToBeUnFollowed.setFollowers(personToBeUnFollowedFollowers-1);
            repository.save(currentUnFollower);
            repository.save(personToBeUnFollowed);

            response.setStatusCode(200);
            response.setMessage(unFollower.get().getUsername() +"unfollowed "+personToUnfollow.get().getUsername());
            response.setStatusCode(200);
        }else{
            response.setMessage("cannot unfollow user at this time");
        }
        return response;
    }

    @Override
    public UserDto getProfile(Long userId) {
        UserDto response =new UserDto();
        Optional<User> user =repository.findById(userId);
        if(user.isPresent()){
            User currUser =user.get();
            response.setMessage("profile for "+currUser.getUsername());
            response.setUser(user.orElseThrow());
            response.setStatusCode(200);
        }else{
            response.setMessage("user does not exist");
        }
        return response;
    }

    @Override
    public UserDto getAllUsers() {
        UserDto response =new UserDto();
        if(!repository.findAll().isEmpty()){
            var users =repository.findAll();
            response.setMessage("All users");
            response.setUsers(users);
            response.setStatusCode(200);
        }else{
            response.setMessage("no users found");
            response.setStatusCode(404);
        }
        return response;
    }

    @Override
    public UserDto findUserByUsername(String username) {
        UserDto response =new UserDto();
        var existingUser =repository.findByUsername(username);
        if(existingUser !=null){
            response.setUser(existingUser);
            response.setMessage("user "+existingUser.getUsername()+" found");
            response.setStatusCode(200);
        }else{
            var similarUsers =repository.findAll().stream()
                    .filter(user->user.getUsername().toLowerCase().contains(username.toLowerCase()))
                    .collect(Collectors.toList());
            response.setMessage("user "+username+" was not found..but we found similar to "+username);
            response.setUsers(similarUsers);
            response.setStatusCode(200);
        }

        return response;
    }
}
