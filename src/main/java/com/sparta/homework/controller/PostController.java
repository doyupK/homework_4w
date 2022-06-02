package com.sparta.homework.controller;

import com.sparta.homework.Dto.PostRequestDto;
import com.sparta.homework.domain.Posts;
import com.sparta.homework.repository.PostRepository;
import com.sparta.homework.security.UserDetailsImpl;
import com.sparta.homework.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

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
    public List<Posts> getPosts() {
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
    public Long deleteMemo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Posts post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );
        if(!Objects.equals(post.getUsername(), userDetails.getUsername())){
            throw new IllegalArgumentException("작성자 정보와 틀립니다..");
        }
        postRepository.deleteById(id);
        return id;
    }



    @GetMapping("/api/detail/{id}")
    public String detailPost(Model model, @PathVariable Long id) {

        Posts post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시글이 존재 하지 않습니다.")
        );

        model.addAttribute("post", post);
        return "detail";
    }
    @GetMapping("/api/write")
    public String writePost() {
        return "write";
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<String> handleNoSuchElementFoundException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @GetMapping("/check/{id}")
    @ResponseBody
    public String check(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){

        if(userDetails == null){
            throw new IllegalArgumentException("로그인 정보가 없습니다.");
        }

        return null;
    }

}
