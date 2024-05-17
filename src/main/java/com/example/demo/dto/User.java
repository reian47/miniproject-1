package com.example.demo.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor // 기본생성자가 자옫응로 만들어진다.
@Getter
@Setter
public class User {
    private int userId;
    private String email;
    private String name;
    private String password;
    private String regDate; // 원래는 날짜 type으로 읽어온후 문자열로 변환
}

/*
user_id	int	NO	PRI		auto_increment
email	varchar(255)	NO
name	varchar(50)	NO
password	varchar(500)	NO
regdate	timestamp	YES		CURRENT_TIMESTAMP	DEFAULT_GENERATED

*/