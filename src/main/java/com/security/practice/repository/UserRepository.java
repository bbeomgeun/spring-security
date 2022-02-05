package com.security.practice.repository;

import com.security.practice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// 기본적인 CRUD 내장
// @Repository 라는 어노테이션이 없어도 된다.
public interface UserRepository extends JpaRepository<User, Long> {

    // findBy까지는 규칙 + 이후 Username은 문법
    // select * from user where username = ? 이 호출
    public User findByUsername(String username);
}
