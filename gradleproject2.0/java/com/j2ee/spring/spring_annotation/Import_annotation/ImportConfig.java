package com.j2ee.spring.spring_annotation.Import_annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(Student.class)
public class ImportConfig {
    public ImportConfig() {
        System.out.println("初始化ImportConfig");
    }
}