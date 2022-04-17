package com.ykn.jobscheduler.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: hexiangtao
 * @date: 2020/3/28 14:07
 */
@Configuration
public class WebConfigure implements WebMvcConfigurer {

    @Value("${console.username:}")
    private String username;

    @Value("${console.password:}")
    private String password;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ConsoleAuthInterceptor(username, password)).addPathPatterns("/console/**");
    }
}
