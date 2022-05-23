package com.sparta.homework.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
// OrderBy 순으로 수정된 날짜 순으로 내림차순
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();
}