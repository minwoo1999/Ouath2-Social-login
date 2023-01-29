package com.example.security1.Service;


import com.example.security1.controller.dto.RequestUserJoin;
import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void save(RequestUserJoin userDto){
        userDto.setRole("ROLE_USER");
        String encode = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encode);


        User email = userRepository.findByEmail(userDto.getEmail());
        if(email!=null){
            System.out.println("중복된 이메일입니다");
        }else{
            userRepository.save(userDto.toEntity());
        }






    }


}
