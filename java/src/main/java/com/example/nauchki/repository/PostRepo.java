package com.example.nauchki.repository;

import com.example.nauchki.model.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;


public interface PostRepo extends CrudRepository<Post, Long> {
    List<Post> findByTag(String tag);
    Set<Post> findAll();
}