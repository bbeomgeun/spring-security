package com.security.practice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 로그인 한 사람만
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                // 로그인 + admin or manager 권한 필요
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                // 로그인 + admin 권한 필요
                .anyRequest().permitAll() // 이외 요청은 권한 허용
                .and()
                .formLogin()
                .loginPage("/login"); // 권한이 없는 페이지를 갈 때 login이 뜨게끔 login 페이지를 등록
    }
}
