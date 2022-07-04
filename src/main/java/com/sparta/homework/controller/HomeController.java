package com.sparta.homework.controller;

import com.sparta.homework.repository.PostRepository;
import com.sparta.homework.security.UserDetailsImpl;
import com.sparta.homework.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final PostRepository postRepository;
    private final PostService postService;

    @Autowired
    public HomeController(PostService postService,PostRepository postRepository) {
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // hello 22333333333333   re1266
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
        else{
            model.addAttribute("username","방문자");
        }

        return "index";
    }
}