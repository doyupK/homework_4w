package com.sparta.homework.repository;

import com.sparta.homework.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
// OrderBy 순으로 수정된 날짜 순으로 내림차순
public interface PostRepository extends JpaRepository<Posts, Long> {
    List<Posts> findAllByOrderByModifiedAtDesc();
}