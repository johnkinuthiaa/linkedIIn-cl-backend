package com.slippery.linkedlnclnbackend.service;

import com.slippery.linkedlnclnbackend.dto.PostsDto;
import com.slippery.linkedlnclnbackend.models.Posts;

public interface PostService {
    PostsDto createNewPost(PostsDto post);
    PostsDto updateExistingPost(PostsDto post);
    PostsDto deletePost(PostsDto postsDto);
    PostsDto deleteAllPosts(Long userId);
    PostsDto getAllPosts();
    PostsDto findPostsByKeyWords(String words);

}
