package com.example.security1.config.auth;

import com.example.security1.auth.PrincipalDetails;
import com.example.security1.config.oauth.provider.GoogleUserInfo;
import com.example.security1.config.oauth.provider.NaverUserInfo;
import com.example.security1.config.oauth.provider.OAuth2UserInfo;
import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private  UserRepository userRepository;


    //함수종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.

    //구글로 부터 받은 userRequest데이터에 대한 후처리되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration"+userRequest.getClientRegistration()); //registration로 어떤 OAuth로 로그인했는지 확인가능
        System.out.println("getAccessToken"+userRequest.getAccessToken().getTokenValue());



        OAuth2User oAuth2User=super.loadUser(userRequest);
        //구글 로그인 버튼 클릭-> 구글 로그인창-> 로그인완료->code리턴(OAuth-Client라이브러리)->access token 요청
        //userRequest정보->loadUser함수호출()->구글로부터 회원프로필받아준다.
        System.out.println("userRequest"+oAuth2User.getAttributes());

        //회원가입을 강제로함

        OAuth2UserInfo oAuth2UserInfo=null;

        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo =new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
        }else{
            System.out.println("우리는 구글과 네이버 로그인만 지원합니다");
        }

        String provider=oAuth2UserInfo.getProvider();
        String providerId=oAuth2UserInfo.getProviderId();
        String username=provider+"_"+providerId;
        String password=bCryptPasswordEncoder.encode("겟인데어");
        String email=oAuth2UserInfo.getEmail();
        User userEntity = userRepository.findByUsername(username);
        if(userEntity==null){
            System.out.println("Oauth 로그인이 최초입니다");
            userEntity  = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .providerId(providerId)
                    .provider(provider)
                    .build();
            userRepository.save(userEntity);

        }else{
            System.out.println("구글 로그인을 이미 한적이 있습니다. 당신은 회원가입이 되어있습니다");
        }
        //authentication에 저장
        return new PrincipalDetails(userEntity,oAuth2User.getAttributes());
    }
}
