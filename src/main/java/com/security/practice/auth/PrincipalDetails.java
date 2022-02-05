package com.security.practice.auth;

// 시큐리티가 /login 주소 요청을 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티 session을 만들어준다. (security ContextHolder에 저장)
// 홀더에 들어갈 수 있는 오버젝트는 정해져있음 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 됨.
// User오브젝트 타입 => UserDetails 타입 객체

// Security Session => Authentication(인증) => UserDetails(PrincipalDetails)
// 여기서 UserDetails을 구현한 PrincipalDetals를 만들었으므로 이걸 넣을 Authentication 객체가 필요하다

import com.security.practice.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user; // 콤포지션

    public PrincipalDetails(User user) {
        this.user = user;
    }


    // 해당 User의 권한을 리턴하는 곳!
    // 유저 필드의 getRole은 String 타입이므로 GrantedAuthority collection 타입을 만들어준다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> user.getRole());
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠겼는지?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 만료
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        //  우리 사이트에서 만약 1년동안 회원이 로그인을 하지 않았더라면? 휴면 계정으로 전환하기로함
        // user.getLoginDate를 가져와서 현재시간 - 로그인 시간이 1년을 초과하면 isEnable false로 할 수 있다.
        return true;
    }
}
