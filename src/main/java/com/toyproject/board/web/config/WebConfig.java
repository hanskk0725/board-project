package com.toyproject.board.web.config;

import com.toyproject.board.web.argumentResolver.LoginUserArgumentResolver;
import com.toyproject.board.web.interceptor.LoginCheck;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheck())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register", "/logout", "/users/add", "/");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserArgumentResolver());
    }
}
