package com.example.nauchki.repository;

import com.example.nauchki.model.ChildrenImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildrenImgRepo extends JpaRepository<ChildrenImg, Long> {
}
