package com.example.nauchki.repository;

import com.example.nauchki.model.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {
    Optional<FileStorage> findById(Long Id);
    Optional<FileStorage> findByName(String name);
    Optional<FileStorage> findByExternalId(String name);
    Optional<FileStorage> findByExternalPath(String path);
}
