package com.proj.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.formLogin(form -> {
				form
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/")
				.permitAll()
				.successHandler(new AuthenticationSuccessHandler() {
				     
				    @Override
				    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				            Authentication authentication) throws IOException, ServletException {
				         
				        System.out.println("Logged user: " + authentication.getName());
				         
				        response.sendRedirect("/");
				    }
				});
			})
			.oauth2Login(form -> {
				form
				.loginPage("/login")
		        .userInfoEndpoint()
			    .userService(principalOauth2UserService);
			})
			.logout()
			.logoutSuccessUrl("/login")
			.logoutSuccessHandler(new LogoutSuccessHandler() {
			                    @Override
			                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
			                            Authentication authentication) throws IOException, ServletException {
			                        System.out.println("User logged out: " + authentication.getName());
			                        response.sendRedirect("/login");
			                    }
			                })
			;
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails admin = User.builder().username("admin")
								.password(passwordEncoder().encode("admin")).roles("ADMIN")
								.build();
		return new InMemoryUserDetailsManager(admin);
	}
}
