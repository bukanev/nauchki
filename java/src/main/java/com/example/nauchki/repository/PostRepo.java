package com.example.nauchki.repository;

import com.example.nauchki.model.Post;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;


public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByTag(String tag);
}