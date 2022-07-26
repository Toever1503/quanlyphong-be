package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class BeanConfiguration {
    public static String FOLDER_SYS = "images";
    public static String HOST = "http://localhost:8080/";

    //Password encoder bean configuration
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //WebMvcConfigurer bean
    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:3000",
                                "http://localhost:8081")
                        .allowedOriginPatterns("*.*.*.*:*")
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS");
            }
        };
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(500, 5000, 300l, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        return executor;
    }

}
