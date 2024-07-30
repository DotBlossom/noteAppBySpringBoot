package project.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import project.interceptor.LoggerInterceptor;


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor());
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/jpa/**")
			.allowedOrigins("http://localhost:3000" ,"http://localhost:8080/swagger-ui")
			.allowedMethods("GET","POST","DELETE","PUT");
	}	

}