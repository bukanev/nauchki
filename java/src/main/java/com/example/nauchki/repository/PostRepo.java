package com.example.nauchki.repository;

import com.example.nauchki.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByTag(String tag);
    @Query("select distinct tag from Post")
    List<String> findAllTag();
}