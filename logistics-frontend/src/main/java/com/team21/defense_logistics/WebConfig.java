package com.team21.defense_logistics;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
                    throws Exception {
                HttpSession s = req.getSession(false);
                if (s == null || s.getAttribute("user") == null) {
                    res.sendRedirect(req.getContextPath() + "/login");
                    return false;
                }
                return true;
            }
        }).addPathPatterns("/**")
          .excludePathPatterns("/login", "/logout", "/css/**", "/error", "/favicon.ico");
    }
}