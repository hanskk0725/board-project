package com.toyproject.board.domain.repository;

import com.toyproject.board.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    @Query("SELECT p FROM Post p JOIN FETCH p.user")
    List<Post> findAllWithUser();

    @Query("select p from Post p join fetch p.user where p.id= :id")
    Optional<Post> findPostWithUserById(Long id);
}
