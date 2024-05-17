package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 트랜잭션 단위로 실행될 메소드를 선언하고 있는 클래스
// 스프링이 관리하는 Bean
@Service
@RequiredArgsConstructor // lombok이 final필드를 초기화하는 생성자를 자동으로 생성한다.
public class UserService {

    private final UserDao userDao;

    // Spring이 UserService를 Bean으로 생성할때 생성자를 이용해
    //생성을 하는데, 이때 UserDao Bean이 있는지 보고 그 빈을 주입한다. 생성자 주입.
    /*
    public UserService(UserDao userDao){
        this.userDao = userDao;
    }
    */
    // 보통 서비스에서는 @Transactional 을 붙영서 하나의 트랜잭션으로 처리하게 된다
    // Spring Boot는 트랜잭션을  처리해주는 트랜잭션 관리자를 가지고 있다.

    @Transactional
    public User addUser(String name, String email, String password){
        /*
        insert into user (email,name,password,regdate)values(?,?,?,now()); #user_id auto gen
        select last_insert_id();
        insert into user_role( user_id, role_id) values( ?, 1);
         */

        // 트랜잭션이 시작된다.
        User user = userDao.addUser(email, name, password);
        userDao.mappingUserRole(user.getUserId()); //권한을 부여한다.
        return user;
        //트랜잭션이 끝난다.



        // 회원 정보를 저장한다.
        // 회원 정보를 저장한 후에 회원 정보를 읽어온다.
        // 회원 정보를 읽어온 후에 회원 정보를 리턴한다.

    }
}
