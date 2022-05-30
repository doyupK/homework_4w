package com.sparta.homework.service;

import com.sparta.homework.model.Post;
import com.sparta.homework.model.User;
import com.sparta.homework.repository.PostRepository;
import com.sparta.homework.Dto.PostRequestDto;
import com.sparta.homework.repository.UserRepository;
import com.sparta.homework.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long update(Long id, PostRequestDto requestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if(Objects.equals(post.getUsername(), userDetails.getUsername())){
            post.update(requestDto);
        }
        else throw new IllegalArgumentException("작성자와 로그인 정보가 다릅니다.");
        return post.getId();
    }



    public void save(PostRequestDto requestDto, UserDetailsImpl userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다. 로그인 후 시도해주세요. ")
        );
        if (user != null) {
            requestDto.setUsername(userDetails.getUsername());
            Post post = new Post(requestDto);
            postRepository.save(post);
        }
    }
}