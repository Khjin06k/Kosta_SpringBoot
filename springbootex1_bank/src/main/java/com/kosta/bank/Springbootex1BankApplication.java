package com.kosta.bank;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 메인을 실행시켜주는 애너테이션
@SpringBootApplication
public class Springbootex1BankApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springbootex1BankApplication.class, args);
    }

}
