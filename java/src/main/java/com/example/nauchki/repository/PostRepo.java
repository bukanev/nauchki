package com.example.nauchki.repository;

import com.example.nauchki.model.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PostRepo extends CrudRepository<Post, Long> {
    List<Post> findByTag(String tag);
}