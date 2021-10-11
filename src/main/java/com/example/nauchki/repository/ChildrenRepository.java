package com.example.nauchki.repository;

import com.example.nauchki.model.Children;
import com.example.nauchki.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChildrenRepository extends JpaRepository<Children, Long> {
    List<Children> findByParent(User parent);
    @Query(value = "select * from Public.children where user_id ="+":id", nativeQuery = true)
    List<Children> findAllByParentId(@Param("id") Long id);
}
