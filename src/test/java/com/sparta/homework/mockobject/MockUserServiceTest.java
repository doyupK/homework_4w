package com.sparta.homework.mockobject;

import com.sparta.homework.Dto.SignupRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeValue;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

public class MockUserServiceTest {

    @Test
    void updateProduct_Normal() {
        // given
        String username = "gltlvl";
        String password = "rlaehduq";
        String passwordCheck = "rlaehduq";

        SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, passwordCheck);
        Long id = 1L;


        MockUserService mockUserService = new MockUserService();

        // when
        ModelAndView result = mockUserService.registerUser(signupRequestDto);

        // then
        assertViewName(result, "login");
    }

    @Nested
    @Test
    @DisplayName("하하하")
    void signup_User_specialUsername_fail() {
        // given
        String username = "gltlvl$";
        String password = "rlaehduq";
        String passwordCheck = "rlaehduq";
        ModelAndView mav = new ModelAndView();
        mav.setViewName("Login");

        SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, passwordCheck);

        MockUserService mockUserService = new MockUserService();

        // when
        ModelAndView result = mockUserService.registerUser(signupRequestDto);

        // then
        assertModelAttributeValue(result,"message","닉네임에 불가한 문자나, 최소 3자 이상이어야 합니다.");
    }
    @Test
    void signup_User_passwordCheck_fail() {
        // given
        String username = "gltlvl$";
        String password = "rlaehduq";
        String passwordCheck = "rlaehd";
        ModelAndView mav = new ModelAndView();
        mav.setViewName("Login");

        SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, passwordCheck);


        MockUserService mockUserService = new MockUserService();

        // when
        ModelAndView result = mockUserService.registerUser(signupRequestDto);

        // then
        assertModelAttributeValue(result,"message","비밀번호를 다시 확인하세요");
    }



}