package com.sparta.homework.controller;

import com.sparta.homework.domain.Post;
import com.sparta.homework.domain.PostRepository;
import com.sparta.homework.domain.PostRequestDto;
import com.sparta.homework.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    @PostMapping("/api/posts")
    public Post createPost(@RequestBody PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        return postRepository.save(post);
    }

    @GetMapping("/api/posts")
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @PutMapping("/api/posts/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody PostRequestDto requestDto){
        postService.update(id,requestDto);
        return id;
    }

    @DeleteMapping("/api/posts/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        postRepository.deleteById(id);
        return id;
    }

    @GetMapping("/api/posts/check")
    public String dbGetPassword(@RequestParam Long id) {
        return postService.getPasswordService(id);
    }
}