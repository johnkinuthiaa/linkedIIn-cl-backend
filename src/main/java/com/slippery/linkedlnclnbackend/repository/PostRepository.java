package com.slippery.linkedlnclnbackend.repository;

import com.slippery.linkedlnclnbackend.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts,Long> {
}
