package com.geggitech.springboot.repository;

import com.geggitech.springboot.entity.Comment;
import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(nativeQuery = true)
    List<Comment> findByPostId(long id);

}
