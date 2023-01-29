package com.example.security1.config;
//구글 로그인이 완료된 뒤에 후처리가 필요함
// 1.코드받기(인증됨)
// 2. 엑세스토큰(권한이생김)
// 3.사용자 프로필정보를 가져와서
// 4. 그 정보를 토대로 회원가입을 자동으로 진행시키기도 함.
// 4-2 이메일 전화벊 이름 아이디 쇼핑 몰-> 집주소,백화점몰->vip등급
// 5. 추가적인 회원가입 폼이 나와서 정보를 입력시켜줘야한다.
import com.example.security1.config.auth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// preAuthrize를 많이사용함
@Configuration
@EnableWebSecurity  // 스프링 시큐리티 필터가 스프링 필터체인에 등록이된다.
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true) //secured 어노테이션 활성화 ,preAuthorize라는 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    // 해당 메서드의 리턴되는 오브젝트를 Ioc로 등록해준다.
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() //인증만되면 들어갈수있는주소
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")// 로그인을 하지않았을 시 로그인페이지로 이동시켜림
                .loginProcessingUrl("/login") //login 주소가 호출이되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

        //구글 로그인이 완료된 뒤의 후처리가 필요함
        //TIP 코드 x, (엑세스토큰+사용자프로필정보 o)

    }
}
