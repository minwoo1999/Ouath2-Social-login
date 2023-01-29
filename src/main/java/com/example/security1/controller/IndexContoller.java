package com.example.security1.controller;

import com.example.security1.Service.UserService;
import com.example.security1.auth.PrincipalDetails;
import com.example.security1.controller.dto.RequestUserJoin;
import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexContoller {



    private final UserService userService;

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOAuthLogin(Authentication authentication,@AuthenticationPrincipal OAuth2User oauth){ //의존성주입
        System.out.println("/test/login=========");
        OAuth2User oAuth2User= (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication"+oAuth2User.getAttributes());
        System.out.println("authentication"+oauth.getAttributes());

        return "OAuth 세션정보 확인하기";
    }

    @GetMapping("/test/login")
    public @ResponseBody String logingTest(Authentication authentication , @AuthenticationPrincipal PrincipalDetails userDetails){ //의존성주입
        System.out.println("/test/login=========");
        PrincipalDetails principalDetails= (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication"+principalDetails.getUser());
        System.out.println("userDetails:"+userDetails.getUser());
        return "세션정보 확인하기";
    }

    @GetMapping("/")
    public String index(){
        // 머스테치 기본폴더 src/main/reseource
        //뷰 리졸버 설정: template(prefinx), mustache(suffix) 생략가능
        return "index";
    }

    /**
     * 로그인한사람만 접근가능하게설정
     * @return
     */

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principalDetails"+principalDetails.getUser());
        return "user";
    }
    /**
     * 어드민만 접근가능하게설정
     * @return
     */
    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }
    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }
    //스프링시큐리티 해당주소를 낚아 채버리네요 !! SecurityConfig 파일 생성 후 작동안함
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }
    /**
     * 회원가입페이지로 이동
     * @return
     */
    @GetMapping("/join")
    public String joinForm(){
        return "join";
    }
    /**
     * 회원가입 post 요청
     * @return
     */
    @PostMapping("/joinProc")
    public String join(RequestUserJoin user){
        userService.save(user);
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN") //하나만 걸고싶을땐 Secured
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }
    //여러개로 걸고싶을땐 PreAuthorize
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")

    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터정보";
    }



}
