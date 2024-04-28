package com.tweetApp;

import com.tweetApp.swagger.SwaggerFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@Slf4j
//@CrossOrigin(origins = "*",maxAge = 3600)
public class UserAndAuthentication {

	public static void main(String[] args) {
		SpringApplication.run(UserAndAuthentication.class, args);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		log.debug("Entering filterRegisterationBean Method !!!");
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		SwaggerFilter myFilter = new SwaggerFilter();
		filterRegistrationBean.setFilter(myFilter);
		log.debug("Exiting filterRegisterationBean Method !!!");
		return filterRegistrationBean;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}


}
