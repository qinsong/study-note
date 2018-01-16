package com.qs.sys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = { "com.qs" })
@EnableTransactionManagement
public class SysMainApplication{
    public SysMainApplication() {
    }
    public static void main(String[] args) {
        SpringApplication.run(SysMainApplication.class, args);
    }
}
