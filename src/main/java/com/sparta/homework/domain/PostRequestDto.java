package com.sparta.homework.domain;

import lombok.Getter;
// 데이터를 주고받을 떄 사용되는 Data transfer Object   -> 원 객체의 정보를 상하게 하지 않기 위해
@Getter
public class PostRequestDto {
    private String title;
    private String username;
    private String contents;
    private String password;
}
