package com.example.security1.auth;

import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login");
//login 요청이오면 자동으로 UserDetailsService 타입으로 Ioc 되어있는 loadUserByUsername 함수가 실행
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    //시큐리티 session=Authentication=Userdetails
    //시큐리티 session=Authentication(내부 Userdetails)=Userdetails
    //시큐리티 session(내부 Authentication(내부 Userdetails))
    //Authenication은 자동으로만들어짐
    // form 에서 넘어오는 name 값과 같아야함

    //함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity=userRepository.findByUsername(username);
        if (userEntity!=null){
            //authentication에 저장
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
