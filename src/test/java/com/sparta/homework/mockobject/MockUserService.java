package com.sparta.homework.mockobject;

import com.sparta.homework.Dto.SignupRequestDto;
import com.sparta.homework.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class MockUserService {
    private final MockUserRepository mockUserRepository;

    public MockUserService() {
        this.mockUserRepository = new MockUserRepository();
    }

    public ModelAndView registerUser(SignupRequestDto requestDto) throws IllegalArgumentException{
        String username = requestDto.getUsername();
        ModelAndView mav = new ModelAndView();
        if(!requestDto.getPassword().equals(requestDto.getPasswordCheck())){
            mav.addObject("message", "비밀번호를 다시 확인하세요");
            mav.setViewName("signup");
            return mav;
        }
        if(username.contains(requestDto.getPassword())){
            mav.addObject("message", "비밀번호에 아이디가 포함되어 있습니다.");
            mav.setViewName("signup");
            return mav;
        }
        if(!Pattern.matches("^[a-zA-Z\\d]*$",username) || username.length()<=3){
            mav.addObject("message","닉네임에 불가한 문자나, 최소 3자 이상이어야 합니다.");
            mav.setViewName("signup");
            return mav;
        }
        if(requestDto.getPassword().length()<=4 || requestDto.getPassword().contains(username)){
            mav.addObject("message","비밀번호에 닉네임이 포함되었거나, 최소 4자 이상이어야 합니다.");
            mav.setViewName("signup");
            return mav;
        }
        else{
            Optional<User> found = MockUserRepository.findByUsername(username);
            if (found.isPresent()) {
                mav.addObject("message", "중복된 사용자 ID입니다.");
                mav.setViewName("signup");
                return mav;

            }
//            String password = passwordEncoder.encode(requestDto.getPassword());
            User user = new User(username, requestDto.getPassword());
            mockUserRepository.save(user);
            mav.setViewName("login");
        }
        return mav;
    }




}