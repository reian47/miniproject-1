package com.example.demo.dao;

import com.example.demo.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

// spring이 관리하는 Bean
@Repository
public class UserDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private SimpleJdbcInsertOperations insertUser;


    public UserDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertUser = new SimpleJdbcInsert(dataSource).withTableName("user").usingGeneratedKeyColumns("user_id"); //자동으로증가되는 id 설정
    }

    // Spring JDBC를 이용한 코드. Spirng JDBC를  좀더 쉽게 사용할수 있도록 도와주는 라이브러리이다.
    @Transactional
    public User addUser(String email, String name, String password) {
        //Service에서 이미 트랜잭션이 시작했기 때문에, 그 트랜잭션에 포함된다.
        // insert into user (email,name,password,regdate) values(:email,:name,:password, :regdate); #user_id auto gen
        // SELECT LAST_INSERT_ID();
        User user = new User();
        user.setName(name); // Name 칼럼.
        user.setEmail(email); // email
        user.setPassword(password); // password
        user.setRegDate(new java.util.Date().toString()); //regdate
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        Number number = insertUser.executeAndReturnKey(params); // insert를 실행하고, 자동으로 생성된 id를 가져온다.
        insertUser.execute(params);

        int userId = number.intValue();
        user.setUserId(userId);
        return user;
    }


    @Transactional
    public void mappingUserRole(int userId){
        //Service에서 이미 트랜잭션이 시작했기 때문에, 그 트랜잭션에 포함된다.
        // insert into user_role( user_id, role_id) values( ?, 1);
        String sql = "insert into user_role( user_id, role_id ) values (:userId, 1)";
        SqlParameterSource params = new MapSqlParameterSource ("userId", userId);
        jdbcTemplate.update(sql, params);
    }
}

/*
        insert into user (email,name,password,regdate)values(?,?,?,now());#user_id auto gen
        select last_insert_id();
        insert into user_role( user_id, role_id) values( ?, 1);
 */