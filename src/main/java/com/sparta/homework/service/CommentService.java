package com.sparta.homework.service;

import com.sparta.homework.Dto.CommentRequestDto;
import com.sparta.homework.domain.Comment;
import com.sparta.homework.domain.User;
import com.sparta.homework.repository.CommentRepository;
import com.sparta.homework.repository.UserRepository;
import com.sparta.homework.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }


    public void save(Long id, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다. 로그인 후 시도해주세요. ")
        );
        if (user != null) {
            requestDto.setPostid(id);
            requestDto.setUsername(userDetails.getUsername());
            Comment comment = new Comment(requestDto);
            commentRepository.save(comment);
        }
    }
    @Transactional
    public void update(Long id, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if(Objects.equals(comment.getUsername(), userDetails.getUsername())){
            comment.update(requestDto);
        }
        else throw new IllegalArgumentException("작성자와 로그인 정보가 다릅니다.");
    }

    public List<Comment> getComments(Long id) {

        List<Comment> comments =commentRepository.findAllByPostidOrderByModifiedAtDesc(id);

        return comments;
    }
}
