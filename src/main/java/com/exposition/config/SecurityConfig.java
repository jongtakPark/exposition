package com.exposition.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.exposition.service.CompanyService;
import com.exposition.service.MemberService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final MemberService memberService;
	private final CompanyService companyService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.
		formLogin().loginPage("/signup/login").defaultSuccessUrl("/")
		.usernameParameter("mid")
		.passwordParameter("password")
		.failureUrl("/signup/login/error")
		.and()
		.logout().logoutUrl("signup/logout").logoutRequestMatcher(new AntPathRequestMatcher("/signup/logout")).logoutSuccessUrl("/");		
//		.and()
//		.authorizeRequests()
//		.mvcMatchers("/").permitAll() // 모든 사용자 인증없이 해당경로에 접근하도록 설정
//		.mvcMatchers("/admin/**").hasRole("ADMIN") // /admin 경로 접근자는 ADMIN Role일 경우만 접근가능하도록 설정
//		.anyRequest().authenticated(); // 나머지 경로들은 모두 인증을 요구하도록 설정
//		.and()
//		.exceptionHandling() // 인증되지 않은 사용자가 리소스에 접근하였을 때 수행되는 핸들러 등록
//		.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
	}
	
	
	//비밀번호 인코딩
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth
		.userDetailsService(memberService)
		.passwordEncoder(passwordEncoder())
		.and()
		.userDetailsService(companyService)
		.passwordEncoder(passwordEncoder());
	}
	
	
	
}
