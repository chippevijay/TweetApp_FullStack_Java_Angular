package com.tweetApp.JwtService;

import com.tweetApp.service.UserService;
import com.tweetApp.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Slf4j
@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserService userDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.debug("Entering AuthenticationManagerBuider configure method!!!");
		auth.authenticationProvider(authProvider());
		log.debug("Exiting AuthenticationManagerBuider configure method!!!");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.debug("Entering HttpSecurity configure method!!!");
		http.csrf().disable().authorizeRequests().antMatchers("/tweets/**","/**","/").permitAll().anyRequest().authenticated()
				.and().exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		log.debug("Exiting HttpSecurity configure method!!!");
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		log.debug("Creation of Authentication Manager Bean successful!!!");
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().antMatchers(HttpMethod.POST, "/refresh")
				.antMatchers(HttpMethod.OPTIONS, "/**")
				.and().ignoring()
				.antMatchers(HttpMethod.GET, "/" // Other Stuff You want to Ignore
				).and().ignoring()
				.antMatchers("/h2-console/**/**");// Should not be done in Production!
	}
	

	
	@Bean
	public DaoAuthenticationProvider authProvider() {

	  DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	  authProvider.setUserDetailsService(userDetailsService);
	  authProvider.setPasswordEncoder(passwordEncoder);
	  return authProvider;

	}

}
