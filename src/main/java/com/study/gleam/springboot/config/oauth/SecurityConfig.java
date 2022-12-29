package com.study.gleam.springboot.config.oauth;

import com.study.gleam.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()// h2-console 화면을 사용하기 위해 해당 옵션들을 disabled
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()// URL별 권한 관리를 성정하는 옵션 시작점 (antMatchers 옵션 사용을 위한 필수)
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()// 1.권한 관리 대상 지정 2.URL,HTTP 메소드별 관리 가능 3."/"등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한 부여
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())// 4."/api/v1/**" 주소를 가진 API는 USER 권한 가진 사람만 접근 가능
                    .anyRequest().authenticated()// 1.설정된 값들 이외 나머지 URL들을 나타냄 2.authenticated()를 추가하여 나머지 URL들은 모두 인증된 사용자들만 허용 (로그인 사용자)
                .and()
                    .logout()// 1.로그아웃 기능에 대한 설정 시작점
                        .logoutSuccessUrl("/")// 2.로그아웃 성공시 "/" 주소로 이동
                .and()
                    .oauth2Login()// 1.OAuth2 로그인 기능에 대한 설정 시작점
                        .userInfoEndpoint()// 2.OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정 담당
                            .userService(customOAuth2UserService);// 3.소셜 로그인 성공시 후속 조치를 진행할 UserService 인터페이스의 구현체 등록 4.리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시 가능
    }
}