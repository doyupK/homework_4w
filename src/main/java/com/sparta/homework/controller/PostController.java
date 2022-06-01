package com.sparta.homework.controller;

import com.sparta.homework.Dto.PostRequestDto;
import com.sparta.homework.model.Post;
import com.sparta.homework.repository.PostRepository;
import com.sparta.homework.security.UserDetailsImpl;
import com.sparta.homework.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@Controller
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    @Autowired
    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @PostMapping("/api/posts")
    @ResponseBody
    public boolean createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

//        postRepository.save(post);
        postService.save(requestDto, userDetails);
        return true;

    }

    @GetMapping("/api/posts")
    @ResponseBody
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @PutMapping("/api/posts/{id}")
    @ResponseBody
    public Long updateMemo(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println(userDetails.getUsername());
        postService.update(id, requestDto, userDetails);
//        return "작성자와 로그인 정보가 다릅니다.";
        return id;
    }

    @DeleteMapping("/api/posts/{id}")
    @ResponseBody
    public Long deleteMemo(@PathVariable Long id) {
        postRepository.deleteById(id);
        return id;
    }



    @GetMapping("/api/detail/{id}")
    public String detailPost(Model model, @PathVariable Long id) {
        System.out.println(id);
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시글이 존재 하지 않습니다.")
        );
        model.addAttribute("post", post);
        return "detail";
    }
    @GetMapping("/api/write")
    public String writePost() {
        return "write";
    }


}
