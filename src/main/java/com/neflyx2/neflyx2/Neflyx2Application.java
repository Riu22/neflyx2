package com.neflyx2.neflyx2;

import com.neflyx2.neflyx2.interceptor.session_interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Neflyx2Application implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(Neflyx2Application.class, args);
	}

    @Autowired
    private session_interceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/admin/**");

    }
}
