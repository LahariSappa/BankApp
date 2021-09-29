package com.training.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.training.filters.JwtFilter;
import com.training.services.AdminService;

@Configuration
@EnableWebSecurity
public class AdminConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private AdminService adminService;
	
	@Autowired 
	private JwtFilter jwtFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new PasswordEncoder()
		{
			@Override
			public String encode(CharSequence userEntered) {
				return userEntered.toString(); }

			@Override
			public boolean matches(CharSequence userEntered, String password) {
				System.out.println("UserEntered "+userEntered);
				System.out.println("password"+password);
				if(password.equals(userEntered))   
					return true;
				return false;

			} }; }
	public void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(adminService)
		.passwordEncoder(passwordEncoder());

	}

	protected void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity
		.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers("/authenticate")
		.permitAll()
		.and() 
		.authorizeRequests() 
		.antMatchers("/admin/**")
		.hasRole("ADMIN")
		.and() 
		.authorizeRequests() 
		.antMatchers("/manager/**")
		.hasAnyRole("MANAGER","ADMIN")
		.and()
		.authorizeRequests()
		.antMatchers("/user/**")
		.hasAnyRole("USER","ADMIN")
		.anyRequest()
		.authenticated();
	
		
		httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
	}
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}


