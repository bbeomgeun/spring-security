package com.security.practice.auth;

import com.security.practice.model.User;
import com.security.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login")
// login요청이 오면 자동으로 UserDetailsService 타입으로 Ioc 되어 있는 loadUserByUserName이 호출된다.
// @Service로 PrincipalDetailsService가 빈으로 등록, 호출이 되면 자동으로 loadUserByUserName이 호출
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 리턴된 UserDetails는 시큐리티 session(내부 Authentication(내부 UserDetails))
    // 리턴되면서 알아서 authentication 객체와 session 객체를 모두 만들어서 알아서 넣어준다
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인 form data에서 넘어오는 username과 파라미터의 이름이 일치해야 작동한다.
        System.out.println("username : "+username);
        User userEntity = userRepository.findByUsername(username);
        System.out.println("userEntity = " + userEntity);
        if (userEntity != null) {
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
