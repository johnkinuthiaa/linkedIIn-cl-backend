package com.slippery.linkedlnclnbackend.service.implementation;

import com.slippery.linkedlnclnbackend.dto.PostsDto;
import com.slippery.linkedlnclnbackend.models.Posts;
import com.slippery.linkedlnclnbackend.models.User;
import com.slippery.linkedlnclnbackend.repository.PostRepository;
import com.slippery.linkedlnclnbackend.repository.UserRepository;
import com.slippery.linkedlnclnbackend.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostServiceImpl(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public PostsDto createNewPost(PostsDto post) {
        Optional<User> optUser =userRepository.findById(post.getUserId());
        PostsDto response =new PostsDto();
        if(optUser.isPresent()){
            Posts newPost = new Posts();
            newPost.setContent(post.getContent());
            newPost.setPostTo(post.getPostTo());
            newPost.setCreatedOn(LocalDateTime.now());
            newPost.setImage(post.getImage());
            newPost.setUser(post.getUser());
            response.setMessage("post created successfully!");
            response.setStatusCode(200);
            response.setPost(newPost);
        }else{
            response.setMessage("post not created");
            response.setStatusCode(500);
        }
        return response;
    }


    @Override
    public PostsDto updateExistingPost(PostsDto post) {
        return null;
    }

    @Override
    public PostsDto deletePost(PostsDto postsDto) {
        Optional<User> optionalUser =userRepository.findById(postsDto.getUserId());
        Optional<Posts> optionalPost =postRepository.findById(postsDto.getId());
        PostsDto response =new PostsDto();
        if(optionalUser.isPresent()&&optionalPost.isPresent()){
            postRepository.deleteById(postsDto.getId());
            response.setMessage("post deleted");
            response.setStatusCode(200);
        }else{
            response.setMessage("post not deleted");
            response.setStatusCode(200);
        }
        return response;
    }

    @Override
    public PostsDto deleteAllPosts(Long userId) {
        return null;
    }

    @Override
    public PostsDto getAllPosts() {
        PostsDto response =new PostsDto();
        response.setPostsList(postRepository.findAll());
        response.setMessage("all posts");
        response.setStatusCode(200);
        return response;
    }

    @Override
    public PostsDto findPostsByKeyWords(String words) {
        PostsDto response =new PostsDto();
        var matching =postRepository.findAll().stream()
                .filter(posts -> posts.getContent().toLowerCase().contains(words.toLowerCase()))
                .collect(Collectors.toList());
        if(!matching.isEmpty()){
            response.setMessage("posts related to "+words);
            response.setStatusCode(200);
            response.setPostsList(matching);
        }else{
            response.setMessage("posts not found");
            response.setStatusCode(404);
        }


        return response;
    }
}
