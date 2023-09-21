package com.project.semipermbackend.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 시큐리티 필터 추가 +스프링 시큐리티가 활성화가 되어 있는데 어떤 설정을 해당파일에서 하겠다.
@RequiredArgsConstructor
public class SecurityConfig {
    private final OAuth2AccessTokenAuthenticationFilter oAuth2AccessTokenAuthenticationFilter;
    private final AuthenticationCheckFilter authenticationCheckFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;

    private final String[] POST_PERMITTED_URLS = {
            "/oauth2/login/*",
            "/member"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 통한 인증 메커니즘 방식 X,
                .and()

                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                //AbstractAuthenticationProcessingFilter를 상속한 OAuth2LoginAuthenticationFilter에서 실행된다

                .authorizeHttpRequests((authz) -> authz
                        .antMatchers(HttpMethod.POST, POST_PERMITTED_URLS).permitAll()
                        .anyRequest().authenticated()
                )

                .oauth2Login()
                .successHandler(oAuth2SuccessHandler)
                .failureHandler(oAuth2FailureHandler);

        httpSecurity
                // TODO oauth-client 라이브러리 사용하면 이거 생략 가능!!!
                .addFilterBefore(oAuth2AccessTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // TODO 필터랑 별개로 컨트롤러 타지않고 그냥 PASS 시키는 이유?
                .addFilterBefore(authenticationCheckFilter, UsernamePasswordAuthenticationFilter.class)
         ;

        return httpSecurity.build();
    }

    // TODO 임시로 필터 못타게 함. front에서 바로 요청하는 케이스가 없다는 가정 하.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/member");
    }
}
