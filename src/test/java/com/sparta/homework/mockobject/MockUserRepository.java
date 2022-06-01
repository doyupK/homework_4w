package com.sparta.homework.mockobject;

import com.sparta.homework.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MockUserRepository {
    public static List<User> users = new ArrayList<>();
    // 상품 테이블 ID: 1부터 시작
    private Long id = 1L;

    // 중복
    public User save(User user) {
        // 유저 회원가입
        user.setId(id);
        ++id;
        users.add(user);
        return user;
    }

    // 상품 ID 로 상품 조회
    public static Optional<User> findByUsername(String username) {
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

}