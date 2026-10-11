package com.defenselogix.personnelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.defenselogix.personnelservice")
public class PersonnelServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PersonnelServiceApplication.class, args);
    }
}