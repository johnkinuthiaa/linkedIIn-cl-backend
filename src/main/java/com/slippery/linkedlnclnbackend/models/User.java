package com.slippery.linkedlnclnbackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String role ="user";
    private Long following;
    private Long followers;
    private String school;
    private String work;
    private String profileImage;
    private String bannerImage;
    private String industry;
    private Long profileViews;
    private Long PostImpressions;
    @ElementCollection
    private List<String> savedItems =new ArrayList<>();
    @ElementCollection
    private List<String> connections =new ArrayList<>();
    @ElementCollection
    private List<String> activity=new ArrayList<>();
    @ElementCollection
    private List<String> experience=new ArrayList<>();
    @ElementCollection
    private List<String> skills=new ArrayList<>();
    @ElementCollection
    private List<String> interests=new ArrayList<>();
    @ElementCollection
    private List<String> followersList=new ArrayList<>();
    @ElementCollection
    private List<String> followingList=new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Posts> posts = new HashSet<>();
}
